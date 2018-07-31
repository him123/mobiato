package com.ae.benchmark.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.AddCustomerActivity;
import com.ae.benchmark.adapters.RecyclerAdapterCustomerRecent;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Collection;
import com.ae.benchmark.model.RecentCustomer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentCustomerSelection extends Fragment {


    @InjectView(R.id.recyclerview_recent)
    RecyclerView recyclerview_recent;

    LinearLayoutManager mLayoutManager_recent;

    RecyclerAdapterCustomerRecent recyclerAdapter_recent;
    List<RecentCustomer> itemList;
    RecentCustomer recentCustomer;
    boolean flag_seq;
    ViewPager viewPager;

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private Toolbar toolbar;
    String selectedDate = "";
    private int year, month, day;
    TextView mTitle;

    TabLayout tabLayout;

    @InjectView(R.id.pg)
    ProgressBar pg;

    DBManager dbManager;

    public FragmentCustomerSelection() {
        // Required empty public constructor
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_select_cust_list_main, container, false);
        ButterKnife.inject(this, v);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Journey Plans");

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabDots);

        new AsyncTaskRunner().execute();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCustomerActivity.class));
            }
        });

        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

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

        return v;
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

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
//
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
//            mTitle.setText(month+"/"+day+"/"+year);
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


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
//        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                itemList = new ArrayList<>();
                dbManager = new DBManager(getActivity());
                dbManager.open();
                itemList = dbManager.getAllREcentCust();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            // execution of result of Long time consuming operation
//            progressDialog.dismiss();
//            finalResult.setText(result);

            Collections.reverse(itemList);

            List<RecentCustomer> newData = new ArrayList<>();
            if (itemList.size()>5){
                for (int i=0 ; i<5; i++){
                    newData.add(i , itemList.get(i));
                }
            } else {
                newData = itemList;
            }

            recyclerAdapter_recent = new RecyclerAdapterCustomerRecent(newData, getActivity());

            recyclerview_recent.setAdapter(recyclerAdapter_recent);

            mLayoutManager_recent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerview_recent.setLayoutManager(mLayoutManager_recent);

            pg.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
//            progressDialog = ProgressDialog.show(getActivity(),
//                    "ProgressDialog",
//                    "Preparing Journey Plan");
        }


        @Override
        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);

        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}