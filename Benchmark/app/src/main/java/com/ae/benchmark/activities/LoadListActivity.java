package com.ae.benchmark.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterLoad;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class LoadListActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerview_load)
    RecyclerView recyclerview_load;

//    @InjectView(R.id.recyclerview_recent)
//    RecyclerView recyclerview_recent;

    RecyclerAdapterLoad recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_list);
        ButterKnife.inject(this);


        LayoutInflater mInflater = LayoutInflater.from(LoadListActivity.this);
//
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Load");

        itemList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            item = new Item();

            item.item_name_en = "2012462260";
            item.load_no = "Load No. 800302051";

            itemList.add(item);
        }


        mLayoutManager = new LinearLayoutManager(LoadListActivity.this);
        recyclerview_load.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerAdapterLoad(itemList, LoadListActivity.this);
        recyclerview_load.setAdapter(recyclerAdapter);
    }
}
