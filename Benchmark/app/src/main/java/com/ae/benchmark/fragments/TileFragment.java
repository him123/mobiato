package com.ae.benchmark.fragments;

import android.content.Context;
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

public class TileFragment extends Fragment {


    public TileFragment() {
        // Required empty public constructor
    }

    public static TileFragment newInstance(String content, Context c) {
        TileFragment fragment = new TileFragment();

        fragment.mContent = content;
        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tile_fragment, container, false);
        ButterKnife.inject(this, v);


        return v;
    }
}