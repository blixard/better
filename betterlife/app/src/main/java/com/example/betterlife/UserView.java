package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.ArrayList;

public class UserView extends AppCompatActivity implements postsAdapter.OnPostListener {

    ArrayList<Posts> alPosts = new ArrayList<Posts>();
    ArrayList<Comments> alComments = new ArrayList<Comments>();
    Posts pTemp = new Posts();
    Handler mHandler = new Handler();
    final static String TAG = "UserView shubha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        ImageView ivProfilePic = findViewById(R.id.iv_user_profilepic);
        TextView tvUsername = findViewById(R.id.tv_user_username);
        TextView tvName = findViewById(R.id.tv_user_fullname);
        RecyclerView rvPosts = findViewById(R.id.rv_user_posts);
        RecyclerView rvComments = findViewById(R.id.rv_user_comments);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct !=null) {

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

            String [] userIdArr = personEmail.split("@gmail\\.c")[0].split("\\.");
            String username = "";
            for(int i=0; i<userIdArr.length; i++){
                username += userIdArr[i];
            }

            tvUsername.setText(username);
            tvName.setText(personName);
            if(!photo.equals("")){
                new loadImage( photo, ivProfilePic ).start();
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users").child(personId);
            Task<DataSnapshot> task = userRef.get();
            task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if(!user.posts.equals("")){
                        String [] arr = user.posts.split(",");
                        for(int i=0; i<arr.length; i++){
                            getPostsList(arr[i]);
                        }
                    }

                    if(!user.comments.equals("")){
                        String [] arr = user.comments.split(",");
                        for(int i=0; i<arr.length; i++){
                            getCommentsList(arr[i]);
                        }
                    }
                }
            });

        }
    }

//    sets the posts in the list
    public void getPostsList(String postId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("posts").child(postId);
        Task<DataSnapshot> task = userRef.get();
        task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Posts p = dataSnapshot.getValue(Posts.class);
                Log.d(TAG, "onSuccess: " + p);
                setPostsList(p);
            }
        });

    }

    public void setPostsList (Posts p){
        alPosts.add(p);
        setRecyclerViewPosts();
        Log.d(TAG, "setPostsList: " + alPosts );
    }

    public void setRecyclerViewPosts(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String username = "";
        if(acct !=null) {
            username = acct.getId();
        }
        Log.d(TAG, "setRecyclerViewPosts: " + alPosts);
        RecyclerView rvPosts = findViewById(R.id.rv_user_posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(new postsAdapter(alPosts ,this, username  ) );
    }

    //    sets the posts in the list
    public void getCommentsList(String commentId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("comments").child(commentId.split("@")[0]).child(commentId.split("@")[1]);
        Task<DataSnapshot> task = userRef.get();
        task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Comments comment = dataSnapshot.getValue(Comments.class);
                Log.d(TAG, "onSuccess: " + comment);
                setCommentList(comment);
            }
        });

    }

    public void setCommentList (Comments comment){
        alComments.add(comment);
        setRecyclerViewComments();
        Log.d(TAG, "setPostsList: " + alComments );
    }

    public void setRecyclerViewComments(){
        Log.d(TAG, "setRecyclerViewPosts: " + alPosts);
        RecyclerView rvPosts = findViewById(R.id.rv_user_comments);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(new CommentsAdapter(alComments) );
    }

    @Override
    public void onPostClick(int position) {
        Log.d("TAGrupel", "onPostClick: clicked.");
        Posts p = alPosts.get(position);
        Intent intent = new Intent(getApplicationContext(), PostView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post" , p );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLikeCLick(int position, ImageView iv) {
        Posts p = alPosts.get(position);
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

    //thread to load image
    class loadImage extends Thread{
        String url;
        Bitmap bitmap;
        ImageView imageView;

        loadImage(String url , ImageView imageView){
            this.url = url;
            this.imageView = imageView;

        }

        public void run(){

            InputStream inputStream = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
//            Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        }


    }

}