package com.datatech.halalhubs.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.activity.HomeActivity;
import com.datatech.halalhubs.model.StatusMessageHolder;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.utility.ApplicationUtility;

import java.util.HashMap;

import network.CustomListener;
import network.GetLoggedIn;

public class LoginView extends Fragment implements StatusMessageHolder {


    public static TextView status;
    EditText identifier_input;
    EditText password_input;
    Button login;
    Activity activity = getActivity();

    OnClickListener try_to_login = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (ApplicationUtility.isNetworkAvailable(getActivity())) {

                String identifier = identifier_input.getText().toString();
                String password = password_input.getText().toString();

                HashMap<String, String> params = new HashMap<String, String>();
                params.put(new String("identifier"), identifier);
                params.put(new String("password"), password);
//
//                new LoginUser(LoginView.newInstance(), identifier, password, getActivity())
//                        .execute();

                GetLoggedIn getLogInDetails = new GetLoggedIn(getActivity(), true, params);
                getLogInDetails.setListener(new CustomListener() {
                    @Override
                    public void ModificationMade() {
                        if (ApplicationData.aUser.getStatus()) {
                            goToHomeActivty();
                        } else {
                            Toast.makeText(getActivity(), "Check Your mail ID & Password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                getLogInDetails.execute();


            }
            // else {
            //                //TODO OPEN INTERNET CHECK DIALOG
            //            }
        }
    };


    public static LoginView newInstance() {
        return new LoginView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login_view, container,
                false);
        status = (TextView) view.findViewById(R.id.login_status_message);

        identifier_input = (EditText) view
                .findViewById(R.id.login_form_identifier_edittext);
        password_input = (EditText) view
                .findViewById(R.id.login_form_password_edittext);

        login = (Button) view.findViewById(R.id.login_button);
        login.setOnClickListener(try_to_login);
        return view;
    }

    @Override
    public void status(String result, String message) {

        try {
            status.setVisibility(View.VISIBLE);
            if (result.equalsIgnoreCase("error")) {
                status.setText(message);
            }
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
        }

    }

    private void goToHomeActivty() {

        //getActivity().finish();
        Intent aIntent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        startActivity(aIntent);

    }
}
