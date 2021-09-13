package com.example.betterlife;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<Comments> localDataSet;
    final String TAG = "CommentsAdapterdebug";
    Handler mHandler = new Handler() ;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView comm;
        private final TextView postcount;
        private final TextView username;
        private final ImageView profilePic;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            comm = (TextView) view.findViewById(R.id.tv_rvc_comment);
            postcount = (TextView) view.findViewById(R.id.tv_rvc_postcount) ;
            username = (TextView) view.findViewById(R.id.tv_rvc_username);
            profilePic = (ImageView)  view.findViewById(R.id.iv_rvc_profile);
        }

        public TextView getComm() {
            return comm;
        }

        public TextView getUsername() {
            return username;
        }
        public TextView getPostcount() {
            return postcount;
        }
        public ImageView getProfilePic() {
            return profilePic;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CommentsAdapter(ArrayList<Comments> dataSet ) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comments_row_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getComm().setText( localDataSet.get(position).comm );
        viewHolder.getUsername().setText(localDataSet.get(position).user);
        String pc = localDataSet.get(position).profilePicture;
        String s = localDataSet.get(position).postCount;
        int c = 0;
        if(s!=null){
            c = s.split(",").length;
        }
        viewHolder.getPostcount().setText(c + " posts");
        if(!pc.equals("")){
            new loadImage(pc, viewHolder.getProfilePic() ).start();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

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





