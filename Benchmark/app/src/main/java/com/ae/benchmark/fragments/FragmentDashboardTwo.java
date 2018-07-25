package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;

import butterknife.ButterKnife;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentDashboardTwo extends Fragment {


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

        return v;
    }
}