package com.ae.benchmark.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.ae.benchmark.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    String name;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            txt_cust_name.setText(name);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText("PAYMENT DETAILS");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_amount.addTextChangedListener(textWatcher);

        swcPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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

                String myFormat = " d" + " MMM" + "," + " yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edtDate.setText(sdf.format(myCalendar.getTime()));

            }

        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                new DatePickerDialog(PaymentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList ();
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
            txt_amount.setText(s.toString());
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}