package com.datatech.halalhubs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.datatech.halalhubs.R;

/*
 * Opening splash screen menu
 */

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_DISPLAY_TIME = 2000; // splash screen delay time

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, LogInActivity.class);

                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();

                // transition from splash to main menu
                overridePendingTransition(R.anim.fadein,
                        R.anim.fadeout);

            }
        }, SPLASH_DISPLAY_TIME);
    }

}
