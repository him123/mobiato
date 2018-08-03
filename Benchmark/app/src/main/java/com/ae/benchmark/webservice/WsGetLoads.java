package com.ae.benchmark.webservice;

import android.content.Context;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.NetworkUtility;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WsGetLoads {

    private Context context;
    private String message = "";
    private boolean success = false;

    public WsGetLoads(Context context) {
        this.context = context;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void executeWebservice() {
        final String url = Const.WS_URL + "LoadSet?$filter=Route%20eq%20%27"+ UtilApp.ReadSharePrefrenceString(context, Constant.SHRED_PR.USERNAME)+"%27&$expand=MatDoc&$format=json";
        parseResponse(NetworkUtility.getApiData(context, url));
    }

    private void parseResponse(final String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("d")) {
                jsonObject = jsonObject.getJSONObject("d");
                JSONArray array = jsonObject.optJSONArray("results");
                if(array.length() > 0){
                    success = true;
                    DBManager dbManager = new DBManager(context);
                    dbManager.open();
                    dbManager.insertLoadArr(array);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            message = context.getString(R.string.common_error);
        }
    }
}
