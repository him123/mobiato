package com.ae.benchmark.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.adapters.RecyclerItemsAdapterForALL;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class ALLItemsListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    @InjectView(R.id.txt_tot)
    TextView txt_tot;

    RecyclerItemsAdapterForALL recyclerAdapter;

    public static List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    DBManager dbManager;

    String load_no;
    String with_laod;

    Transaction transaction = new Transaction();
    List<Item> newItemArr = new ArrayList<>();
    int ordId = 0, loadId = 0;
    Customer customer;
    double tot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            load_no = extras.getString("load_no");
            with_laod = extras.getString("with_load");
            if (with_laod.equals("no"))
                customer = extras.getParcelable("cust");
        }

        registerReceiver(broadcastReceiver2, new IntentFilter(RecyclerItemsAdapterForALL.BROADCAST_ACTION));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ALL ITEMS");

        itemList = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbManager = new DBManager(ALLItemsListActivity.this);

        dbManager.open();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (itemList.size() <= 0) {
                    Toast.makeText(ALLItemsListActivity.this, "Please select item", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < itemList.size(); i++) {
                    if (!itemList.get(i).item_qty.equals("0")) {
                        Item item = itemList.get(i);
                        newItemArr.add(item);
                        tot += Double.parseDouble(itemList.get(i).item_price);
                    }
                }

                long lastIOrdId = dbManager.getLastOrderID();
                if (lastIOrdId == 0) {
                    ordId = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.ORD_LAST));
                } else {
                    ordId = (int) lastIOrdId + 1;
                }

                long lastLoadId = dbManager.getLastLoadHeaderNo();
                if (lastLoadId == 0) {
                    loadId = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.LOAD_LAST));
                } else {
                    loadId = (int) lastLoadId + 1;
                }
                itemList.clear();
                recyclerAdapter = new RecyclerItemsAdapterForALL(newItemArr, ALLItemsListActivity.this, false);
                recyclerview_items.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataChanged();

                fab.setVisibility(View.GONE);
                rl_checkout.setVisibility(View.VISIBLE);
                txt_tot.setText("Total SAR: " + tot);


            }
        });

        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//<<<<<<< HEAD

                if (with_laod.equals("yes")) {

                    long lastInvId = dbManager.getLastInvoiceID();

                    int invNum;
                    if (lastInvId == 0) {
                        invNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.INV_LAST));
                    } else {
                        invNum = (int) lastInvId + 1;
                    }

                    //CREATE LOAD WITH LOAD ITEMS
                    dbManager.insertLoad(newItemArr, loadId + "", UtilApp.getCurrentDate(), "0", "1", "1");

                    //INSERT TRANSACTION
                    Transaction transaction = new Transaction();

                    transaction.tr_type = Constant.TRANSACTION_TYPES.TT_LOAD_CREATE;
                    transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                    transaction.tr_customer_num = "";
                    transaction.tr_customer_name = "";
                    transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                    transaction.tr_invoice_id = invNum + "";
                    transaction.tr_order_id = ordId + "";
                    transaction.tr_collection_id = "";
                    transaction.tr_pyament_id = "";

                    dbManager.insertTransaction(transaction);

                    //CREATING INVOICE FOR ORDER
                    SalesInvoice salesInvoice = new SalesInvoice();

                    salesInvoice.inv_no = "" + invNum;
                    salesInvoice.inv_type = "Order";
                    salesInvoice.inv_type_code = "02";
                    salesInvoice.cust_code = "";
                    salesInvoice.cust_sales_org = "";
                    salesInvoice.cust_dist_channel = "";
                    salesInvoice.cust_division = "";
                    salesInvoice.inv_date = UtilApp.getCurrentDate();
                    salesInvoice.del_date = UtilApp.getCurrentDate();
                    salesInvoice.cust_name_en = "";
                    salesInvoice.tot_amnt_sales = "" + tot;
                    salesInvoice.inv_header_dis_val = "0";
                    salesInvoice.inv_header_dis_per = "0";
                    salesInvoice.inv_header_vat_val = "0";
                    salesInvoice.inv_header_vat_per = "0";

                    dbManager.insertSalesInvoiceHeader(salesInvoice);


                    new SweetAlertDialog(ALLItemsListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Load created successfully!")
                            .setContentText("Your Load request sent successfully!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
//                                    dbManager.updateCustomerTransactionType(customer.cust_num, "order", "1");
//                                    finish();

                                    UtilApp.askForPrint(ALLItemsListActivity.this, ALLItemsListActivity.this);
                                }
                            })
                            .show();

//                    dbManager.insertLoadItems(item, "1");


                } else {

                    long lastInvId = dbManager.getLastInvoiceID();
                    int invNum;
                    if (lastInvId == 0) {
                        invNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.INV_LAST));
                    } else {
                        invNum = (int) lastInvId + 1;
                    }

                    dbManager.insertOrderItems(
                            newItemArr,
                            ordId + "",
                            UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID),
                            customer.cust_num, tot + "", UtilApp.getCurrentDate());

                    //INSERT TRANSACTION
                    Transaction transaction = new Transaction();

                    transaction.tr_type = Constant.TRANSACTION_TYPES.TT_OREDER_CREATED;
                    transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                    transaction.tr_customer_num = customer.cust_num;
                    transaction.tr_customer_name = customer.cust_name_en;
                    transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                    transaction.tr_invoice_id = invNum + "";
                    transaction.tr_order_id = "";
                    transaction.tr_collection_id = "";
                    transaction.tr_pyament_id = "";

                    dbManager.insertTransaction(transaction);

                    //CREATING INVOICE FOR ORDER
                    SalesInvoice salesInvoice = new SalesInvoice();

                    salesInvoice.inv_no = "" + invNum;
                    salesInvoice.inv_type = "Order";
                    salesInvoice.inv_type_code = "02";
                    salesInvoice.cust_code = customer.cust_num;
                    salesInvoice.cust_sales_org = customer.cust_sales_org;
                    salesInvoice.cust_dist_channel = customer.cust_dist_channel;
                    salesInvoice.cust_division = customer.cust_division;
                    salesInvoice.inv_date = UtilApp.getCurrentDate();
                    salesInvoice.del_date = UtilApp.getCurrentDate();
                    salesInvoice.cust_name_en = customer.cust_name_en;
                    salesInvoice.tot_amnt_sales = "" + tot;
                    salesInvoice.inv_header_dis_val = "0";
                    salesInvoice.inv_header_dis_per = "0";
                    salesInvoice.inv_header_vat_val = "0";
                    salesInvoice.inv_header_vat_per = "0";

                    dbManager.insertSalesInvoiceHeader(salesInvoice);

                    new SweetAlertDialog(ALLItemsListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Done!")
                            .setContentText("Your order created successfully!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    dbManager.updateCustomerTransactionType(customer.cust_num, "order", "1");
//                                    finish();

                                    UtilApp.askForPrint(ALLItemsListActivity.this, ALLItemsListActivity.this);
                                }
                            })
                            .show();

                }

//=======
//                dbManager.insertOrderItems(
//                        newItemArr,
//                        ordId + "",
//                        UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID),
//                        customer.cust_num, tot+"", UtilApp.getCurrentDate());
//
//                //INSERT TRANSACTION
//                Transaction transaction = new Transaction();
//
//                transaction.tr_type = Constant.TRANSACTION_TYPES.TT_OREDER_CREATED;
//                transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
//                transaction.tr_customer_num = customer.cust_num;
//                transaction.tr_customer_name = customer.cust_name_en;
//                transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
//                transaction.tr_invoice_id = ordId+"";
//                transaction.tr_order_id = "";
//                transaction.tr_collection_id = "";
//                transaction.tr_pyament_id = "";
//                transaction.tr_is_posted = "No";
//
//                dbManager.insertTransaction(transaction);
//
//                //CREATING INVOICE FOR ORDER
//                SalesInvoice salesInvoice = new SalesInvoice();
//
//                long lastInvId = dbManager.getLastInvoiceID();
////                long lastCollId = dbManager.getLastCollectionID();
//                int invNum = (int) lastInvId + 1;
////                int CollNum = (int) lastCollId + 1;
//                salesInvoice.inv_no = "" + invNum;
//                salesInvoice.inv_type = "Order";
//                salesInvoice.inv_type_code = "02";
//                salesInvoice.cust_code = customer.cust_num;
//                salesInvoice.cust_sales_org = customer.cust_sales_org;
//                salesInvoice.cust_dist_channel = customer.cust_dist_channel;
//                salesInvoice.cust_division = customer.cust_division;
//                salesInvoice.inv_date = UtilApp.getCurrentDate();
//                salesInvoice.del_date = UtilApp.getCurrentDate();
//                salesInvoice.cust_name_en = customer.cust_name_en;
//                salesInvoice.tot_amnt_sales = "" + tot;
//                salesInvoice.inv_header_dis_val = "0";
//                salesInvoice.inv_header_dis_per = "0";
//                salesInvoice.inv_header_vat_val = "0";
//                salesInvoice.inv_header_vat_per = "0";
//
//                dbManager.insertSalesInvoiceHeader(salesInvoice);
//
//
//                new SweetAlertDialog(ALLItemsListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Done!")
//                        .setContentText("Your order created successfully!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                                dbManager.updateCustomerTransactionType(customer.cust_num, "order", "1");
//                                finish();
//                            }
//                        })
//                        .show();
//>>>>>>> 3ed8b975f5cd6631c08cc7c31d0f2649c029422b

            }
        });
    }

    protected void onResume()// Enter from Bottom to Top
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        itemList.clear();
        itemList = dbManager.getAllItems();

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsAdapterForALL(itemList, this, false);
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


            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).item_code.equals(intent.getStringExtra("item_code")))
                    itemList.get(i).item_qty = intent.getStringExtra("item_qty");
            }

            recyclerAdapter.notifyDataChanged();
//            }
        }

    };
}
