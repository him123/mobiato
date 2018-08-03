package com.ae.benchmark.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.fragments.FragmentAddCustOne;
import com.ae.benchmark.fragments.FragmentAddCustTwo;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class AddCustomerActivity extends AppCompatActivity {

    private Toolbar toolbar;

    static android.support.v4.app.FragmentManager fragmentManager;
    private String str_Post_Status;

    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    final int NUM_PAGES = 3;
    boolean flag;

    ViewPager viewPager;
    TextView mTitle;

    @InjectView(R.id.txt_title)
    TextView txt_title;

    @InjectView(R.id.btn_skip)
    Button btn_skip;

    @InjectView(R.id.btn_next)
    Button btn_next;

    int current = 0;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_wizard);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("ADD CUSTOMER");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        txt_title.setText("Office Address");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onPageSelected(int position) {
                // Check if this is the page you want.

                if (position == 0) {
                    txt_title.setText("Office Address");
                    btn_next.setText("Next");
                } else if (position == 1) {
                    txt_title.setText("Document Details");

                    btn_next.setText("Submit");
                }
            }
        });


        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current >= 0) {
                    current--;
                    viewPager.setCurrentItem(current);
                }
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 1) {

                    if (FragmentAddCustOne.edtCustomerName.getText().toString().equals("")) {
                        FragmentAddCustOne.edtCustomerName.setError("Can not be blank");
                        FragmentAddCustOne.edtCustomerName.requestFocus();
                    } else if (FragmentAddCustOne.edtDistCh.getText().toString().equals("")) {
                        FragmentAddCustOne.edtDistCh.setError("Can not be blank");
                        FragmentAddCustOne.edtDistCh.requestFocus();
                    } else if (FragmentAddCustOne.edtDiv.getText().toString().equals("")) {
                        FragmentAddCustOne.edtDiv.setError("Can not be blank");
                        FragmentAddCustOne.edtDiv.requestFocus();
                    } else if (FragmentAddCustOne.edtSalesOrg.getText().toString().equals("")) {
                        FragmentAddCustOne.edtSalesOrg.setError("Can not be blank");
                        FragmentAddCustOne.edtSalesOrg.requestFocus();
                    } else if (FragmentAddCustOne.edtCreditLim.getText().toString().equals("")) {
                        FragmentAddCustOne.edtCreditLim.setError("Can not be blank");
                        FragmentAddCustOne.edtCreditLim.requestFocus();
                    } else {
                        current++;
                        viewPager.setCurrentItem(current);
                    }

                } else {

                    if (FragmentAddCustTwo.edtAvailBal.getText().toString().equals("")) {
                        FragmentAddCustTwo.edtAvailBal.setError("Can not be blank");
                        FragmentAddCustTwo.edtAvailBal.requestFocus();
                    } else if (FragmentAddCustTwo.edtPayTerm.getText().toString().equals("")) {
                        FragmentAddCustTwo.edtPayTerm.setError("Can not be blank");
                        FragmentAddCustTwo.edtPayTerm.requestFocus();
                    } else if (FragmentAddCustTwo.edtAddress.getText().toString().equals("")) {
                        FragmentAddCustTwo.edtAddress.setError("Can not be blank");
                        FragmentAddCustTwo.edtAddress.requestFocus();
                    } else if (FragmentAddCustTwo.edtCustType.getText().toString().equals("")) {
                        FragmentAddCustTwo.edtCustType.setError("Can not be blank");
                        FragmentAddCustTwo.edtCustType.requestFocus();
                    } else {
                        db = new DBManager(getApplicationContext());
                        db.open();
                        long id = db.getLastCustomerID();

                        int nextCustID = (int) id + 1;

                        db.insertCustomer(nextCustID + "", Constant.NEW_CUSTOMER.edtCustomerName, "",
                                Constant.NEW_CUSTOMER.edtDistCh, Constant.NEW_CUSTOMER.edtDiv, Constant.NEW_CUSTOMER.edtSalesOrg,
                                Constant.NEW_CUSTOMER.edtCreditLim, Constant.NEW_CUSTOMER.edtAvailBal, Constant.NEW_CUSTOMER.edtPayTerm, Constant.NEW_CUSTOMER.edtAddress,
                                Constant.NEW_CUSTOMER.edtCustType, "0"
                                , "0", "43.104137", "43.104137",
                                UtilApp.getCurrentDate());

                        onBackPressed();
                    }

                }
            }
        });
    }

//    private int getItem(int i) {
//        return viewPager.getCurrentItem() + i;
//    }

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

//                    current = 0;
                    FragmentAddCustOne fragmentAnimation1 = new FragmentAddCustOne();
                    return fragmentAnimation1;

                case 1:
//                    current = 1;
                    FragmentAddCustTwo fragmentAnimation2 = new FragmentAddCustTwo();
                    return fragmentAnimation2;

//                case 2:
////                    current = 2;
//                    FragmentAddCustTwo fragmentAnimation3 = new FragmentAddCustTwo();
//                    return fragmentAnimation3;

            }
            return null;
        }
    }
}
