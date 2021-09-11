package com.example.betterlife;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class postsAdapter extends RecyclerView.Adapter<postsAdapter.ViewHolder> {

    private ArrayList<Posts> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView des;
        private final TextView confidence;
        private final TextView location;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            title = (TextView) view.findViewById(R.id.tv_title_post_ma);
            des = (TextView) view.findViewById(R.id.tv_des_ma);
            confidence = (TextView) view.findViewById(R.id.tv_rv_confidence);
            location = (TextView) view.findViewById(R.id.tv_rv_location);


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
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public postsAdapter(ArrayList<Posts> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);



        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitle().setText(localDataSet.get(position).title);
        viewHolder.getDes().setText(localDataSet.get(position).description);
        viewHolder.getConfidence().setText( "confidence: " +   localDataSet.get(position).confidence);
        viewHolder.getLocation().setText(localDataSet.get(position).lattitude + " , " + localDataSet.get(position).longitude );

        Log.d("yolopel" , localDataSet.get(position).description);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
