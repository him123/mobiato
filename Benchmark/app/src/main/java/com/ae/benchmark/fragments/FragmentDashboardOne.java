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

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    String jsonString = "{\n" +
            "\"print_type\":\"OrderRequest\",\n" +
            "\"TripID\":\"\",\n" +
            "\"customer_name_en\":\"\",\n" +
            "\"customer_name_ar\":\"\",\n" +
            "\"SALESMAN\":\"\",\n" +
            "\"ROUTE\":\"\",\n" +
            "\"invoice_date\":\"\",\n" +
            "\"customer_address\":\"\",\n" +
            "\"print_type\":\"\",\n" +
            "\"DOC DATE\":\"\",\n" +
            "\"LPONO\":\"\",\n" +
            "\"INVOICETYPE\":\"\",\n" +
            "\"CONTACTNO\":\"\",\n" +
            "\"TRN\":\"\",\n" +
            "\"LANG\":\"AR\",\n" +
            "\"invoicepaymentterms\":\"2\",\n" +
            "\"ORDERNO\":\"\",\n" +
            "\"invoicenumber\":\"\",\n" +
            "\"TRIP START DATE\":\"\",\n" +
            "\"TIME\":\"45456454\",\n" +
            "\"CUSTOMER\":\"customer name - customer name2\",\n" +
            "\"ADDRESS\":\"\",\n" +
            "\"ARBADDRESS\":\"\",\n" +
            "\"TOTAL\":\"2568.00\",\n" +
            "\n" +
            "\"TOTAL\":[{\n" +
            "\"Total Amount(AED)\":\"2568.00\",\n" +
            "\"Total Befor TAX(AED)\":\"2568.00\",\n" +
            "\"GROSS AMOUNT: AED - \":\"2568.00\"\n" +
            "}\n" +
            "]\n" +
            ",\n" +
            "\"HEADERS\":[\n" +
            "\"ITEM NO\",\n" +
            "\"ENGLISH DESCRIPTION\",\n" +
            "\"ARABIC DESCRIPTION\",\n" +
            "\"UOM\",\n" +
            "\"TOTAL UNITS\",\n" +
            "\"UNIT PRICE\",\n" +
            "\"AMOUNT\"\n" +
            "]\n" +
            "\n" +
            ",\n" +
            "\n" +
            "\"data\":[\n" +
            "[\n" +
            "\"1\",\n" +
            "\"bottle with woater\",\n" +
            "\"bottle with woater\",\n" +
            "\"AE\",\n" +
            "\"25\",\n" +
            "\"12\",\n" +
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
//
//        txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD / 30));
//        db = new DBManager(getActivity());
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//
//        txtDate.setText(UtilApp.getCurrentDate());
////        setDtd();
//        swcDtdMtd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD / 30));
//                    setDtd();
//                } else {
//                    txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD));
//                    setMtd();
//                }
//            }
//        });
//
//        btn_test_print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    PrinterHelper printerHelper = new PrinterHelper(getActivity(), getActivity());
//                    JSONObject jsonObject = new JSONObject(jsonString);
//                    printerHelper.execute(jsonObject);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        return v;
    }


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

                                if (txtMiddleSmall != null)
                                    txtMainSell.post(new Runnable() {

                                        public void run() {
                                            try {
                                                if (txtMiddleSmall != null)
                                                    txtMainSell.setText("" + counterSell);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }

                                    });
                                if (txtSell != null)
                                    txtSell.post(new Runnable() {

                                        public void run() {
                                            try {
                                                if (txtSell != null)
                                                    txtSell.setText("" + counterSell);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    });
                                counterSell++;

                                if (txtCollection != null)
                                    txtCollection.post(new Runnable() {

                                        public void run() {
                                            try {
                                                if (txtCollection != null)
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

//    @Override
//    public void setMenuVisibility(final boolean visible) {
//        super.setMenuVisibility(visible);
//        if (visible) {
////            txtDate.setText(UtilApp.getCurrentDate());
//            setDtd();
//        }
//    }


    @Override
    public void onStart() {
        super.onStart();

        isStarted = true;
        if (isVisible && isStarted){
            viewInit();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewInit();
        }
    }

    private void viewInit(){
        txtMiddleSmall.setText("of " + String.valueOf(Constant.TARGET_DTD_MTD / 30));
        db = new DBManager(getActivity());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        txtDate.setText(UtilApp.getCurrentDate());
//        setDtd();
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

        setDtd();
    }
}