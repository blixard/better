package com.example.betterlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterlife.ml.Model;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

public class Pothole extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static List<Category> probability;
    double latitude;
    double longitude;
    public static final int PERMISSION_ID = 44;

    TextView latTextView, lonTextView;
    String lat_f , lon_f ;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pothole);
        EditText etTitle = (EditText) findViewById(R.id.et_pothole_report_title);
        EditText etDes = (EditText) findViewById(R.id.et_pothole_des);
        Button btnCam = (Button) findViewById(R.id.btn_pothole_camera);
        Button btnSubmit = (Button) findViewById(R.id.btn_pothole_submit);
        latTextView = findViewById(R.id.tv_pothole_lattitude);
        lonTextView = findViewById(R.id.tv_pothole_longitude);


        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                if(title.equals("")){
                    title = "Nothing entered";
                }
                String des = etDes.getText().toString();
                if(des.equals("")){
                    des = "Nothing entered";
                }
                double confidence = -1;
                if(probability!=null ){
                    confidence = Double.valueOf(probability.get(1).getScore());
                }

                writeNewPost("user", title,"pothole", des, confidence , lat_f , lon_f);

                Intent intent = new Intent(getApplicationContext() , AfterSubmission.class);
                startActivity(intent);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.iv_pothole);
            imageView.setImageBitmap(imageBitmap);
            try {
                Model model = Model.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorImage image = TensorImage.fromBitmap(imageBitmap);

                // Runs model inference and gets result.
                Model.Outputs outputs = model.process(image);
                probability = outputs.getProbabilityAsCategoryList();

                TextView tvRes = findViewById(R.id.tv_pothole_1);
                tvRes.setText( "report confidence : " + probability.get(1).getScore() +"\ntake clear picture for better confidence");
                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }
        }
    }

    public void writeNewPost( String username, String title, String post_type, String description, double confidence, String lattitude, String longitude) {
        Posts post = new Posts(username, title, post_type, description, confidence, lattitude, longitude);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("posts");
        String postId = postRef.push().getKey();
        postRef.child(postId).setValue(post);
    }


//    location stuff
private boolean checkPermissions() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        return true;
    }
    return false;
}


    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }





    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @SuppressLint("MissingPermission")

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            lat_f  = String.valueOf(location.getLatitude());
                            lon_f  = String.valueOf(location.getLongitude());

                            latTextView.setText("Lattitude: " +lat_f + "");
                            lonTextView.setText("Longitude: "+ lon_f + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat_f  = String.valueOf(mLastLocation.getLatitude());
            lon_f  = String.valueOf(mLastLocation.getLongitude());
            latTextView.setText("Lattitude: " +mLastLocation.getLatitude() + "");
            lonTextView.setText("Longitude: "+ mLastLocation.getLongitude() + "");
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }


    }

}