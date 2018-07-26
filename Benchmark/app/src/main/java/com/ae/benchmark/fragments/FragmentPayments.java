package com.ae.benchmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.LoginActivity;
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

public class FragmentPayments extends Fragment {


    @InjectView(R.id.btn_day_end)
    Button btn_day_end;

    public FragmentPayments() {
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
        View v = inflater.inflate(R.layout.fragment_end_trip, container, false);
        ButterKnife.inject(this, v);

        btn_day_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilApp.clearSharedPreferences(getActivity());
//                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISLOGIN, false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish(); // call this to finish the current activity


            }
        });

        return v;
    }

}