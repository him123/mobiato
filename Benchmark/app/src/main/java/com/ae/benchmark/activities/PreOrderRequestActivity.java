package com.ae.benchmark.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.OrderAdapter;
import com.ae.benchmark.adapters.RecyclerItemsUnloadAdapter;
import com.ae.benchmark.adapters.SalesAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.SalesInvoice;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.rest.RestClient;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.MyFirebaseMessagingService;
import com.ae.benchmark.util.UtilApp;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Himm on 3/13/2018.
 */

public class PreOrderRequestActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerview_orders)
    RecyclerView recyclerview_orders;

    @InjectView(R.id.rl_checkout)
    RelativeLayout rl_checkout;

    @InjectView(R.id.txt_tot)
    TextView txt_tot;

    SalesAdapter recyclerAdapter;

    List<Item> itemList;
    Item item;
    LinearLayoutManager mLayoutManager;
    private Toolbar toolbar;
    String isCoupon = "", type = "", custName = "", oldOrNew = "";

    @InjectView(R.id.waiting_layout)
    LinearLayout waiting_layout;

    @InjectView(R.id.main_layout)
    RelativeLayout main_layout;

    @InjectView(R.id.txt_counter)
    TextView txt_counter;

    @InjectView(R.id.txt_up)
    TextView txt_up;

    @InjectView(R.id.prb)
    ProgressBar prb;

    @InjectView(R.id.img_sad)
    ImageView img_sad;

    public static final String BROADCAST_ACTION = "com.benchmark.FCM";

    DBManager dbManager;
    TextView mTitle;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales_order);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            isCoupon = extras.getString("isScan");
            type = extras.getString("type");
            custName = extras.getString("name");
            oldOrNew = extras.getString("tag");
            customer = extras.getParcelable("cust");
        }


        registerReceiver(broadcastReceiver, new IntentFilter(InputDailogActivity.BROADCAST_ACTION));
        registerReceiver(broadcastReceiver2, new IntentFilter(MyFirebaseMessagingService.BROADCAST_ACTION));

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        if (type.equals("cash")) {
            mTitle.setText("CASH SALE");

            main_layout.setVisibility(View.VISIBLE);
            waiting_layout.setVisibility(View.GONE);

            if (oldOrNew.equals("new")) {
            }

        } else if (type.equals("norm")) {
            mTitle.setText("SALE");

            main_layout.setVisibility(View.VISIBLE);
            waiting_layout.setVisibility(View.GONE);

            if (oldOrNew.equals("new")) {
            }
        } else if (type.equals("custody")) {
            mTitle.setText("CUSTODY SALE");

            main_layout.setVisibility(View.GONE);
            waiting_layout.setVisibility(View.VISIBLE);

            request_for_approval("SV1", custName, "SM1", "22");


        } else if (type.equals("Order")) {
            mTitle.setText("Order");

            main_layout.setVisibility(View.VISIBLE);
            waiting_layout.setVisibility(View.GONE);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        itemList = new ArrayList<>();


        dbManager = new DBManager(PreOrderRequestActivity.this);

        dbManager.open();

        itemList = dbManager.getVanStock();

        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_orders.setLayoutManager(mLayoutManager);

        recyclerAdapter = new SalesAdapter(itemList, this, isCoupon, oldOrNew);
        recyclerview_orders.setAdapter(recyclerAdapter);


        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("", "Array of qty: " + arrQty.toString());
                makeDilog(arrItem);
            }
        });
    }

    private void makeDilog(final ArrayList<Item> arrItem) {

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_receipt_summary, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        LinearLayout ll_items = deleteDialogView.findViewById(R.id.ll_items);
        LinearLayout ll_qty = deleteDialogView.findViewById(R.id.ll_qty);
        LinearLayout ll_price = deleteDialogView.findViewById(R.id.ll_price);

        TextView txt_sub_tot = deleteDialogView.findViewById(R.id.txt_sub_tot);
        TextView txt_vat = deleteDialogView.findViewById(R.id.txt_vat);
        TextView txt_grand_tot = deleteDialogView.findViewById(R.id.txt_grand_tot);

        double subTot = 0;
        for (int i = 0; i < arrItem.size(); i++) {
            TextView itemTV = new TextView(this);
            TextView qtyTV = new TextView(this);
            TextView priceTV = new TextView(this);

            itemTV.setTypeface(null, Typeface.BOLD);
            qtyTV.setTypeface(null, Typeface.BOLD);
            priceTV.setTypeface(null, Typeface.BOLD);


//            itemTV.setTextSize(14);
//            qtyTV.setTextSize(14);
//            priceTV.setTextSize(14);

            itemTV.setText(arrItem.get(i).item_name_en);
            qtyTV.setText(arrItem.get(i).item_qty);
            priceTV.setText(arrItem.get(i).item_price);

            subTot += Double.parseDouble(arrItem.get(i).item_price);

            ll_items.addView(itemTV);
            ll_qty.addView(qtyTV);
            ll_price.addView(priceTV);
        }

        txt_sub_tot.setText(subTot + "");
        double vatVal = subTot * 100 / 5;

        double grandTot = subTot + vatVal;
        txt_grand_tot.setText(grandTot + "");


        deleteDialogView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });

        deleteDialogView.findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteDialog.dismiss();


                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
                    }
                });

                dbManager = new DBManager(PreOrderRequestActivity.this);
                dbManager.open();

                String date = UtilApp.getCurrentDate();
                String time = UtilApp.getCurrentTime();

//                if (type.equals("cash")) {


//                    dbManager.getBottleQty(arrItem.get(i).item_code, arrItem.get(i).item_qty);


                //CREATING INVOICE FOR SALE
                SalesInvoice salesInvoice = new SalesInvoice();

                long lastInvId = dbManager.getLastInvoiceID();
                long lastCollId = dbManager.getLastCollectionID();
                int invNum = (int) lastInvId + 1;
                int CollNum = (int) lastCollId + 1;
                salesInvoice.inv_no = "" + invNum;
                salesInvoice.inv_type = "Sale";
                salesInvoice.inv_type_code = "01";
                salesInvoice.cust_code = customer.cust_num;
                salesInvoice.cust_sales_org = customer.cust_sales_org;
                salesInvoice.cust_dist_channel = customer.cust_dist_channel;
                salesInvoice.cust_division = customer.cust_division;
                salesInvoice.inv_date = UtilApp.getCurrentDate();
                salesInvoice.del_date = "21-02-2018";
                salesInvoice.cust_name_en = customer.cust_name_en;
                salesInvoice.tot_amnt_sales = "" + price;
                salesInvoice.inv_header_dis_val = "0";
                salesInvoice.inv_header_dis_per = "0";
                salesInvoice.inv_header_vat_val = "0";
                salesInvoice.inv_header_vat_per = "0";

                dbManager.insertSalesInvoiceHeader(salesInvoice);


                double dueAmt = price + 50;
                dbManager.insertCollectionHeader("" + CollNum, "" + invNum, customer.cust_num,
                        customer.cust_name_en, customer.cust_type, "0",
                        "" + price, salesInvoice.inv_date, "" + dueAmt, salesInvoice.inv_date);


                //INSERT TRANSACTION
                Transaction transaction = new Transaction();

                transaction.tr_type = Constant.TRANSACTION_TYPES.TT_SALES_CREATED;
                transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                transaction.tr_customer_num = customer.cust_num;
                transaction.tr_customer_name = customer.cust_name_en;
                transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(PreOrderRequestActivity.this, Constant.SHRED_PR.SALESMANID);
                transaction.tr_invoice_id = invNum + "";
                transaction.tr_order_id = "";
                transaction.tr_collection_id = "";
                transaction.tr_pyament_id = "";

                dbManager.insertTransaction(transaction);

                for (int i = 0; i < arrItem.size(); i++) {
                    String current = dbManager.getBottleQty(arrItem.get(i).item_code);
                    int remaining_qty = Integer.parseInt(current) - Integer.parseInt(arrItem.get(i).item_qty);

                    Item item = new Item();
                    item.sales_inv_nun = salesInvoice.inv_no;
                    item.item_code = arrItem.get(i).item_code;
                    item.item_name_en = arrItem.get(i).item_name_en;
                    item.item_price = arrItem.get(i).item_price;
                    item.item_qty = arrItem.get(i).item_qty;
                    if (arrItem.get(i).is_empty.equals("0"))
                        item.item_barcode = barCodeArr.get(i);
                    else
                        item.item_barcode = "0";

                    item.item_disc_val = "0";
                    item.item_disc_per = "0";
                    item.item_vat_val = "0";
                    item.item_vat_per = "0";

                    dbManager.insertSalesInvoiceItem(item);

                    dbManager.updateVanStock(arrItem.get(i).item_code, "" + remaining_qty);

                    dbManager.updateCustomerTransactionType(customer.cust_num, "sale", "1");
                    if (customer.cust_type.equals("credit"))
                        dbManager.updateCustomerTransactionType(customer.cust_num, "collection", "1");

//                        dbManager.updateUnloadVanStock(arrItem.get(i).item_code, "" + remaining_qty);

                }


//                } else if (type.equals("norm")) {
//                    dbManager.insertTransaction("Invoice Created (Cash Sale)", date, time);
//                } else if (type.equals("custody")) {
//                    dbManager.insertTransaction("Invoice Created (Cash Sale)", date, time);
//                } else if (type.equals("Order")) {
//                    dbManager.insertTransaction("Order Created", date, time);
//                }

                if (isCoupon.equals("yes")) {
                    deleteDialog.dismiss();
                    finish();
                } else {
                    deleteDialog.dismiss();

                    if (customer.cust_type.equals("cash")) {
                        Intent i = new Intent(PreOrderRequestActivity.this, PaymentActivity.class);
                        i.putExtra("name", custName);
                        startActivity(i);

                        finish();
                    } else {
                        Intent i = new Intent(PreOrderRequestActivity.this, CollectionPaymentActivity.class);
                        i.putExtra("name", custName);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });

        deleteDialog.show();
    }

    private void request_for_approval(final String supervisor_id, String cust_id, String salesman_id, String no_of_bottles) {
        RestClient.getMutualTransfer().request_for_approval(supervisor_id,
                cust_id,
                salesman_id,
                no_of_bottles,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.v("", "Response: " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(UtilApp.getString(response.getBody().in()));
                            Log.v("", "==== Json: " + jsonObject.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.v("", "Error: " + error);
                    }
                });
    }

    ArrayList<Double> arrQty = new ArrayList<>();
    ArrayList<Item> arrItem = new ArrayList<>();
    double price;
    ArrayList<String> barCodeArr;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {

            Item item = intent.getParcelableExtra("item");

            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).item_code.equals(item.item_code)) {
                    itemList.get(i).item_qty = item.item_qty;
                }
            }


            Log.d("", "=========== broadcat receiver : " + intent.getStringExtra("message"));
//            Item item = intent.getParcelableExtra("item");
            barCodeArr = intent.getStringArrayListExtra("barcodeArr");
            arrItem.add(item);

            if (intent.getDoubleExtra("price", 0.0) != 0.0)
                arrQty.add(intent.getDoubleExtra("price", 0.0));

            if (intent.getStringExtra("tag").equals("new")) {
                new SweetAlertDialog(PreOrderRequestActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Congratulations!")
                        .setContentText("You are eligible to get free Bottle!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            } else if (intent.getStringExtra("tag").equals("custody")) {

                main_layout.setVisibility(View.GONE);
                waiting_layout.setVisibility(View.VISIBLE);
                mTitle.setText("Custody Sale");

                request_for_approval("SV1", custName, "SM1", "22");

            } else if (intent.getStringExtra("tag").equals("show")) {

                final Dialog alertDialog = new Dialog(PreOrderRequestActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_cash_custody);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                alertDialog.findViewById(R.id.rl_cash).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        alertDialog.dismiss();

                    }
                });

                alertDialog.findViewById(R.id.rl_custody).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        alertDialog.dismiss();

                        main_layout.setVisibility(View.GONE);
                        waiting_layout.setVisibility(View.VISIBLE);
                        mTitle.setText("Custody Sale");

                        request_for_approval("SV1", custName, "SM1", intent.getStringExtra("bottle"));
                    }
                });

                alertDialog.show();
            }

            if (arrQty.size() > 0) {
                price = 0;
                for (int i = 0; i < arrQty.size(); i++)
                    price += arrQty.get(i);

                txt_tot.setText(price + " AED");
            }

        }


    };


    protected void onResume()// Enter from Bottom to Top
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        recyclerAdapter.notifyDataChanged();

    }

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("", "=========== broadcat receiver : " + intent.getStringExtra("message"));


            if (intent.getStringExtra("status").equals("yes")) {

                main_layout.setVisibility(View.VISIBLE);
                waiting_layout.setVisibility(View.GONE);

                new SweetAlertDialog(PreOrderRequestActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Approved!")
                        .setContentText("You request has been approved!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            } else {

                img_sad.setVisibility(View.VISIBLE);
                prb.setVisibility(View.GONE);
                txt_counter.setVisibility(View.GONE);
                txt_up.setText("Your Supervisor has rejected your request!");
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiver2);
    }
}
