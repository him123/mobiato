package com.ae.benchmark.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.DrawerItemCustomAdapter;
import com.ae.benchmark.fragments.DashboardFragment;
import com.ae.benchmark.fragments.FragmentCatalogue;
import com.ae.benchmark.fragments.FragmentCustomerSelection;
import com.ae.benchmark.fragments.FragmentDashboardOne;
import com.ae.benchmark.fragments.FragmentDataPostingAudit;
import com.ae.benchmark.fragments.FragmentManageInventory;
import com.ae.benchmark.fragments.FragmentPayments;
import com.ae.benchmark.fragments.FragmentSales;
import com.ae.benchmark.fragments.InvoiceSummury;
import com.ae.benchmark.model.DataModel;

import butterknife.ButterKnife;

/**
 * Created by Rakshit on 12-Jan-17.
 */
/*
@ This activity will be called when the + button is clicked on the visit list or all customer screen.
@ This will create a customer in the backend.
*/
public class DashBoardActivityListFragments extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_list);
        ButterKnife.inject(this);


        mTitle = mDrawerTitle = getTitle();

        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.nav_item_activity_titles_own);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[10];


        drawerItem[0] = new DataModel(R.drawable.ic_icon_order, "Home");
        drawerItem[1] = new DataModel(R.drawable.ic_icon_order, "Manage Inventory");
        drawerItem[2] = new DataModel(R.drawable.ic_icon_order, "Journey Plan");

        drawerItem[3] = new DataModel(R.drawable.ic_icon_order, "Payments");
//        drawerItem[1] = new DataModel(R.drawable.ic_icon_order, "Route Recoun");
        drawerItem[4] = new DataModel(R.drawable.ic_icon_order, "Sales Snap");

        drawerItem[5] = new DataModel(R.drawable.ic_icon_order, "Data Posting Audit");
        drawerItem[6] = new DataModel(R.drawable.ic_icon_order, "Catalogue");
        drawerItem[7] = new DataModel(R.drawable.ic_icon_order, "Share App");

        drawerItem[8] = new DataModel(R.drawable.ic_icon_order, "Rate This App");
        drawerItem[9] = new DataModel(R.drawable.ic_icon_order, "Suggestions");


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }


    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {

            //        <item>Home</item>
//        <item>Manage Inventory</item>
//        <item>Journey Plan</item>
//        <item>Payments</item>
//       <!-- <item>Route Recone</item>-->
//        <item>Sales Snap</item>
//        <item>Data Posting Audit</item>
//        <item>Catalogue</item>
//        <item>Share App</item>
//        <item>Rate This App</item>
//        <item>Suggestions</item>


            case 0:
                //Home
                fragment = new DashboardFragment();
                break;
            case 1:
                //Manage Inventory
                fragment = new FragmentManageInventory();
                break;
            case 2:
                //Customer Selection
                fragment = new FragmentCustomerSelection();
                break;
            case 3:
                //Payment
                fragment = new FragmentPayments();
                break;
            case 4:
                //Sale
                fragment = new FragmentSales();
                break;
            case 5:
                //Data posting Audit
                fragment = new FragmentDataPostingAudit();
                break;
            case 6:
                //Catalogue
                fragment = new FragmentCatalogue();
                break;
            case 7:
                //Share this App
                fragment = new FragmentCatalogue();
                break;
            case 8:
                //Rate this app
                fragment = new FragmentCatalogue();
                break;
            case 9:
                //Suggestions
                fragment = new FragmentCatalogue();
                break;

            default:
                fragment = new DashboardFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}
