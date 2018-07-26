package com.ae.benchmark.fragments;

import android.annotation.SuppressLint;
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
import com.ae.benchmark.activities.CollectionPaymentActivity;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.activities.LoadListActivity;
import com.ae.benchmark.adapters.CollectionAdapter;
import com.ae.benchmark.adapters.RecyclerAdapter;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCOCollection extends Fragment {

    @InjectView(R.id.recyclerview_collection)
    RecyclerView recyclerview_collection;

    CollectionAdapter recyclerAdapter;

    List<Collection> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    DBManager dbManager;
    Customer customer;

    @SuppressLint("ValidFragment")
    public FragmentCOCollection(Customer customer) {
        // Required empty public constructor
        this.customer = customer;
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

//        recentCustomer = getActivity().getIntent().getExtras().getParcelable("cust");

        dbManager = new DBManager(getActivity());

        dbManager.open();

        itemList = new ArrayList<>();
        itemList.clear();
        itemList = dbManager.getAllCollections(customer.cust_num);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_collection.setLayoutManager(mLayoutManager);

        recyclerAdapter = new CollectionAdapter(itemList, getActivity());
        recyclerview_collection.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CollectionPaymentActivity.class));
            }
        });

        return v;
    }

}