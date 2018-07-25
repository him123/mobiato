package com.ae.benchmark.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersPast;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersRecent;
import com.ae.benchmark.fragments.DashboardFragment;
import com.ae.benchmark.fragments.FragmentDashboardOne;
import com.ae.benchmark.fragments.FragmentDashboardThree;
import com.ae.benchmark.fragments.FragmentDashboardTwo;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.views.KKViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class OrderReqeustActivity extends AppCompatActivity {

    private Toolbar toolbar;

//    @InjectView(R.id.recyclerview_recent_orders)
//    RecyclerView recyclerview_recent_orders;

//    LinearLayoutManager mLayoutManager_recent;

    //    RecyclerAdapterOrdersRecent recyclerAdapter_recent;
    List<Item> itemList;
    Item item;

    private KKViewPager viewPager;


    @InjectView(R.id.recyclerview_past_orders)
    RecyclerView recyclerview_past_orders;


    RecyclerAdapterOrdersRecent recyclerAdapter;
    RecyclerAdapterOrdersPast recyclerAdapterPast;

    //    List<Customer> itemCustList;
//    Customer customer;
    private GridLayoutManager mGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_co_order);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ORDER REQUEST");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        mLayoutManager_recent = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerview_recent_orders.setLayoutManager(mLayoutManager_recent);

        viewPager = (KKViewPager) findViewById(R.id.kk_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        viewPager.setAnimationEnabled(true);
        viewPager.setFadeEnabled(true);
        viewPager.setFadeFactor(0.6f);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });

        itemList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            item = new Item();

//            item.cust_id = "2012462260";
//            item.name = "Load No. 800302051";
//            item.address = "Delivery Date: 2017.02.10";

            itemList.add(item);
        }

//        recyclerAdapter_recent = new RecyclerAdapterOrdersRecent(itemList, this);
//        recyclerview_recent_orders.setAdapter(recyclerAdapter_recent);


//        itemCustList = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            customer = new Customer();
//
//            customer.cust_id = "2012462260";
//            customer.name = "Load No. 800302051";
//            customer.address = "Delivery Date: 2017.02.10";
//
//            itemCustList.add(customer);
//        }

        mGridLayoutManager = new GridLayoutManager(this, 2);
        recyclerview_past_orders.setLayoutManager(mGridLayoutManager);
        recyclerAdapterPast = new RecyclerAdapterOrdersPast(itemList, this);
        recyclerview_past_orders.setAdapter(recyclerAdapterPast);

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
}
