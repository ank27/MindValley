package com.mindvalley_ankur_khandelwal_android_test.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.ui.NetworkImageView;
import com.mindvalley_ankur_khandelwal_android_test.Model.PinModel;
import com.mindvalley_ankur_khandelwal_android_test.R;
import com.mindvalley_ankur_khandelwal_android_test.Utils.OnItemClickListner;
import com.mindvalley_ankur_khandelwal_android_test.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

public class MyPinsAdapter extends RecyclerView.Adapter<MyPinsAdapter.ViewHolder> {
    ArrayList<PinModel> pinsArrayList;
    Activity activity;
    LayoutInflater mInflater;
    public static OnItemClickListner itemClickListner;

    public MyPinsAdapter(ArrayList<PinModel> pinsArrayList, Activity activity) {
        this.pinsArrayList = pinsArrayList;
        this.activity = activity;
        mInflater = ((LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public MyPinsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.single_pin_layout, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(MyPinsAdapter.ViewHolder holder, int position) {
        PinModel singlePinModel=pinsArrayList.get(position);
        holder.likeCount.setText(String.valueOf(singlePinModel.likes));
        ImageLoader imageLoader = VolleySingleton.getImageLoader();
        imageLoader.get(singlePinModel.urls.small, ImageLoader.getImageListener(holder.pinImage, Color.parseColor(singlePinModel.color), Color.parseColor(singlePinModel.color)));
        holder.categoryLayout.removeAllViews();
        if(singlePinModel.categories.size()>0){
            View category_view;
            for(int i=0;i<singlePinModel.categories.size();i++){
                category_view=mInflater.inflate(R.layout.single_category_layout,null);
                TextView category=(TextView) category_view.findViewById(R.id.category);
                category.setText(singlePinModel.categories.get(i).title);
                holder.categoryLayout.addView(category_view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return pinsArrayList.size();
    }


    public void clear() {
        pinsArrayList.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public CardView cardView;
        public ImageView pinImage;
        public RelativeLayout imageLayout;
        public TextView likeCount;
        public LinearLayout categoryLayout;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            cardView=(CardView) itemLayoutView.findViewById(R.id.cardView);
            pinImage=(NetworkImageView) itemLayoutView.findViewById(R.id.pinImage);
            imageLayout=(RelativeLayout) itemLayoutView.findViewById(R.id.imageLayout);
            likeCount=(TextView) itemLayoutView.findViewById(R.id.likeCount);
            categoryLayout=(LinearLayout) itemLayoutView.findViewById(R.id.categoryLayout);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListner.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListner clickListener) {
        itemClickListner = clickListener;
    }
}
