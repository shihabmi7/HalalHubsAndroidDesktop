package network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Get_LocationName_By_GeoCode extends BaseTask{
	
	public Get_LocationName_By_GeoCode(Context ctx, boolean displayProgress) {
		super(ctx, displayProgress);
		
	}

	String requestString="";public String result="";
	private static final String LOG_TAG = "ExampleApp"; 
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/details";
	private static final String OUT_JSON = "/json";
	
	private static final String API_KEY = "AIzaSyAfDPQWyquHNzpDkICpOzvvluLRFCe4s4A";
	// ==> Browser API not the Android API . OK?
	
	
	private String getLocation(String input) {
		
		
	    ArrayList<String> resultList = null;
	    System.out.println("IN HERE");
	
	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?&language=en_us&sensor=true&key=" + API_KEY);
	       // sb.append("&components=country:uk");
	       // sb.append("&types=geocode");
	        sb.append("&reference=" + URLEncoder.encode(input, "utf8"));
	
	        System.out.println(sb.toString());
	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
	
	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return "";
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return "";
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
	
	    try {
	    	System.out.println(jsonResults.toString());
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        
//	        result
	        
	        JSONObject predsJsonObj = jsonObj.getJSONObject("result");
	        JSONObject predsJsonGeo = predsJsonObj.getJSONObject("geometry");
	        JSONObject predsJsonLoc = predsJsonGeo.getJSONObject("location");
	        String lng=predsJsonLoc.getString("lng");
	        String lat=predsJsonLoc.getString("lat");
	        return lat+","+lng;//new LatLng(lat, longitude);
	        //System.out.println(lng+" "+lat);
	        // Extract the Place descriptions from the results
//	        resultList = new ArrayList<String>(predsJsonArray.length());
//	        for (int i = 0; i < predsJsonArray.length(); i++) {
//	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }
	
	    return "";
	}

	@Override
	boolean task() {
		// TODO Auto-generated method stub
		result=getLocation(requestString);
		System.out.println("Result "+result);
		return true;
	}
	
	public void setData(String input){
		requestString=input;
	}

	@Override
	boolean taskOnComplete() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void setListener(CustomListener cust){
		listener=cust;
	}

	@Override
	boolean taskOnFailure() {
		// TODO Auto-generated method stub
		return false;
	}
}

