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
import com.ae.benchmark.localdb.DatabaseHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentDashboardThree extends Fragment {


    @InjectView(R.id.txt_visited_cust)
    TextView txt_visited_cust;

    @InjectView(R.id.txt_load_qty)
    TextView txt_load_qty;

    @InjectView(R.id.txt_custody_bottles)
    TextView txt_custody_bottles;

    @InjectView(R.id.txt_cash_bottles)
    TextView txt_cash_bottles;

    @InjectView(R.id.txt_planned_visits)
    TextView txt_planned_visits;

    @InjectView(R.id.txt_unplanned_visits)
    TextView txt_unplanned_visits;

    DBManager db;

    public FragmentDashboardThree() {
        // Required empty public constructor
    }


    public static FragmentDashboardThree createInstance() {
        FragmentDashboardThree partThreeFragment = new FragmentDashboardThree();
        return partThreeFragment;
    }

    static FragmentManager fragmentManager;

    public static Fragment newInstance() {
        FragmentDashboardThree fragment = new FragmentDashboardThree();
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
        View v = inflater.inflate(R.layout.fragment_dashboard_three,
                container,
                false);
        ButterKnife.inject(this, v);

        db = new DBManager(getActivity());
        db.open();
        int tot_visited_cust = db.getVisitedCust();
        txt_visited_cust.setText(tot_visited_cust + "");

        String load_qty = db.getLoadQty(DatabaseHelper.ITEM_QTY, DatabaseHelper.TABLE_VANSTOCK_ITEMS);
        txt_load_qty.setText(load_qty);

        String cash_count = db.getLoadQty(DatabaseHelper.SVH_EMPTY_TYPE,
                DatabaseHelper.TABLE_INVOICE_HEADER, "cash");
        String custody_count = db.getLoadQty(DatabaseHelper.SVH_EMPTY_TYPE,
                DatabaseHelper.TABLE_INVOICE_HEADER, "custody");

        txt_cash_bottles.setText(cash_count);
        txt_custody_bottles.setText(custody_count);


        return v;

    }
}