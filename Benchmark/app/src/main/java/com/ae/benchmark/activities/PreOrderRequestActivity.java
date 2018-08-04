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
import android.support.v4.content.ContextCompat;
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
//    String isCoupon=;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales_order);
        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            isCoupon = extras.getString("isCoupon");
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

        if (customer.cust_type.equalsIgnoreCase("cash")) {
            mTitle.setText("CASH SALE");
        } else {
            mTitle.setText("CREDIT SALE");
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

        if (isCoupon.equalsIgnoreCase("yes")) {
            itemList = dbManager.getVanStock(false);
        } else {
            itemList = dbManager.getVanStock(true);
        }

        for (int i = 0; i < itemList.size(); i++)
            itemList.get(i).item_qty = "0";


        mLayoutManager = new LinearLayoutManager(this);
        recyclerview_orders.setLayoutManager(mLayoutManager);

        recyclerAdapter = new SalesAdapter(itemList, this, isCoupon, oldOrNew);
        recyclerview_orders.setAdapter(recyclerAdapter);


        rl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("", "Array of qty: " + arrQty.toString());
                double subTot = 0;
                for (int i = 0; i < arrItem.size(); i++) {
                    subTot += Double.parseDouble(arrItem.get(i).item_price);
                }

//                if (customer.cust_type.equalsIgnoreCase("credit")) {
                    if (subTot > 0) {
                        if (Double.parseDouble(customer.cust_avail_bal) < (subTot * 5 / 100) &&
                                customer.cust_type.equalsIgnoreCase("credit")) {
                            Toast.makeText(PreOrderRequestActivity.this, "You don't have enough balance", Toast.LENGTH_SHORT).show();
                        } else {
                            makeDilog(arrItem);
                        }
                    } else {
                        Toast.makeText(PreOrderRequestActivity.this, "Please select at-least one item", Toast.LENGTH_SHORT).show();
                    }
//                }
//                else {
//
//                }

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
        TextView txt_vat_value = deleteDialogView.findViewById(R.id.txt_vat_value);
        TextView txt_grand_tot = deleteDialogView.findViewById(R.id.txt_grand_tot);
        TextView txtBack = deleteDialogView.findViewById(R.id.txtBack);
        TextView txtProceed = deleteDialogView.findViewById(R.id.txtProceed);

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
        double vatVal = subTot * 5 / 100;

        txt_vat_value.setText(vatVal + "");
        final double grandTot = subTot + vatVal;
        txt_grand_tot.setText(grandTot + "");


        deleteDialogView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        txtProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbManager = new DBManager(PreOrderRequestActivity.this);
                dbManager.open();

                //CREATING INVOICE FOR SALE
                SalesInvoice salesInvoice = new SalesInvoice();

                long lastInvId = dbManager.getLastInvoiceID();
                long lastCollId = dbManager.getLastCollectionID();

                double invNum, CollNum;
                if (lastInvId == 0) {
                    invNum = Double.parseDouble(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.INV_LAST));
                } else {
                    invNum = (int) lastInvId + 1;
                }

                if (lastCollId == 0) {
                    CollNum = Double.parseDouble(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.COLLECTION_LAST));
                } else {
                    CollNum = (int) lastCollId + 1;
                }

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
                dbManager.updateCustomerArr(customer.cust_num, String.valueOf(grandTot));

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
                transaction.tr_is_posted = "No";

                dbManager.insertTransaction(transaction);

                for (int i = 0; i < arrItem.size(); i++) {
                    String current = dbManager.getBottleQty(arrItem.get(i).item_code);
                    double remaining_qty = Double.parseDouble(current) - Double.parseDouble(arrItem.get(i).item_qty);

                    Item item = new Item();
                    item.sales_inv_nun = salesInvoice.inv_no;
                    item.item_code = arrItem.get(i).item_code;
                    item.item_name_en = arrItem.get(i).item_name_en;
                    item.item_price = arrItem.get(i).item_price;
                    item.item_qty = arrItem.get(i).item_qty;
                    if (arrItem.get(i).item_barcode != null && isCoupon.equalsIgnoreCase("yes"))
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

                }


                // REDIRECTING ACTIVITY CONDITION
                if (isCoupon.equals("yes")) {
                    deleteDialog.dismiss();

                    Intent intent = new Intent(PreOrderRequestActivity.this, CustomerDetailOperationActivity.class);
                    intent.putExtra("cust", customer);
                    intent.putExtra("tag", "old");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    UtilApp.askForPrint(PreOrderRequestActivity.this, PreOrderRequestActivity.this, intent);
                } else {
                    deleteDialog.dismiss();

                    if (customer.cust_type.equalsIgnoreCase("cash")) {
                        final Intent i = new Intent(PreOrderRequestActivity.this, PaymentActivity.class);
                        i.putExtra("cust", customer);
                        i.putExtra("amount", "" + price);
                        i.putExtra("col_doc_no", "" + CollNum);
                        i.putExtra("invDate", "" + salesInvoice.inv_date);
                        i.putExtra("amt", String.valueOf(grandTot));

//                        UtilApp.askForPrint(PreOrderRequestActivity.this,
//                                PreOrderRequestActivity.this, i);

                        final Dialog alertDialog = new Dialog(PreOrderRequestActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.dialog_print_donot_print);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        ImageView img_print = alertDialog.findViewById(R.id.img_pring);
                        img_print.setColorFilter(ContextCompat.getColor(PreOrderRequestActivity.this, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);

                        alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic

                                alertDialog.dismiss();

                                startActivity(i);

                            }
                        });

                        alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //your business logic
                                alertDialog.dismiss();

                                startActivity(i);

                            }
                        });

                        alertDialog.show();

                    } else {

                        UtilApp.askForPrint(PreOrderRequestActivity.this,
                                PreOrderRequestActivity.this);
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

                txt_tot.setText(price + " SAR");
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


            isCoupon = intent.getStringExtra("isCoupon");
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

//    private void askForPrint(final Intent intent){
//
//        final Dialog alertDialog = new Dialog(PreOrderRequestActivity.this);
//        alertDialog.setCancelable(false);
//        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        alertDialog.setContentView(R.layout.dialog_print_donot_print);
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//
//        alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //your business logic
//
//                alertDialog.dismiss();
//                startActivity(intent);
//
//            }
//        });
//
//        alertDialog.findViewById(R.id.rl_donot_print).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //your business logic
//                alertDialog.dismiss();
//                startActivity(intent);
//
//            }
//        });
//
//        alertDialog.show();
//    }
}
