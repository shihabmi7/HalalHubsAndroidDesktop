package com.datatech.halalhubs.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.activity.HomeActivity;
import com.datatech.halalhubs.activity.LogInActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class SocialLoginFragmentShihab extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private Button facebook;
    private Button twitter;
    private Button linkedin;
    private Button googleplus;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SocialLoginFragment";
    private static final int RC_SIGN_IN = 9001;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private Button sign_out;

    public SocialLoginFragmentShihab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_social_login, container, false);
        ((LogInActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        // init buttons and set Listener
        facebook = (Button) rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);

//        twitter = (Button) rootView.findViewById(R.id.twitter);
//        twitter.setOnClickListener(loginClick);
        linkedin = (Button) rootView.findViewById(R.id.linkedin);
        linkedin.setOnClickListener(loginClick);

        googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        //Get Keys for initiate SocialNetworks
        String TWITTER_CONSUMER_KEY = getActivity().getString(R.string.twitter_consumer_key);
        String TWITTER_CONSUMER_SECRET = getActivity().getString(R.string.twitter_consumer_secret);
        String TWITTER_CALLBACK_URL = getActivity().getString(R.string.twitter_callback);

        String LINKEDIN_CONSUMER_KEY = getActivity().getString(R.string.linkedin_consumer_key);
        String LINKEDIN_CONSUMER_SECRET = getActivity().getString(R.string.linkedin_consumer_secret);
        String LINKEDIN_CALLBACK_URL = getActivity().getString(R.string.linkedin_callback);


        // TODO: SHIHAB START WORKING

        // Views
        mStatusTextView = (TextView) rootView.findViewById(R.id.mStatusTextView);
        sign_out = (Button) rootView.findViewById(R.id.sign_out);
        sign_out.setOnClickListener(loginClick);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        return rootView;
    }

    private void signIn() {
        showProgressDialog();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    // [END signOut]
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            googleplus.setVisibility(View.GONE);
            sign_out.setVisibility(View.VISIBLE);

        } else {
            mStatusTextView.setText("Sign Out");
            googleplus.setVisibility(View.VISIBLE);
            sign_out.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            hideProgressDialog();
            goToHomeActivty();
            /*GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(acct.getDisplayName() + acct.getEmail());
            updateUI(true);*/
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void goToHomeActivty() {

        getActivity().finish();
        Intent aIntent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        startActivity(aIntent);

    }
    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.facebook:
                    break;
//                case R.id.twitter:
//                    networkId = TwitterSocialNetwork.ID;
//                    break;
                case R.id.linkedin:
                    //  networkId = LinkedInSocialNetwork.ID;
                    break;
                case R.id.googleplus:
                    //   networkId = GooglePlusSocialNetwork.ID;

                    signIn();
                    break;
                case R.id.sign_out:
                    //   networkId = GooglePlusSocialNetwork.ID;
                    signOut();
                    break;
            }
        }
    };

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Sign in google");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}