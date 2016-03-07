package network;

import android.content.Context;
import android.util.Log;

import com.datatech.halalhubs.model.Restaurant;
import com.datatech.halalhubs.utility.ApplicationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GetOrderResponseFromAPI extends BaseTask {

    String start = "1";
    String requestString = "";
    public String jsonData = "";
    public String Message = "";
    public boolean Status = false;

    public final String TAGS = "data";
    public final String PROFILE = "profile";
    public final String NAME = "name";
    public final String ID = "id";
    public final String EMAIL = "email";
    public final String ADDRESS = "address";
    public final String POSTCODE = "postalCode";
    public final String FOOD_TYPE_TAG = "tag";
    public final String FOOD_LOGO_IMAGE = "logoImage";
    public final String STATUS = "status";
    public final String OPENING_TIME = "opening_time";
    public final String CLOSING_TIME = "closing_time";
    public String postCode = null;
    public String tag = null;
    HashMap<String, String> orderParam = null;

    /*{
        "status": "success",
            "message": "Order created",
            "data": {
        "item": {
            "id": 93,
                    "creationDate": "2015-12-19T12:20:30.410Z",
                    "updateDate": "2015-12-19T12:20:30.410Z"
        }
    }
    }*/

    public GetOrderResponseFromAPI(Context ctx, boolean displayProgress,  HashMap<String, String> orderParam) {

        super(ctx, displayProgress);
        this.orderParam = orderParam;
    }

    @Override
    boolean task() {

        try {

//            HashMap<String, String> params = new HashMap<String, String>();
//            params.put(new String("postalCode"), postCode);
//            params.put(new String("tag"), tag);

            HttpRequest request = HttpRequest.post(
                    ApplicationData.REGISTRATION_ORDER_URL, orderParam, true);

            jsonData = request.body();
            Log.e("HALAL RESPONSE", jsonData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    @Override
    boolean taskOnComplete() {

        ApplicationData.restaurantList.clear();

        try {

            JSONObject jObject = new JSONObject(jsonData);

            // JSONArray jsonArray = new JSONArray(jsonData);

            JSONArray jsonArray = jObject.getJSONArray(TAGS);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jobj2 = jsonArray.getJSONObject(i);

                Restaurant resObj = new Restaurant();
                resObj.setRestaurentID(jobj2.getString(ID));
                resObj.setRestaurentName(jobj2.getString(NAME));
                resObj.setAddress(jobj2.getString(ADDRESS));
                resObj.setEmail(jobj2.getString(EMAIL));

                Log.e("Nearest Res", "Res: >> " + resObj.getRestaurentName());

				/*
                 * if (jobj2.has(TAGS)) {
				 * 
				 * JSONArray array = jobj2.getJSONArray(TAGS);
				 * 
				 * for (int j = 0; j < array.length(); j++) {
				 * 
				 * JSONObject jObj3 = array.getJSONObject(j);
				 * 
				 * String tag = jObj3.getString(FOOD_TYPE_TAG);
				 * 
				 * Log.e(" fOOD tag >> ", tag);
				 * 
				 * // photo_reference = jObj3.getString("photo_reference"); //
				 * resObj.setImage_Url(PHOTO_URL_FIRST_PART // + photo_reference
				 * + "&" + key); } } if (jobj2.has(PROFILE)) {
				 * 
				 * // JSONArray array = jobj2.getJSONArray(PROFILE); //
				 * JSONObject jObj3 = jobj2.getJSONObject(PROFILE); String
				 * image_url = jObj3.getString(FOOD_LOGO_IMAGE);
				 * resObj.setImage_Url(ApplicationData.BASE_URL + image_url);
				 * 
				 * Log.e(" Res Image >> ", image_url);
				 * 
				 * }
				 *//*
                     * else {
					 * 
					 * resObj.setImage_Url(jobj2.getString("icon")); }
					 */

                ApplicationData.restaurantList.add(resObj);

            }

        } catch (JSONException e) {

            e.printStackTrace();
        } catch (Exception e) {

        }

        return true;
    }

    @Override
    boolean taskOnFailure() {

        return false;

    }

    @Override
    public void setListener(CustomListener cust) {
        listener = cust;
    }
}
