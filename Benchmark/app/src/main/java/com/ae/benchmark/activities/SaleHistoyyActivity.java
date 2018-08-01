package com.ae.benchmark.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.SalesHistoryAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Transaction;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SaleHistoyyActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerAudit)
    RecyclerView recyclerAudit;
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBManager db;
    Context context;
    SalesHistoryAdapter adapter;

    private Toolbar toolbar;
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_histoyy);
        ButterKnife.inject(this);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("Sales History");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = new DBManager(context);
        transactions = db.getAllTransactionsSales();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerAudit.setLayoutManager(mLayoutManager);

        adapter = new SalesHistoryAdapter(transactions , context , "");
        recyclerAudit.setAdapter(adapter);
    }
}
