package com.ae.benchmark.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
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

    @InjectView(R.id.ll_empty)
    LinearLayout ll_empty;

    @InjectView(R.id.ll_reason)
    LinearLayout ll_reason;

    @InjectView(R.id.ll_scan_code)
    LinearLayout ll_scan_code;

    @InjectView(R.id.ll_selling)
    LinearLayout ll_selling;

    boolean flag;

    Item item;

    String newOrOld, item_type;
    Intent intent;
    public static final String BROADCAST_ACTION = "com.benchmark.DIALOG";
    String qty;
    DBManager db;
    Double price;
    Spinner sp_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input_item_dialog);
        ButterKnife.inject(this);

        db = new DBManager(InputDailogActivity.this);
        intent = new Intent(BROADCAST_ACTION);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isCoupon = extras.getString("isCoupon");
            flag = extras.getBoolean("isScan");
            item = extras.getParcelable("item");
            newOrOld = extras.getString("tag");
            item_type = extras.getString("item_type");
        }

        db.open();
        qty = db.getBottleQty(item.item_code);
        price = Double.parseDouble(db.getBottlePrice(item.item_code));
        txt_qty.setText("Available Qty: " + qty);
        db.close();


//
//        if (item_type.equals("coupon")) {
//            ll_selling.setVisibility(View.GONE);
//            ll_reason.setVisibility(View.GONE);
//            ll_scan_code.setVisibility(View.GONE);
//        } else if (item_type.equals("bottle")) {
//            edt_sale.setClickable(true);
//            ll_empty.setVisibility(View.VISIBLE);
//        }

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
                ll_empty.setVisibility(View.GONE);
                ll_reason.setVisibility(View.GONE);
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


                if (item_type.equalsIgnoreCase("empty")) {
                    if (!edt_sale.getText().toString().equals("")) {
                        double final_price = Double.parseDouble(edt_sale.getText().toString()) * price;
                        if (Double.parseDouble(edt_sale.getText().toString()) <= Double.parseDouble(qty)) {

                            intent.putExtra("tag", "show");
                            intent.putExtra("bottle", edt_sale.getText().toString());
                            intent.putExtra("price", final_price);
                            intent.putExtra("empty", "1");
                            item.item_qty = edt_sale.getText().toString();
                            item.is_empty = "1";
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


                } else if (item_type.equalsIgnoreCase("bottle")) {

                    if (edt_sale.getText().toString().equals("")) {
//                        if (isCoupon.equalsIgnoreCase("yes"))
//                            Toast.makeText(InputDailogActivity.this, "Please Scan Coupon", Toast.LENGTH_SHORT).show();
//                        else
                        Toast.makeText(InputDailogActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                    } else if ((!edt_sale.getText().toString().equalsIgnoreCase(edt_emp.getText().toString()))
                            && sp_reason.getSelectedItemPosition() == 0) {
                        Toast.makeText(InputDailogActivity.this, "Please select reason", Toast.LENGTH_SHORT).show();
                    } else {

                        if (isCoupon.equalsIgnoreCase("yes") && edt_coupon_code.getText().toString().equals("")) {
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

                        sendBroadcast(intent);
                        finish();
                    }
                }else{

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

            qty = db.getBottleQty(item.item_code);
            if (data != null) {
                String message = data.getStringExtra("code");


                if (edt_sale.getText().toString().equals(""))
                    edt_sale.setText("0");

                double current = Double.parseDouble(edt_sale.getText().toString());

                if (Double.parseDouble(edt_sale.getText().toString()) <= Double.parseDouble(qty) - 1) {
                    current++;
                    edt_sale.setText("" + current);
                    arr.add(message);
                    edt_coupon_code.setText(arr.toString());
                    edt_emp.setText("" + current);

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
}