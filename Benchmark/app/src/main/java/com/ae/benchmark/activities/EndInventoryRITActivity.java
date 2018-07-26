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
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.adapters.RecyclerItemsUnloadAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
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

public class EndInventoryRITActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    RecyclerItemsUnloadAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    DBManager dbManager;
    public static final String BROADCAST_ACTION_END_INVENTORY = "com.benchmark.CHECKIN_END_INVENTORY";
//    String load_no;
//    String isBack;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.inject(this);

        intent = new Intent(BROADCAST_ACTION_END_INVENTORY);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            load_no = extras.getString("load_no");
//            isBack = extras.getString("isBack");
//        }

        registerReceiver(broadcastReceiver2, new IntentFilter(RecyclerItemsAdapter.BROADCAST_ACTION));


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Stock Remain In Truck");

        itemList = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbManager = new DBManager(EndInventoryRITActivity.this);


        registerReceiver(broadcastReceiver2, new IntentFilter(RecyclerItemsUnloadAdapter.BROADCAST_ACTION));
        registerReceiver(broadcastReceiverCHK, new IntentFilter(RecyclerItemsUnloadAdapter.BROADCAST_ACTION_CHK));

        dbManager.open();
//        dbManager.updateLoadVerified(load_no);
//
//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrCheckedItems.size() == 0 && arrCheckedQty.size() == 0) {
                    Toast.makeText(EndInventoryRITActivity.this, "Please select atlease one item", Toast.LENGTH_SHORT).show();
                } else {

                    UtilApp.WriteSharePrefrence(EndInventoryRITActivity.this, Constant.SHRED_PR.ISUNLOAD, true);
                    for (int i = 0; i < arrCheckedItems.size(); i++) {

                        dbManager.processUnload(arrCheckedItems.get(i), arrCheckedQty.get(i));

                        //UNLOAD
                        UtilApp.WriteSharePrefrence(EndInventoryRITActivity.this,
                                Constant.SHRED_PR.ISUNLOAD, true);

                        //FOR CHECK IN AFTER UNLOAD
                        UtilApp.WriteSharePrefrence(EndInventoryRITActivity.this,
                                Constant.SHRED_PR.ISCHECKIN, true);


                        new SweetAlertDialog(EndInventoryRITActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Done")
                                .setContentText("Fresh unload created!")
                                .setConfirmText("Ok!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        sendBroadcast(intent);
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }


    protected void onResume()// Enter from Bottom to Top
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        itemList.clear();
        dbManager.open();
        itemList = dbManager.getVanStock();

        for (int i = 0; i < itemList.size(); i++) {
//            arrCheckedItems.add(itemList.get(i).item_code);
//            arrCheckedQty.add(itemList.get(i).item_qty);
        }

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsUnloadAdapter(itemList, this);
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
            Item item = intent.getParcelableExtra("item");

            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).item_code.equals(item.item_code)) {
                    itemList.get(i).item_qty = item.item_qty;
                }
            }

//            arrCheckedQty.add(item.item_qty);

            recyclerAdapter.notifyDataChanged();
        }
    };

    ArrayList<String> arrCheckedItems = new ArrayList<>();
    ArrayList<String> arrCheckedQty = new ArrayList<>();

    private BroadcastReceiver broadcastReceiverCHK = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getBooleanExtra("isChecked", false)) {
                arrCheckedItems.add(intent.getStringExtra("item_code"));
                arrCheckedQty.add(intent.getStringExtra("item_qty"));
//                Toast.makeText(EndInventoryRITActivity.this,
//                        "test: " + intent.getStringExtra("item_code") + " added", Toast.LENGTH_SHORT).show();
            } else {
                arrCheckedItems.remove(intent.getStringExtra("item_code"));
                arrCheckedQty.add(intent.getStringExtra("item_qty"));
//                Toast.makeText(EndInventoryRITActivity.this,
//                        "test: " + intent.getStringExtra("item_code") + " removed", Toast.LENGTH_SHORT).show();
            }


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver2);
        unregisterReceiver(broadcastReceiverCHK);
    }

}
