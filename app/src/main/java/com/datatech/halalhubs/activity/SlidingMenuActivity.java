package com.datatech.halalhubs.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.datatech.halalhubs.R;
import com.mikhaellopez.circularimageview.CircularImageView;




/*
 * Opening splash screen menu
 */

public class SlidingMenuActivity extends Activity {

    private static final int SPLASH_DISPLAY_TIME = 2000; // splash screen delay time

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sliding_menu);

        CircularImageView circularImageView = (CircularImageView) findViewById(R.id.profile_pic);
        // Set Border
        circularImageView.setBorderColor(getResources().getColor(R.color.grey_light));
        circularImageView.setBorderWidth(10);
        // Add Shadow with default param
        circularImageView.addShadow();
        // or with custom param
        circularImageView.setShadowRadius(15);
        circularImageView.setShadowColor(Color.RED);


    }

}
