package com.ae.benchmark.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;
import com.google.android.gms.actions.ItemListIntents;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class ItemsListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.card_date)
    CardView card_date;

    @InjectView(R.id.checkbox)
    CheckBox checkbox;

    RecyclerItemsAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    DBManager dbManager;

    String load_no;
    String isBack;

    Transaction transaction = new Transaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.inject(this);

        checkbox.setVisibility(View.GONE);

        card_date.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            load_no = extras.getString("load_no");
            isBack = extras.getString("isBack");
        }

        registerReceiver(broadcastReceiver2, new IntentFilter(RecyclerItemsAdapter.BROADCAST_ACTION));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ITEMS");

        itemList = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbManager = new DBManager(ItemsListActivity.this);

        dbManager.open();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.open();

                dbManager.insertVanStockArr(itemList, load_no);
                dbManager.updateLoadVerified(load_no);

                try {
//                    JSONObject jsonObject = new JSONObject(printJson);
//                    UtilApp.askForPrint(ItemsListActivity.this,
//                            ItemsListActivity.this, jsonObject);

                    final JSONObject mainArr = new JSONObject();
                    mainArr.put("print_type", Constant.VAN_STOCK);
                    mainArr.put("ROUTE", UtilApp.ReadSharePrefrence(ItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("DOC DATE", UtilApp.getCurrentDate());
                    mainArr.put("TIME", "00:00:00");
                    mainArr.put("SALESMAN", UtilApp.ReadSharePrefrence(ItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("CONTACTNO", "1234");
                    mainArr.put("supervisorname", "-");
                    mainArr.put("LANG", "AR");
                    mainArr.put("INVOICETYPE", "ORDER REQUEST");
                    mainArr.put("invoicenumber", "5465");
                    mainArr.put("SALESMANNO", "45645");


                    mainArr.put("invoicepaymentterms", "3");
//                    mainArr.put("CUSTOMERID", customer.cust_num);
//                    mainArr.put("CUSTOMER", customer.cust_name_en);
//                    mainArr.put("ADDRESS", customer.cust_address);
//                    mainArr.put("ARBADDRESS", customer.cust_address);
                    mainArr.put("TourID", UtilApp.ReadSharePrefrence(ItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("LANG", "ar");
                    mainArr.put("invoicepaymentterms", "2");
                    mainArr.put("RECEIPT", "INVOICE RECEIPT");
                    mainArr.put("SUB TOTAL", "1000");
                    mainArr.put("INVOICE DISCOUNT", "20");
                    mainArr.put("NET SALES", "980");
                    mainArr.put("TRIP START DATE", "");

                    JSONArray TOTAL = new JSONArray();
                    JSONObject jobjTot = new JSONObject();

                    double tot = 0.0;

                    JSONArray outterData = new JSONArray();
                    JSONArray data = null;
                    double qty = 0.0;
                    for (int i = 0; i < itemList.size(); i++) {
                        Item item = itemList.get(i);
                        tot += Double.parseDouble(item.item_price);
                        qty = Double.parseDouble(item.item_qty);

                        data = new JSONArray();

                        data.put(item.item_code);
                        data.put(item.item_name_en);
                        data.put(item.item_qty);
                        data.put(item.item_qty);
                        data.put("" + qty);

                        outterData.put(data);
                    }

                    JSONArray totArr = new JSONArray();
                    JSONObject totObj = new JSONObject();

                    totObj.put("Total Amount(AED)", "" + tot);
                    totObj.put("Total Befor TAX(AED)", "" + tot);
                    totObj.put("GROSS AMOUNT: AED - ", "" + tot);
                    totArr.put(totObj);
                    mainArr.put("TOTAL", totArr);

                    mainArr.put("closevalue", tot);

                    JSONArray HEADERS = new JSONArray();
                    HEADERS.put("ITEM#");
                    HEADERS.put("DESCRIPTION");
                    HEADERS.put("LOADED QTY");
                    HEADERS.put("SALE QTY");
                    HEADERS.put("TRUCK STOCK");
                    HEADERS.put("TOTAL");

                    mainArr.put("HEADERS", HEADERS);

                    mainArr.put("data", outterData);


                    if (dbManager.checkIsNotVerified()) {

                        final Dialog alertDialog = new Dialog(ItemsListActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.dialog_print_donot_print);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        ImageView img_print = alertDialog.findViewById(R.id.img_pring);

                        img_print.setColorFilter(ContextCompat.getColor(ItemsListActivity.this, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);


                        alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic

                                alertDialog.dismiss();

                                try {
                                    PrinterHelper printerHelper = new PrinterHelper(ItemsListActivity.this,
                                            ItemsListActivity.this);
                                    printerHelper.execute(mainArr);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                alertDialog.dismiss();
                                finish();
                            }
                        });

                        alertDialog.show();

                    } else {
                        if (isBack.equals("No")) {


//                            long lastInvId = dbManager.getLastInvoiceID();
//                            int invNum = load_no;

                            transaction.tr_type = Constant.TRANSACTION_TYPES.TT_LOAD_CONF;
                            transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                            transaction.tr_customer_num = "";
                            transaction.tr_customer_name = "";
                            transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                            transaction.tr_invoice_id = load_no;
                            transaction.tr_order_id = "";
                            transaction.tr_collection_id = "";
                            transaction.tr_pyament_id = "";
                            transaction.tr_is_posted = "No";

                            dbManager.insertTransaction(transaction);


                            final Dialog alertDialog = new Dialog(ItemsListActivity.this);
                            alertDialog.setCancelable(false);
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            alertDialog.setContentView(R.layout.dialog_print_donot_print);
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            ImageView img_print = alertDialog.findViewById(R.id.img_pring);

                            img_print.setColorFilter(ContextCompat.getColor(ItemsListActivity.this, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);


                            alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //your business logic

                                    alertDialog.dismiss();

                                    try {
                                        PrinterHelper printerHelper = new PrinterHelper(ItemsListActivity.this,
                                                ItemsListActivity.this);
                                        printerHelper.execute(mainArr);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //your business logic
                                    alertDialog.dismiss();
                                    startActivity(new Intent(ItemsListActivity.this, JourneyPlanActivity.class));
                                    finish();
                                }
                            });

                            alertDialog.show();


                        } else
                            finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    protected void onResume()// Enter from Bottom to Top
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        itemList.clear();
        itemList = dbManager.getLoadItem(load_no);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsAdapter(itemList, this, false);
        recyclerview_items.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();
    }

    protected void onPause()// Exit from Top to Bottom
    {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

//    protected void onResume()// Enter from Bottom to Top
//    {
//        super.onResume();
//        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//    }
//
//    protected void onPause()// Exit from Top to Bottom
//    {
//        super.onPause();
//        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
//    }


    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("", "=========== broadcat receiver : " + intent.getStringExtra("load_no"));


//            if (intent.getStringExtra("status").equals("yes")) {
            String load_no = intent.getStringExtra("load_no");
            itemList.clear();
            itemList = dbManager.getLoadItem(load_no);

            mLayoutManager = new LinearLayoutManager(ItemsListActivity.this);
            recyclerview_items.setLayoutManager(mLayoutManager);

            recyclerAdapter = new RecyclerItemsAdapter(itemList, ItemsListActivity.this, false);
            recyclerview_items.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataChanged();
//            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver2);
    }

    public void callback() {

        dbManager = new DBManager(ItemsListActivity.this);
        dbManager.open();
        if (dbManager.checkIsNotVerified()) {
            finish();
        } else {
            startActivity(new Intent(ItemsListActivity.this, JourneyPlanActivity.class));
            finish();
        }

    }
}
