package com.datatech.halalhubs.activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.view.LoginView;
import com.datatech.halalhubs.view.RegisterView;
import com.datatech.halalhubs.view.SocialLoginFragmentShihab;


public class LogInActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    static Context context;
    private static ProgressDialog pd;
    boolean isFormLogin;
    Button btnSignupOrLogin;
    Button btnForgotPass;

    public static void showProgress(String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    public static void hideProgress() {
        pd.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        context = this;
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        homeAsUpByBackStack();

        /*
         Initialize social button container
        */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_socialbtn_container, new SocialLoginFragmentShihab())
                    .commit();
        }

        //Signup or login button
        btnSignupOrLogin = (Button) findViewById(R.id.btn_signup_login);
        btnSignupOrLogin.setOnClickListener(this);

        //Forgot password button
        btnForgotPass = (Button) findViewById(R.id.btn_forgot_pass);
//        btnForgotPass.setOnClickListener(listenerButtonSignup);

        //Default view
        loadLoginView();

        // test shihab
        Log.e("Main Activity", "onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Main Activity", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main Activity", "onResume");
    }

        /*
           Load everytime actiivty created
        */
    void loadLoginView() {

        isFormLogin = true;
        btnForgotPass.setVisibility(View.VISIBLE);

        FragmentTransaction fragment_transaction = getFragmentManager()
                .beginTransaction();
        fragment_transaction.replace(R.id.fragmentcontainer, LoginView.newInstance());
        fragment_transaction.addToBackStack(null);
        fragment_transaction.commit();
    }

    /*
      called when user click to register view
    */
    void loadRegisterView() {

        isFormLogin = false;
        btnForgotPass.setVisibility(View.INVISIBLE);

        FragmentTransaction fragment_transaction = getFragmentManager()
                .beginTransaction();
        fragment_transaction.replace(R.id.fragmentcontainer, RegisterView.newInstance());
        fragment_transaction.addToBackStack(null);
        fragment_transaction.commit();
    }

    @Override
    public void onClick(View v) {

        if (v == btnSignupOrLogin) {

            if (isFormLogin) {
                loadRegisterView();
            } else {
                loadLoginView();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackStackChanged() {

        Log.e("onBackStackChanged", "Called...");
        homeAsUpByBackStack();

    }

    /*
          Change the title back when the fragment is changed
        */
    private void homeAsUpByBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
