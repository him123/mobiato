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

public class FragmentDashboardThree extends Fragment {


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
        View v = inflater.inflate(R.layout.fragment_dashboard_three, container, false);
        ButterKnife.inject(this, v);

        return v;

    }
}