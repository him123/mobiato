package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;

import butterknife.ButterKnife;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCOFeed extends Fragment {



    public FragmentCOFeed() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentCOFeed fragment = new FragmentCOFeed();
        return fragment;
    }

    public static FragmentCOFeed createInstance() {
        FragmentCOFeed partThreeFragment = new FragmentCOFeed();
        return partThreeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_co_feed, container, false);
        ButterKnife.inject(this, v);
//
        return v;
    }
}