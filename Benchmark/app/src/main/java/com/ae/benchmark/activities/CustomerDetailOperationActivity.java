package com.ae.benchmark.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.adapters.CollectionAdapter;
import com.ae.benchmark.adapters.FeedAdapter;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Feed;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;
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

public class CustomerDetailOperationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private com.github.clans.fab.FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fab1, fab2, fab3, fab4, fab5;
    Context context;

    @InjectView(R.id.recycle_feed)
    RecyclerView recycle_feed;

    @InjectView(R.id.txt_credit_limit)
    TextView txt_credit_limit;

    @InjectView(R.id.txt_credit_days)
    TextView txt_credit_days;

    @InjectView(R.id.txt_available_bal)
    TextView txt_available_bal;

    FeedAdapter recyclerAdapter;

    List<Feed> itemList;
    List<Transaction> itemListTransaction;
    Feed feed;
    LinearLayoutManager mLayoutManager;
    MaterialShowcaseSequence sequence;
    DBManager dbManager;

    @InjectView(R.id.txt_empty)
    TextView txt_empty;
    Customer customer;
    String oldOrNew;
    String isStockCaptured = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.inject(this);
        context = this;

        dbManager = new DBManager(CustomerDetailOperationActivity.this);
        dbManager.open();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            custName = extras.getString("name");
            oldOrNew = extras.getString("tag");
            customer = extras.getParcelable("cust");
        }


        txt_credit_days.setText(customer.cust_payment_term);
        txt_credit_limit.setText(customer.cust_credit_limit + " SAR");
        txt_available_bal.setText(customer.cust_avail_bal + " SAR");

        sequence = new MaterialShowcaseSequence(this, "OPR");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        UtilApp.WriteSharePrefrence(CustomerDetailOperationActivity.this, Constant.SHRED_PR.ISPAYMET, true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(customer.cust_name_en);

        Typeface mFontBold = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_bold.ttf");
        Typeface mFontLight = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_light.ttf");
        Typeface mFontRegular = Typeface.createFromAsset(getAssets(),
                "fonts/proximanova_regular.ttf");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab5);


        fam = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fab_menu);

        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new com.github.clans.fab.FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
            }
        });

        itemList = new ArrayList<>();

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetailOperationActivity.this, CaptureStockActivity.class);
                i.putExtra("flag", "ORD");
                i.putExtra("name", customer.cust_name_en);
                i.putExtra("num", customer.cust_num);
                i.putExtra("cust", customer);
                i.putExtra("tag", oldOrNew);

                i.putExtra("tag", oldOrNew);
                startActivity(i);
                fam.close(true);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetailOperationActivity.this, FragmentContainActivity.class);
                i.putExtra("flag", "ORD");
                i.putExtra("name", customer.cust_name_en);
                i.putExtra("tag", oldOrNew);
                i.putExtra("cust", customer);
                startActivity(i);
                fam.close(true);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDilog(CustomerDetailOperationActivity.this, customer, oldOrNew);
                fam.close(true);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetailOperationActivity.this, FragmentContainActivity.class);
                i.putExtra("flag", "COL");
                i.putExtra("cust", customer);
                startActivity(i);
                fam.close(true);
            }
        });


        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetailOperationActivity.this, FragmentContainActivity.class);
                i.putExtra("flag", "MER");
                i.putExtra("cust", customer);
                startActivity(i);
                fam.close(true);
            }
        });
    }

    private void makeDilog(final Context context, final Customer customer, final String oldOrNew) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.dialog_coupon_or_not, null);
        TextView txt_title = deleteDialogView.findViewById(R.id.txt_title);
        txt_title.setText("COUPON");
//        Spinner spinner2 = (Spinner) deleteDialogView.findViewById(R.id.sp_reason);
//        spinner2.setAdapter(dataAdapter);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.rl_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

                Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
                i.putExtra("isCoupon", "yes");
                i.putExtra("type", "cash");
                i.putExtra("name", customer.cust_name_en);
                i.putExtra("cust", customer);
                i.putExtra("tag", oldOrNew);
                startActivity(i);
            }
//                });

//                deleteDialogView.findViewById(R.id.rl_custody).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //your business logic
//                        deleteDialog.dismiss();
//
//                        Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
//                        i.putExtra("isScan", "yes");
//                        i.putExtra("type", "custody");
//                        i.putExtra("name", custName);
//                        i.putExtra("tag", oldOrNew);
//                        startActivity(i);
//                    }
//                });

//                deleteDialogView.findViewById(R.id.rl_normal).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //your business logic
//                        deleteDialog.dismiss();
//
//                        Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
//                        i.putExtra("isScan", "yes");
//                        i.putExtra("type", "norm");
//                        i.putExtra("name", custName);
//                        i.putExtra("tag", oldOrNew);
//                        startActivity(i);
//                    }
//                });
//
//                deleteDialog.show();
//            }
        });

        deleteDialogView.findViewById(R.id.rl_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();

                Intent i = new Intent(CustomerDetailOperationActivity.this, PreOrderRequestActivity.class);
                i.putExtra("isCoupon", "no");
                i.putExtra("type", "norm");
                i.putExtra("name", customer.cust_name_en);
                i.putExtra("cust", customer);
                i.putExtra("tag", oldOrNew);
                startActivity(i);
            }
        });

        deleteDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String emptydBottles = dbManager.getLoadEmptyOrFilledBottles(0, customer.cust_num);
        txt_empty.setText(emptydBottles);

        isStockCaptured = dbManager.getCustStockCaptured(customer.cust_num);


        fab1.setLabelTextColor(context.getResources().getColor(R.color.white));

        if (isStockCaptured.equals("1")) {

//            fam.setSelected(true);

            fab2.setEnabled(true);
            fab3.setEnabled(true);
            fab4.setEnabled(true);
            fab5.setEnabled(true);

            fab2.setLabelTextColor(context.getResources().getColor(R.color.white));
            fab3.setLabelTextColor(context.getResources().getColor(R.color.white));
            fab4.setLabelTextColor(context.getResources().getColor(R.color.white));
            fab5.setLabelTextColor(context.getResources().getColor(R.color.white));

        } else {
            fab2.setEnabled(false);
            fab3.setEnabled(false);
            fab4.setEnabled(false);
            fab5.setEnabled(false);

            fab2.setLabelTextColor(context.getResources().getColor(R.color.gray));
            fab3.setLabelTextColor(context.getResources().getColor(R.color.gray));
            fab4.setLabelTextColor(context.getResources().getColor(R.color.gray));
            fab5.setLabelTextColor(context.getResources().getColor(R.color.gray));
        }


        itemList.clear();


        itemListTransaction = dbManager.getAllTransactionsForCustomer(customer.cust_num);

        boolean flag1 = false, flag2 = false;

        if (itemListTransaction.size() > 0) {
            for (int i = 0; i < itemListTransaction.size(); i++) {

                feed = new Feed();

                if (itemListTransaction.get(i).tr_date_time.equals(UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime())) {
                    if (!flag1) {
                        feed.type = 1;
                        feed.date = "Today";
                        flag1 = true;
                        i--;
                    } else {
                        feed.type = 2;
                        feed.desc = itemListTransaction.get(i).tr_date_time;


                        switch (itemListTransaction.get(i).tr_type) {
                            case Constant.TRANSACTION_TYPES.TT_CASH_COLLECTION:
                                feed.name = "Cash collection created";
                                feed.inv_no = "Collection id: " + itemListTransaction.get(i).tr_collection_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_CREDIT_COLLECTION:
                                feed.name = "Credit collection created";
                                feed.inv_no = "Collection id: " + itemListTransaction.get(i).tr_collection_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_PAYMENT_BY_CASH:
                                feed.name = "Payment done by cash.";
                                feed.inv_no = "Payment id: " + itemListTransaction.get(i).tr_pyament_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_PAYMENT_BY_CHEQUE:
                                feed.name = "Payment done by cheque.";
                                feed.inv_no = "Payment id: " + itemListTransaction.get(i).tr_pyament_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_STOCK_CAP:
                                feed.name = "Stock captured.";
                                feed.inv_no = "";
                                break;
                            case Constant.TRANSACTION_TYPES.TT_SALES_CREATED:
                                feed.name = "Sales created.";
                                feed.inv_no = "Invoice id: " + itemListTransaction.get(i).tr_invoice_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_OREDER_CREATED:
                                feed.name = "Order created.";
                                feed.inv_no = "Order id: " + itemListTransaction.get(i).tr_order_id;
                                break;

                            default:
                        }

                    }

                    itemList.add(feed);

                }
            }


            for (int i = 0; i < itemListTransaction.size(); i++) {

                feed = new Feed();

                if (itemListTransaction.get(i).tr_date_time.equals(UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime())) {

                } else {
                    if (!flag2) {
                        feed.type = 1;
                        feed.date = "Yesterday";
                        flag2 = true;
                        i--;
                    } else {
                        feed.type = 2;
                        feed.desc = itemListTransaction.get(i).tr_date_time;
//                        feed.inv_no = itemListTransaction.get(i).tr_collection_id;

                        switch (itemListTransaction.get(i).tr_type) {
                            case Constant.TRANSACTION_TYPES.TT_CASH_COLLECTION:
                                feed.name = "Cash collection created";
                                feed.inv_no = "Collection id: " + itemListTransaction.get(i).tr_collection_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_CREDIT_COLLECTION:
                                feed.name = "Credit collection created";
                                feed.inv_no = "Collection id: " + itemListTransaction.get(i).tr_collection_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_PAYMENT_BY_CASH:
                                feed.name = "Payment done by cash.";
                                feed.inv_no = "Payment id: " + itemListTransaction.get(i).tr_pyament_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_PAYMENT_BY_CHEQUE:
                                feed.name = "Payment done by cheque.";
                                feed.inv_no = "Payment id: " + itemListTransaction.get(i).tr_pyament_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_STOCK_CAP:
                                feed.name = "Stock captured.";
                                feed.inv_no = "";
                                break;
                            case Constant.TRANSACTION_TYPES.TT_SALES_CREATED:
                                feed.name = "Sales created.";
                                feed.inv_no = "Invoice id: " + itemListTransaction.get(i).tr_invoice_id;
                                break;
                            case Constant.TRANSACTION_TYPES.TT_OREDER_CREATED:
                                feed.name = "Order created.";
                                feed.inv_no = "Order id: " + itemListTransaction.get(i).tr_invoice_id;
                                break;

                            default:
                        }
                    }
                    itemList.add(feed);
                }
            }
        }


        mLayoutManager = new LinearLayoutManager(CustomerDetailOperationActivity.this);
        recycle_feed.setLayoutManager(mLayoutManager);

        recyclerAdapter = new FeedAdapter(itemList, CustomerDetailOperationActivity.this, itemListTransaction);
        recycle_feed.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cust_operation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.nav_details:{

                Intent intent = new Intent(getApplicationContext() , CustomerDetailActivity.class);
                intent.putExtra("cust" , customer);
                startActivity(intent);
                break;
            }


            case R.id.nav_stock:
                Toast.makeText(getBaseContext(), "You selected Stock", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sales:
                Toast.makeText(getBaseContext(), "You selected Sale", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Print:
                Toast.makeText(getBaseContext(), "You selected Print", Toast.LENGTH_SHORT).show();
                break;

            case R.id.coupon_history:
                startActivity(new Intent(CustomerDetailOperationActivity.this, CouponHistoryActivity.class));
//                Toast.makeText(getBaseContext(), "You selected Promotion", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;

    }
}
