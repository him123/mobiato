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
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentSalesBad extends Fragment {


    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    RecyclerItemsAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    @InjectView(R.id.fab)
    FloatingActionButton fab;


    public FragmentSalesBad() {
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
        View v = inflater.inflate(R.layout.fragment_sales_bad, container, false);
        ButterKnife.inject(this, v);

        itemList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            item = new Item();

//            item.name = "Robert Micheal";
//            item.desc = "Board Street, Dubai";

            itemList.add(item);
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsAdapter(itemList, getActivity(), false);
//        recyclerview_items.setAdapter(recyclerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemsListActivity.class);
//                i.putExtra("load_no", load.load_no);
                i.putExtra("isBack", "Yes");
                getActivity().startActivity(i);
//                getActivity().startActivity(new Intent(getActivity(), ItemsListActivity.class));
            }
        });

        return v;
    }
}