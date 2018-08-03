package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.DashBoardActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;
import com.ae.benchmark.views.TwoLevelCircularProgressBar;

import org.json.JSONObject;

import java.text.DecimalFormat;
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
    @InjectView(R.id.txt_left)
    TextView txtLeft;
    @InjectView(R.id.txt_right)
    TextView txtRight;
    @InjectView(R.id.txtDate)
    TextView txtDate;

    @InjectView(R.id.btn_test_print)
    Button btn_test_print;

    String jsonString = "{\n" +
            "\"customer_name_en\":\"\",\n" +
            "\"customer_name_ar\":\"\",\n" +
            "\"SALESMAN\":\"\",\n" +
            "\"ROUTE\":\"\",\n" +
            "\"invoice_date\":\"\",\n" +
            "\"customer_address\":\"\",\n" +
            "\"print_type\":\"\",\n" +
            "\"DOC DATE\":\"\",\n" +
            "\"LPONO\":\"\",\n" +
            "\"CONTACTNO\":\"\",\n" +
            "\"TRN\":\"\",\n" +
            "\"ORDERNO\":\"\",\n" +
            "\"TRIP START DATE\":\"\",\n" +
            "\"invoicepriceprint\":\"1\",\n" +
            "\"invoicepaymentterms\":\"2\",\n" +
            "\"SUB TOTAL\":\"215.32\",\n" +
            "\"invoicenumber\":\"INV123\",\n" +
            "\"TIME\":\"45456454\",\n" +
            "\"LANG\":\"AR\",\n" +
            "\"TOTAL\":\"2568.00\",\n" +
            "\"INVOICE DISCOUNT\":\"\",\n" +
            "\"VAT\":\"\",\n" +
            "\"NET SALES\":\"1235.00\",\n" +
            "\"invoicefooter\":\"\",\n" +
            "\"TOTAL\":[{\n" +
            "\"Total Amount(AED)\":\"2568.00\",\n" +
            "\"Total Befor TAX(AED)\":\"2568.00\",\n" +
            "\"GROSS AMOUNT: AED - \":\"2568.00\"\n" +
            "}\n" +
            "]\n" +
            ",\n" +
            "\"HEADERS\":[\n" +
            "\"SI No\",\n" +
            "\"Item Code\",\n" +
            "\"Description\",\n" +
            "\"UOM\",\n" +
            "\"QTY\",\n" +
            "\"UNIT Price\",\n" +
            "\"Total amount\",\n" +
            "\"Total Disc\",\n" +
            "\"Vat Amt\",\n" +
            "\"Vat %\",\n" +
            "\"Amount SAR\"\n" +
            "]\n" +
            "\n" +
            ",\n" +
            "\n" +
            "\"data\":[\n" +
            "[\n" +
            "\"1\",\n" +
            "\"241\",\n" +
            "\"bottle with woater\",\n" +
            "\"bottle\",\n" +
            "\"2\",\n" +
            "\"256\",\n" +
            "\"545\",\n" +
            "\"\",\n" +
            "\"25\",\n" +
            "\"5\",\n" +
            "\"565\"\n" +
            "]\n" +
            "]\n" +
            "}\n";


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

        txtDate.setText(UtilApp.getCurrentDate());
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

        btn_test_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PrinterHelper printerHelper = new PrinterHelper(getActivity(), getActivity());
                    JSONObject jsonObject = new JSONObject(jsonString);
                    printerHelper.execute(jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return v;
    }

//    public void setDtd() {
//        try {
//            ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHeadToday();
//
//            double totSale = 0;
//            for (int i = 0; i < salesInvoices.size(); i++) {
//                if (salesInvoices.get(i).getInv_type().equals("Sale")) {
//                    totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
//                }
//            }
//            if (totSale > Constant.TARGET_DTD_MTD / 30) {
//                imgSale.setBackgroundResource(R.drawable.ic_action_up_green);
//            } else {
//                imgSale.setBackgroundResource(R.drawable.ic_action_down_red);
//            }
//<<<<<<< HEAD
//
//            txtMainSell.setText(String.valueOf((int) totSale));
//            txtSell.setText(String.valueOf((int) totSale));
//
//            int totColl = db.getAllInvoiceHeadCollectionToday();
//            if (totColl > Constant.TARGET_DTD_MTD / 30) {
//                imgCollection.setBackgroundResource(R.drawable.ic_action_up_green);
//            } else {
//                imgCollection.setBackgroundResource(R.drawable.ic_action_down_red);
//            }
//            txtCollection.setText(String.valueOf(totColl));
//            final int totalProgressTime = 100;
//            final Thread t = new Thread() {
//                @Override
//                public void run() {
//                    int jumpTime = 0;
//
//                    try {
//                        while (jumpTime < totalProgressTime) {
//                            try {
//                                sleep(50);
//                                jumpTime += 2;
//                                progressMain.setProgressValue(jumpTime);
//                                progressSell.setProgressValue(jumpTime);
//                                progressCollection.setProgressValue(jumpTime);
//                            } catch (InterruptedException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//=======
//        }
//        if (totSale > Constant.TARGET_DTD_MTD / 30) {
//            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
//            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
//        } else {
//            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
//            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
//        }
//
//        txtMainSell.setText(String.valueOf((int) totSale));
//        txtSell.setText(String.valueOf((int) totSale));
//        txtLeft.setText(new DecimalFormat("##.#k").format(totSale/1000));
//
//        double totColl = db.getAllInvoiceHeadCollectionToday();
//        if (totColl > Constant.TARGET_DTD_MTD / 30) {
//            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
//            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
//        } else {
//            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
//            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
//        }
//        txtCollection.setText(String.valueOf((int)totColl));
//        txtRight.setText(new DecimalFormat("##.#k").format(totColl/1000));
//        final int totalProgressTime = 100;
//        final Thread t = new Thread() {
//            @Override
//            public void run() {
//                int jumpTime = 0;
//
//                try {
//                    while (jumpTime < totalProgressTime) {
//                        try {
//                            sleep(50);
//                            jumpTime += 2;
//                            progressMain.setProgressValue(jumpTime);
//                            progressSell.setProgressValue(jumpTime);
//                            progressCollection.setProgressValue(jumpTime);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//>>>>>>> 5e34b104ce2ee846d6d3dc2161a68bc8dd83cabb
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            t.start();
//
//            if (totSale > 100) {
//
//                int val = (int) totSale;
//
//                totalSell = (int) totSale;
//                totalCollection = (int) totColl;
//                counterSell = (int) totSale - 100;
//                counterCollection = (int) totColl - 100;
//                new Thread(new Runnable() {
//
//                    public void run() {
//                        while (counterSell < totalSell) {
//                            try {
//                                Thread.sleep(25);
//                            } catch (InterruptedException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                            txtMainSell.post(new Runnable() {
//
//                                public void run() {
//                                    txtMainSell.setText("" + counterSell);
//
//                                }
//
//                            });
//                            txtSell.post(new Runnable() {
//
//                                public void run() {
//                                    txtSell.setText("" + counterSell);
//
//                                }
//
//                            });
//                            counterSell++;
//
//                            txtCollection.post(new Runnable() {
//
//                                public void run() {
//                                    txtCollection.setText("" + counterCollection);
//
//                                }
//
//                            });
//                            counterCollection++;
//                        }
//
//                    }
//
//                }).start();
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void setMtd() {
        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHead();

        double totSale = 0;
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equals("Sale")) {
                totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
            }
        }

        if (totSale > Constant.TARGET_DTD_MTD) {
            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.soft_green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.red_btn_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        txtMainSell.setText(String.valueOf((int) totSale));
        txtSell.setText(String.valueOf((int) totSale));

        txtLeft.setText(new DecimalFormat("##.#k").format(totSale / 1000));

        double totColl = db.getAllInvoiceHeadCollection();

        if (totColl > Constant.TARGET_DTD_MTD) {
            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.soft_green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.red_btn_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        txtCollection.setText(String.valueOf((int) totColl));
        txtRight.setText(new DecimalFormat("##.#k").format(totColl / 1000));
        final int totalProgressTime = 100;
        try {

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

        } catch (Exception e) {
            e.printStackTrace();
        }

//<<<<<<< HEAD
//=======
//        if (totSale > 100) {
//>>>>>>> ae207932a06e0ad259371e640f3038dad57b5995

        try {
            if (totSale > 100) {

                int val = (int) totSale;

                totalSell = (int) totSale;
                totalCollection = (int) totColl;
                counterSell = (int) totSale - 100;
                counterCollection = (int) totColl - 100;
                try {
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
                                        try {
                                            txtMainSell.setText("" + counterSell);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                                txtSell.post(new Runnable() {

                                    public void run() {
                                        try {
                                            txtSell.setText("" + counterSell);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                                counterSell++;

                                txtCollection.post(new Runnable() {

                                    public void run() {
                                        try {
                                            txtCollection.setText("" + counterCollection);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                                counterCollection++;
                            }

                        }

                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDtd() {
        ArrayList<SalesInvoice> salesInvoices = db.getAllInvoiceHeadToday();

        double totSale = 0;
        for (int i = 0; i < salesInvoices.size(); i++) {
            if (salesInvoices.get(i).getInv_type().equals("Sale")) {
                totSale += Double.parseDouble(salesInvoices.get(i).getTot_amnt_sales());
            }
        }
        if (totSale > Constant.TARGET_DTD_MTD / 30) {
            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.soft_green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            imgSale.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
            imgSale.setColorFilter(ContextCompat.getColor(getContext(), R.color.red_btn_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        txtMainSell.setText(String.valueOf((int) totSale));
        txtSell.setText(String.valueOf((int) totSale));
        txtLeft.setText(new DecimalFormat("##.#k").format(totSale / 1000));

        double totColl = db.getAllInvoiceHeadCollectionToday();
        if (totColl > Constant.TARGET_DTD_MTD / 30) {
            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_up_green));
            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.soft_green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            imgCollection.setBackground(getResources().getDrawable(R.drawable.ic_action_down_red));
            imgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.red_btn_bg_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        txtCollection.setText(String.valueOf((int) totColl));
        txtRight.setText(new DecimalFormat("##.#k").format(totColl / 1000));
        final int totalProgressTime = 100;
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (totSale > 100) {

            int val = (int) totSale;

            totalSell = (int) totSale;
            totalCollection = (int) totColl;
            counterSell = (int) totSale - 100;
            counterCollection = (int) totColl - 100;
            try {
                new Thread(new Runnable() {

                    public void run() {
                        while (counterSell < totalSell) {
                            try {
                                Thread.sleep(25);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            try {

                                txtMainSell.post(new Runnable() {

                                    public void run() {
                                        try {
                                            txtMainSell.setText("" + counterSell);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }

                                });

                                txtSell.post(new Runnable() {

                                    public void run() {
                                        try {
                                            txtSell.setText("" + counterSell);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                                counterSell++;

                                txtCollection.post(new Runnable() {

                                    public void run() {
                                        try {
                                            txtCollection.setText("" + counterCollection);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }

                                });
                                counterCollection++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }

                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}