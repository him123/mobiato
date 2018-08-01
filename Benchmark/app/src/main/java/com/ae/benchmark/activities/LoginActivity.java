package com.ae.benchmark.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Sales;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseInstanceIDService;
import com.ae.benchmark.util.UtilApp;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {


    @InjectView(R.id.btn_login)
    Button btn_login;

    @InjectView(R.id.edt_id)
    EditText edt_id;

    @InjectView(R.id.edt_pwd)
    EditText edt_pwd;

    @InjectView(R.id.text_input_id)
    TextInputLayout text_input_id;

    @InjectView(R.id.text_input_pwd)
    TextInputLayout text_input_pwd;

    DBManager dbManager;


    String items = "[\n" +
            "                    {\n" +
            "                    \"item_code\":\"1\",\n" +
            "                    \"item_name_en\":\"GBC Coupon Book 20’s\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"pcs\",\n" +
            "                    \"item_price\":\"2000\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    },\n" +
            "                    {\n" +
            "                    \"item_code\":\"2\",\n" +
            "                    \"item_name_en\":\"GBC Coupon Book 50’s\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"pcs\",\n" +
            "                    \"item_price\":\"5000\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    },\n" +
            "                    {\n" +
            "                    \"item_code\":\"3\",\n" +
            "                    \"item_name_en\":\"GBC Coupon Book 100’s\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"pcs\",\n" +
            "                    \"item_price\":\"10000\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    },\n" +
            "                    {\n" +
            "                    \"item_code\":\"4\",\n" +
            "                    \"item_name_en\":\"GBC Water\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"43\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    },\n" +
            "\n" +
            "                    {\n" +
            "                    \"item_code\":\"5\",\n" +
            "                    \"item_name_en\":\"GBC Empty Bottle\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"66\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    },\n" +
            "                    {\n" +
            "                    \"item_code\":\"6\",\n" +
            "                    \"item_name_en\":\"GBC Bottle with Water\",\n" +
            "                    \"item_name_ar\":\"\",\n" +
            "                    \"item_type\":\"coupon\",\n" +
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"85\",\n" +
            "                    \"item_barcode\":\"52365926\",\n" +
            "                    \"division\":\"division1\",\n" +
            "                    \"conversion_factor\":\"5\"\n" +
            "\n" +
            "\n" +
            "                    }\n" +
            "]";

    String customers = "[\n" +
            "                    {\n" +
            "                    \"cust_num\":\"45\",\n" +
            "                    \"cust_name_en\":\"Al Maya Market\",\n" +
            "                    \"cust_name_ar\":\"سوق المايا\",\n" +
            "                    \"cust_dist_channel\":\"divsion1\",\n" +
            "                    \"cust_division\":\"org1\",\n" +
            "                    \"cust_sales_org\":\"2563\",\n" +
            "                    \"cust_credit_limit\":\"256\",\n" +
            "                    \"cust_avail_bal\":\"30000\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"credit\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"7\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"9\",\n" +
            "                    \"cust_long\":\"43.104137\",\n" +
            "                    \"cust_created_date\":\"26\",\n" +
            "                    \"cust_lat\":\"43.104137\"\n" +
            "\n" +
            "                    },\n" +
            "\n" +
            "                    {\n" +
            "                   \"cust_num\":\"65\",\n" +
            "                    \"cust_name_en\":\"Al Maya Market\",\n" +
            "                    \"cust_name_ar\":\"سوق المايا\",\n" +
            "                    \"cust_dist_channel\":\"divsion1\",\n" +
            "                    \"cust_division\":\"org1\",\n" +
            "                    \"cust_sales_org\":\"2563\",\n" +
            "                    \"cust_credit_limit\":\"256\",\n" +
            "                    \"cust_avail_bal\":\"30000\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"cash\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"3\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"2\",\n" +
            "                      \"cust_long\":\"43.104137\",\n" +
            "                    \"cust_created_date\":\"26\",\n" +
            "                    \"cust_lat\":\"43.104137\"\n" +
            "                    }\n" +
            "                    ,\n" +
            "\n" +
            "                    {\n" +
            "                   \"cust_num\":\"33\",\n" +
            "                    \"cust_name_en\":\"Al Maya Market\",\n" +
            "                    \"cust_name_ar\":\"سوق المايا\",\n" +
            "                    \"cust_dist_channel\":\"divsion1\",\n" +
            "                    \"cust_division\":\"org1\",\n" +
            "                    \"cust_sales_org\":\"2563\",\n" +
            "                    \"cust_credit_limit\":\"256\",\n" +
            "                    \"cust_avail_bal\":\"30000\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"credit\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"5\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"5\",\n" +
            "                     \"cust_long\":\"43.104137\",\n" +
            "                    \"cust_created_date\":\"26\",\n" +
            "                    \"cust_lat\":\"43.104137\"\n" +
            "                    }\n" +
            "\n" +
            "                    ]";

    String loads = "[\n" +
            "                    {\n" +
            "                    \"load_no\":\"546\",\n" +
            "                    \"delivery_date\":\"19-07-2018\",\n" +
            "\n" +
            "                    \"load_items\":[\n" +
            "                    {\n" +
            "                    \"load_no\":\"546\",\n" +
            "                    \"item_code\":\"1\",\n" +
            "                    \"item_name\":\"GBC Bottle with Water\",\n" +
            "                    \"item_type\":\"Bottle\",\n" +
            "                    \"item_qty\":\"12\",\n" +
            "                    \"item_uom\":\"Bottle\",\n" +
            "                    \"load_date\":\"19-07-2018\",\n" +
            "                    \"total_price\":\"58\"\n" +
            "                    },\n" +
            "\n" +
            "\n" +
            "                    {\"load_no\":\"546\",\n" +
            "                    \"item_code\":\"2\",\n" +
            "                    \"item_name\":\"GBC Empty Bottle\",\n" +
            "                    \"item_type\":\"Empty\",\n" +
            "                    \"item_qty\":\"14\",\n" +
            "                    \"item_uom\":\"Bottle\",\n" +
            "                    \"load_date\":\"19-07-2018\",\n" +
            "                    \"total_price\":\"685\"},\n" +
            "{\"load_no\":\"546\",\n" +
            "                    \"item_code\":\"3\",\n" +
            "                    \"item_name\":\"GBC Coupon Book 50’s\",\n" +
            " \"item_type\":\"Coupon\",\n" +
            "                    \"item_qty\":\"14\",\n" +
            "                    \"item_uom\":\"Coupon\",\n" +
            "                    \"load_date\":\"19-07-2018\",\n" +
            "                    \"total_price\":\"5000\"}\n" +
            "                    ]\n" +
            "                    }\n" +
            "\n" +
            "                    ]";

    String salesman = "{\"data\":{\n" +
            "                    \"trip_id\":\"546\",\n" +
            "                    \"salesman_id\":\"546\",\n" +
            "                    \"salesman_name_en\":\"546\",\n" +
            "                    \"salesman_name_ar\":\"546\",\n" +
            "                    \"salesman_dis_channel\":\"546\",\n" +
            "                    \"salesman_org\":\"546\",\n" +
            "                    \"salesman_division\":\"546\",\n" +
            "                    \"salesman_route\":\"546\",\n" +
            "                    \"salesman_vehicle_no\":\"546\",\n" +
            "                    \"supervisor_id\":\"24\",\n" +
            "                    \"INV_LAST\":\"10\",\n" +
            "                    \"ORD_LAST\":\"10\",\n" +
            "                    \"LOAD_LAST\":\"10\",\n" +
            "                    \"COLLECTION_LAST\":\"10\",\n" +
            "                    \"CUSTOMER_LAST\":\"10\",\n" +
            "                    \"PAYMENT_LAST\":\"10\",\n" +
            "                    \"LOAD_REQUEST_LAST\":\"10\",\n" +
            "                    \"status\":\"1\",\n" +
            "                    \"message\":\"You are successfully login.\"\n" +
            "\n" +
            "                    }\n" +
            "}\n";


    String fcm_id;

    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Downloading...");
        pDialog.setCancelable(false);

//        items = getResources().getString(R.string.item_resp);
//        customers = getResources().getString(R.string.customer_resp);
//        loads = getResources().getString(R.string.item_resp);
//        salesman = getResources().getString(R.string.login_resp);

        fcm_id = UtilApp.ReadSharePrefrenceString(LoginActivity.this, Constant.SHRED_PR.FCM_TOKEN);

        Log.i("", "Check FCM ID : " + fcm_id);

        dbManager = new DBManager(LoginActivity.this);
        dbManager.open();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_id.getText().toString().equals("admin") &&
                        edt_pwd.getText().toString().equals("admin")) {
//                    new AsyncTaskRunner().execute();
//                    login(edt_id.getText().toString(), edt_pwd.getText().toString(), fcm_id);
                } else if (edt_id.getText().toString().equals("")) {
//                    edt_id.setError("Please enter username");
                    Toast.makeText(LoginActivity.this, "Please enter username.", Toast.LENGTH_SHORT).show();
                } else if (edt_pwd.getText().toString().equals("")) {
//                    edt_pwd.setError("Please enter username", R.drawable.ic_icon_order);
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                } else {

                    pDialog.show();
//                    Toast.makeText(LoginActivity.this, "Username and password doesn't match.", Toast.LENGTH_SHORT).show();
//                    login(edt_id.getText().toString(), edt_pwd.getText().toString(), fcm_id);
//                    new HttpGetRequest().execute();
                    new AsyncTaskRunner("5").execute();
                }
            }
        });
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
//        ProgressDialog progressDialog;

        String id;

        public AsyncTaskRunner(String id) {
            this.id = id;

        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
//                HttpURLConnection urlConnection = null;

//                try {
//
//                    URL url = new URL("https://gbcportal.gbc.sa/sap/opu/odata/sap/ZSFA_5G_DOWNLOAD_SRV/UserauthSet?$filter=USERID%20eq%20%27C11117%27%20and%20PASSWORD%20eq%20%27C11117%27&$format=json");
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    try {
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(line).append("\n");
//                        }
//                        bufferedReader.close();
//                        Log.v("", " Response: " + stringBuilder.toString());
//                        resp = stringBuilder.toString();
////                        return respstringBuilder.toString();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } finally {
//                    urlConnection.disconnect();
//                }


                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISSALES, true);
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISJPLOADED, false);
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISPAYMET, false);

                // SALESMAN
                JSONObject SalesmanObj = new JSONObject(String.valueOf(salesman));

                Gson gson = new Gson();

                Sales sales = gson.fromJson(SalesmanObj.toString(), Sales.class);
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.INV_LAST, sales.getData().getINVLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.ORD_LAST, sales.getData().getORDLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.LOAD_LAST, sales.getData().getLOADLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.COLLECTION_LAST, sales.getData().getCOLLECTIONLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.CUSTOMER_LAST, sales.getData().getCUSTOMERLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.PAYMENT_LAST, sales.getData().getPAYMENTLAST());
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.LOAD_REQUEST_LAST, sales.getData().getLOADREQUESTLAST());
//                JSONArray itemJArr = itemObj.getJSONArray("data");
//                dbManager.insertSalesman(SalesmanObj);


                //ITEM INSERT
//                JSONObject itemObj = new JSONObject(String.valueOf(items));
//                JSONArray itemJArr = itemObj.getJSONArray("data");
                JSONArray itemJArr = new JSONArray(items);
                dbManager.insertItemsArray(itemJArr);

                //CUSTOMER INSERT
//                JSONObject custObj = new JSONObject(customers);
//                JSONArray custJArr = custObj.getJSONArray("data");
                JSONArray custJArr = new JSONArray(customers);
                dbManager.insertCustomerArr(custJArr);

                //LOAD INSERT
//                JSONObject loadObj = new JSONObject(loads);
//                JSONArray loadJArr = loadObj.getJSONArray("data");
                JSONArray loadJArr = new JSONArray(loads);
                dbManager.insertLoadArr(loadJArr);
//                dbManager.insertUnload(loadJArr);


                UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.ISCHECKIN, false);

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
//            progressDialog.dismiss();

            pDialog.dismissWithAnimation();

            UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.IS_DAY_END, false);

            pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.ISLOGIN, true);

                    Intent intent;

                    if (id.equalsIgnoreCase("sm1")) {

                        intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        intent.putExtra("end", "0");
                    } else {

                        intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        intent.putExtra("end", "0");
                    }

                    intent.putExtra("type", "login");

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
            });
        }


        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(LoginActivity.this,
//                    "Fetching data", "Loading...");

            pDialog.show();

        }


        @Override
        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);

        }
    }

    @Override
    protected void onResume() {
        startService(new Intent(LoginActivity.this, MyFirebaseInstanceIDService.class));
        super.onResume();
    }


    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = "https://gbcportal.gbc.sa/sap/opu/odata/sap/ZSFA_5G_DOWNLOAD_SRV/UserauthSet?$filter=USERID%20eq%20%27C11117%27%20and%20PASSWORD%20eq%20%27C11117%27&$format=json";
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
//                URL myUrl = new URL(stringUrl);
//                //Create a connection
//                HttpURLConnection connection = (HttpURLConnection)
//                        myUrl.openConnection();
//                //Set methods and timeouts
//                connection.setRequestMethod(REQUEST_METHOD);
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CONNECTION_TIMEOUT);
//
//                //Connect to our url
//                connection.connect();
//                //Create a new InputStreamReader
//                InputStreamReader streamReader = new
//                        InputStreamReader(connection.getInputStream());
//                //Create a new buffered reader and String Builder
//                BufferedReader reader = new BufferedReader(streamReader);
//                StringBuilder stringBuilder = new StringBuilder();
//                //Check if the line we are reading is not null
//                while ((inputLine = reader.readLine()) != null) {
//                    stringBuilder.append(inputLine);
//                }
//                //Close our InputStream and Buffered reader
//                reader.close();
//                streamReader.close();
//                //Set our result equal to our stringBuilder
//                result = stringBuilder.toString();

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("https://gbcportal.gbc.sa/sap/opu/odata/sap/ZSFA_5G_DOWNLOAD_SRV/UserauthSet?$filter=USERID%20eq%20%27C11117%27%20and%20PASSWORD%20eq%20%27C11117%27&$format=json"));
                HttpResponse httpResponse = client.execute(request);

                Log.v("", " ================== Reponse: " + httpResponse.getStatusLine().toString());
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.v("", "Check this response: " + result);
        }
    }


    private void login(final String id, String pass, String fcm) {
        RestClient.getMutualTransfer().login(id,
                pass,
                fcm, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.v("", "Response: " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(UtilApp.getString(response.getBody().in()));
                            Log.v("", "==== Json: " + jsonObject.toString());

                            if (jsonObject.getString("STATUS").equals("1")) {
                                new AsyncTaskRunner("2").execute();
                            } else {
                                Toast.makeText(LoginActivity.this, "Error in Login", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.v("", "Error: " + error);
                    }
                });
    }
}
