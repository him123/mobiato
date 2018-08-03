package com.ae.benchmark.webservice;

import android.content.Context;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.LoginActivity;
import com.ae.benchmark.activities.NetworkUtility;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WsLogin {

    private Context context;
    private String message = "";
    private boolean success = false;

    public WsLogin(Context context) {
        this.context = context;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void executeWebservice(String username, String password) {
        final String url = Const.WS_URL + "UserauthSet?$filter=USERID%20eq%20%27"+username+"%27%20and%20PASSWORD%20eq%20%27"+password+"%27&$format=json";
        parseResponse(NetworkUtility.getApiData(context, url), username);
    }

    private void parseResponse(final String response, final String username) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("d")) {
                jsonObject = jsonObject.getJSONObject("d");
                JSONArray array = jsonObject.optJSONArray("results");
                if(array.length() > 0){
                    jsonObject = array.getJSONObject(0);
                    success = jsonObject.optString("status").equals("1");
                    message = jsonObject.optString("message");

                    if (success) {
                        DBManager dbManager = new DBManager(context);
                        dbManager.open();
                        dbManager.insertSalesman(jsonObject);
                        UtilApp.WriteSharePrefrence(context, Constant.SHRED_PR.USERNAME, username);
                    } else {
                        success = false;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            message = context.getString(R.string.common_error);
        }
    }
}
