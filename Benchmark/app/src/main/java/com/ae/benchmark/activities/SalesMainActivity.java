package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.fragments.FragmentSalesBad;
import com.ae.benchmark.fragments.FragmentSalesGood;
import com.ae.benchmark.fragments.FragmentSalesSales;
import com.ae.benchmark.model.Customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class SalesMainActivity extends AppCompatActivity {


//    @InjectView(R.id.recyclerview_recent)
//    RecyclerView recyclerview_recent;

//    LinearLayoutManager mLayoutManager_recent;

    //    RecyclerAdapterCustomerRecent recyclerAdapter_recent;
    List<Customer> itemList;
    Customer customer;
    boolean flag_seq;
    ViewPager viewPager;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    @InjectView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    //    @InjectView(R.id.fab)
//    FloatingActionButton fab;
//
    private Toolbar toolbar;
    String selectedDate = "";
    private int year, month, day;
    TextView mTitle;

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
            case R.id.nav_camera:
//                Toast.makeText(getBaseContext(), "You selected Phone", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SalesMainActivity.this, InvoiceSummaryActivity.class));
                break;

            case R.id.nav_gallery:
                Toast.makeText(getBaseContext(), "You selected Computer", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_slideshow:
                Toast.makeText(getBaseContext(), "You selected Gamepad", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_manage:
                Toast.makeText(getBaseContext(), "You selected Camera", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Toast.makeText(getBaseContext(), "You selected Video", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_send:
                Toast.makeText(getBaseContext(), "You selected EMail", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        makeDilogBack(SalesMainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_with_tile);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("SALES");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                makeDilogBack(SalesMainActivity.this);
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.

                if (position == 0) {
                    txt_title.setText("SALES");
                } else if (position == 1) {
                    txt_title.setText("GOOD RETURN");
                } else {
                    txt_title.setText("BAD RETURN");
                }
            }
        });

        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDilogBack(SalesMainActivity.this);
            }
        });
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
                    FragmentSalesSales fragmentAnimation1 = new FragmentSalesSales();
                    return fragmentAnimation1;
                case 1:
                    FragmentSalesGood fragmentAnimation2 = new FragmentSalesGood();
                    return fragmentAnimation2;
                case 2:
                    FragmentSalesBad fragmentAnimation3 = new FragmentSalesBad();
                    return fragmentAnimation3;

            }
            return null;
        }
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

    private void makeDilogBack(final Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_confirmatin_back, null);
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
                finish();

            }
        });

        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

            }
        });

        deleteDialog.show();
    }

    private void makeDilog(final Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_sales_infp, null);
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

            }
        });

        deleteDialog.show();
    }
}
