package com.example.betterlife;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.window.SplashScreen;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements postsAdapter.OnPostListener {

    ArrayList<Posts> al;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(acct !=null){
            username = acct.getId();
        }

        ConstraintLayout clMenu = (ConstraintLayout)findViewById(R.id.cl_1_menu);
        ConstraintLayout clReport = (ConstraintLayout)findViewById(R.id.cl_2_report);
        ConstraintLayout clPothole = (ConstraintLayout)findViewById(R.id.cl_3_pothole);
        ConstraintLayout clGarbage = (ConstraintLayout)findViewById(R.id.cl_4_garbage);



        clMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                startActivity(intent);
            }
        });

        clReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report.class);
                startActivity(intent);
            }
        });

        clPothole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pothole.class);
                startActivity(intent);
            }
        });

        clGarbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Garbage.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        al = new ArrayList<Posts>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                al.clear();
                int count = 0;
                for(DataSnapshot dsp: dataSnapshot.getChildren() ){
                    Posts value = dataSnapshot.child(String.valueOf(dsp.getKey()) ).getValue(Posts.class);
                    if(count++ >30){
                        break;
                    }
                    al.add(value);
                }

                setRecyclerViewData(al);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onPostClick(int position) {
        Log.d("TAGrupel", "onPostClick: clicked.");
        Posts p = al.get(position);
        Intent intent = new Intent(getApplicationContext(), PostView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post" , p );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLikeCLick(int position , ImageView iv) {
        Posts p = al.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("posts");
        String temp = p.likes;
        String [] arr = temp.split(",");
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userID="";
        if(acct !=null){
            userID= acct.getId();
        }
        int flag = 0;
        String str = "";
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals(userID)){
                flag = 1;
                continue;
            }
            str += arr[i] + ",";
        }

        if(flag == 0){
            iv.setImageResource(R.mipmap.like_pic);
            str = str + userID;
            p.setLikes(str);
            postRef.child(p.id).setValue(p);
        }
        else{
            if(str!=""){
                str = str.substring(0, str.length()-1);
            }
            iv.setImageResource(R.mipmap.like_pic_foreground);
            p.setLikes(str);
            postRef.child(p.id).setValue(p);
        }
    }

    public void setRecyclerViewData(ArrayList<Posts> al){
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new postsAdapter(al, this, username));
    }

//    public void writeNewPost( String username, String title, String post_type, String description ) {
//        Posts post = new Posts(username, title , post_type , description);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference postRef = database.getReference("posts");
//        String postId = postRef.push().getKey();
//        postRef.child(postId).setValue(post);
//    }
}