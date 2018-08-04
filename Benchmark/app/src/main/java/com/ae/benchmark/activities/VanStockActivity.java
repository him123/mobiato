package com.ae.benchmark.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.adapters.SalesHistoryAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VanStockActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    RecyclerItemsAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    private Toolbar toolbar;
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("Van Stock");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setVisibility(View.GONE);
        try {
            DBManager dbManager = new DBManager(VanStockActivity.this);
            dbManager.open();
            itemList = new ArrayList<>();

            itemList.clear();
            itemList = dbManager.getVanStock(true);

            mLayoutManager = new LinearLayoutManager(this);
            recyclerview_items.setLayoutManager(mLayoutManager);

            recyclerAdapter = new RecyclerItemsAdapter(itemList, this, true);
            recyclerview_items.setAdapter(recyclerAdapter);

            recyclerAdapter.notifyDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
