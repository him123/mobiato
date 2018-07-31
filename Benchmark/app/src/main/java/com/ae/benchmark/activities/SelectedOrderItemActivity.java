package com.ae.benchmark.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapterForALL;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectedOrderItemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    RecyclerItemsAdapterForALL recyclerAdapter;

    public static List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_order_item);
        ButterKnife.inject(this);

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

        dbManager = new DBManager(getApplicationContext());

        dbManager.open();

        itemList = dbManager.getAllItems();

        List<Item> newItemArr = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if (!itemList.get(i).item_qty.equals("0")) {
                Item item = itemList.get(i);
                newItemArr.add(item);
            }
        }

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsAdapterForALL(newItemArr, this, false);
        recyclerview_items.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();
    }
}
