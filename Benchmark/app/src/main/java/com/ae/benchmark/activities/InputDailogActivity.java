package com.ae.benchmark.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.CommonMethods;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseMessagingService;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
//import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
//import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by Himm on 3/13/2018.
 */

public class InputDailogActivity extends Activity {

    String isCoupon = "";

    @InjectView(R.id.edt_coupon_code)
    EditText edt_coupon_code;

    @InjectView(R.id.edt_sale)
    EditText edt_sale;

    @InjectView(R.id.edt_emp)
    EditText edt_emp;

//    MaterialShowcaseSequence sequence;

    @InjectView(R.id.txt_name)
    TextView txt_name;

    @InjectView(R.id.txt_qty)
    TextView txt_qty;

    @InjectView(R.id.txt_approved)
    TextView txt_approved;

    @InjectView(R.id.ll_empty)
    LinearLayout ll_empty;

    @InjectView(R.id.ll_reason)
    LinearLayout ll_reason;

    @InjectView(R.id.ll_scan_code)
    LinearLayout ll_scan_code;

    @InjectView(R.id.ll_selling)
    LinearLayout ll_selling;

    @InjectView(R.id.img_approved)
    ImageView img_approved;

    boolean flag;

    Item item;

    String newOrOld, item_type;
    Intent intent;
    public static final String BROADCAST_ACTION = "com.benchmark.DIALOG";
    String qty;
    DBManager db;
    Double price;
    Spinner sp_reason;
    String custName, cust_num;
    String emp_type = "";
    String emptry_bttles = "0";

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input_item_dialog);
        ButterKnife.inject(this);

        registerReceiver(broadcastReceiver2, new IntentFilter(MyFirebaseMessagingService.BROADCAST_ACTION));

        db = new DBManager(InputDailogActivity.this);
        intent = new Intent(BROADCAST_ACTION);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isCoupon = extras.getString("isCoupon");
            flag = extras.getBoolean("isScan");
            item = extras.getParcelable("item");
            newOrOld = extras.getString("tag");
            item_type = extras.getString("item_type");
            custName = extras.getString("cust_name");
            cust_num = extras.getString("cust_num");
        }

        if (isCoupon.equalsIgnoreCase("yes")) {
            edt_sale.setEnabled(false);
            edt_emp.setEnabled(false);
        }

        db.open();
        qty = db.getBottleQty(item.item_code);
        price = Double.parseDouble(db.getBottlePrice(item.item_code));
        txt_qty.setText("Available Qty: " + qty);
//        db.close();

        List<String> list = new ArrayList<String>();

        list.add("Select Reason");
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");

        txt_name.setText(item.item_name_en);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_reason = (Spinner) findViewById(R.id.sp_reason);
        sp_reason.setAdapter(dataAdapter);

        ImageView img_qr = (ImageView) findViewById(R.id.img_qr);

//        sequence = new MaterialShowcaseSequence(this, "QRCODE");
//        ShowcaseConfig config = new ShowcaseConfig();
//        config.setDelay(500); // half second between each showcase view
//        sequence.setConfig(config);
//
//        sequence.addSequenceItem(img_qr,
//                "Scan QR/BAR Code", "GOT IT");
//
//        sequence.start();


        if (isCoupon.equals("no")) {
            ll_scan_code.setVisibility(View.GONE);

            if (item_type.equalsIgnoreCase("bottle")) {
                ll_empty.setVisibility(View.VISIBLE);
                ll_reason.setVisibility(View.VISIBLE);
                ll_selling.setVisibility(View.VISIBLE);

//                edt_sale.setEnabled(true);
//                edt_sale.setClickable(true);
//                edt_sale.setFocusable(true);
                edt_sale.setHint("Bottle");

            } else if (item_type.equalsIgnoreCase("empty")) {
                ll_scan_code.setVisibility(View.GONE); // NOT ABLE TO SCAN
                ll_reason.setVisibility(View.GONE);
                ll_empty.setVisibility(View.GONE);
//                edt_sale.setEnabled(true);
//                edt_sale.setClickable(true);
//                edt_sale.setFocusable(true);
                edt_sale.setHint("Bottle");
                edt_sale.requestFocus();

            } else if (item_type.equalsIgnoreCase("coupon")) {
                ll_empty.setVisibility(View.GONE);
                ll_reason.setVisibility(View.GONE);
                ll_selling.setVisibility(View.VISIBLE);// ABLE TO SCAN
                ll_scan_code.setVisibility(View.VISIBLE);

//                edt_sale.setEnabled(false);
//                edt_sale.setClickable(false);
//                edt_sale.setFocusable(false);
                edt_sale.setHint("Pcs");
            } else {
                ll_scan_code.setVisibility(View.GONE); // NOT ABLE TO SCAN NO EMPTY
                ll_reason.setVisibility(View.GONE);
                ll_empty.setVisibility(View.GONE);
//                edt_sale.setEnabled(true);
//                edt_sale.setClickable(true);
//                edt_sale.setFocusable(true);
                edt_sale.setHint("Pcs");
                edt_sale.requestFocus();
            }

        } else {
            ll_scan_code.setVisibility(View.VISIBLE);

            if (item_type.equalsIgnoreCase("bottle")) {
                ll_empty.setVisibility(View.VISIBLE);
                ll_reason.setVisibility(View.VISIBLE);
                ll_selling.setVisibility(View.VISIBLE); // ABLE TO SCAN
                ll_scan_code.setVisibility(View.VISIBLE);
                edt_sale.setHint("Bottle");
//                edt_sale.setEnabled(false);
            } else if (item_type.equalsIgnoreCase("coupon")) {
                ll_empty.setVisibility(View.GONE);
                ll_reason.setVisibility(View.GONE);
                ll_selling.setVisibility(View.VISIBLE);// ABLE TO SCAN
                ll_scan_code.setVisibility(View.VISIBLE);

//                edt_sale.setEnabled(false);
//                edt_sale.setClickable(false);
//                edt_sale.setFocusable(false);
                edt_sale.setHint("Pcs");
            } else if (item_type.equalsIgnoreCase("empty")) {
                ll_scan_code.setVisibility(View.GONE); // NOT ABLE TO SCAN
                ll_reason.setVisibility(View.GONE);
                ll_empty.setVisibility(View.GONE);
//                edt_sale.setEnabled(true);
//                edt_sale.setClickable(true);
//                edt_sale.setFocusable(true);
                edt_sale.setHint("Bottle");
                edt_sale.requestFocus();
            } else {
                ll_scan_code.setVisibility(View.GONE); // NOT ABLE TO SCAN NO EMPTY
                ll_reason.setVisibility(View.GONE);
                ll_empty.setVisibility(View.GONE);
//                edt_sale.setEnabled(true);
//                edt_sale.setClickable(true);
//                edt_sale.setFocusable(true);
                edt_sale.setHint("Pcs");
                edt_sale.requestFocus();
            }
        }

//        edt_sale.setEnabled(false);
//        edt_sale.setClickable(false);
//        edt_sale.setFocusable(false);

        img_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilApp.checkPermission(Manifest.permission.CAMERA, InputDailogActivity.this)) {
                    Intent intent = new Intent(InputDailogActivity.this, ScanActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    ActivityCompat.requestPermissions(InputDailogActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }
            }
        });
//

        Button btn_confirm = (Button) findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Double.parseDouble(qty) <
                        Double.parseDouble(edt_sale.getText().toString())) {
                    Toast.makeText(InputDailogActivity.this,
                            "You have entered qty more then stock",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (item_type.equalsIgnoreCase("bottle")) {

                    if (edt_sale.getText().toString().equals("")) {
                        Toast.makeText(InputDailogActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                    } else if (!edt_sale.getText().toString().equalsIgnoreCase(edt_emp.getText().toString())) {
                        //&& sp_reason.getSelectedItemPosition() == 0) {
//                        Toast.makeText(InputDailogActivity.this, "Please select reason", Toast.LENGTH_SHORT).show();


                        if (Double.parseDouble(edt_emp.getText().toString()) >
                                Double.parseDouble(edt_sale.getText().toString())) {

                            double diffQty = Double.parseDouble(edt_emp.getText().toString())
                                    - Double.parseDouble(edt_sale.getText().toString());
                            db.updateVanStockEmptiesAdd("13000000", "" + diffQty);


                            double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;

                            intent.putExtra("tag", newOrOld);
                            intent.putExtra("bottle", edt_sale.getText().toString());
                            intent.putExtra("price", final_price);
                            item.item_qty = edt_sale.getText().toString();
                            item.is_empty = "0";
                            item.item_emp_qty = edt_emp.getText().toString();
                            item.item_price = "" + final_price;
                            intent.putExtra("item", item);
                            intent.putExtra("barcodeArr", arr);
                            intent.putExtra("empt_type", emp_type);
                            intent.putExtra("isCoupon", isCoupon);

                            if (isCoupon.equalsIgnoreCase("yes")) {// COUPON YES
                                if (edt_coupon_code.getText().toString().trim().equalsIgnoreCase("")) {
                                    Toast.makeText(InputDailogActivity.this,
                                            "Please scan coupon", Toast.LENGTH_SHORT).show();
                                } else {
                                    sendBroadcast(intent);
                                    finish();
                                }

                            } else { // COUPON NO OR DIRECT DISTRIBUTOR
                                sendBroadcast(intent);
                                finish();
                            }
                            return;
                        }

                        final Dialog alertDialog = new Dialog(InputDailogActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.dialog_cash_custody);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                        alertDialog.findViewById(R.id.rl_cash).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                emp_type = "cash";
                                double sel_qry = Double.parseDouble(edt_sale.getText().toString());
                                double emp_qty = Double.parseDouble(edt_emp.getText().toString());

                                double diff = sel_qry - emp_qty;
                                emptry_bttles = "" + diff;
                                alertDialog.dismiss();

                            }
                        });

                        alertDialog.findViewById(R.id.rl_custody).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                alertDialog.dismiss();


//                                main_layout.setVisibility(View.GONE);
//                                waiting_layout.setVisibility(View.VISIBLE);
//                                mTitle.setText("Custody Sale");


                                pDialog = new SweetAlertDialog(InputDailogActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait for Supervisor approval...");
                                pDialog.setCancelable(false);
                                pDialog.show();

                                double diffQty = Double.parseDouble(edt_sale.getText().toString())
                                        - Double.parseDouble(edt_emp.getText().toString());

                                request_for_approval("SV1", cust_num + " \n" + custName,
                                        UtilApp.ReadSharePrefrenceString(InputDailogActivity.this,
                                                Constant.SALESMAN.SALESMAN_ROUTE),
                                        "" + diffQty);
                            }
                        });

                        if (emp_type.equalsIgnoreCase("")) {

                            alertDialog.show();
                        } else {
                            if (isCoupon.equalsIgnoreCase("yes") &&
                                    edt_coupon_code.getText().toString().equals("")) {
                                Toast.makeText(InputDailogActivity.this, "Please scan coupon", Toast.LENGTH_SHORT).show();
                            } else {

                                double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;

                                intent.putExtra("tag", newOrOld);
                                intent.putExtra("bottle", edt_sale.getText().toString());
                                intent.putExtra("price", final_price);
                                item.item_qty = edt_sale.getText().toString();
                                item.is_empty = "0";
                                item.item_emp_qty = edt_emp.getText().toString();
                                item.item_price = "" + final_price;
                                intent.putExtra("item", item);
                                intent.putExtra("barcodeArr", arr);
                                intent.putExtra("empt_type", emp_type);
                                intent.putExtra("isCoupon", isCoupon);
                                intent.putExtra("empty_bottles", emptry_bttles);

                                if (emp_type.equalsIgnoreCase("custody") &&
                                        Double.parseDouble(edt_emp.getText().toString()) !=
                                                Double.parseDouble(edt_sale.getText().toString())) {
                                    Toast.makeText(InputDailogActivity.this,
                                            "Please match you selling and empty qty, both should be same", Toast.LENGTH_SHORT).show();

                                    edt_sale.startAnimation(CommonMethods.animation(getApplicationContext()));
                                } else {
                                    sendBroadcast(intent);
                                    finish();
                                }


                            }
                        }

                    } else {

                        if (isCoupon.equalsIgnoreCase("yes") &&
                                edt_coupon_code.getText().toString().equals("")) {
                            Toast.makeText(InputDailogActivity.this, "Please scan coupon", Toast.LENGTH_SHORT).show();
                        } else {

                            double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;

                            intent.putExtra("tag", newOrOld);
                            intent.putExtra("bottle", edt_sale.getText().toString());
                            intent.putExtra("price", final_price);
                            item.item_qty = edt_sale.getText().toString();
                            item.is_empty = "0";
                            item.item_emp_qty = edt_emp.getText().toString();
                            item.item_price = "" + final_price;
                            intent.putExtra("item", item);
                            intent.putExtra("barcodeArr", arr);
                            intent.putExtra("empt_type", emp_type);
                            intent.putExtra("isCoupon", isCoupon);

                            intent.putExtra("empty_bottles", emptry_bttles);

                            sendBroadcast(intent);
                            finish();
                        }
                    }
                } else if (item_type.equalsIgnoreCase("coupon")) {
                    if (edt_sale.getText().toString().equals("") ||
                            edt_coupon_code.getText().toString().equals("")) {
                        Toast.makeText(InputDailogActivity.this, "Please Scan Coupon and enter quantity", Toast.LENGTH_SHORT).show();
                    } else {
                        double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;

                        intent.putExtra("tag", newOrOld);
                        intent.putExtra("bottle", edt_sale.getText().toString());
                        intent.putExtra("price", final_price);
                        item.item_qty = edt_sale.getText().toString();
                        item.is_empty = "0";
                        item.item_emp_qty = "0";
                        item.item_price = "" + final_price;
                        intent.putExtra("item", item);
                        intent.putExtra("barcodeArr", arr);
                        intent.putExtra("isCoupon", isCoupon);
                        intent.putExtra("empt_type", emp_type);
                        intent.putExtra("empty_bottles", emptry_bttles);

                        sendBroadcast(intent);
                        finish();
                    }
                } else {

                    if (!edt_sale.getText().toString().equals("")) {
                        double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;
                        if (Double.parseDouble(edt_sale.getText().toString()) <= Double.parseDouble(qty)) {

                            intent.putExtra("tag", "no");
                            intent.putExtra("bottle", edt_sale.getText().toString());
                            intent.putExtra("price", final_price);
                            intent.putExtra("empty", "0");
                            item.item_qty = edt_sale.getText().toString();
                            item.is_empty = "0";
                            item.item_emp_qty = "0";
                            item.item_price = "" + final_price;
                            intent.putExtra("item", item);
                            intent.putExtra("isCoupon", isCoupon);

                            intent.putStringArrayListExtra("barcodeArr", arr);
                            sendBroadcast(intent);
                            finish();
                        } else {
                            new SweetAlertDialog(InputDailogActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Entered quantity exceed to van stock limit!")
                                    .show();
                        }
                    } else {
                        Toast.makeText(InputDailogActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        edt_sale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!emp_type.equalsIgnoreCase("custody"))
                        edt_emp.setText(edt_sale.getText().toString());
                } catch (Exception e) {
                    edt_sale.setText("");
                    edt_emp.setText("");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
//                btn_personal_signup.setEnabled(true);
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(InputDailogActivity.this, ScanActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InputDailogActivity.this);
                    alertDialogBuilder.setMessage("Kindly grant all permission, we respect your privacy and data!");
                    alertDialogBuilder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null)));
                                    }
                                }
                            });

                    alertDialogBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                return;
            }
        }
    }

    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            edt_emp.setEnabled(true);
            edt_sale.setEnabled(true);

            qty = db.getBottleQty(item.item_code);
            if (data != null) {
                String message = data.getStringExtra("code");


                if (edt_sale.getText().toString().equals(""))
                    edt_sale.setText("0");

                double current = Double.parseDouble(edt_sale.getText().toString());

                if (Double.parseDouble(edt_sale.getText().toString()) <= Double.parseDouble(qty) - 1) {
//                    current++;
//                    edt_sale.setText("" + current);
                    arr.add(message);
                    edt_coupon_code.setText(arr.toString());
//                    edt_emp.setText("" + current);

                    if (emp_type.equalsIgnoreCase("")) {
                        switch (message) {
                            case Constant.BARCODE.COUPON_BUSINESS_20:
                                edt_sale.setText("20");
                                edt_emp.setText("20");
                                break;
                            case Constant.BARCODE.COUPON_BUSINESS_50:
                                edt_sale.setText("50");
                                edt_emp.setText("50");
                                break;
                            case Constant.BARCODE.COUPON_BUSINESS_100:
                                edt_sale.setText("100");
                                edt_emp.setText("100");
                                break;
                            case Constant.BARCODE.LEAFLET_BUSINESS:
                                edt_sale.setText("1");
                                edt_emp.setText("1");
                                break;


                            case Constant.BARCODE.COUPON_HOME_20:
                                edt_sale.setText("20");
                                edt_emp.setText("20");
                                break;
                            case Constant.BARCODE.COUPON_HOME_50:
                                edt_sale.setText("50");
                                edt_emp.setText("50");
                                break;
                            case Constant.BARCODE.COUPON_HOME_100:
                                edt_sale.setText("100");
                                edt_emp.setText("100");
                                break;
                            case Constant.BARCODE.LEAFLET_HOME:
                                edt_sale.setText("1");
                                edt_emp.setText("1");
                                break;
                            default:
                                current++;
                                edt_sale.setText("" + current);
                                edt_coupon_code.setText(arr.toString());
                                edt_emp.setText("" + current);
                                break;

                        }
                    }

//                    edt_sale.setEnabled(true);
//                    edt_sale.setClickable(true);
//                    edt_sale.setFocusable(true);

                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Entered quantity exceed to van stock limit!")
                            .show();
                }
            }
//            textView1.setText(message);
        }
    }


    private void request_for_approval(final String supervisor_id,
                                      String cust_id,
                                      String salesman_id, String no_of_bottles) {
        RestClient.getMutualTransfer().request_for_approval(supervisor_id,
                cust_id,
                salesman_id,
                no_of_bottles,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.v("", "Response: " + response);

                        try {

//                            pDialog.dismissWithAnimation();

                            JSONObject jsonObject = new JSONObject(UtilApp.getString(response.getBody().in()));
                            Log.v("", "==== Json: " + jsonObject.toString());

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

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {

            if (intent.getStringExtra("status").equals("yes")) {

                pDialog.dismissWithAnimation();
                new SweetAlertDialog(InputDailogActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Approved!")
                        .setContentText("You request has been approved!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                emp_type = "custody";
                                emptry_bttles = intent.getStringExtra("no_of_bottles");
                                edt_emp.startAnimation(CommonMethods.animation(getApplicationContext()));
                                Double approved_qty = Double.parseDouble(intent.getStringExtra("no_of_bottles"));
                                Double exisiting_qty = Double.parseDouble(edt_emp.getText().toString());
                                Double tot = approved_qty + exisiting_qty;
                                edt_emp.setText("" + tot);

                                txt_approved.setVisibility(View.VISIBLE);
                                txt_approved.setText("Your Supervisor has approved " + approved_qty + " Bottles.");
                                edt_emp.setEnabled(false);
                                img_approved.setVisibility(View.VISIBLE);
                                img_approved.startAnimation(CommonMethods.animation(getApplicationContext()));
                                txt_approved.startAnimation(CommonMethods.animation(getApplicationContext()));

                            }
                        })
                        .show();
            } else {

                pDialog.dismissWithAnimation();
                new SweetAlertDialog(InputDailogActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Rejected!")
                        .setContentText("You request has been rejected!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver2);
    }
}