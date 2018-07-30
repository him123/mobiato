package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.RecentCustomer;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.model.Transaction;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentDashboardTwo extends Fragment {


    @InjectView(R.id.txtTotalSale)
    TextView txtTotalSale;
    @InjectView(R.id.txtTotalCollection)
    TextView txtTotalCollection;
    @InjectView(R.id.txtNoOfEmpties)
    TextView txtNoOfEmpties;
    @InjectView(R.id.txtValueOfEmpties)
    TextView txtValueOfEmpties;
    @InjectView(R.id.txtDropCustomer)
    TextView txtDropCustomer;
    @InjectView(R.id.txtDropInvoice)
    TextView txtDropInvoice;

    DBManager db;

    public FragmentDashboardTwo() {
        // Required empty public constructor
    }


    public static FragmentDashboardTwo createInstance() {
        FragmentDashboardTwo partThreeFragment = new FragmentDashboardTwo();
        return partThreeFragment;
    }

    static FragmentManager fragmentManager;

    public static Fragment newInstance() {
        FragmentDashboardTwo fragment = new FragmentDashboardTwo();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_two, container, false);
        ButterKnife.inject(this, v);
        db = new DBManager(getActivity());

        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHeadToday();

        double totSale = 0;
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equals("Sale")) {
                totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
            }
        }

        txtTotalSale.setText(String.valueOf((int) totSale));

        double totColl = db.getAllInvoiceHeadCollectionToday();
        txtTotalCollection.setText(String.valueOf((int)totColl));

        ArrayList<RecentCustomer> recentCustomers = db.getAllREcentCustToday();
        if (recentCustomers.size()>0){
            txtDropCustomer.setText(String.valueOf((int) totSale / recentCustomers.size()));
        } else {
            txtDropCustomer.setText(String.valueOf((int) totSale));
        }


        ArrayList<Transaction> transactions  = db.getAllTransactionsToday();
        if (transactions.size()>0){
            txtDropInvoice.setText(String.valueOf((int) totSale / transactions.size()));
        } else {
            txtDropInvoice.setText(String.valueOf((int) totSale));
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}