package network;

import android.content.Context;
import android.util.Log;

import com.datatech.halalhubs.model.Food;
import com.datatech.halalhubs.utility.ApplicationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetFoodListFromAPI extends BaseTask {

    String start = "1";
    String requestString = "";
    public String jsonData = "";
    public String Message = "";
    public boolean Status = false;

    private final String FOOD_ITEM = "foodItem";
    public String TAGS_MENU_LIST = "menuItems";
    public String PROFILE = "profile";
    public String FOOD_TYPE_NAME = "name";
    public String NAME = "name";
    public String EMAIL = "email";
    public String ADDRESS = "address";
    public String POSTCODE = "postalCode";
    public String FOOD_TYPE_TAG = "tag";
    public String FOOD_LOGO_IMAGE = "logoImage";
    public String STATUS = "status";
    public String OPENING_TIME = "openingTime";
    public String CLOSING_TIME = "closingTime";

    // "openingTime": "1970-01-01T04:00:00.000Z",
    // "closingTime": "1970-01-01T16:00:00.000Z",
    // "deliveryStartTime": "1970-01-01T05:00:00.000Z",
    // "deliveryEndTime"

    // logoImage

    public GetFoodListFromAPI(Context ctx, boolean displayProgress) {

        super(ctx, displayProgress);

    }

    @Override
    boolean task() {

        try {

            HttpRequest request = HttpRequest
                    .get(ApplicationData.SHOW_RESTAURENT_MENU);
            jsonData = request.body();

            Log.e("Food RESPONSE", jsonData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    boolean taskOnComplete() {

        ApplicationData.foodList.clear();

        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jobj2 = jsonArray.getJSONObject(i);

                // Restaurant resObj = new Restaurant();
                // resObj.setRestaurentName(jobj2.getString(NAME));

                Food food = new Food(Food.FOOD_SECTION);
                food.setFoodTypeName(jobj2.getString(FOOD_TYPE_NAME));

                Log.e("Food ", "Food Type : >> " + food.getFoodTypeName());
                ApplicationData.foodList.add(food);

                if (jobj2.has(TAGS_MENU_LIST)) {

                    JSONArray array = jobj2.getJSONArray(TAGS_MENU_LIST);

                    for (int j = 0; j < array.length(); j++) {

                        Food aFood = new Food(Food.FOOD_ITEM);
                        JSONObject jObj3 = array.getJSONObject(j);

                        aFood.setFoodName(jObj3.getString(NAME));
                        aFood.setFoodIDNo(jObj3.getString(FOOD_ITEM));

                        Log.e("Food ", "Food Name: >> " + aFood.getFoodName());
                        ApplicationData.foodList.add(aFood);

                    }
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

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
