package com.ae.benchmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ItemsListActivity;
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

public class FragmentIMLoadRequest extends Fragment {


    @InjectView(R.id.recyclerview_load)
    RecyclerView recyclerview_load;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    LoadHeaderAdapter recyclerAdapter;

    List<Load> itemList;
    Load item;
    LinearLayoutManager mLayoutManager;

    DBManager dbManager;

    public FragmentIMLoadRequest() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentIMLoadRequest fragment = new FragmentIMLoadRequest();
        return fragment;
    }

    public static FragmentIMLoadRequest createInstance() {
        FragmentIMLoadRequest partThreeFragment = new FragmentIMLoadRequest();
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
        View v = inflater.inflate(R.layout.fragment_im_req_load, container, false);
        ButterKnife.inject(this, v);

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
//
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Verify Load");

        dbManager = new DBManager(getActivity());
        dbManager.open();

//        itemList = dbManager.getAllLoad();

//        for (int i = 0; i < 2; i++) {
//            item = new Item();
//
//            item.name = "2012462260";
//            item.desc = "Load No. 800302051";
//
//            itemList.add(item);
//        }


//        mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerview_load.setLayoutManager(mLayoutManager);
//
//        recyclerAdapter = new LoadHeaderAdapter(itemList, getActivity());
//        recyclerview_load.setAdapter(recyclerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemsListActivity.class);
//                i.putExtra("load_no", load.load_no);
                i.putExtra("isBack", "Yes");
                getActivity().startActivity(i);
//                getActivity().startActivity(new Intent(getActivity(), ItemsListActivity.class));
//                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        return v;
    }
}