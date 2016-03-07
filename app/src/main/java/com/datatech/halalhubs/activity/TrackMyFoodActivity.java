package com.datatech.halalhubs.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.adpater.RestaurentListAdapter;
import com.datatech.halalhubs.model.Restaurant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

public class TrackMyFoodActivity extends CustomWindow implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    TrackMyFoodActivity activity = this;
    private RestaurentListAdapter resAdapter;
    ArrayList<Restaurant> resList = new ArrayList<Restaurant>();

    TextView restaurent_name, textView_restaurent_address, restaurent_open,
            restaurent_close;
    ImageView imageView_restaurent_pic;
    // ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    Context context = this;
    ;
    LinearLayout linear_restaurent_info;
    private View relative_restaurent_info;
    Button button_show_menu;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location mLastLocation;
    private final String LOG_TAG = "LaurenceTestApp";
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onResume() {

        super.onResume();
        doIncrease();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);

        try {

            //int position = getIntent().getIntExtra("position", 0);

//            imageView_restaurent_pic = (ImageView) findViewById(R.id.imageView_menu);
//            restaurent_name = (TextView) findViewById(R.id.menu_name);
//            textView_restaurent_address = (TextView) findViewById(R.id.restaurent_address);
//            restaurent_open = (TextView) findViewById(R.id.restaurent_open);
//            restaurent_close = (TextView) findViewById(R.id.restaurent_close);
//            button_show_menu = (Button) findViewById(R.id.button_show_menu);
//
//            button_show_menu.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
////					startActivity(new Intent(activity,
////							DemoRestaurentFoodListActivity.class));
//
//                }
//            });

            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            buildGoogleApiClient();


        } catch (RuntimeException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /*
     * Put marker on the map
     *
     * @param Lat   Lon where to place the marker
     * @param Title on the marker
     */

   /* public void putMarker(LatLng latlng,  String description) {

        String snippet = description;

        if (isOnline()) {
            // map.clear();
            Marker marker = mMap.addMarker(new MarkerOptions().position(latlng)
                    .snippet(snippet).visible(true));

            // Set marker position as default to use
            mCurrentLocation = latlng;
            mCurrentMarket = marker;

            if (description != null) {

                marker.setTitle(snippet);
                marker.showInfoWindow();

                //hashMap.put(marker, branch);

            } else {
                getArea(latlng, marker);
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 9));
        }

    }*/

    /*
     * Get location name by Lat Lon
     *
     * @param arg0
     * @param m
     */
   /* public void getArea(LatLng arg0, final Marker m) {

        final Get_LocationName_By_GeoLocation areaName = new Get_LocationName_By_GeoLocation(
                this, true);
        areaName.setData(arg0);
        areaName.setListener(new CustomListener() {

            @Override
            public void ModificationMade() {
                m.setTitle(areaName.result);
                m.showInfoWindow();
            }
        });
        areaName.execute();

    }*/

    @Override
    public boolean onMyLocationButtonClick() {

        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {

        /* you can check & add manifest  permission settings here

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission to access the location is missing.

            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

        */
        if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {

        // set which Service API you used
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    protected void onStop() {

        super.onStop();
        // Disconnecting the client invalidates it.
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.i(LOG_TAG, "GoogleApiClient connection has failed");

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, location.toString());

//		updating_location.setText("Locaton Updating: " + location.getLatitude() + ","
//				+ location.getLongitude());

    }

    @Override
    public void onConnected(Bundle connectionHint) {
    /*          use when you need update location specific time interval


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    */

        // Provides a simple way of getting a device's location and is well
        // suited for
        // applications that do not require a fine-grained location and that do
        // not need location
        // updates. Gets the best and most recent location currently available,
        // which may be null
        // in rare cases when a location is not available.

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {


            LatLng currentLatLong = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(currentLatLong).snippet("Current Location").visible(true));

            marker.setTitle("Current Point");
            marker.showInfoWindow();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 14));

			/* txtOutput.setText("Last Known Location:"+String.valueOf(mLastLocation.getLatitude() + ","
                    + String.valueOf(mLastLocation.getLongitude()))); */
            // mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            // reloadData();

            return true;
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Please check your connectivity.")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    // finish();
                                    Intent startMain = new Intent(
                                            Intent.ACTION_MAIN);
                                    startMain.addCategory(Intent.CATEGORY_HOME);
                                    startMain
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(startMain);
                                    android.os.Process
                                            .killProcess(android.os.Process
                                                    .myPid());
                                }

                            })
                    .setNegativeButton("Retry",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    isOnline();

                                }

                            }).show();
        }
        return false;
    }
}
