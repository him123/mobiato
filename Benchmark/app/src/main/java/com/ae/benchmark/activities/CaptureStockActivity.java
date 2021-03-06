package com.ae.benchmark.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class CaptureStockActivity extends AppCompatActivity {

    DBManager dbManager;

    @InjectView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    @InjectView(R.id.edt_empt)
    EditText edt_empt;

    private Toolbar toolbar;
    TextView mTitle;
    String oldNew;

    Customer customer;
    String filledBottles;
    String emptydBottles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_stock);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customer = extras.getParcelable("cust");
            oldNew = extras.getString("tag");
        }

        dbManager = new DBManager(CaptureStockActivity.this);
        dbManager.open();

        filledBottles = dbManager.getLoadEmptyOrFilledBottles(1, customer.cust_num);
        emptydBottles = dbManager.getLoadEmptyOrFilledBottles(0, customer.cust_num);

        edt_empt.setText(emptydBottles);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("Capture Stock");

        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int empty = Integer.parseInt(emptydBottles);
                int inputed = Integer.parseInt(edt_empt.getText().toString());

                int tot = empty + inputed;

                UtilApp.WriteSharePrefrence(CaptureStockActivity.this, Constant.SHRED_PR.ISSTOCKCAPTURED, true);
//
//                UtilApp.WriteSharePrefrence(CaptureStockActivity.this, Constant.SHRED_PR.EMPTYES, "" + tot);

                Toast.makeText(CaptureStockActivity.this, "Stock captured!", Toast.LENGTH_SHORT).show();


                String date = UtilApp.getCurrentDate();
                String time = UtilApp.getCurrentTime();

                dbManager.updateEmptyBottles("" + tot, customer.cust_num);

                dbManager.insertTransaction("Stock Captured", date, time);

                finish();

//                new SweetAlertDialog(CaptureStockActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Stock Captured!")
//                        .setContentText("Do you want to go to sales?")
//                        .setCancelText("Back")
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                finish();
//                            }
//                        })
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
////                                sDialog.dismissWithAnimation();
//
//                                makeDilog(CaptureStockActivity.this, customer.cust_name_en, oldNew);
//                            }
//                        })
//                        .show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void makeDilog(final Context context, final String custName, final String oldOrNew) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_coupon_or_not, null);
        TextView txt_title = deleteDialogView.findViewById(R.id.txt_title);
        txt_title.setText("COUPON");
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.rl_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

                Intent i = new Intent(CaptureStockActivity.this, PreOrderRequestActivity.class);
                i.putExtra("isScan", "yes");
                i.putExtra("type", "cash");
                i.putExtra("name", custName);
                i.putExtra("tag", oldOrNew);
                startActivity(i);
            }
//                });

//                deleteDialogView.findViewById(R.id.rl_custody).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //your business logic
//                        deleteDialog.dismiss();
//
//                        Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
//                        i.putExtra("isScan", "yes");
//                        i.putExtra("type", "custody");
//                        i.putExtra("name", custName);
//                        i.putExtra("tag", oldOrNew);
//                        startActivity(i);
//                    }
//                });

//                deleteDialogView.findViewById(R.id.rl_normal).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //your business logic
//                        deleteDialog.dismiss();
//
//                        Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
//                        i.putExtra("isScan", "yes");
//                        i.putExtra("type", "norm");
//                        i.putExtra("name", custName);
//                        i.putExtra("tag", oldOrNew);
//                        startActivity(i);
//                    }
//                });
//
//                deleteDialog.show();
//            }
        });

        deleteDialogView.findViewById(R.id.rl_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

                Intent i = new Intent(CaptureStockActivity.this, PreOrderRequestActivity.class);
                i.putExtra("isScan", "no");
                i.putExtra("type", "norm");
                i.putExtra("name", custName);
                i.putExtra("tag", oldOrNew);
                startActivity(i);
            }
        });

        deleteDialog.show();
    }

}
