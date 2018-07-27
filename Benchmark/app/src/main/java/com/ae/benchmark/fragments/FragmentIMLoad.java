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
import com.ae.benchmark.adapters.LoadHeaderAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Load;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentIMLoad extends Fragment {


    @InjectView(R.id.recyclerview_load)
    RecyclerView recyclerview_load;

    LoadHeaderAdapter recyclerAdapter;

    List<Load> itemList;
    //    Item item;
    LinearLayoutManager mLayoutManager;

    DBManager dbManager;

    public FragmentIMLoad() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentIMLoad fragment = new FragmentIMLoad();
        return fragment;
    }

    public static FragmentIMLoad createInstance() {
        FragmentIMLoad partThreeFragment = new FragmentIMLoad();
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
        View v = inflater.inflate(R.layout.fragment_im_load, container, false);
        ButterKnife.inject(this, v);

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
//
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Verify Load");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        dbManager = new DBManager(getActivity());
        dbManager.open();

        itemList = dbManager.getAllLoad("0");

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_load.setLayoutManager(mLayoutManager);

        recyclerAdapter = new LoadHeaderAdapter(itemList, getActivity(), false);
        recyclerview_load.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();

    }
}