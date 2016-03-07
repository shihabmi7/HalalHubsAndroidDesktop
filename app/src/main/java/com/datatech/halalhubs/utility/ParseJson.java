package com.datatech.halalhubs.utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson {
	private JSONObject object = null;

	public String getStatus() {
		String status = "";
		try {
			status = object.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public String getMessage() {
		String message = "";
		try {
			message = object.getString("message");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public void setJson(String json) {

		try {
			object = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
