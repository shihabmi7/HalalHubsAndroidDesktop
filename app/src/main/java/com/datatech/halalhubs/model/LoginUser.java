package com.datatech.halalhubs.model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.datatech.halalhubs.activity.HomeActivity;
import com.datatech.halalhubs.utility.ApplicationUtility;
import com.datatech.halalhubs.utility.ParseJson;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;*/

public class LoginUser extends AsyncTask<Void, Void, String> {

    String identifier, password;
    Context context;
    private String loginURL = "http://45.55.196.7:1337/auth/local";
    private StatusMessageHolder holder;

    @Override
    protected String doInBackground(Void... params) {
//        return callLoginApi();
        return "";
    }

    @Override
    protected void onPreExecute() {
        ApplicationUtility.showProgress("Logging", context);
    }

    @Override
    protected void onPostExecute(String loginStatus) {
        ApplicationUtility.hideProgress();
        ParseJson parse = new ParseJson();
        parse.setJson(loginStatus);

        try {
            if (parse.getStatus().equalsIgnoreCase("error")) {

                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                holder.status(parse.getStatus(), parse.getMessage());


            } else {

                Toast.makeText(context, "Successfully Log In", Toast.LENGTH_LONG).show();
                goToHomeActivty();

            }

        } catch (NullPointerException e) {

            e.printStackTrace();
            // Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
        }

    }

    private void goToHomeActivty() {

        //getActivity().finish();
        Intent aIntent = new Intent(context, HomeActivity.class);
        context.startActivity(aIntent);

    }

    // block by shihab
 /*   private String callLoginApi() {
        String response = "";
        DefaultHttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(loginURL);
        try {

            StringEntity se = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("identifier", "" + identifier));
            nameValuePairs.add(new BasicNameValuePair("password", "" + password));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            HttpResponse execute = client.execute(post);
            InputStream urlContent = execute.getEntity().getContent();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlContent));
            String line = "";
            while ((line = br.readLine()) != null) {
                response += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }*/

}
