package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentIMVanStock extends Fragment {


    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    RecyclerItemsAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    private Toolbar toolbar;
//    DBManager dbManager;
private Boolean isStarted = false;
    private Boolean isVisible = false;
    public FragmentIMVanStock() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentIMVanStock fragment = new FragmentIMVanStock();
        return fragment;
    }

    public static FragmentIMVanStock createInstance() {
        FragmentIMVanStock partThreeFragment = new FragmentIMVanStock();
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
        View v = inflater.inflate(R.layout.activity_items_list, container, false);
        ButterKnife.inject(this, v);

        fab.setVisibility(View.GONE);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

//        dbManager = new DBManager(getActivity());
//
//        dbManager.open();


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        isStarted = true;
        if (isVisible && isStarted){
            viewInitVanstock();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewInitVanstock();
        }
    }


    private void viewInitVanstock(){
        try {
            DBManager dbManager = new DBManager(getActivity());
            dbManager.open();
            itemList = new ArrayList<>();

            itemList.clear();
            itemList = dbManager.getFullVanStock(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerview_items.setLayoutManager(mLayoutManager);

            recyclerAdapter = new RecyclerItemsAdapter(itemList, getActivity(), true);
            recyclerview_items.setAdapter(recyclerAdapter);

            recyclerAdapter.notifyDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}