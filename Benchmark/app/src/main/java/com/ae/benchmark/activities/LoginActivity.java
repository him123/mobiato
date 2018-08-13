package com.ae.benchmark.activities;

import android.content.Context;
import android.app.Activity;
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
import com.ae.benchmark.introslider.WelcomeActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Sales;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseInstanceIDService;
import com.ae.benchmark.util.UtilApp;
import com.ae.benchmark.webservice.WsGetCustomers;
import com.ae.benchmark.webservice.WsGetFreeGoods;
import com.ae.benchmark.webservice.WsGetItems;
import com.ae.benchmark.webservice.WsGetLoads;
import com.ae.benchmark.webservice.WsLogin;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

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

    String fcm_id;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

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

//                    Toast.makeText(LoginActivity.this, "Username and password doesn't match.", Toast.LENGTH_SHORT).show();
//                    login(edt_id.getText().toString(), edt_pwd.getText().toString(), fcm_id);
//                    new HttpGetRequest().execute();
                    //new AsyncTaskRunner("5").execute();
                    new LoginTask().execute();
                }
            }
        });
    }


    private final class LoginTask extends AsyncTask<Void, Void, Void> {

        private WsLogin wsLogin;
        private Activity activity;
        private SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please Wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            this.activity = LoginActivity.this;
            wsLogin = new WsLogin(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {


            login(edt_id.getText().toString().trim(),
                    edt_pwd.getText().toString(), fcm_id);

            if (!edt_id.getText().toString().equalsIgnoreCase("SV1"))
                wsLogin.executeWebservice(edt_id.getText().toString().trim(), edt_pwd.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pDialog.dismiss();

            if (wsLogin.getSuccess()) {
                UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SALESMAN.SALESMAN_ROUTE, edt_id.getText().toString());
                new AsyncTaskRunner("5").execute();
            } else {
                if (!edt_id.getText().toString().equalsIgnoreCase("SV1"))
                    new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(wsLogin.getMessage())
                            .show();
            }

        }
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        private SweetAlertDialog pDialog;
        private Activity activity;
//        ProgressDialog progressDialog;

        String id;

        public AsyncTaskRunner(String id) {
            this.id = id;

        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISSALES, true);
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISJPLOADED, false);
                UtilApp.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.ISPAYMET, false);


                WsGetItems wsGetItems = new WsGetItems(activity);
                wsGetItems.executeWebservice();

                WsGetCustomers wsGetCustomers = new WsGetCustomers(activity);
                wsGetCustomers.executeWebservice();

                WsGetFreeGoods wsGetFreeGoods = new WsGetFreeGoods(activity);
                wsGetFreeGoods.executeWebservice();

                WsGetLoads wsGetLoads = new WsGetLoads(activity);
                wsGetLoads.executeWebservice();


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

            activity = LoginActivity.this;
            pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please Wait...");
            pDialog.setCancelable(false);
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
//                                new AsyncTaskRunner("2").execute();
                                Toast.makeText(LoginActivity.this, "FCM Req sent", Toast.LENGTH_SHORT).show();

                                if (edt_id.getText().toString().equalsIgnoreCase("SV1")) {

                                    UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.USERNAME,
                                            edt_id.getText().toString());

                                    UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.ISLOGIN,
                                            true);

                                    Intent intent = new Intent(LoginActivity.this, SuperVisorApproveActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
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


//    public static ArrayList<String> loginUser(Context context, String username, String password, String url) {
//        ArrayList<String> mylist = new ArrayList<String>();
//        try {
//            Log.e("URL is", "" + url);
//            DefaultHttpClient client = new DefaultHttpClient();
//            client.getCredentialsProvider().setCredentials(getAuthScope(), getCredentials());
//            HttpGet get = new HttpGet(getUrl(url));
//            String authString = App.SERVICE_USER + ":" + App.SERVICE_PASSWORD;
//            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
//            get.addHeader(App.SAP_CLIENT, App.SAP_CLIENT_ID);
//            get.addHeader(ACCEPT, APPLICATION_JSON);
//            HttpResponse response = client.execute(get);
//            if (response.getStatusLine().getStatusCode() == 201 || response.getStatusLine().getStatusCode() == 200) {
//                Header[] headers = response.getAllHeaders();
//                HttpEntity r_entity = response.getEntity();
//                String jsonString = getJSONString(r_entity);
//                JSONObject jsonObj = new JSONObject(jsonString);
//                jsonObj = jsonObj.getJSONObject("d");
//                Log.e("JSON", "" + jsonObj);
//                JSONArray jsonArray = jsonObj.getJSONArray("results");
//                jsonObj = jsonArray.getJSONObject(0);
//                //jsonObj = jsonObj.getJSONObject("__metadata");
//                String message = jsonObj.getString("Message");
//                Log.e("Message", "" + message);
//                String user = jsonObj.getString("Username");
//                String passCode = jsonObj.getString("Password");
//                String returnMessage = jsonObj.getString("Message");
//                mylist.add(user);
//                mylist.add(passCode);
//                mylist.add(returnMessage);
//                return mylist;
//            } else {
//                Log.e("Fail Again", "Fail Again");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mylist;
//    }
}
