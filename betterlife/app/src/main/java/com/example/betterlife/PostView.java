package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class PostView extends AppCompatActivity {

    ArrayList<Comments> al;
    String tempString ;
    Uri tempUri;
    int ti;
    static final String TAG = "abcrupel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        TextView title = findViewById(R.id.tv_pv_title);
        TextView username = findViewById(R.id.tv_pv_username);
        TextView description = findViewById(R.id.tv_pv_description);
        TextView confidence = findViewById(R.id.tv_pv_confidence);
        TextView cordinates = findViewById(R.id.tv_pv_cordinates);
        TextView likes = findViewById(R.id.tv_pv_likes);
        TextView valpoints = findViewById(R.id.tv_pv_valpoints);
        TextView shares = findViewById(R.id.tv_pv_shares);
        ImageView pic = findViewById(R.id.iv_post_view_picture);


        Bundle b = this.getIntent().getExtras();
        Posts post = (Posts) b.getSerializable("post");
        title.setText(post.title);
        username.setText(post.username);
        description.setText("description: " + post.description);
        confidence.setText("confidence : " + post.confidence);
        cordinates.setText(post.lattitude + "," + post.longitude);
        int likesCount = post.likes.split(",").length;
        likes.setText( String.valueOf(likesCount) +" likes");
        valpoints.setText(String.valueOf(post.valiance) +" val points");
//        shares.setText(post.shares);




//        comment section
//        inputs
        EditText commentIn = findViewById(R.id.et_pv_comment_text);
        ImageView sendBtn = findViewById(R.id.iv_pv_comment_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                String userId = "";
                String profilePicture ="";
                if(acct !=null){
                    userId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();
                    if(personPhoto == null){
                        profilePicture = "";
                    }
                    else{
                        profilePicture = personPhoto.toString();
                    }
                }
                String temp = commentIn.getText().toString();
                commentIn.setText("");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference("users").child(userId);
                Task<DataSnapshot> task = userRef.get();
                task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        DatabaseReference postRef = database.getReference("comments").child(post.id);
                        String profilePicture = user.personPhoto;
                        String commentId = postRef.push().getKey();
                        String postCount = user.posts;
                        String [] userIdArr = user.personEmail.split("@gmail\\.c")[0].split("\\.");
                        String userId = "";
                        for(int i=0; i<userIdArr.length; i++){
                            userId += userIdArr[i];
                        }
                        Comments comment = new Comments( userId , temp , post.id , profilePicture, postCount );
                        String comm = post.id + "@" + commentId;
                        setCommentUser(acct.getId() , comm);
                        postRef.child(commentId).setValue(comment);
                    }
                });

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference comRef = database.getReference("comments").child(post.id);

        al = new ArrayList<Comments>();

        comRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(al != null){
                    al.clear();
                }
                int count = 0;
                for(DataSnapshot dsp: dataSnapshot.getChildren() ){
                    Comments value = dataSnapshot.child(String.valueOf(dsp.getKey()) ).getValue(Comments.class);
                    if(count++ >20){
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


    public void setRecyclerViewData(ArrayList<Comments>al){
        RecyclerView rvCom = findViewById(R.id.rv_pv_comments_list);
        rvCom.setLayoutManager(new LinearLayoutManager(this));
        rvCom.setAdapter(new CommentsAdapter(al));
    }

    public void setCommentUser(String username , String comment ) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Users user = new Users();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(username);
        Task<DataSnapshot> task =  userRef.get();
        task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                user.setComments(user.comments +   comment + ",");
                userRef.setValue(user);
            }
        });

    }
}