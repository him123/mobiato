package com.ae.benchmark.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ae.benchmark.R;
import com.ae.benchmark.util.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentAddCustTwo extends Fragment {


    @InjectView(R.id.edtCrNum)
    EditText edtCrNum;
    @InjectView(R.id.edtCrExpDate)
    EditText edtCrExpDate;
    @InjectView(R.id.edtIqmaNo)
    EditText edtIqmaNo;
    @InjectView(R.id.edtIqamExpDate)
    EditText edtIqamExpDate;

    public FragmentAddCustTwo() {
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
        View v = inflater.inflate(R.layout.fragment_add_customer_two, container, false);
        ButterKnife.inject(this, v);

        edtCrNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtCrNum.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtCrNum = edtCrNum.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtCrNum = "";
                }
            }
        });

        edtCrExpDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtCrExpDate.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtCrExpDate = edtCrExpDate.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtCrExpDate = "";
                }
            }
        });

        edtIqmaNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtIqmaNo.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtIqmaNo = edtIqmaNo.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtIqmaNo = "";
                }
            }
        });

        edtIqamExpDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtIqamExpDate.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtIqamExpDate = edtIqamExpDate.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtIqamExpDate = "";
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