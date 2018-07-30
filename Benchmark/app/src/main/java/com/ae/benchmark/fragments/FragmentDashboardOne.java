package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.views.TwoLevelCircularProgressBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentDashboardOne extends Fragment {

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.progressMain)
    TwoLevelCircularProgressBar progressMain;
    @InjectView(R.id.txtMainSell)
    TextView txtMainSell;
    @InjectView(R.id.progressSell)
    TwoLevelCircularProgressBar progressSell;
    @InjectView(R.id.txtSell)
    TextView txtSell;
    @InjectView(R.id.progressCollection)
    TwoLevelCircularProgressBar progressCollection;
    @InjectView(R.id.txtCollection)
    TextView txtCollection;

    DBManager db;

    int counterSell, counterCollection;
    int totalSell, totalCollection;
    @InjectView(R.id.txt_middle_small)
    TextView txtMiddleSmall;
    @InjectView(R.id.swcDtdMtd)
    Switch swcDtdMtd;
    @InjectView(R.id.imgSale)
    ImageView imgSale;
    @InjectView(R.id.imgCollection)
    ImageView imgCollection;

    public FragmentDashboardOne() {
        // Required empty public constructor
    }

    public static FragmentDashboardOne createInstance() {
        FragmentDashboardOne partThreeFragment = new FragmentDashboardOne();
        return partThreeFragment;
    }

    static FragmentManager fragmentManager;


    public static Fragment newInstance(FragmentManager fragmentManager1) {
        FragmentDashboardOne fragment = new FragmentDashboardOne();
        fragmentManager = fragmentManager1;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_one, container, false);
        ButterKnife.inject(this, v);

        txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD / 30));
        db = new DBManager(getActivity());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        setDtd();
        swcDtdMtd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD / 30));
                    setDtd();
                } else {
                    txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD));
                    setMtd();
                }
            }
        });
        return v;
    }

    public void setDtd() {
        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHeadToday();

        double totSale = 0;
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equals("Sale")) {
                totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
            }
        }
        if (totSale> Constant.TARGET_DTD_MTD/30){
            imgSale.setBackgroundResource(R.drawable.ic_action_up_green);
        } else {
            imgSale.setBackgroundResource(R.drawable.ic_action_down_red);
        }

        txtMainSell.setText(String.valueOf((int) totSale));
        txtSell.setText(String.valueOf((int) totSale));

        int totColl = db.getAllInvoiceHeadCollectionToday();
        if (totColl> Constant.TARGET_DTD_MTD/30){
            imgCollection.setBackgroundResource(R.drawable.ic_action_up_green);
        } else {
            imgCollection.setBackgroundResource(R.drawable.ic_action_down_red);
        }
        txtCollection.setText(String.valueOf(totColl));
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                try {
                    while (jumpTime < totalProgressTime) {
                        try {
                            sleep(50);
                            jumpTime += 2;
                            progressMain.setProgressValue(jumpTime);
                            progressSell.setProgressValue(jumpTime);
                            progressCollection.setProgressValue(jumpTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        if (totSale > 100) {

            int val = (int) totSale;

            totalSell = (int) totSale;
            totalCollection = (int) totColl;
            counterSell = (int) totSale - 100;
            counterCollection = (int) totColl - 100;
            new Thread(new Runnable() {

                public void run() {
                    while (counterSell < totalSell) {
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        txtMainSell.post(new Runnable() {

                            public void run() {
                                txtMainSell.setText("" + counterSell);

                            }

                        });
                        txtSell.post(new Runnable() {

                            public void run() {
                                txtSell.setText("" + counterSell);

                            }

                        });
                        counterSell++;

                        txtCollection.post(new Runnable() {

                            public void run() {
                                txtCollection.setText("" + counterCollection);

                            }

                        });
                        counterCollection++;
                    }

                }

            }).start();

        }
    }

    public void setMtd() {
        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHead();

        double totSale = 0;
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equals("Sale")) {
                totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
            }
        }

        if (totSale> Constant.TARGET_DTD_MTD){
            imgSale.setBackgroundResource(R.drawable.ic_action_up_green);
        } else {
            imgSale.setBackgroundResource(R.drawable.ic_action_down_red);
        }
        txtMainSell.setText(String.valueOf((int) totSale));
        txtSell.setText(String.valueOf((int) totSale));

        int totColl = db.getAllInvoiceHeadCollection();

        if (totColl> Constant.TARGET_DTD_MTD){
            imgCollection.setBackgroundResource(R.drawable.ic_action_up_green);
        } else {
            imgCollection.setBackgroundResource(R.drawable.ic_action_down_red);
        }

        txtCollection.setText(String.valueOf(totColl));
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                try {
                    while (jumpTime < totalProgressTime) {
                        try {
                            sleep(50);
                            jumpTime += 2;
                            progressMain.setProgressValue(jumpTime);
                            progressSell.setProgressValue(jumpTime);
                            progressCollection.setProgressValue(jumpTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        if (totSale > 100) {

            int val = (int) totSale;

            totalSell = (int) totSale;
            totalCollection = (int) totColl;
            counterSell = (int) totSale - 100;
            counterCollection = (int) totColl - 100;
            new Thread(new Runnable() {

                public void run() {
                    while (counterSell < totalSell) {
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        txtMainSell.post(new Runnable() {

                            public void run() {
                                txtMainSell.setText("" + counterSell);

                            }

                        });
                        txtSell.post(new Runnable() {

                            public void run() {
                                txtSell.setText("" + counterSell);

                            }

                        });
                        counterSell++;

                        txtCollection.post(new Runnable() {

                            public void run() {
                                txtCollection.setText("" + counterCollection);

                            }

                        });
                        counterCollection++;
                    }

                }

            }).start();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}