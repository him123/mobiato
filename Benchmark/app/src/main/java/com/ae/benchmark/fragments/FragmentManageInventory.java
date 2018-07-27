package com.ae.benchmark.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.ItemsListActivity;
import com.ae.benchmark.localdb.DBManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class FragmentManageInventory extends Fragment {

    static FragmentManager fragmentManager;
    View rootView;
    private String str_Post_Status;
    DBManager dbManager;

    public static Fragment newInstance(FragmentManager fragmentManager1) {
        FragmentManageInventory fragment = new FragmentManageInventory();
        fragmentManager = fragmentManager1;
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mange_inventory, container, false);

        initViewPagerAndTabs(rootView);
        dbManager = new DBManager(getActivity());

        try {
            JSONObject props = new JSONObject();
            props.put("Fragment", "DashboardFragment");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViewPagerAndTabs(View rootView) {

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(FragmentIMLoad.createInstance(), getString(R.string.tab_load));
        pagerAdapter.addFragment(FragmentIMLoadRequest.newInstance(), getString(R.string.tab_load_req));
        pagerAdapter.addFragment(FragmentIMVanStock.newInstance(), getString(R.string.tab_van_stock));
        pagerAdapter.addFragment(FragmentIMVUnload.newInstance(), getString(R.string.tab_unload));

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.

//                if (position == 0) {
//
//                } else if (position == 1) {
//                    dbManager.open();
//                    if (dbManager.checkIsNotVerified())
//                        viewPager.setCurrentItem(0);
//                    dbManager.close();
//
//                } else if (position == 2) {
//                    dbManager.open();
//                    if (dbManager.checkIsNotVerified())
//                        viewPager.setCurrentItem(0);
//                    dbManager.close();
//                } else if (position == 3) {
//                    dbManager.open();
//                    if (dbManager.checkIsNotVerified())
                        viewPager.setCurrentItem(position);
//                    dbManager.close();
//                }
            }
        });

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        /*dbManager.open();
        if (dbManager.checkIsNotVerified()){
            LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
            for(int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }

            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(0, false);
                        return  true;
                    }
                    return false;
                }
            });
        }*/

    }

    public static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
