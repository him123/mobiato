package com.ae.benchmark.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterAudit;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class CustomerPrintActivity extends AppCompatActivity {


    private Toolbar toolbar;
    TextView mTitle;

    @InjectView(R.id.recyclerAudit)
    RecyclerView recyclerAudit;
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBManager db;
    Context context;
    RecyclerAdapterAudit adapter;

    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_print);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customer = getIntent().getParcelableExtra("cust");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("Print");

        db = new DBManager(CustomerPrintActivity.this);
        db.open();

        transactions = db.getAllTransactionsForCustomer(customer.cust_num);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CustomerPrintActivity.this);
        recyclerAudit.setLayoutManager(mLayoutManager);

        adapter = new RecyclerAdapterAudit(transactions, context, "");
        recyclerAudit.setAdapter(adapter);

    }
}
