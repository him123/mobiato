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
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.localdb.DatabaseHelper;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseMessagingService;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class ItemsListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

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
        dbManager.updateLoadVerified(load_no);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.open();
                dbManager.insertVanStockArr(itemList, load_no);
                if (dbManager.checkIsNotVerified()) {

                    finish();
                } else {
                    if (isBack.equals("No")) {


                        transaction.tr_type = Constant.TRANSACTION_TYPES.TT_LOAD_CONF;
                        transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                        transaction.tr_customer_num = "";
                        transaction.tr_customer_name = "";
                        transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                        transaction.tr_invoice_id = "";
                        transaction.tr_order_id = "";
                        transaction.tr_collection_id = "";
                        transaction.tr_pyament_id = "";
                        transaction.tr_is_posted = "No";

                        dbManager.insertTransaction(transaction);

                        startActivity(new Intent(ItemsListActivity.this, SelectCustomerListMainActivity.class));


                    } else
                        finish();
                }

//                finish();
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
}
