package com.ae.benchmark.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ALLItemsListActivity;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersPast;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersRecent;
import com.ae.benchmark.adapters.TestFragmentAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.views.KKViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCOReturns extends Fragment {



    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private KKViewPager viewPager;
    List<Item> itemList;
    Item item;

    @InjectView(R.id.recyclerview_past_orders)
    RecyclerView recyclerview_past_orders;


    RecyclerAdapterOrdersRecent recyclerAdapter;
    RecyclerAdapterOrdersPast recyclerAdapterPast;
    protected static final String[] CONTENT2 = new String[]{"Pager Carousel", "Title 2",
            "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"};
    //    List<Customer> itemCustList;
//    Customer recentCustomer;
    private GridLayoutManager mGridLayoutManager;

    Customer customer;
    public FragmentCOReturns(Customer customer) {
        // Required empty public constructor
        this.customer = customer;
    }

    public FragmentCOReturns() {
        // Required empty public constructor

    }

    public static FragmentCOReturns newInstance(String content, Context c) {
        FragmentCOReturns fragment = new FragmentCOReturns();

        fragment.mContent = content;
        return fragment;
    }

    private String mContent = "???";

    String custName, tag;
    DBManager db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_co_retrun, container, false);
        ButterKnife.inject(this, v);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            custName = extras.getString("name");
            tag = extras.getString("tag");
        }

        viewPager = (KKViewPager) v.findViewById(R.id.kk_pager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ALLItemsListActivity.class);
                intent.putExtra("type", "Order");
                intent.putExtra("isScan", "yes");
                intent.putExtra("name", custName);
                intent.putExtra("tag", tag);
                intent.putExtra("cust", customer);
                intent.putExtra("with_load", "no");
                intent.putExtra("is_return", "yes");

                startActivity(intent);
            }
        });

        return v;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    FragmentDashboardOne fragmentAnimation1 = new FragmentDashboardOne();
                    return fragmentAnimation1;

                case 1:
                    FragmentDashboardTwo fragmentAnimation2 = new FragmentDashboardTwo();
                    return fragmentAnimation2;

                case 2:
                    FragmentDashboardThree fragmentAnimation3 = new FragmentDashboardThree();
                    return fragmentAnimation3;

            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        db = new DBManager(getActivity());
        db.open();


        itemList = new ArrayList<>();
        itemList.clear();
        itemList = db.getAllReturnsForCustomer(customer.cust_num);


        viewPager.setAdapter(new TestFragmentAdapter(getChildFragmentManager(),
                getActivity(), itemList));

        viewPager.setAnimationEnabled(true);
        viewPager.setFadeEnabled(true);
        viewPager.setFadeFactor(0.6f);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview_past_orders.setLayoutManager(mGridLayoutManager);
        recyclerAdapterPast = new RecyclerAdapterOrdersPast(itemList, getActivity());
        recyclerview_past_orders.setAdapter(recyclerAdapterPast);
    }
}