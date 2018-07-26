package com.ae.benchmark.adapters;
/*
 *
 * Copyright (C) 2014 Krishna Kumar Sharma
 *
 *  */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ae.benchmark.fragments.FragmentCOOrder;
import com.ae.benchmark.fragments.TestFragment;
import com.ae.benchmark.fragments.TileFragment;
import com.ae.benchmark.model.Item;

import java.util.ArrayList;
import java.util.List;

public class TestFragmentAdapter extends FragmentStatePagerAdapter {

    private Context context;
    List<Item> items;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (object != null) {
            return ((Fragment) object).getView() == view;
        } else {
            return false;
        }
    }

    public TestFragmentAdapter(FragmentManager fm,
                               Context context, List<Item> items) {
        super(fm);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(items.get(position),
                context);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).order_id;
    }

}