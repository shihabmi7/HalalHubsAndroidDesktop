package com.datatech.halalhubs.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.model.Order;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.view.QustomDialogBuilder;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ShowShopingDetailsActivity extends CustomWindow {

    LinearLayout dynamic_layout, dynamic_calculation;

    public final String TAG = "ShowShopingDetail";
    String[] list = {"Sub Total", "Delivery fee", "Sales tax", "Total"};
    Button btn_go_to_checklist;
    Double subTotal = 0.0;
    Double delivery_fee = 30.0;
    Double tax_rate = 7.5;
    private Double total_tax = 0.0;
    private Double total = 0.0;

    // note that these credentials will differ between live & sandbox environments.

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final String CONFIG_CLIENT_ID = "ASfZoNx7lDYMEbkWhcMfTwApBsb4uiVS_1LuRzv71naEeYOIxO2Rs4bbyiWC1N-YYxL5Mmgrfvo-X4NK";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    RadioGroup radioOrderType;
    RadioButton radioOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO SHOW all order to go CHECK OUT PAGE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping_details);

        radioOrderType = (RadioGroup) findViewById(R.id.radioGroup_OrderType);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        btn_go_to_checklist = (Button) findViewById(R.id.btn_go_to_checklist);

        btn_go_to_checklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO LOG IN TO PAYPAL & PAY
                onBuyPressed();


            }
        });

        dynamic_layout = (LinearLayout) findViewById(R.id.dynamic_layout);
        dynamic_calculation = (LinearLayout) findViewById(R.id.dynamic_calculation);

        makeFullCalculation();

        // just test
        CreateOrder();

    }

    public void makeFullCalculation() {


        if (dynamic_calculation != null) {

            clearDynamicViewsNValues();

            for (int i = 0; i < ApplicationData.foodOrderList.size(); i++) {

                dynamic_layout.addView(makeAFoodItemLayout(i));

            }

            for (int i = 0; i < list.length; i++) {


                switch (i) {
                    case Order.SUBTOTAL:

                        dynamic_calculation.addView(makeACalculationLayout(i,
                                subTotal.toString()));
                        break;

                    case Order.DELIVERY_FEE:

                        dynamic_calculation.addView(makeACalculationLayout(i,
                                delivery_fee.toString()));
                        break;

                    case Order.SALES_TAX:

                        total_tax = (subTotal * tax_rate) / 100;
                        dynamic_calculation.addView(makeACalculationLayout(i,
                                total_tax.toString()));
                        break;

                    case Order.TOTAL:

                        total = subTotal + delivery_fee + total_tax;
                        dynamic_calculation.addView(makeACalculationLayout(i,
                                total.toString()));
                        break;
                }

            }
        }


    }



    private void clearDynamicViewsNValues() {
        dynamic_calculation.removeAllViewsInLayout();
        dynamic_layout.removeAllViewsInLayout();
        subTotal = 0.0;
        total_tax = 0.0;
        total = 0.0;
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {

        return new PayPalPayment(new BigDecimal(total), "USD", "Food Order Price",
                paymentIntent);
    }


    /**
     * @param i
     * @return
     */
    public RelativeLayout makeAFoodItemLayout(final int i) {


        Order aOrder = ApplicationData.foodOrderList.get(i);

        double item_price = (aOrder.getQuantity() * aOrder.getFoodPrice());
        subTotal += item_price;

        // Create new Layout
        final RelativeLayout primary_layout = new RelativeLayout(this);
        LayoutParams layoutParam = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        primary_layout.setLayoutParams(layoutParam);
        // primary_layout.setOrientation(LinearLayout.HORIZONTAL);
        // primary_layout.setBackgroundColor(0xff99ccff);
        //String cross = " � ";

        String makeString = aOrder.getQuantity() + " "
                + aOrder.getFoodName();


        ImageView imageView_remove = createAImageview(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, RelativeLayout.CENTER_VERTICAL,
                10, 20);

        TextView item_name = createATextViewWithParam(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, RelativeLayout.CENTER_HORIZONTAL, imageView_remove.getId(),
                makeString, 20, 10, 20);

        TextView txt_item_price = createATextView(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, RelativeLayout.ALIGN_PARENT_RIGHT,
                "" + item_price, 20, 10, 20);

        primary_layout.addView(imageView_remove);
        primary_layout.addView(item_name);
        primary_layout.addView(txt_item_price);
        imageView_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    QustomDialogBuilder qustomDialogBuilder = new QustomDialogBuilder(activity).
                            setTitle("Warning").
                            setTitleColor("#F7931D").
                            setDividerColor("#F7931D").setMessage("You want to remove this item?").setIcon(getResources().getDrawable(R.drawable.ic_launcher));

                    qustomDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    if (ApplicationData.foodOrderList.size() > 0) {

                                        ApplicationData.foodOrderList.remove(i);
                                        dynamic_layout.removeView(primary_layout);
                                        SuperActivityToast.create(activity, "Succesfully removed", Toast.LENGTH_LONG, SuperToast.Animations.FLYIN).show();

                                        if (ApplicationData.foodOrderList.size() == 0) {
                                            onBackPressed();
                                        } else {
                                            makeFullCalculation();
                                        }

                                        // update the notification icon successfully done
                                        doIncrease();
                                    }
                                }
                            }

                    );

                    qustomDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()

                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Toast.makeText(activity, "Neg", Toast.LENGTH_LONG).show();

                                }
                            }

                    );

                    qustomDialogBuilder.show();


                } catch (ArrayIndexOutOfBoundsException e) {

                    e.printStackTrace();
                }

            }
        });


        return primary_layout;
    }

    /**
     * @param i
     * @return
     */
    public RelativeLayout makeACalculationLayout(int i, String value) {
        // Create new LinearLayout
        RelativeLayout primary_layout = new RelativeLayout(this);

        LayoutParams layoutParam = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        primary_layout.setLayoutParams(layoutParam);

        // FOR LINEAR LAYOUT SET ORIENTATION
        // primary_layout.setOrientation(LinearLayout.HORIZONTAL);

        // FOR BACKGROUND COLOR
        // primary_layout.setBackgroundColor(0xff99ccff);

        primary_layout.addView(createATextView(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, RelativeLayout.ALIGN_LEFT, list[i],
                20, 10, 20));
        primary_layout.addView(createATextView(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, RelativeLayout.ALIGN_PARENT_RIGHT,
                value, 20, 10, 20));
        return primary_layout;
    }

    public TextView createATextView(int layout_width, int layout_height, int align,
                                    String text, int fontSize, int margin, int padding) {

        TextView textView_item_name = new TextView(this);

        // LayoutParams layoutParams = new LayoutParams(
        // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // layoutParams.gravity = Gravity.LEFT;
        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(
                layout_width, layout_height);

        _params.setMargins(margin, margin, margin, margin);
        _params.addRule(align);
        textView_item_name.setLayoutParams(_params);

        textView_item_name.setText(text);
        textView_item_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textView_item_name.setTextColor(Color.parseColor("#000000"));
        // textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        textView_item_name.setPadding(padding, padding, padding, padding);

        return textView_item_name;

    }

    public TextView createATextViewWithParam(int layout_widh, int layout_height, int align, int align_id,
                                             String text, int fontSize, int margin, int padding) {

        TextView textView_item_name = new TextView(this);

        // LayoutParams layoutParams = new LayoutParams(
        // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // layoutParams.gravity = Gravity.LEFT;
        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(
                layout_widh, layout_height);

        _params.setMargins(margin, margin, margin, margin);
        _params.addRule(align, align_id);
        textView_item_name.setLayoutParams(_params);

        textView_item_name.setText(text);
        textView_item_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textView_item_name.setTextColor(Color.parseColor("#000000"));
        // textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        textView_item_name.setPadding(padding, padding, padding, padding);

        return textView_item_name;

    }

    public ImageView createAImageview(int layout_width, int layout_height, int align,
                                      int margin, int padding) {

        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(
                layout_width, layout_height);

        _params.setMargins(margin, margin, margin, margin);
        _params.addRule(align);
        imageView.setLayoutParams(_params);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setImageResource(R.mipmap.remove);
        return imageView;

    }

    public void onBuyPressed() {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(ShowShopingDetailsActivity.this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {

            if (resultCode == Activity.RESULT_OK) {

                try {
                    PaymentConfirmation confirm =
                            data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                    Log.i(TAG, confirm.toJSONObject().toString(4));
                    Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                    if (confirm != null) {

                        SuperActivityToast.create(activity, "Successfully Your Order Received...",
                                SuperToast.Duration.SHORT, Style.getStyle(Style.GRAY, SuperToast.Animations.POPUP)).show();
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */

                        clearOrderList();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                } catch (Exception e) {
                }


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        doIncrease();

    }

    public String getTotal() {
        return total.toString();
    }

    // TODO WE SHOULD HIT THE GETORDERAPI
    private void CreateOrder() {


//        "orderType": "", ok
//        "orderDate":, ok
//        "requireDate":, ok
//        "deliveryDate":, will get later
//        "deliveryAddress": "", ok
//        totalAmount":, ok

        HashMap<String, String> params = new HashMap<String, String>();
        // get radio button value
        int selectedId = radioOrderType.getCheckedRadioButtonId();
        radioOrderButton = (RadioButton) findViewById(selectedId);

        // get current time
        String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        Datetime = dateformat.format(c.getTime());
        Toast.makeText(activity, "" + Datetime + "\n"+radioOrderButton.getText().toString(), Toast.LENGTH_LONG).show();


        params.put(new String("orderType"), radioOrderButton.getText().toString());
        params.put(new String("orderDate"), "");
        params.put(new String("requireDate"), "");
        params.put(new String("deliveryDate"), "");
        params.put(new String("deliveryAddress"), "");
        params.put(new String("totalAmount"), getTotal());

//        GetOrderResponseFromAPI orderResponseFromAPI=new GetOrderResponseFromAPI(activity,true,params);
//        orderResponseFromAPI.setListener(new CustomListener() {
//            @Override
//            public void ModificationMade() {
//
//            }
//        });
//        orderResponseFromAPI.execute();

    }

}
