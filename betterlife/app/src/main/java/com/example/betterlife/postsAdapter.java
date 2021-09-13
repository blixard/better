package com.example.betterlife;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.lock.qual.LockPossiblyHeld;

import java.util.ArrayList;

public class postsAdapter extends RecyclerView.Adapter<postsAdapter.ViewHolder> {

    private ArrayList<Posts> localDataSet;
    private OnPostListener monPostListener;
    String musername;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView des;
        private final TextView confidence;
        private final TextView location;
        private final TextView likescount;
        private final  ImageView like;
        private final  ImageView comment;
        private final  ImageView share;
        private final ConstraintLayout cl;
        OnPostListener onPostListener;
        String username;

        public ViewHolder(View view , OnPostListener onPostListener, String username) {
            super(view);
            // Define click listener for the ViewHolder's View
            this.onPostListener = onPostListener;
            this.username = username;

            title = (TextView) view.findViewById(R.id.tv_title_post_ma);
            des = (TextView) view.findViewById(R.id.tv_des_ma);
            confidence = (TextView) view.findViewById(R.id.tv_rv_confidence);
            location = (TextView) view.findViewById(R.id.tv_rv_location);
            likescount = (TextView) view.findViewById(R.id.tv_rv_likecount);
            like = (ImageView) view.findViewById(R.id.iv_rv_likes);
            comment = (ImageView) view.findViewById(R.id.iv_rv_comments);
            share = (ImageView) view.findViewById(R.id.iv_rv_share);
            cl = (ConstraintLayout) view.findViewById(R.id.rv_main);

            view.setOnClickListener(this);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPostListener.onLikeCLick(getAdapterPosition() , getLike());
                }
            });


        }

        public TextView getTitle() {
            return title;
        }
        public TextView getDes() {
            return des;
        }
        public TextView getConfidence() {
            return confidence;
        }
        public TextView getLocation() {
            return location;
        }
        public TextView getLikescount() {
            return likescount;
        }
        public ImageView getLike() {
            return like;
        }
        public ImageView getComment() {
            return comment;
        }
        public ImageView getShare() {
            return share;
        }
        public ConstraintLayout getLayout() {
            return cl;
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }

    }

    public interface OnPostListener{
        void onPostClick(int position);

        void onLikeCLick(int position , ImageView iv);
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public postsAdapter(ArrayList<Posts> dataSet, OnPostListener onPostListener , String username) {
        localDataSet = dataSet;
        this.monPostListener = onPostListener;
        this.musername = username;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);



        return new ViewHolder(view , monPostListener, musername );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitle().setText(localDataSet.get(position).title);
        viewHolder.getDes().setText(localDataSet.get(position).description);
        viewHolder.getConfidence().setText( "confidence: " +   localDataSet.get(position).confidence);
        viewHolder.getLikescount().setText(  localDataSet.get(position).likes.split(",").length + " likes");
        viewHolder.getLocation().setText(localDataSet.get(position).lattitude + " , " + localDataSet.get(position).longitude );
        String[] arr = localDataSet.get(position).likes.split(",");
        int flag = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals(musername)){
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            viewHolder.getLike().setImageResource(R.mipmap.like_pic_foreground);
        }
        else{
            viewHolder.getLike().setImageResource(R.mipmap.like_pic_round);
        }


        Log.d("yolopel" , localDataSet.get(position).description);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
