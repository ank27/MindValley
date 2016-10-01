package com.mindvalley_ankur_khandelwal_android_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindvalley_ankur_khandelwal_android_test.Adapter.MyPinsAdapter;
import com.mindvalley_ankur_khandelwal_android_test.Model.PinModel;
import com.mindvalley_ankur_khandelwal_android_test.Utils.Logger;
import com.mindvalley_ankur_khandelwal_android_test.Utils.OnItemClickListner;
import com.mindvalley_ankur_khandelwal_android_test.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MyPinActivity will load all pins from url and show in a gridview
 */
public class MyPinActivity extends AppCompatActivity {
    RecyclerView recyclerStaggered;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;
    String pinUrl = "http://pastebin.com/raw/wgkJgazE";
    String TAG="PinActivity";
    ProgressBar progressBar;
    MyPinsAdapter pinsAdapter;
    Activity activity;
    ArrayList<PinModel> pinModels;
    static boolean is_swipe_refresh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Pins");
        toolbar.setNavigationIcon(R.drawable.ic_pinterest);
        activity=this;
        recyclerStaggered = (RecyclerView) findViewById(R.id.recyclerStaggered);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabUser);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        final GridLayoutManager layoutManager= new GridLayoutManager(activity, 2);
        recyclerStaggered.setHasFixedSize(true);
        recyclerStaggered.setLayoutManager(layoutManager);
        loadPinData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadPinData();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);

        recyclerStaggered.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeRefreshLayout.setEnabled(true);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Profile Coming soon...", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

    private void loadPinData() {
        if(!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setVisibility(View.GONE);
        RequestQueue requestQueue = VolleySingleton.getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, pinUrl, null, createReqSuccessListener(), createReqErrorListener());
        requestQueue.add(jsonArrayRequest);
    }


    private Response.Listener<JSONArray> createReqSuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonResponse) {
                Logger.d(TAG,swipeRefreshLayout.isRefreshing()+"");
                Logger.d(TAG,"jsonResponse "+jsonResponse);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                pinModels= new ArrayList<>(Arrays.asList(gson.fromJson(jsonResponse.toString(), PinModel[].class)));
                pinsAdapter = new MyPinsAdapter(pinModels, activity);
                recyclerStaggered.setAdapter(pinsAdapter);
                recyclerStaggered.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                pinsAdapter.setOnItemClickListener(new OnItemClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                        PinterestApplication.selectedPinModels=pinModels.get(position);
                        Intent detailsIntent=new Intent(activity,PinDetailsActivity.class);
                        startActivity(detailsIntent);
                    }
                });
            }
        };
    }


    private Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                recyclerStaggered.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error while fetching...swipe down to load again!!!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
