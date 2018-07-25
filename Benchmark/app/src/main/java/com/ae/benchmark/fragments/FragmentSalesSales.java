package com.ae.benchmark.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.CustomerDetailOperationActivity;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentSalesSales extends Fragment {


    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.img_info)
    ImageView img_info;

    RecyclerItemsAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    public FragmentSalesSales() {
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
        View v = inflater.inflate(R.layout.fragment_sales_sales, container, false);
        ButterKnife.inject(this, v);

        UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISPAYMET, true);
        UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, true);
        UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISDATA, true);

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
        recyclerview_items.setAdapter(recyclerAdapter);

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDilog(getActivity());
            }
        });

        return v;
    }




    private void makeDilog(final Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_sales_infp, null);
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

            }
        });

        deleteDialog.show();
    }
}