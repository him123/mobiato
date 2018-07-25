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

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CollectionPaymentActivity;
import com.ae.benchmark.adapters.CollectionAdapter;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentDataPostingAudit extends Fragment {

    @InjectView(R.id.recyclerview_collection)
    RecyclerView recyclerview_collection;
    CollectionAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    public FragmentDataPostingAudit() {
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
        View v = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.inject(this, v);

        itemList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            item = new Item();
//            item.name = "Robert Micheal";
//            item.desc = "Board Street, Dubai";

            itemList.add(item);
        }

//        mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerview_collection.setLayoutManager(mLayoutManager);
//
//        recyclerAdapter = new CollectionAdapter(itemList, getActivity());
//        recyclerview_collection.setAdapter(recyclerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CollectionPaymentActivity.class));
            }
        });

        return v;
    }

}