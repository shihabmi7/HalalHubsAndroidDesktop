package com.datatech.halalhubs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.adpater.RestaurentListAdapter;
import com.datatech.halalhubs.model.Restaurant;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.utility.InternetConnection;

import java.util.ArrayList;

import network.CustomListener;
import network.GetNearestRestaurentFromAPI;

public class RestaurentListActivity extends CustomWindow {

    RestaurentListActivity activity = this;
    Context context = this;
    // TODO GET THE LIST FROM HALAL HUBS : DONE
    // TODO SET THE LIST ITEM LAYOUT & ADAPTER : DONE
    // TODO SHOW LIST : DONE

    ListView list_view;
    private RestaurentListAdapter resAdapter;
    ArrayList<Restaurant> resList = new ArrayList<Restaurant>();
    private String postCode = "";

    @Override
    protected void onResume() {
        super.onResume();
        doIncrease();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_list);

        // // TODO: 8/15/2016 i just try to add what happen if add slideing menu here  
        initializeSlidingMenu();

        list_view = (ListView) findViewById(R.id.list_view);
        postCode = getIntent().getStringExtra(ApplicationData.POST_CODE);

        try {

            list_view.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    startActivity(new Intent(activity,
                            RestaurentViewActivity.class).putExtra("position",
                            position));

                }
            });

            if (InternetConnection.isAvailable(activity)) {

                getRestaurentListFromAPI(postCode);

            } else {
                showAlert(activity);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void getRestaurentListFromAPI(String postCode) {

        GetNearestRestaurentFromAPI api = new GetNearestRestaurentFromAPI(
                activity, true, postCode, "");

        api.setListener(new CustomListener() {

            @Override
            public void ModificationMade() {

                resList = ApplicationData.restaurantList;
                resAdapter = new RestaurentListAdapter(context,
                        resList);

                list_view.setAdapter(resAdapter);

            }
        });

        api.execute();
    }

    public void showAlert(final Activity activity) {

        if (InternetConnection.isAvailable(activity)) {

            getRestaurentListFromAPI(postCode);

        } else {

            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Please check your connectivity.")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    InternetConnection
                                            .ExitApplication(activity);

                                }

                            })
                    .setNegativeButton("Retry",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    showAlert(activity);

                                }

                            }).show();
        }

    }

}
