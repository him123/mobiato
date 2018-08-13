package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerMain;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerRecent;
import com.ae.benchmark.fragments.FragmentCustAll;
import com.ae.benchmark.fragments.FragmentCustSeq;
import com.ae.benchmark.fragments.FragmentJourneyPlan;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.RecentCustomer;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class JourneyPlanActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{


    @InjectView(R.id.txt_title)
    TextView txt_title;


    @InjectView(R.id.recyclerview_recent)
    RecyclerView recyclerview_recent;

    @InjectView(R.id.recyclerview_main)
    RecyclerView recyclerview_main;


    LinearLayoutManager mLayoutManager_recent;
    RecyclerAdapterCustomerRecent recyclerAdapter_recent;

    LinearLayoutManager mLayoutManager_maint;
    RecyclerAdapterCustomerMain recyclerAdapter_main;

    List<RecentCustomer> itemList;
    List<RecentCustomer> uniqueItemList;

    List<Customer> itemListMain;

    RecentCustomer recentCustomer;
    boolean flag_seq;

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    //    private Toolbar toolbar;
//    String selectedDate = "";
//    private int year, month, day;
//    TextView mTitle;
    @InjectView(R.id.pg)
    ProgressBar pg;

//    @InjectView(R.id.ll_tab)
//    RelativeLayout rl_name_seq;
//
//    @InjectView(R.id.ll_butons)
//    LinearLayout ll_butons;

    @InjectView(R.id.rl_name)
    RelativeLayout rl_name;

//    @InjectView(R.id.txt_title)
//    TextView txt_title;

//    @InjectView(R.id.txt_title_seq)
//    TextView txt_title_seq;
//    MaterialShowcaseSequence sequence;


    DBManager dbManager;
    boolean flag;


    private Toolbar toolbar;

    String selectedDate = "";
    TextView mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_plan);
        ButterKnife.inject(this);

        UtilApp.WriteSharePrefrence(JourneyPlanActivity.this,
                Constant.SHRED_PR.ISJPLOADED, true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        toolbar.setTitleTextColor(Color.WHITE);

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        mTitle.setText(getCurrentDate());
        mTitle.setText("Customers");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();

                Intent i = new Intent(JourneyPlanActivity.this, DashBoardActivity.class);
//                    i.putExtra("load_no", load.load_no);
//                    i.putExtra("isBack", "No");
                startActivity(i);
            }
        });


        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        pg.setVisibility(View.VISIBLE);


        new AsyncTaskRunner().execute();
        new AsyncTaskRunnerMain("ALL").execute();


        rl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    txt_title.setText("ALL");
                    flag = false;
                    new AsyncTaskRunnerMain("ALL").execute();
                } else {
                    txt_title.setText("SEQ");
                    flag = true;
                    new AsyncTaskRunnerMain("SEQ").execute();
                }

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyPlanActivity.this, AddCustomerActivity.class));
            }
        });

    }


    // ASYNC TASK FOR RECENT CUSTOMERS
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                dbManager = new DBManager(JourneyPlanActivity.this);
                dbManager.open();


                //Recent customers
                itemList = new ArrayList<>();
                itemList.clear();
                itemList = dbManager.getAllREcentCust();


                HashSet<RecentCustomer> listToSet = new HashSet<RecentCustomer>(itemList);

                uniqueItemList = new ArrayList<>(listToSet);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            //RECENT CUSTOMERS
            mLayoutManager_recent = new LinearLayoutManager(JourneyPlanActivity.this,
                    LinearLayoutManager.HORIZONTAL, false);
            recyclerview_recent.setLayoutManager(mLayoutManager_recent);
            recyclerAdapter_recent = new RecyclerAdapterCustomerRecent(uniqueItemList, JourneyPlanActivity.this);

            recyclerview_recent.setAdapter(recyclerAdapter_recent);
            pg.setVisibility(View.GONE);
        }


        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);

        }
    }


    // ASYNC TASK FOR ALL/SEQ CUSTOMERS
    private class AsyncTaskRunnerMain extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        String type;

        public AsyncTaskRunnerMain(String type) {
            this.type = type;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                dbManager = new DBManager(JourneyPlanActivity.this);
                dbManager.open();

                //Main Customres
                itemListMain = new ArrayList<>();
                itemListMain.clear();
                if (type.equalsIgnoreCase("ALL"))
                    itemListMain = dbManager.getAllCust();
                else
                    itemListMain = dbManager.getAllCust();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation


            //MAIN CUSTOMERS
            mLayoutManager_maint = new LinearLayoutManager(JourneyPlanActivity.this,
                    LinearLayoutManager.VERTICAL, false);
            recyclerview_main.setLayoutManager(mLayoutManager_maint);
            recyclerAdapter_main = new RecyclerAdapterCustomerMain(itemListMain, JourneyPlanActivity.this);

            recyclerview_main.setAdapter(recyclerAdapter_main);


            pg.setVisibility(View.GONE);
        }


        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_customer, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        recyclerAdapter_main.setFilter(itemListMain);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.nav_stock: {
                Intent intent = new Intent(JourneyPlanActivity.this, VanStockActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                Constant.VAN_STOCK = "yes";
                startActivity(intent);
                break;
            }

            case R.id.nav_dashboard: {
                Intent intent = new Intent(JourneyPlanActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }

            case R.id.nav_sales: {
                Intent intent = new Intent(JourneyPlanActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Constant.NAV_AUDIT = "yes";
                startActivity(intent);
                break;
            }

            case R.id.nav_Print: {
                Intent intent2 = new Intent(JourneyPlanActivity.this, CustomerPrintActivity.class);
                intent2.putExtra("all", "yes");
                startActivity(intent2);
                break;
            }

            case R.id.nav_map:
                Intent intent = new Intent(JourneyPlanActivity.this, MapsActivity.class);
                startActivity(intent);
                Toast.makeText(JourneyPlanActivity.this, "You selected Map", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Customer> filteredModelList = filter(itemListMain, newText);

        recyclerAdapter_main.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Customer> filter(List<Customer> models, String query) {
        query = query.toLowerCase();
        final List<Customer> filteredModelList = new ArrayList<>();
        for (Customer model : models) {
            final String text = model.cust_name_en;
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
