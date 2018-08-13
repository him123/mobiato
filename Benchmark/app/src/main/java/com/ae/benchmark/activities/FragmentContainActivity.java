package com.ae.benchmark.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.fragments.FragmentCOCollection;
import com.ae.benchmark.fragments.FragmentCODelivery;
import com.ae.benchmark.fragments.FragmentCOMerchandising;
import com.ae.benchmark.fragments.FragmentCOOrder;
import com.ae.benchmark.fragments.FragmentCOReturns;
import com.ae.benchmark.fragments.FragmentCOSales;
import com.ae.benchmark.model.Customer;

import butterknife.ButterKnife;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentContainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String flag = "";
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_containr);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getString("flag");
            customer = extras.getParcelable("cust");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("SALES");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = null;

        if (flag.equals("SAL")) {
            mTitle.setText("SALES");
            fragment = new FragmentCOSales();
        } else if (flag.equals("ORD")) {
            mTitle.setText("ORDER");
            fragment = new FragmentCOOrder(customer);
        } else if (flag.equals("COL")) {
            mTitle.setText("COLLECTIONS");
            fragment = new FragmentCOCollection(customer);
        } else if (flag.equals("MER")) {
            mTitle.setText("MERCHANDISING");
            fragment = new FragmentCOMerchandising();
        } else if (flag.equals("DEL")) {
            mTitle.setText("DELEVERY");
            fragment = new FragmentCODelivery();
        } else if (flag.equals("RETURNS")) {
            mTitle.setText("RETURNS");
            fragment = new FragmentCOReturns(customer);
        }

        fragmentTransaction.add(R.id.fragment_container, fragment, "HELLO");
        fragmentTransaction.commit();
    }
}
