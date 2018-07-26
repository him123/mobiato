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


    @InjectView(R.id.edtTradeName)
    EditText edtTradeName;
    @InjectView(R.id.edtArea)
    EditText edtArea;
    @InjectView(R.id.edtStreet)
    EditText edtStreet;
    @InjectView(R.id.edtEmail)
    EditText edtEmail;
    @InjectView(R.id.edtMobile)
    EditText edtMobile;
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

        edtTradeName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtTradeName.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtTradeName = edtTradeName.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtTradeName = "";
                }
            }
        });

        edtArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtArea.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtArea = edtArea.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtArea = "";
                }
            }
        });

        edtStreet.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtStreet.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtStreet = edtStreet.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtStreet = "";
                }
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtEmail.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtEmail = edtEmail.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtEmail = "";
                }
            }
        });

        edtMobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtMobile.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtMobile = edtMobile.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtMobile = "";
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