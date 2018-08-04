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
        final String url = Const.WS_URL + "UserauthSet?$filter=USERID%20eq%20%27" + username + "%27%20and%20PASSWORD%20eq%20%27" + password + "%27&$format=json";
        parseResponse(NetworkUtility.getApiData(context, url), username);
    }

    private void parseResponse(final String response, final String username) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("d")) {
                jsonObject = jsonObject.getJSONObject("d");
                JSONArray array = jsonObject.optJSONArray("results");
                if (array.length() > 0) {
                    jsonObject = array.getJSONObject(0);
                    success = jsonObject.optString("status").equals("1");
                    message = jsonObject.optString("message");

                    if (success) {
                        DBManager dbManager = new DBManager(context);
                        dbManager.open();
                        dbManager.insertSalesman(jsonObject);
                        UtilApp.WriteSharePrefrence(context, Constant.SHRED_PR.USERNAME, username);


                        if (jsonObject.optString("inv_last").equalsIgnoreCase(""))
                            UtilApp.WriteSharePrefrence(context, Constant.INV_LAST, "0");
                        else
                            UtilApp.WriteSharePrefrence(context, Constant.INV_LAST, jsonObject.optString("inv_last"));

                        if (jsonObject.optString("order_last").equalsIgnoreCase(""))
                            UtilApp.WriteSharePrefrence(context, Constant.ORD_LAST, "0");
                        else
                            UtilApp.WriteSharePrefrence(context, Constant.ORD_LAST, jsonObject.optString("order_last"));

                        if (jsonObject.optString("load_last").equalsIgnoreCase(""))
                            UtilApp.WriteSharePrefrence(context, Constant.LOAD_LAST, "0");
                        else
                            UtilApp.WriteSharePrefrence(context, Constant.LOAD_LAST, jsonObject.optString("load_last"));

                        if (jsonObject.optString("collection_last").equalsIgnoreCase(""))
                            UtilApp.WriteSharePrefrence(context, Constant.COLLECTION_LAST, "0");
                        else
                            UtilApp.WriteSharePrefrence(context, Constant.COLLECTION_LAST, jsonObject.optString("collection_last"));

                        if (jsonObject.optString("customer_last").equalsIgnoreCase(""))
                            UtilApp.WriteSharePrefrence(context, Constant.CUSTOMER_LAST, "0");
                        else
                            UtilApp.WriteSharePrefrence(context, Constant.CUSTOMER_LAST, jsonObject.optString("customer_last"));
//                        UtilApp.WriteSharePrefrence(context, Constant.PAYMENT_LAST, jsonObject.optString("message"));
//                        UtilApp.WriteSharePrefrence(context, Constant.LOAD_REQUEST_LAST, jsonObject.optString("message"));


//                        SALESMAN_ID = "SALESMAN";
//                        public static final String SALESMAN_NAME = "name1";
//                        public static final String SALESMAN_SALES_ORG = "salesorg";
//                        public static final String SALESMAN_CHANNEL = "channel";
//                        public static final String SALESMAN_DIVISION = "Division";
//                        public static final String SALESMAN_VEHICLE = "Vehicle";
//                        public static final String SALESMAN_SUPERVISOR = "supervisor";
                        //SALES MAN DETIALS

                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_ID, jsonObject.optString("SALESMAN"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_NAME, jsonObject.optString("name1"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_SALES_ORG, jsonObject.optString("salesorg"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_CHANNEL, jsonObject.optString("channel"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_DIVISION, jsonObject.optString("Division"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_VEHICLE, jsonObject.optString("Vehicle"));
                        UtilApp.WriteSharePrefrence(context, Constant.SALESMAN.SALESMAN_SUPERVISOR, jsonObject.optString("supervisor"));

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
