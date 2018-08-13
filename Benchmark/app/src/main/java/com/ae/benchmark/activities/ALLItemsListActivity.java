package com.ae.benchmark.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.RecyclerItemsAdapter;
import com.ae.benchmark.adapters.RecyclerItemsAdapterForALL;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class ALLItemsListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.recyclerview_items)
    RecyclerView recyclerview_items;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    @InjectView(R.id.txt_tot)
    TextView txt_tot;

    @InjectView(R.id.card_date)
    CardView card_date;

    RecyclerItemsAdapterForALL recyclerAdapter;

    public static List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    DBManager dbManager;

    String load_no;
    String with_laod;

    Transaction transaction = new Transaction();
    List<Item> newItemArr = new ArrayList<>();
    int ordId = 0, loadId = 0;
    Customer customer;
    double tot = 0;
    String is_return;

    String orderPrint = "{\n" +
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
            "\"TIME\":\"45456454\",\n" +
            "\n" +
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.inject(this);

        card_date.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            load_no = extras.getString("load_no");
            with_laod = extras.getString("with_load");
            is_return = extras.getString("is_return");

            if (with_laod.equals("no"))
                customer = extras.getParcelable("cust");
        }

        registerReceiver(broadcastReceiver2, new IntentFilter(RecyclerItemsAdapterForALL.BROADCAST_ACTION));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ALL ITEMS");

        itemList = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbManager = new DBManager(ALLItemsListActivity.this);

        dbManager.open();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemList.size() <= 0) {
                    Toast.makeText(ALLItemsListActivity.this, "Please select item", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < itemList.size(); i++) {
                    if (!itemList.get(i).item_qty.equals("0")) {
                        Item item = itemList.get(i);
                        newItemArr.add(item);
                        tot += Double.parseDouble(itemList.get(i).item_price);
                    }
                }

                long lastIOrdId = dbManager.getLastOrderID();
                if (lastIOrdId == 0) {
                    ordId = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.ORD_LAST));
                } else {
                    ordId = (int) lastIOrdId + 1;
                }

                long lastLoadId = dbManager.getLastLoadHeaderNo();
                if (lastLoadId == 0) {
                    loadId = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.LOAD_LAST));
                } else {
                    loadId = (int) lastLoadId + 1;
                }
                itemList.clear();
                recyclerAdapter = new RecyclerItemsAdapterForALL(newItemArr, ALLItemsListActivity.this, false);
                recyclerview_items.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataChanged();

                fab.setVisibility(View.GONE);
                rl_checkout.setVisibility(View.VISIBLE);
                txt_tot.setText("Total SAR: " + tot);


            }
        });

        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final JSONObject outterObject = new JSONObject();
                JSONArray headerData = null;
//                JSONArray headerDataReturn = null;
                JSONArray headerJarr = new JSONArray();
                JSONObject totalObj = new JSONObject();

                long lastInvId = dbManager.getLastInvoiceID();
                long lastOrderId;
                if (is_return.equalsIgnoreCase("yes")) {
                    lastOrderId = dbManager.getLastReturnID();
                } else {
                    lastOrderId = dbManager.getLastOrderID();
                }

                int invNum, ordId;

                invNum = (int) lastInvId + 1;
                ordId = (int) lastOrderId + 1;
//                }

                if (with_laod.equals("yes")) {

                    //CREATE LOAD WITH LOAD ITEMS
                    dbManager.insertLoad(newItemArr, loadId + "", UtilApp.getCurrentDate(), "0", "1", "1");

                    //INSERT TRANSACTION
                    Transaction transaction = new Transaction();

                    transaction.tr_type = Constant.TRANSACTION_TYPES.TT_LOAD_CREATE;
                    transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                    transaction.tr_customer_num = "";
                    transaction.tr_customer_name = "";
                    transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                    transaction.tr_invoice_id = invNum + "";
                    transaction.tr_order_id = ordId + "";
                    transaction.tr_collection_id = "";
                    transaction.tr_pyament_id = "";

                    dbManager.insertTransaction(transaction);

                    //CREATING INVOICE FOR ORDER
                    SalesInvoice salesInvoice = new SalesInvoice();

                    salesInvoice.inv_no = "" + invNum;
                    salesInvoice.inv_type = "Order";
                    salesInvoice.inv_type_code = "02";
                    salesInvoice.cust_code = "";
                    salesInvoice.cust_sales_org = "";
                    salesInvoice.cust_dist_channel = "";
                    salesInvoice.cust_division = "";
                    salesInvoice.inv_date = UtilApp.getCurrentDate();
                    salesInvoice.del_date = UtilApp.getCurrentDate();
                    salesInvoice.cust_name_en = "";
                    salesInvoice.tot_amnt_sales = "" + tot;
                    salesInvoice.inv_header_dis_val = "0";
                    salesInvoice.inv_header_dis_per = "0";
                    salesInvoice.inv_header_vat_val = "0";
                    salesInvoice.inv_header_vat_per = "0";

                    dbManager.insertSalesInvoiceHeader(salesInvoice);


                    new SweetAlertDialog(ALLItemsListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Load created successfully!")
                            .setContentText("Your Load request sent successfully!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    try {
                                        JSONObject outterObject = new JSONObject(orderPrint);
                                        UtilApp.askForPrint(ALLItemsListActivity.this, ALLItemsListActivity.this, outterObject);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .show();


                } else {

                    if (is_return.equalsIgnoreCase("yes")) {
                        dbManager.insertReturnItems(
                                newItemArr,
                                ordId + "",
                                UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID),
                                customer.cust_num, tot + "", UtilApp.getCurrentDate());
                    } else {
                        dbManager.insertOrderItems(
                                newItemArr,
                                ordId + "",
                                UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID),
                                customer.cust_num, tot + "", UtilApp.getCurrentDate());
                    }
                    //INSERT TRANSACTION
                    Transaction transaction = new Transaction();

                    if (is_return.equalsIgnoreCase("yes"))
                        transaction.tr_type = Constant.TRANSACTION_TYPES.TT_RETURN_CREATED;
                    else
                        transaction.tr_type = Constant.TRANSACTION_TYPES.TT_OREDER_CREATED;
                    transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                    transaction.tr_customer_num = customer.cust_num;
                    transaction.tr_customer_name = customer.cust_name_en;
                    transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID);
                    transaction.tr_invoice_id = invNum + "";
                    transaction.tr_order_id = "";
                    transaction.tr_collection_id = "";
                    transaction.tr_pyament_id = "";

                    dbManager.insertTransaction(transaction);

                    //CREATING INVOICE FOR ORDER
                    SalesInvoice salesInvoice = new SalesInvoice();

                    salesInvoice.inv_no = "" + invNum;
                    salesInvoice.inv_type = "Order";
                    salesInvoice.inv_type_code = "02";
                    salesInvoice.cust_code = customer.cust_num;
                    salesInvoice.cust_sales_org = customer.cust_sales_org;
                    salesInvoice.cust_dist_channel = customer.cust_dist_channel;
                    salesInvoice.cust_division = customer.cust_division;
                    salesInvoice.inv_date = UtilApp.getCurrentDate();
                    salesInvoice.del_date = UtilApp.getCurrentDate();
                    salesInvoice.cust_name_en = customer.cust_name_en;
                    salesInvoice.tot_amnt_sales = "" + tot;
                    salesInvoice.inv_header_dis_val = "0";
                    salesInvoice.inv_header_dis_per = "0";
                    salesInvoice.inv_header_vat_val = "0";
                    salesInvoice.inv_header_vat_per = "0";

                    dbManager.insertSalesInvoiceHeader(salesInvoice);


                    try {

                        double totAmt = 0;
                        for (int i = 0; i < newItemArr.size(); i++) {
//                            headerData.put(i + "");// SI NO

                            headerData = new JSONArray();
//                            headerDataReturn = new JSONArray();

                            Item item = newItemArr.get(i);

//                            headerDataReturn.put(item.item_code);
//                            headerDataReturn.put(item.item_name_en);
//                            headerDataReturn.put(item.item_code);
//                            headerDataReturn.put(item.item_code);
//                            headerDataReturn.put(item.item_code);

                            headerData.put(item.item_code); // ITEM CODE
                            headerData.put(item.item_name_en);// EN NAME
                            headerData.put(item.item_name_en);// AR NAME

                            headerData.put(item.item_uom);// UOM
                            double totUnits = +Double.parseDouble(item.item_qty);
                            headerData.put(totUnits + ""); // TOTAL UNITS
                            headerData.put(item.item_price); // UNIT PRICE
                            totAmt = +Double.parseDouble(item.item_price);
                            headerData.put(totAmt + ""); // Amount

//                        double vatAmt = totAmt * 5 / 100;
//                        headerData.put(vatAmt + ""); //Vat Amt
//                        headerData.put("5"); //Vat %
//                        double amtSAR = totAmt + vatAmt;
//                        headerData.put(amtSAR + ""); //Amount SAR
                            headerJarr.put(headerData);
                        }


                        outterObject.put("customer_name_en", "");
                        outterObject.put("customer_name_ar", "");
                        outterObject.put("SALESMAN", "");
                        outterObject.put("ROUTE", "");
                        outterObject.put("TripID", "fdasf");
                        outterObject.put("invoice_date", UtilApp.getCurrentDate());
                        outterObject.put("customer_address", "");
                        outterObject.put("print_type", Constant.ORDER_REQUEST);
                        outterObject.put("DOC DATE", "");
                        outterObject.put("LPONO", "");
                        outterObject.put("CONTACTNO", "");
                        outterObject.put("CUSTOMER", "customer1 - custoerm2");
                        outterObject.put("ADDRESS", "");
                        outterObject.put("ARBADDRESS", "");
                        outterObject.put("ORDERNO", "");
                        outterObject.put("TRIP START DATE", "");
                        outterObject.put("invoicepriceprint", "1");
                        outterObject.put("invoicepaymentterms", "2");
                        outterObject.put("SUB TOTAL", totAmt + "");
                        outterObject.put("invoicenumber", invNum);
                        outterObject.put("TIME", UtilApp.getCurrentDate());
                        outterObject.put("LANG", "AR");
                        outterObject.put("INVOICE DISCOUNT", "");
                        outterObject.put("VAT", "5");
                        outterObject.put("NET SALES", totAmt);
                        outterObject.put("invoicefooter", "");

                        Const.custPayID = customer.cust_num;
                        Const.custPayName = customer.cust_name_en;
                        Const.custPayNameAR = customer.cust_name_ar;
                        Const.custPayAddress = customer.cust_address;
                        Const.cusVATno = "";
                        Const.cusBranchID = "";
                        Const.cusBranchName = "";


//                        if(is_return.equalsIgnoreCase("yes")){
//                            // HEADER ARRAY RETURN
//                            JSONArray HEADER = new JSONArray();
//                            HEADER.put("ITEM#");
//                            HEADER.put("DESCRIPTION");
//                            HEADER.put("INVOICE CREDIT");
//                            HEADER.put("LOADED IN");
//                            HEADER.put("PRICE");
//                            HEADER.put("-----VARIANCE----- QTY         AMOUNT");
//
//                        }else{
                        // HEADER ARRAY ORDER
                        JSONArray HEADER = new JSONArray();
                        HEADER.put("ITEM NO");
                        HEADER.put("ENGLISH DESCRIPTION");
                        HEADER.put("ARABIC DESCRIPTION");
                        HEADER.put("UOM");
                        HEADER.put("TOTAL UNITS");
                        HEADER.put("UNIT PRICE");
                        HEADER.put("AMOUNT");
//                        }


                        //ADDING TOTAL IN MAIN OBJECT
                        totalObj.put("Total Amount(AED)", totAmt);
                        double afterTax = totAmt * 5 / 100;
                        totalObj.put("Total Befor TAX(AED)", totAmt);
                        totalObj.put("GROSS AMOUNT: AED - ", afterTax);

                        //ADDING DATA IN MAIN OBJECT
                        JSONArray totalArr = new JSONArray();
                        totalArr.put(totalObj);

                        outterObject.put("TOTAL", totalArr);
                        outterObject.put("data", headerJarr);
                        outterObject.put("HEADERS", HEADER);

                        Log.v("", "Check this json array: " + outterObject.toString());


                        final Dialog alertDialog = new Dialog(ALLItemsListActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.dialog_print_donot_print);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        ImageView img_print = alertDialog.findViewById(R.id.img_pring);

                        img_print.setColorFilter(ContextCompat.getColor(ALLItemsListActivity.this, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);

                        dbManager.updateCustomerTransactionType(customer.cust_num, "order", "1");
                        alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic

                                alertDialog.dismiss();

                                try {
                                    PrinterHelper printerHelper =
                                            new PrinterHelper(ALLItemsListActivity.this,
                                                    ALLItemsListActivity.this);
                                    if (is_return.equalsIgnoreCase("yes")) {
                                        JSONObject jsonObject = createDataforBadReturns();
                                        printerHelper.execute(jsonObject);
                                    } else {
                                        printerHelper.execute(outterObject);
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                alertDialog.dismiss();
                                finish();
                            }
                        });

                        alertDialog.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    new SweetAlertDialog(ALLItemsListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText("Done!")
//                            .setContentText("Your order created successfully!")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                    dbManager.updateCustomerTransactionType(customer.cust_num, "order", "1");
////                                    finish();
//
//                                    try{
//                                        JSONObject outterObject = new JSONObject(orderPrint);
//                                        UtilApp.askForPrint(ALLItemsListActivity.this,
//                                                ALLItemsListActivity.this,outterObject );
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            })
//                            .show();

                }


            }
        });
    }

    protected void onResume()// Enter from Bottom to Top
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        itemList.clear();
        itemList = dbManager.getAllItems();

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_items.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerItemsAdapterForALL(itemList, this, false);
        recyclerview_items.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();
    }

    protected void onPause()// Exit from Top to Bottom
    {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

//    protected void onResume()// Enter from Bottom to Top
//    {
//        super.onResume();
//        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//    }
//
//    protected void onPause()// Exit from Top to Bottom
//    {
//        super.onPause();
//        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
//    }


    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).item_code.equals(intent.getStringExtra("item_code")))
                    itemList.get(i).item_qty = intent.getStringExtra("item_qty");
            }

            recyclerAdapter.notifyDataChanged();
//            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver2);
    }

    public void callback() {
        finish();
    }

    public JSONObject createDataforBadReturns() {
        JSONArray jArr = new JSONArray();
        JSONObject mainArr = new JSONObject();
        try {
            JSONArray jInter = new JSONArray();
            JSONObject jDict = new JSONObject();
//            jDict.put(App.REQUEST, App.BAD_RETURN_REPORT);

            mainArr.put("print_type", Constant.BAD_RETURN_REPORT);
            mainArr.put("ROUTE", UtilApp.ReadSharePrefrence(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
            mainArr.put("DOC DATE", UtilApp.getCurrentDate());
            mainArr.put("TIME", UtilApp.getCurrentTime());
            mainArr.put("SALESMAN", UtilApp.ReadSharePrefrence(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
            mainArr.put("CONTACTNO", "1234");
            mainArr.put("DOCUMENT NO", "80001234");  //Load Summary No
            mainArr.put("ORDERNO", "80001234");  //Load Summary No
            mainArr.put("TRIP START DATE", "");
            mainArr.put("supervisorname", "-");
            mainArr.put("TripID", UtilApp.ReadSharePrefrence(ALLItemsListActivity.this, Constant.SHRED_PR.SALESMANID));
            //mainArr.put("invheadermsg","HAPPY NEW YEAR");
            mainArr.put("LANG", "en");
            mainArr.put("invoicepaymentterms", "2");
            mainArr.put("invoicenumber", "1300000001");
            mainArr.put("INVOICETYPE", "SALES INVOICE");
            String arabicCustomer = "اللولو هايبر ماركت";
            mainArr.put("CUSTOMER", "LULU HYPER MARKET" + "-" + arabicCustomer);
            mainArr.put("ADDRESS", "3101, 21st Street, Riyadh");
            mainArr.put("ARBADDRESS", "");
            mainArr.put("displayupc", "0");
            mainArr.put("invoicepriceprint", "1");
            mainArr.put("SUB TOTAL", "1000");
            mainArr.put("INVOICE DISCOUNT", "20");
            mainArr.put("NET SALES", "980");
            mainArr.put("closevalue", "+5000");
            mainArr.put("damagevariance", "+1000");
            mainArr.put("TOTAL_DAMAGE_VALUE", "+2000");
            //mainArr.put("Load Number","1");


//            JSONArray jData = new JSONArray();
//            for(DepositReport obj:depositReports){
//                JSONArray jData1 = new JSONArray();
//                jData1.put(obj.getInvoiceNo());
//                jData1.put(obj.getCustomerNo());
//                jData1.put(obj.getCustomerName());
//                jData1.put(obj.getChequeNo());
//                jData1.put(obj.getChequeDate());
//                jData1.put(obj.getBankName());
//                jData1.put(obj.getChequeAmount());
//                totalCheque += Double.parseDouble(obj.getChequeAmount());
//                jData1.put(obj.getCashAmount());
//                totalCash += Double.parseDouble(obj.getCashAmount());
//                jData.put(jData1);
//            }


            JSONArray HEADERS = new JSONArray();
            JSONArray TOTAL = new JSONArray();
            HEADERS.put("ITEM#");
            HEADERS.put("DESCRIPTION");
            HEADERS.put("INVOICE CREDIT");
            HEADERS.put("LOADED IN");
            HEADERS.put("PRICE");//Summation of all
            HEADERS.put("-----VARIANCE----- QTY         AMOUNT");  //Truck Damage
            //HEADERS.put("Description");
            //HEADERS.put(obj1);
            // HEADERS.put(obj2);
            mainArr.put("HEADERS", HEADERS);
            JSONObject totalObj = new JSONObject();
            totalObj.put("INVOICE CREDIT", "+200");
            totalObj.put("LOADED IN", "+100");  //Summation of all
            totalObj.put("-----VARIANCE----- QTY         AMOUNT", "+100");  //Summation of all
            TOTAL.put(totalObj);
            mainArr.put("TOTAL", TOTAL);
            JSONArray jData1 = new JSONArray();


            jData1.put("14020106");
            jData1.put("Test Material");
            jData1.put("+10");
            jData1.put("+9");
            jData1.put("+12");
            jData1.put("-1         +12");
            JSONArray jData2 = new JSONArray();
            jData2.put("14020106");
            jData2.put("Test Material");
            jData2.put("+10");
            jData2.put("+9");
            jData2.put("+12");
            jData2.put("-1         +12");
            JSONArray jData3 = new JSONArray();
            jData3.put("14020106");
            jData3.put("Test Material");
            jData3.put("+10");
            jData3.put("+9");
            jData3.put("+12");
            jData3.put("-1         +12");
            JSONArray jData = new JSONArray();
            jData.put(jData1);
            jData.put(jData2);
            jData.put(jData3);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DATA", jData);
            jsonObject.put("HEADERS", HEADERS);
            jsonObject.put("TOTAL", totalObj);
            JSONArray jDataNew = new JSONArray();
            jDataNew.put(jsonObject);
            mainArr.put("data", jData);
            // mainArr.put("tcData",jData);
            //  mainArr.put("creditData",jData);

            /*mainArr.put("data",jData);
            mainArr.put("data",jData);
            mainArr.put("data",jData);
*/
            jDict.put("mainArr", mainArr);
            jInter.put(jDict);
            jArr.put(jInter);
            jArr.put(HEADERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainArr;
    }
}
