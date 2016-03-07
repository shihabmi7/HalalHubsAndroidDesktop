package com.datatech.halalhubs.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.model.StatusMessageHolder;
import com.datatech.halalhubs.utility.ApplicationUtility;

import network.GetRegisteredAPI;

public class RegisterView extends Fragment implements StatusMessageHolder {

    public static TextView status;
    EditText fullname_input;
    EditText email_input;
    EditText username_input;
    EditText password_input;
    Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RegisterView newInstance() {
        return new RegisterView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_view,
                container, false);
        status = (TextView) view.findViewById(R.id.register_status_message);
        fullname_input = (EditText) view
                .findViewById(R.id.register_form_firstName_edittext);
        email_input = (EditText) view
                .findViewById(R.id.register_form_email_edittext);
        username_input = (EditText) view
                .findViewById(R.id.register_form_username_edittext);
        password_input = (EditText) view
                .findViewById(R.id.register_form_password_edittext);
        register = (Button) view.findViewById(R.id.register_button);
        register.setOnClickListener(try_to_register);

        return view;
    }

    OnClickListener try_to_register = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (ApplicationUtility.isNetworkAvailable(getActivity())) {
                String firstname = fullname_input.getText().toString();
                String lastName = fullname_input.getText().toString();
                String email = email_input.getText().toString();
                String username = username_input.getText().toString();
                String password = password_input.getText().toString();

                // TODO CALL GET REGISTERED API & MOVE TO ACTIVITY
                GetRegisteredAPI getRegisteredAPI = new GetRegisteredAPI(getActivity(), true, firstname, lastName, email, username, password);
                getRegisteredAPI.execute();
            }
        }
    };

    @Override
    public void status(String result, String message) {
        // TODO Auto-generated method stub
        status.setVisibility(View.VISIBLE);
        if (result.equalsIgnoreCase("error")) {
            status.setText(message);
        } else {
            status.setText(result);
        }
    }
}
