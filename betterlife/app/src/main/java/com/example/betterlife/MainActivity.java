package com.example.betterlife;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        String [] a = { "abc","abc","abc","abc","abc","abc","abc","abc","abc","abc"};


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        ArrayList<Posts> al = new ArrayList<Posts>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot dsp: dataSnapshot.getChildren() ){
//                    Log.d("RupelTAG", "Value is: " + String.valueOf(dsp.getKey()));
                    Posts value = dataSnapshot.child(String.valueOf(dsp.getKey()) ).getValue(Posts.class);
                    al.add(value);
                }
                rv.setAdapter(new postsAdapter(al));
//                Posts value = dataSnapshot.getValue(Posts.class);
                Log.d("RupelTAG", "Value is: " + al.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        Log.d("tupel", String.valueOf(al.size()) );
        for(Posts p: al){
            Log.d("postru" , p.title );
        }


//        writeNewPost("rupel","title of this", "pothole" , "this is the description" );
    }

    public void writeNewPost( String username, String title, String post_type, String description ) {
        Posts post = new Posts(username, title , post_type , description);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("posts");
        String postId = postRef.push().getKey();
        postRef.child(postId).setValue(post);
    }
}