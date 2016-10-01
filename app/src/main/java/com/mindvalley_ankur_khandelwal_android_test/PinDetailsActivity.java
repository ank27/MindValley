package com.mindvalley_ankur_khandelwal_android_test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mindvalley_ankur_khandelwal_android_test.Model.PinModel;
import com.mindvalley_ankur_khandelwal_android_test.Utils.Logger;
import com.mindvalley_ankur_khandelwal_android_test.Utils.VolleySingleton;


public class PinDetailsActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorDetail;
    CollapsingToolbarLayout collapsingToolbar;
    Activity activity;
    PinModel selectedPin;
    String TAG="PinDetailActivity";
    ImageView pinDetailImage,linkImage;
    TextView username,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        activity=this;
        coordinatorDetail=(CoordinatorLayout) findViewById(R.id.coordinatorDetail);
        pinDetailImage=(ImageView) findViewById(R.id.pinDetailImage);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Pin Detail");
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        linkImage=(ImageView) findViewById(R.id.linkImage);
        username=(TextView) findViewById(R.id.username);
        category=(TextView) findViewById(R.id.category);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectedPin = PinterestApplication.selectedPinModels;
        Logger.d(TAG,"selectedPinId "+selectedPin.id);
        username.setText(selectedPin.user.username);
        String categories="";
        for(int i=0;i<selectedPin.categories.size();i++){
             categories+=selectedPin.categories.get(i).title+", ";
        }
        category.setText(categories.substring(0,categories.length()-1));

        loadPinImage();
    }

    private void loadPinImage() {
        ImageLoader imageLoader = VolleySingleton.getImageLoader();
        imageLoader.get(selectedPin.urls.regular, ImageLoader.getImageListener(pinDetailImage, R.drawable.ic_empty, R.drawable.ic_empty));

        imageLoader.get(selectedPin.links.html, ImageLoader.getImageListener(linkImage, R.drawable.ic_empty, R.drawable.ic_empty));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
