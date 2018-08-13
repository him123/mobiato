package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.data.Const;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Customer;
import com.ae.benchmark.model.Payment;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.PrinterHelper;
import com.ae.benchmark.util.UtilApp;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @InjectView(R.id.ll_pay_by)
    LinearLayout ll_pay_by;
    @InjectView(R.id.btnPayment)
    Button btnPayment;


    @InjectView(R.id.txt_address)
    TextView txt_address;

    Calendar myCalendar;
    //    DatePickerDialog.OnDateSetListener date;
    String col_doc_no, invDate;
    Customer customer;
    DBManager dbManager;
    String grandTot, invNo;
    boolean isCash;
    String printJson = "{\"print_type\":\"PAYMENT_LAST\",\"TripID\":\"\",\"customer_name_en\":\"\",\"customer_name_ar\":\"\",\"SALESMANNO\":\"fdasfa\",\"SALESMAN\":\"\",\"ROUTE\":\"\",\"invoice_date\":\"\",\"customer_address\":\"\",\"DOC DATE\":\"\",\"LPONO\":\"\",\"INVOICETYPE\":\"\",\"CONTACTNO\":\"\",\"TRN\":\"\",\"LANG\":\"ar\",\"invoicepaymentterms\":\"2\",\"ORDERNO\":\"\",\n" +
            "\"Cash\":{\"Amount\":\"1200.00\"},\n" +
            "\"invoicenumber\":\"\",\"TRIP START DATE\":\"\",\"TIME\":\"45456454\",\"CUSTOMER\":\"customer name - customer name2\",\"ADDRESS\":\"\",\"ARBADDRESS\":\"\",\"TOTAL\":{\"Total Amount(AED)\":\"2568.00\",\"Total Befor TAX(AED)\":\"2568.00\",\"GROSS AMOUNT: AED - \":\"2568.00\"},\"closevalue\":\"2351.32\",\"PaymentType\":\"0\",\"\":{\"Amount\":\"4564.23\"},\"HEADERS\":[\"Invoice#\",\"Due Date\",\"Due Amount\",\"Invoice Balance\",\"Amount Paid\"],\"data\":[[\"125\",\"26\\/05\\/2018\",\"2563.12\",\"2563.02\",\"456\"]]}";

    int year, month, day;

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
            customer = extras.getParcelable("cust");
            col_doc_no = extras.getString("col_doc_no");
            invDate = extras.getString("invDate");
            grandTot = extras.getString("amt");
            invNo = extras.getString("invNo");
        }

        if (customer.cust_type.equalsIgnoreCase("cash")) {
            llCash.setVisibility(View.VISIBLE);
            llCheque.setVisibility(View.GONE);
            ll_pay_by.setVisibility(View.GONE);
            swcPayment.setChecked(false);
        } else {
            swcPayment.setChecked(true);
            llCash.setVisibility(View.VISIBLE);
            llCheque.setVisibility(View.VISIBLE);
            ll_pay_by.setVisibility(View.VISIBLE);
        }

        edt_amount.setText(grandTot);
        edtChequeAmount.setText(grandTot);

        edt_amount.setEnabled(false);
//        edtChequeAmount.setEnabled(false);

        txt_address.setText("(" + customer.cust_address + ")");

        txt_amount.setText(grandTot);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText(customer.cust_name_en);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                Toast.makeText(PaymentActivity.this, "Please complete the payment first", Toast.LENGTH_SHORT).show();
            }
        });

//        edt_amount.addTextChangedListener(textWatcher);


        swcPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    isCash = true;
                    llCash.setVisibility(View.VISIBLE);
                    llCheque.setVisibility(View.GONE);
                } else {
                    isCash = false;
                    llCash.setVisibility(View.GONE);
                    llCheque.setVisibility(View.VISIBLE);
                }
            }
        });

        myCalendar = Calendar.getInstance();

        //END BUTTON
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDialog(999);
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

                try {


                    final JSONObject mainArr = new JSONObject();
                    mainArr.put("print_type", Constant.PAYMENT_LAST);
                    mainArr.put("ROUTE", UtilApp.ReadSharePrefrence(PaymentActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("DOC DATE", UtilApp.getCurrentDate());
                    mainArr.put("TIME", "00:00:00");
                    mainArr.put("SALESMAN", UtilApp.ReadSharePrefrence(PaymentActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("CONTACTNO", "1234");
                    mainArr.put("supervisorname", "-");
                    mainArr.put("LANG", "AR");
                    mainArr.put("INVOICETYPE", "ORDER REQUEST");
                    mainArr.put("invoicepaymentterms", "3");
                    mainArr.put("CUSTOMERID", customer.cust_num);
                    mainArr.put("CUSTOMER", customer.cust_name_en);
                    mainArr.put("ADDRESS", customer.cust_address);
                    mainArr.put("ARBADDRESS", customer.cust_address);
                    mainArr.put("TripID", UtilApp.ReadSharePrefrence(PaymentActivity.this, Constant.SHRED_PR.SALESMANID));
                    mainArr.put("LANG", "ar");
                    mainArr.put("invoicepaymentterms", "2");
                    mainArr.put("RECEIPT", "INVOICE RECEIPT");
                    mainArr.put("SUB TOTAL", "1000");
                    mainArr.put("INVOICE DISCOUNT", "20");
                    mainArr.put("NET SALES", "980");


                    JSONArray HEADERS = new JSONArray();
                    JSONArray TOTAL = new JSONArray();

                    HEADERS.put("Invoice#");
                    HEADERS.put("Due Date");
                    HEADERS.put("Due Amount");
                    HEADERS.put("Invoice Balance");
                    HEADERS.put("Amount Paid");
                    //HEADERS.put("Description");

                    //HEADERS.put(obj1);
                    // HEADERS.put(obj2);
                    mainArr.put("HEADERS", HEADERS);
                    JSONObject jCash = new JSONObject();


                    Payment payment = new Payment();
                    dbManager.open();

//                    long lastInvId = dbManager.getLastInvoiceID();
                    long lastCollId = dbManager.getLastCollectionID();

                    int CollNum;
//                    invNum = (int) lastInvId + 1;
                    CollNum = (int) lastCollId + 1;

                    mainArr.put("DOCUMENT NO", invNo);  //Load Summary No
                    mainArr.put("ORDERNO", invNo);
                    mainArr.put("expayment", "");

                    JSONObject totalObj = new JSONObject();
                    totalObj.put("Invoice Balance", "+" + "50");
                    double tot = Double.parseDouble(edt_amount.getText().toString())
                            + Double.parseDouble(edtChequeAmount.getText().toString());
                    totalObj.put("Amount Paid", String.format("%.2f", tot));
                    //totalObj.put("AMOUNT","+2230");
                    TOTAL.put(totalObj);
                    mainArr.put("TOTAL", totalObj);

                    JSONArray jData = new JSONArray();
                    JSONArray jData3 = new JSONArray();
                    jData3.put(UtilApp.getCurrentDate());
                    jData3.put("250");
                    jData3.put("0");
                    jData3.put(String.format("%.2f", tot));

                    jData.put(jData3);

                    mainArr.put("data", jData);
//                    mainArr.put(JsonRpcUtil.PARAM_DATA,jData);
//
//                    jDict.put("mainArr",mainArr);
//                    jInter.put(jDict);
//                    jArr.put(jInter);
//
//                    jArr.put(HEADERS);

                    //STARTING
                    if (swcPayment.isChecked()) {// ======================= CHEQUE PAYMENT

                        if (!edtChequeAmount.getText().toString().equals("")) {
                            payment.setInvoice_id(String.valueOf(invNo));
                            payment.setCollection_id(String.valueOf(CollNum));
                            payment.setPayment_type("Cheque");
                            payment.setPayment_date(edtDate.getText().toString());
                            payment.setCheque_no(edtChequeNumber.getText().toString());
                            payment.setBank_name(spinner.getSelectedItem().toString());
                            payment.setPayment_amount(edtChequeAmount.getText().toString());
                            payment.setCust_id(customer.cust_num);

                            mainArr.put("PaymentType", "1");

                            String paidAmt = edtChequeAmount.getText().toString();
                            if (Double.parseDouble(paidAmt) > Double.parseDouble(grandTot)) {
                                Toast.makeText(PaymentActivity.this, "Please don't pay more", Toast.LENGTH_SHORT).show();
                                return;
                            }

//                            if (Double.parseDouble(paidAmt) == Double.parseDouble(grandTot)) {
//                                dbManager.updateCollectionLastCollectionAmount("" + col_doc_no,
//                                        "" + paidAmt, "no");
//                            } else {

                                dbManager.updateCollectionLastCollectionAmount("" + col_doc_no,
                                        "" + paidAmt, "no");

//                                double halPaidMain =
//                                        Double.parseDouble(grandTot) - Double.parseDouble(paidAmt);
//
//                                dbManager.insertCollectionHeader("" + lastCollId,
//                                        "" + invNo, customer.cust_num,
//                                        customer.cust_name_en, customer.cust_type,
//                                        "0",
//                                        ""+ halPaidMain, invDate, "" ,
//                                        invDate, "yes");
////                            }

                            JSONArray jCheque = new JSONArray();
                            JSONObject jChequeData = new JSONObject();
                            jChequeData.put("Cheque Date", UtilApp.getCurrentDate());
                            jChequeData.put("Cheque No", edtChequeNumber.getText().toString());
                            jChequeData.put("Bank", "Bank");
                            jChequeData.put("Amount", edtChequeAmount.getText().toString());
                            jCheque.put(jChequeData);
                            mainArr.put("Cheque", jCheque);

                            dbManager.insertPayment(payment);

                            Intent intent = new Intent(PaymentActivity.this, CustomerDetailOperationActivity.class);
                            intent.putExtra("cust", customer);
                            intent.putExtra("tag", "old");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

//                            UtilApp.askForPrint(PaymentActivity.this, PaymentActivity.this, intent);
                        } else {
                            Toast.makeText(PaymentActivity.this, "Please enter amout", Toast.LENGTH_SHORT).show();
                        }
                    } else {// ======================= CASH PAYMENT

                        if (!edt_amount.getText().toString().equals("")) {
                            payment.setInvoice_id(String.valueOf(invNo));
                            payment.setCollection_id(String.valueOf(CollNum));
                            payment.setPayment_type("Cash");
                            payment.setPayment_date(UtilApp.getCurrentDate());
                            payment.setCheque_no("");
                            payment.setBank_name("");
                            payment.setPayment_amount(edt_amount.getText().toString());
                            payment.setCust_id(customer.cust_num);


                            mainArr.put("PaymentType", "0");
                            jCash.put("Amount", edt_amount.getText().toString());
                            mainArr.put("Cash", jCash);

                            String paidAmt = edt_amount.getText().toString();



//                            if (dbManager.checkIsCollectionAlreadyExist(lastCollId + "")) { // UPDATE COLLECTION
//                                double lastPaidAmt = Double.parseDouble(dbManager.getLastPaymentAmount(col_doc_no));
//                                double totPaid = lastPaidAmt + Double.parseDouble(edtChequeAmount.getText().toString());
//
//                                paidAmt = String.valueOf(totPaid);
//                            }

                            dbManager.insertCollectionHeader("" + CollNum, "" + invNo, customer.cust_num,
                                    customer.cust_name_en, customer.cust_type, paidAmt,
                                    "", invDate, "" + grandTot, invDate, "no");

                            dbManager.updateCollectionLastCollectionAmount("" + col_doc_no,
                                    "" + paidAmt, "no");


                            dbManager.insertPayment(payment);

                            Intent intent = new Intent(PaymentActivity.this, CustomerDetailOperationActivity.class);
                            intent.putExtra("cust", customer);
                            intent.putExtra("tag", "old");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

//                            UtilApp.askForPrint(PaymentActivity.this, PaymentActivity.this, intent);
                        } else {
                            Toast.makeText(PaymentActivity.this, "Please enter amout", Toast.LENGTH_SHORT).show();
                        }
                    }


                    final Dialog alertDialog = new Dialog(context);
                    alertDialog.setCancelable(false);
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.dialog_print_donot_print);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    ImageView img_print = alertDialog.findViewById(R.id.img_pring);

                    img_print.setColorFilter(ContextCompat.getColor(context, R.color.theme_color), android.graphics.PorterDuff.Mode.MULTIPLY);


                    alertDialog.findViewById(R.id.rl_print).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //your business logic

                            alertDialog.dismiss();

                            try {
                                PrinterHelper printerHelper = new PrinterHelper(PaymentActivity.this, PaymentActivity.this);
                                printerHelper.execute(mainArr);

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
                            Intent intent = new Intent(PaymentActivity.this, CustomerDetailOperationActivity.class);
                            intent.putExtra("cust", customer);
                            intent.putExtra("tag", "old");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                            finish();
                        }
                    });

                    alertDialog.show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtChequeAmount.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(edt_amount.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void callback() {
//        finish();

        Intent intent = new Intent(PaymentActivity.this, CustomerDetailOperationActivity.class);
        intent.putExtra("cust", customer);
        intent.putExtra("tag", "old");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day

            edtDate.setText("" + arg1 + "-" + "" + arg2 + "-" + "" + arg3);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(PaymentActivity.this, "Please complete the payment first", Toast.LENGTH_SHORT).show();
    }
}