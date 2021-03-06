package com.ae.benchmark.activities;

import android.app.ProgressDialog;
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
import com.ae.benchmark.model.Item;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseInstanceIDService;
import com.ae.benchmark.util.UtilApp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

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
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"232\",\n" +
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
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"543\",\n" +
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
            "                    \"item_uom\":\"bottle\",\n" +
            "                    \"item_price\":\"543\",\n" +
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
            "                    \"item_price\":\"65\",\n" +
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
            "                    \"cust_avail_bal\":\"30\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"credit\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"7\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"9\",\n" +
            "                    \"cust_long\":\"43.104137\",\n" +
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
            "                    \"cust_avail_bal\":\"30\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"cash\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"3\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"2\",\n" +
            "                      \"cust_long\":\"43.104137\",\n" +
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
            "                    \"cust_avail_bal\":\"30\",\n" +
            "                    \"cust_payment_term\":\"30\",\n" +
            "                    \"cust_address\":\"Credit\",\n" +
            "                    \"cust_type\":\"tc\",\n" +
            "                    \"cust_possessed_empty_bottle\":\"5\",\n" +
            "                    \"cust_possessed_filled_bottle\":\"5\",\n" +
            "                     \"cust_long\":\"43.104137\",\n" +
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
            "                    \"load_no\":\"1\",\n" +
            "                    \"item_code\":\"1\",\n" +
            "                    \"item_name\":\"GBC Bottle with Water\",\n" +
            "                    \"item_qty\":\"12\",\n" +
            "                    \"item_uom\":\"Bottle\",\n" +
            "                    \"load_date\":\"19-07-2018\",\n" +
            "                    \"total_price\":\"546\"\n" +
            "                    },\n" +
            "\n" +
            "\n" +
            "                    {\"load_no\":\"2\",\n" +
            "                    \"item_code\":\"2\",\n" +
            "                    \"item_name\":\"GBC Empty Bottle\",\n" +
            "                    \"item_qty\":\"14\",\n" +
            "                    \"item_uom\":\"Bottle\",\n" +
            "                    \"load_date\":\"19-07-2018\",\n" +
            "                    \"total_price\":\"685\"}\n" +
            "                    ]\n" +
            "                    }\n" +
            "\n" +
            "                    ]";


    String salesman = "{\n" +
            "                    \"unique_id\":\"2\",\n" +
            "                    \"salesman_id\":\"52\",\n" +
            "                    \"salesman_name_en\":\"Nasir\",\n" +
            "                    \"salesman_name_ar\":\"Nasir\",\n" +
            "                    \"salesman_dis_channel\":\"channel1\",\n" +
            "                    \"salesman_org\":\"org\",\n" +
            "                    \"salesman_division\":\"division1\",\n" +
            "                    \"salesman_route\":\"23rout\",\n" +
            "                    \"salesman_vehicle_no\":\"DB25365\",\n" +
            "                    \"status\":\"1\",\n" +
            "                    \"message\":\"Successfully logged in.\"\n" +
            "                    }";


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
                    login(edt_id.getText().toString(), edt_pwd.getText().toString(), fcm_id);
                } else if (edt_id.getText().toString().equals("")) {
//                    edt_id.setError("Please enter username");
                    Toast.makeText(LoginActivity.this, "Please enter username.", Toast.LENGTH_SHORT).show();
                } else if (edt_pwd.getText().toString().equals("")) {
//                    edt_pwd.setError("Please enter username", R.drawable.ic_icon_order);
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                } else {

                    pDialog.show();
//                    Toast.makeText(LoginActivity.this, "Username and password doesn't match.", Toast.LENGTH_SHORT).show();
                    login(edt_id.getText().toString(), edt_pwd.getText().toString(), fcm_id);
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

//                UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.USERNAME, id);
//
//                UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.EMPTYES, "12");
//
//                UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.ISSTOCKCAPTURED, false);
//
//                int item_id = 100;
//                int load_no = 10000;
//
//                //inserting items
//                for (int i = 0; i < itemCodes.length; i++) {
//
//                    dbManager.insertItems(itemCodes[i], itemNames[i], itemPrices[i]);
//                }
//
//                //Inserting customers
//                for (int i = 0; i < custNames.length; i++) {
//
//                    dbManager.insertCustomer(custNames[i], "CUST " + i + 1, custTel[i]);
//                }
//
//                //inserting load items
//                for (int i = 0; i < 2; i++) {
//
//                    dbManager.insertLoad("" + load_no, "25/03/2018", "0");
//                    dbManager.insertLoadItems("" + item_id, "Butter 250gm " + item_id, "130", "" + load_no);
//
//                    item_id++;
//                    load_no++;
//                }
//
//                //inserting operational transactions
//                for (int i = 0; i < Transaction.length; i++) {
//                    dbManager.insertTransaction(Transaction[i], date[i], time[i]);
//                }


                // SALESMAN
//                JSONObject SalesmanObj = new JSONObject(String.valueOf(salesman));
////                JSONArray itemJArr = itemObj.getJSONArray("data");
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

            pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    UtilApp.WriteSharePrefrence(LoginActivity.this, Constant.SHRED_PR.ISLOGIN, true);

                    Intent intent;

                    if (id.equalsIgnoreCase("sm1")) {

                        intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, SuperVisorApproveActivity.class);
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
                                new AsyncTaskRunner(id).execute();
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
