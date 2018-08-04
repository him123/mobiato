package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.DashBoardActivity;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentSettings extends Fragment {


    @InjectView(R.id.txt_name)
    TextView txt_name;

    @InjectView(R.id.txt_desc)
    TextView txt_desc;

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.inject(this, v);


        txt_name.setText(UtilApp.ReadSharePrefrenceString(getActivity(), Constant.SALESMAN.SALESMAN_NAME));
        txt_desc.setText(UtilApp.ReadSharePrefrenceString(getActivity(), Constant.SALESMAN.SALESMAN_ID));

        return v;
    }

}