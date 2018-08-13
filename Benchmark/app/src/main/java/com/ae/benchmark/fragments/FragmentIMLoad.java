package com.ae.benchmark.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.InputDailogActivity;
import com.ae.benchmark.adapters.LoadHeaderAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Load;
import com.ae.benchmark.webservice.WsGetLoads;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentIMLoad extends Fragment {


    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

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

//        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(WsGetLoads.BROADCAST_REFRESH_LOAD));

        LayoutInflater mInflater = LayoutInflater.from(getActivity());
//
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Verify Load");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);


                new AsyncTaskRunner().execute();
            }
        });

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

//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, final Intent intent) {
//            swipeRefreshLayout.setRefreshing(false);
//
//            itemList.clear();
//            itemList = dbManager.getAllLoad("0");
//            recyclerAdapter.notifyDataChanged();
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            WsGetLoads wsGetLoads = new WsGetLoads(getActivity());
            wsGetLoads.executeWebservice();


            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            itemList.clear();
            itemList = dbManager.getAllLoad("0");

            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerview_load.setLayoutManager(mLayoutManager);

            recyclerAdapter = new LoadHeaderAdapter(itemList, getActivity(), false);
            recyclerview_load.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataChanged();

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}