package com.datatech.halalhubs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint.Align;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.model.Food;
import com.datatech.halalhubs.model.Order;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.utility.InternetConnection;
import com.datatech.halalhubs.view.JustifiedTextView;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import org.json.JSONException;
import org.json.JSONObject;
import network.HttpRequest;

public class ShowFoodDetailsActivity extends CustomWindow implements
        OnClickListener {

    ImageView food_pic;
    ShowFoodDetailsActivity activity = this;

    TextView textView_food_name,
            textView_unit_price, textView_quantity, txt_view_price_digit;

    JustifiedTextView txt_view_food_description;

    public final String TAG = ShowFoodDetailsActivity.class.getCanonicalName();

    Button btn_decrease, btn_increment, btn_add_to_cart;
    int quatity = 1;
    double totalAmount = 0;
    Food food;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food_details);

        // initTitleBar();

        btn_decrease = (Button) findViewById(R.id.btn_decrease);
        btn_increment = (Button) findViewById(R.id.btn_increment);
        btn_add_to_cart = (Button) findViewById(R.id.btn_add_to_cart);

        btn_increment.setOnClickListener(this);
        btn_decrease.setOnClickListener(this);
        btn_add_to_cart.setOnClickListener(this);

        textView_food_name = (TextView) findViewById(R.id.textView_food_name);
        txt_view_food_description = (JustifiedTextView) findViewById(R.id.txt_view_food_description);
        textView_unit_price = (TextView) findViewById(R.id.textView_unit_price);
        textView_quantity = (TextView) findViewById(R.id.textView_quantity);
        txt_view_price_digit = (TextView) findViewById(R.id.txt_view_price_digit);

        textView_quantity.setText(getString(R.string.quantity) + " " + quatity);
        txt_view_food_description.setLineSpacing(15);

//		AppPreferenceValues preferenceValues = new AppPreferenceValues(
//				getApplicationContext());
        // Food aFood = (Food) getIntent().getSerializableExtra("food");

        String FoodIdNo = getIntent().getStringExtra("position");

        // String FoodIdNo = "2";
        url = ApplicationData.SHOW_FOOD_DETAILS + FoodIdNo;

        if (InternetConnection.isAvailable(activity)) {

            getFoodDetails();

        } else {

            showAlert(activity);
        }

    }

    public void showAlert(final Activity activity) {

        if (InternetConnection.isAvailable(activity)) {

            getFoodDetails();

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

    private void getFoodDetails() {

        GetFoodDetailsAsync startAsync = new GetFoodDetailsAsync();
        startAsync.execute(url);

    }

    private class GetFoodDetailsAsync extends AsyncTask<String, Void, Void> {

        private static final String PRICE_PER_UNIT = "pricePerUnit";
        private static final String FOOD_DESCRIPTION = "description";
        private static final String FOOD_NAME = "name";
        private static final String FOOD_IMAGE_URL = "imageUrl";
        private ProgressDialog pDialog;

        private String TAG = "data";

        public GetFoodDetailsAsync() {
            food = new Food(Food.FOOD_ITEM);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ShowFoodDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {

            // JSONObject jObject;
            try {

                HttpRequest request = HttpRequest.get(arg0[0]);
                String JsonResponse = request.body();
                Log.e("JSONObject >>> ", "" + JsonResponse);

                JSONObject jObject = new JSONObject(JsonResponse);
                // JSONArray jsonArray = new JSONArray(JsonResponse);

                // JSONArray jsonArray = jObject.getJSONArray(TAG);
                // food = new Food(Food.FOOD_ITEM);

                // for (int j = 0; j < jsonArray.length(); j++) {

                // JSONObject jOBj = jsonArray.getJSONObject(j);

                if (jObject.has(TAG)) {

                    // JSONArray array = jObject.getJSONArray(TAG);

                    // for (int i = 0; i < array.length(); i++) {

                    JSONObject oneObject = jObject.getJSONObject(TAG);

                    food.setFoodName(oneObject.getString(FOOD_NAME));
                    food.setFoodPrice(oneObject.getString(PRICE_PER_UNIT));
                    food.setFoodDescription(oneObject
                            .getString(FOOD_DESCRIPTION));

                    String image = ApplicationData.BASE_URL
                            + oneObject.getString(FOOD_IMAGE_URL);
                    food.setFoodPictureUrl(image);

                    Log.e("Food ", "Food Name: >> " + food.getFoodName());
                    // }

                } else {

                    Log.e("Food >>> ", "nOT ENTER  !!!!");

                }

                // }

            } catch (JSONException e1) {
                // TODO Auto-generated catch block

                // Toast.makeText(getApplicationContext(), "JSONException",
                // Toast.LENGTH_LONG).show();
                //
                e1.printStackTrace();

            } catch (RuntimeException e) {

                // Toast.makeText(getApplicationContext(), "RuntimeException",
                // Toast.LENGTH_LONG).show();

                e.printStackTrace();

            } catch (Exception e) {

                // Toast.makeText(getApplicationContext(), "Exception",
                // Toast.LENGTH_LONG)
                // .show();

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            textView_food_name.setText(food.getFoodName());
            txt_view_food_description.setText(food.getFoodDescription());
            txt_view_food_description.setAlignment(Align.LEFT);

            // ApplicationData.imageLoader.displayImage(ApplicationData.BASE_URL
            // + food.getFoodPictureUrl(), food_pic);

            textView_unit_price.setText(getString(R.string.price_per_unit)
                    + ": " + food.getFoodPrice());

            txt_view_price_digit.setText(food.getFoodPrice() + "  "
                    + ApplicationData.CURRENCY);

            Log.e("Food Details Page >> ", "" + food.getFoodName());
            //
            // imageLoader.DisplayImage(url_driver, sales_driver_image);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }

    @Override
    public void onClick(View v) {

        if (v == btn_increment) {

            if (quatity < ApplicationData.MAX_NO_QUANTITY) {

                quatity++;

                textView_quantity.setText(getString(R.string.quantity) + " "
                        + quatity);

                // ;
                totalAmount = quatity * Double.parseDouble(food.getFoodPrice());

                txt_view_price_digit.setText(InternetConnection
                        .stringFormat(totalAmount)
                        + "  "
                        + ApplicationData.CURRENCY);
            }

        } else if (v == btn_decrease) {

            if (quatity > ApplicationData.MIN_NO_QUANTITY) {

                quatity--;

                textView_quantity.setText(getString(R.string.quantity) + " "
                        + quatity);

                totalAmount = quatity * Double.parseDouble(food.getFoodPrice());

                txt_view_price_digit.setText(InternetConnection
                        .stringFormat(totalAmount)
                        + "  "
                        + ApplicationData.CURRENCY);
            }

        } else if (v == btn_add_to_cart) {

            if (food != null) {

                Order aOrder = new Order();
                aOrder.setFoodName(food.getFoodName());
                aOrder.setQuatity(quatity);
                aOrder.setFoodPrice(Double.parseDouble(food.getFoodPrice()));
                ApplicationData.foodOrderList.add(aOrder);

                doIncrease();
                finish();

            }

            // aOrder.

//            Toast.makeText(getApplicationContext(), "One item has added to your cart..",
//                    Toast.LENGTH_SHORT).show();

            SuperActivityToast.create(activity, getString(R.string.add_to_cart_msg),
                    SuperToast.Duration.SHORT, Style.getStyle(Style.GRAY, SuperToast.Animations.FLYIN)).show();

        }

    }

    @Override
    protected void onResume() {

        super.onResume();
        doIncrease();

    }

}
