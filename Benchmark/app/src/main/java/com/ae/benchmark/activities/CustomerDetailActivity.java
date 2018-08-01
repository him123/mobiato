package com.ae.benchmark.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterAudit;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Transaction;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CustomerDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBManager db;
    Context context;
    RecyclerAdapterAudit adapter;
    @InjectView(R.id.recyclerAudit)
    RecyclerView recyclerAudit;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.inject(this);
        context = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customer = extras.getParcelable("cust");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(customer.cust_name_en);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db = new DBManager(context);
        transactions = db.getAllTransactionsCust(customer.cust_num);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerAudit.setLayoutManager(mLayoutManager);

        adapter = new RecyclerAdapterAudit(transactions, context, "");
        recyclerAudit.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
