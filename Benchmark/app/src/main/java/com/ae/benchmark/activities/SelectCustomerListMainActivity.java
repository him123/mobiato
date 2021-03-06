package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerRecent;
import com.ae.benchmark.fragments.FragmentCustAll;
import com.ae.benchmark.fragments.FragmentCustSeq;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by Himm on 3/13/2018.
 */

public class SelectCustomerListMainActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerview_recent)
    RecyclerView recyclerview_recent;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    LinearLayoutManager mLayoutManager_recent;

    RecyclerAdapterCustomerRecent recyclerAdapter_recent;

    List<Customer> itemList;
    Customer customer;
    boolean flag_seq;
    ViewPager viewPager;

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private Toolbar toolbar;
    String selectedDate = "";
    private int year, month, day;
    TextView mTitle;
    @InjectView(R.id.pg)
    ProgressBar pg;

    @InjectView(R.id.ll_tab)
    RelativeLayout rl_name_seq;

    @InjectView(R.id.rl_name)
    RelativeLayout rl_name;

//    @InjectView(R.id.txt_title)
//    TextView txt_title;

    @InjectView(R.id.txt_title_seq)
    TextView txt_title_seq;
    MaterialShowcaseSequence sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cust_list_main);
        ButterKnife.inject(this);

        UtilApp.WriteSharePrefrence(SelectCustomerListMainActivity.this, Constant.SHRED_PR.ISJPLOADED, true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        toolbar.setTitleTextColor(Color.WHITE);

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getCurrentDate());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pg.setVisibility(View.VISIBLE);
        new AsyncTaskRunner().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectCustomerListMainActivity.this, AddCustomerActivity.class));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.
                if (position == 0) {
                    txt_title.setText("ALL");
                } else {
                    txt_title.setText("SEQ");
                }
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        rl_name_seq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SelectCustomerListMainActivity.this, "SEQ", Toast.LENGTH_SHORT).show();
//                viewPager.setCurrentItem(1);
            }
        });

        rl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectCustomerListMainActivity.this, "ALL", Toast.LENGTH_SHORT).show();
//                viewPager.setCurrentItem(0);
            }
        });


        txt_title_seq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectCustomerListMainActivity.this, "ALL", Toast.LENGTH_SHORT).show();
            }
        });

        sequence = new MaterialShowcaseSequence(this, "ADDCUST");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);

        sequence.addSequenceItem(fab,
                "Add Customer.", "GOT IT");

        sequence.start();

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    FragmentCustAll fragmentAnimation1 = new FragmentCustAll();
                    return fragmentAnimation1;

                case 1:

                    FragmentCustSeq fragmentAnimation2 = new FragmentCustSeq();
                    return fragmentAnimation2;
            }
            return null;
        }
    }


    private String getCurrentDate() {
        String newDateStr = "";
        try {
            String dateStr = "04/05/2010";

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormater.parse(dateStr);
//            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
            SimpleDateFormat format = new SimpleDateFormat(" d" + " MMM" + "," + " yyyy");

            newDateStr = format.format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDateStr;
    }

    private void setFont() {

        Typeface mFontBold = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_bold.ttf");
        Typeface mFontLight = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_light.ttf");
        Typeface mFontRegular = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_regular.ttf");

        txt_title.setTypeface(mFontRegular);
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
//        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                itemList = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    customer = new Customer();

                    customer.cust_num = "2012462260";
                    customer.cust_name_en = "Load No. 800302051";
                    customer.cust_address = "Delivery Date: 2017.02.10";

                    itemList.add(customer);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            mLayoutManager_recent = new LinearLayoutManager(SelectCustomerListMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerview_recent.setLayoutManager(mLayoutManager_recent);
            recyclerAdapter_recent = new RecyclerAdapterCustomerRecent(itemList, SelectCustomerListMainActivity.this);

            recyclerview_recent.setAdapter(recyclerAdapter_recent);
            viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
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


    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
//                    edt_dob.setError(null);
                    int month = arg2 + 1;
//                    showDate(arg1, arg2 + 1, arg3);
                    selectedDate = arg1 + "-" + month + "-" + arg3;

                    int year = arg0.getYear();
                    int month2 = arg0.getMonth();
                    int day = arg0.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month2, day);

                    SimpleDateFormat format = new SimpleDateFormat(" d" + " MMM" + "," + " yyyy");
                    String strDate = format.format(calendar.getTime());
                    mTitle.setText(strDate);
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_customer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.nav_import:
                Toast.makeText(getBaseContext(), "You selected Import", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_stock:
                Toast.makeText(getBaseContext(), "You selected Stock", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_dashboard:
                Toast.makeText(getBaseContext(), "You selected Dashboard", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sales:
                Toast.makeText(getBaseContext(), "You selected Sales", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Print:
                Toast.makeText(getBaseContext(), "You selected Print", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_cat:
                Toast.makeText(getBaseContext(), "You selected Catalogue", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_map:
                Toast.makeText(getBaseContext(), "You selected Map", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;

    }
}
