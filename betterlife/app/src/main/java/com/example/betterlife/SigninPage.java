package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninPage extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 11;
    static final String TAG = "googleSignIn rupel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct !=null){
//
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//            String photo;
//
//            if(personPhoto == null){
//                photo = "";
//            }
//            else{
//                photo = personPhoto.toString();
//            }
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//            Users user = new Users(personName,personGivenName,personFamilyName,personEmail,personId, photo );
////            Bundle b = new Bundle();
////            b.putSerializable("user" , user);
////            intent.putExtras(b);
//
//            LottieAnimationView lv = findViewById(R.id.lv_sign_in_button);
//            lv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    lv.animate();
//                    lv.playAnimation();
//                }
//            });
//
//
////            String [] userIdArr = user.personEmail.split("@gmail\\.c")[0].split("\\.");
////            String userId = "";
////            for(int i=0; i<userIdArr.length; i++){
////                userId += userIdArr[i];
////            }
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference userRef = database.getReference("users").child(personId);
//            Task<DataSnapshot> res = userRef.get();
//            res.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//                @Override
//                public void onSuccess(DataSnapshot dataSnapshot) {
//                    Users user = dataSnapshot.getValue(Users.class);
//                    if(user == null){
//                        Log.d(TAG, "onSuccess: value is set");
//                        userRef.setValue(user);
//                    }
//                }
//            });
//
//
//            startActivity(intent);
//        }

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        // Signed in successfully, show authenticated UI.
        if (acct != null) {

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String photo;

            if(personPhoto == null){
                photo = "";
            }
            else{
                photo = personPhoto.toString();
            }

            Users user = new Users(personName,personGivenName,personFamilyName,personEmail,personId , photo );
            Bundle b = new Bundle();
            b.putSerializable("user" , user);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtras(b);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users").child(personId);
            Task<DataSnapshot> res = userRef.get();
            res.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);
                    Users u = new Users();
                    if(user == null){
                        GoogleSignInAccount acct = null;
                        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        // Signed in successfully, show authenticated UI.
                        if (acct != null) {

                            String personName = acct.getDisplayName();
                            String personGivenName = acct.getGivenName();
                            String personFamilyName = acct.getFamilyName();
                            String personEmail = acct.getEmail();
                            String personId = acct.getId();
                            Uri personPhoto = acct.getPhotoUrl();
                            String photo;

                            if (personPhoto == null) {
                                photo = "";
                            } else {
                                photo = personPhoto.toString();
                            }
                            u = new Users(personName, personGivenName, personFamilyName, personEmail, personId, photo);
                        }
                        Log.d(TAG, "onSuccess: user is set");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference userRef = database.getReference("users").child(personId);
                        userRef.setValue(u);
                    }
                }
            });

            startActivity(intent);
            finish();

            Log.d(TAG, "" + personEmail);
        }

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String photo;

                if(personPhoto == null){
                    photo = "";
                }
                else{
                    photo = personPhoto.toString();
                }

                Users user = new Users(personName,personGivenName,personFamilyName,personEmail,personId , photo );
                Bundle b = new Bundle();
                b.putSerializable("user" , user);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtras(b);

                FirebaseDatabase database = FirebaseDatabase.getInstance();

//                String [] userIdArr = user.personEmail.split("@gmail\\.c")[0].split("\\.");
//                String userId = "";
//                for(int i=0; i<userIdArr.length; i++){
//                    userId += userIdArr[i];
//                }

                DatabaseReference userRef = database.getReference("users").child(personId);
//                userRef.setValue(user);
                Task<DataSnapshot> res = userRef.get();
                res.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        Users u = new Users();
                        if(user == null){
                            GoogleSignInAccount acct = null;
                            try {
                                acct = completedTask.getResult(ApiException.class);
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                            // Signed in successfully, show authenticated UI.
                            if (acct != null) {

                                String personName = acct.getDisplayName();
                                String personGivenName = acct.getGivenName();
                                String personFamilyName = acct.getFamilyName();
                                String personEmail = acct.getEmail();
                                String personId = acct.getId();
                                Uri personPhoto = acct.getPhotoUrl();
                                String photo;

                                if (personPhoto == null) {
                                    photo = "";
                                } else {
                                    photo = personPhoto.toString();
                                }
                                u = new Users(personName, personGivenName, personFamilyName, personEmail, personId, photo);
                            }
                            Log.d(TAG, "onSuccess: user is set");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = database.getReference("users").child(personId);
                            userRef.setValue(u);
                        }
                    }
                });

                startActivity(intent);
                finish();

                Log.d(TAG, "" + personEmail);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Intent intent = new Intent( getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}