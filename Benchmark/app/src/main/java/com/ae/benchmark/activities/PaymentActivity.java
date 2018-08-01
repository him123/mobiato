package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Payment;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    TextView mTitle;
    Context context;
    @InjectView(R.id.edt_amount)
    EditText edt_amount;

    @InjectView(R.id.txt_amount)
    TextView txt_amount;

    @InjectView(R.id.txt_cust_name)
    TextView txt_cust_name;

    @InjectView(R.id.swcPayment)
    Switch swcPayment;
    @InjectView(R.id.llCash)
    LinearLayout llCash;
    @InjectView(R.id.edt_cheque_amount)
    EditText edtChequeAmount;
    @InjectView(R.id.edt_cheque_number)
    EditText edtChequeNumber;
    @InjectView(R.id.spinner)
    Spinner spinner;
    @InjectView(R.id.edt_date)
    EditText edtDate;
    @InjectView(R.id.llCheque)
    LinearLayout llCheque;
    @InjectView(R.id.btnPayment)
    Button btnPayment;

    //    String name;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    //<<<<<<< HEAD
    String amount, col_doc_no, invDate;
    //=======
//    String amount = "";
//>>>>>>> 21c334a48f6a85e7ca04640c5fd56ed69100b27d
    Customer customer;
    DBManager dbManager;
    String grandTot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DBManager(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            name = extras.getString("name");
            customer = extras.getParcelable("cust");
//            txt_cust_name.setText(name);
            amount = extras.getString("amount");
            col_doc_no = extras.getString("col_doc_no");
            invDate = extras.getString("invDate");
            grandTot = extras.getString("amt");
        }

        txt_amount.setText(amount);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText(customer.cust_name_en);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_amount.addTextChangedListener(textWatcher);

        swcPayment.setChecked(true);
        swcPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    llCash.setVisibility(View.VISIBLE);
                    llCheque.setVisibility(View.GONE);
                } else {
                    llCash.setVisibility(View.GONE);
                    llCheque.setVisibility(View.VISIBLE);
                }
            }
        });

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/" + "MM/" + "yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edtDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        //END BUTTON
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList();
        categories.add("Bank A");
        categories.add("Bank B");
        categories.add("Bank C");
        categories.add("Bank D");
        categories.add("Bank E");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//<<<<<<< HEAD

                if (!edt_amount.getText().toString().equalsIgnoreCase("")) {

                    Payment payment = new Payment();
                    dbManager.open();

                    long lastInvId = dbManager.getLastInvoiceID();
                    long lastCollId = dbManager.getLastCollectionID();

                    int invNum, CollNum;
                    if (lastInvId == 0) {
                        invNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.INV_LAST));
                    } else {
                        invNum = (int) lastInvId + 1;
                    }

                    if (lastCollId == 0) {
                        CollNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.COLLECTION_LAST));
                    } else {
                        CollNum = (int) lastCollId + 1;
                    }

                    if (!swcPayment.isChecked()) {
                        payment.setInvoice_id(String.valueOf(invNum));
                        payment.setCollection_id(String.valueOf(CollNum));
                        payment.setPayment_type("Cash");
                        payment.setPayment_date(UtilApp.getCurrentDate());
                        payment.setCheque_no("");
                        payment.setBank_name("");
                        payment.setPayment_amount(edt_amount.getText().toString());
                        payment.setCust_id("");
                    } else {
                        payment.setInvoice_id(String.valueOf(invNum));
                        payment.setCollection_id(String.valueOf(CollNum));
                        payment.setPayment_type("Cheque");
                        payment.setPayment_date(edtDate.getText().toString());
                        payment.setCheque_no(edtChequeNumber.getText().toString());
                        payment.setBank_name(spinner.getSelectedItem().toString());
                        payment.setPayment_amount(edtChequeAmount.getText().toString());
                        payment.setCust_id("");
                    }

                    if (dbManager.checkIsCollectionAlreadyExist(col_doc_no)) { // UPDATE COLLECTION
                        dbManager.updateCollectionLastCollectionAmount(col_doc_no, edt_amount.getText().toString());
                    } else { // INSERT COLLECTION
//                        long lastCollId = dbManager.getLastCollectionID();
//                        int CollNum = (int) lastCollId + 1;

                        double dueAmt = Integer.parseInt(amount) + 50;
                        dbManager.insertCollectionHeader("" + CollNum, "" + invNum, customer.cust_num,
                                customer.cust_name_en, customer.cust_type, "0",
                                "" + amount, invDate, "" + dueAmt, invDate);
                    }

                    dbManager.insertPayment(payment);


                } else {
                    Toast.makeText(PaymentActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                }
//                makDil();

                UtilApp.askForPrint(PaymentActivity.this, PaymentActivity.this);
            }

        });
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            try {

                double enterted_amount = Double.parseDouble(s.toString());
                double dblAmount = Double.parseDouble(amount);
                if (enterted_amount > dblAmount) {

                    new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Wait!")
                            .setContentText("Your have entered more amount!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    edt_amount.setText("0");
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();
                } else {
                    double byForcate = dblAmount - enterted_amount;
                    txt_amount.setText(byForcate + "");
                }
            } catch (Exception e) {
//                edt_amount.setText("0");
//                txt_amount.setText(amount);
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void makDil() {
//        LayoutInflater factory = LayoutInflater.from(context);
//        final View deleteDialogView = factory.inflate(R.layout.dialog_cash_custody, null);
//        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
//        final TextView txtCust = deleteDialog.findViewById(R.id.txtCustody);
//        final TextView txtCash = deleteDialog.findViewById(R.id.txtCash);
//
//        txtCash.setText("Print");
//        txtCust.setText("Do Not Print");
//        deleteDialog.setView(deleteDialogView);
//        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        deleteDialogView.findViewById(R.id.rl_cash).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //your business logic
//                deleteDialog.dismiss();
        makPay();

//            }
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
//        });
//
//        deleteDialogView.findViewById(R.id.rl_custody).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //your business logic
//                deleteDialog.dismiss();
//                makPay();
//
//            }
//        });
//
//        deleteDialog.show();
    }

    public void makPay() {
        Payment payment = new Payment();
        dbManager.open();

        long lastInvId = dbManager.getLastInvoiceID();
        long lastCollId = dbManager.getLastCollectionID();

        int invNum, CollNum;
        if (lastInvId == 0) {
            invNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.INV_LAST));
        } else {
            invNum = (int) lastInvId + 1;
        }

        if (lastCollId == 0) {
            CollNum = Integer.parseInt(UtilApp.ReadSharePrefrenceString(getApplicationContext(), Constant.COLLECTION_LAST));
        } else {
            CollNum = (int) lastCollId + 1;
        }

        if (!swcPayment.isChecked()) {
            payment.setInvoice_id(String.valueOf(invNum));
            payment.setCollection_id(String.valueOf(CollNum));
            payment.setPayment_type("Cash");
            payment.setPayment_date(UtilApp.getCurrentDate());
            payment.setCheque_no("");
            payment.setBank_name("");
            payment.setPayment_amount(edt_amount.getText().toString());
            payment.setCust_id(customer.cust_num);
        } else {
            payment.setInvoice_id(String.valueOf(invNum));
            payment.setCollection_id(String.valueOf(CollNum));
            payment.setPayment_type("Cheque");
            payment.setPayment_date(edtDate.getText().toString());
            payment.setCheque_no(edtChequeNumber.getText().toString());
            payment.setBank_name(spinner.getSelectedItem().toString());
            payment.setPayment_amount(edtChequeAmount.getText().toString());
            payment.setCust_id(customer.cust_num);
        }

        dbManager.insertPayment(payment);

        onBackPressed();
    }
}