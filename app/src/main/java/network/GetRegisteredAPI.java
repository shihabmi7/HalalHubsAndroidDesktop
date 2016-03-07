package network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.datatech.halalhubs.activity.HomeActivity;
import com.datatech.halalhubs.utility.ApplicationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Shihab on 11/28/2015.
 */

/*  call like this
/*
POST /auth/local/register

        Fields : firstName, lastName, username, email, password `*/

public class GetRegisteredAPI extends BaseTask {

    String userName, password, firstName, lastName, email;
    public String jsonData = "";
    Context context = null;

    public GetRegisteredAPI(Context ctx, boolean displayProgress, String firstName,
                            String lastName, String email, String userName, String password) {
        super(ctx, displayProgress);
        this.context = ctx;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    boolean task() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(new String("firstName"), firstName);
        params.put(new String("lastName"), lastName);
        params.put(new String("username"), userName);
        params.put(new String("email"), email);
        params.put(new String("password"), password);

        HttpRequest request = HttpRequest.post(
                ApplicationData.REGISTRATION_BASE_URL, params, true);

        jsonData = request.body();
        Log.e("HALAL RESPONSE", jsonData);
        return true;
    }

    @Override
    boolean taskOnComplete() {

        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String msg = jsonObject.getString("status");

            if (msg.equalsIgnoreCase("success")) {

                ApplicationData.aUser.setStatus(true);
                goToHomeActivty();

            } else {

                ApplicationData.aUser.setStatus(false);
                Toast.makeText(context, "Check Your mail ID & Password", Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {


        } catch (Exception e) {


        }

        return true;
    }

    private void goToHomeActivty() {

        //getActivity().finish();
        Intent aIntent = new Intent(context, HomeActivity.class);
        context.startActivity(aIntent);

    }

    @Override
    boolean taskOnFailure() {
        return true;
    }
}
