package network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.datatech.halalhubs.activity.HomeActivity;
import com.datatech.halalhubs.utility.ApplicationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Shihab on 11/28/2015.
 */

    /*  call like this
    POST /auth/local
    Fields : identifier, password*/

public class GetLoggedIn extends BaseTask {

    String userName, password;
    public String jsonData = "";
    HashMap<String, String> params = new HashMap<String, String>();
    Context context;

    public GetLoggedIn(Context ctx, boolean displayProgress,HashMap<String, String> params ) {
        super(ctx, displayProgress);
        context = ctx;
        this.params=params;
    }

    @Override
    boolean task() {



        HttpRequest request = HttpRequest.post(
                ApplicationData.LOG_IN_BASE_URL, params, true);

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
                //goToHomeActivty();
            } else {
                ApplicationData.aUser.setStatus(false);
               // Toast.makeText(context, "Check Your mail ID & Password", Toast.LENGTH_LONG).show();
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
