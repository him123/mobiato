package com.ae.benchmark.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapter;
import com.ae.benchmark.adapters.RecyclerAdapterLoadUnloadHistory;
import com.ae.benchmark.fragments.FragmentAddCustOne;
import com.ae.benchmark.fragments.FragmentAddCustTwo;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class LoadUnloadHistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;


    TextView mTitle;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;
    DBManager db;
    ArrayList<Transaction> transactions;

    RecyclerAdapterLoadUnloadHistory recyclerAdapterLoadUnloadHistory;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_unload_history);
        ButterKnife.inject(this);

        transactions = new ArrayList<>();
        db = new DBManager(LoadUnloadHistoryActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        if (getIntent().getExtras().getString("type").equalsIgnoreCase("load"))
            mTitle.setText("Loads");
        else
            mTitle.setText("Unload");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getExtras().getString("type").equalsIgnoreCase("load"))
            transactions = db.getConfLoadUnloadTransactions(Constant.TRANSACTION_TYPES.TT_LOAD_CONF);
        else
            transactions = db.getConfLoadUnloadTransactions(Constant.TRANSACTION_TYPES.TT_UNLOAD);

        mLayoutManager = new LinearLayoutManager(LoadUnloadHistoryActivity.this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapterLoadUnloadHistory =
                new RecyclerAdapterLoadUnloadHistory(transactions, LoadUnloadHistoryActivity.this);

        recyclerview_items.setAdapter(recyclerAdapterLoadUnloadHistory);

    }
}
