package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ae.benchmark.R;
import com.ae.benchmark.util.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentAddCustOne extends Fragment {


    public static EditText edtCustomerName;
    public static EditText edtDistCh;
    public static EditText edtDiv;
    public static EditText edtSalesOrg;
    public static EditText edtCreditLim;
    @InjectView(R.id.btn_next)
    Button btnNext;
    @InjectView(R.id.btn_skip)
    Button btnSkip;

    public FragmentAddCustOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_customer_one, container, false);
        ButterKnife.inject(this, v);

        edtCustomerName = v.findViewById(R.id.edtCustomerName);
        edtDistCh = v.findViewById(R.id.edtDistCh);
        edtDiv = v.findViewById(R.id.edtDiv);
        edtSalesOrg = v.findViewById(R.id.edtSalesOrg);
        edtCreditLim = v.findViewById(R.id.edtCreditLim);

        edtCustomerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtCustomerName.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtCustomerName = edtCustomerName.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtCustomerName = "";
                }
            }
        });

        edtDistCh.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtDistCh.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtDistCh = edtDistCh.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtDistCh = "";
                }
            }
        });

        edtDiv.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtDiv.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtDiv = edtDiv.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtDiv = "";
                }
            }
        });

        edtSalesOrg.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtSalesOrg.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtSalesOrg = edtSalesOrg.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtSalesOrg = "";
                }
            }
        });

        edtCreditLim.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtCreditLim.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtCreditLim = edtCreditLim.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtCreditLim = "";
                }
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}