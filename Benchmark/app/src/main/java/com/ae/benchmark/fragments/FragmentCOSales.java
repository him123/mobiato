package com.ae.benchmark.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.activities.SalesMainActivity;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersPast;
import com.ae.benchmark.adapters.RecyclerAdapterOrdersRecent;
import com.ae.benchmark.adapters.TestFragmentAdapter;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.views.KKViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCOSales extends Fragment {


//    private Toolbar toolbar;

//    @InjectView(R.id.recyclerview_recent_orders)
//    RecyclerView recyclerview_recent_orders;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    //    LinearLayoutManager mLayoutManager_recent;
//
//    RecyclerAdapterOrdersRecent recyclerAdapter_recent;
    List<Item> itemList;
    Item item;

    @InjectView(R.id.recyclerview_past_orders)
    RecyclerView recyclerview_past_orders;

    private KKViewPager viewPager;

    RecyclerAdapterOrdersRecent recyclerAdapter;
    RecyclerAdapterOrdersPast recyclerAdapterPast;

    //    List<Customer> itemCustList;
//    Customer recentCustomer;
    private GridLayoutManager mGridLayoutManager;
    protected static final String[] CONTENT2 = new String[]{"Pager Carousel", "Title 2",
            "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"};

    public FragmentCOSales() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentCOSales fragment = new FragmentCOSales();
        return fragment;
    }

    public static FragmentCOSales createInstance() {
        FragmentCOSales partThreeFragment = new FragmentCOSales();
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
        View v = inflater.inflate(R.layout.fragment_co_sales, container, false);
        ButterKnife.inject(this, v);

//        viewPager = (KKViewPager) v.findViewById(R.id.kk_pager);
//        viewPager.setAdapter(new TestFragmentAdapter(getChildFragmentManager(),
//                getActivity(), CONTENT2));
//        viewPager.setAnimationEnabled(true);
//        viewPager.setFadeEnabled(true);
//        viewPager.setFadeFactor(0.6f);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {
//            }
//
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            public void onPageSelected(int position) {
//                // Check if this is the page you want.
//            }
//        });

//
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setVisibility(View.GONE);

//        mLayoutManager_recent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerview_recent_orders.setLayoutManager(mLayoutManager_recent);

        itemList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            item = new Item();

//            item.cust_id = "2012462260";
//            item.name = "Load No. 800302051";
//            item.address = "Delivery Date: 2017.02.10";

            itemList.add(item);
        }

//        recyclerAdapter_recent = new RecyclerAdapterOrdersRecent(itemList, getActivity());
//        recyclerview_recent_orders.setAdapter(recyclerAdapter_recent);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SalesMainActivity.class));
            }
        });
//        itemCustList = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            recentCustomer = new Customer();
//
//            recentCustomer.cust_id = "2012462260";
//            recentCustomer.name = "Load No. 800302051";
//            recentCustomer.address = "Delivery Date: 2017.02.10";
//
//            itemCustList.add(recentCustomer);
//        }

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview_past_orders.setLayoutManager(mGridLayoutManager);
        recyclerAdapterPast = new RecyclerAdapterOrdersPast(itemList, getActivity());
        recyclerview_past_orders.setAdapter(recyclerAdapterPast);

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
        public android.support.v4.app.Fragment getItem(int position) {
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