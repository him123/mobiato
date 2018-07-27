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


    public static EditText edtAvailBal;
    public static EditText edtPayTerm;
    public static EditText edtAddress;
    public static EditText edtCustType;

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

        edtAvailBal = v.findViewById(R.id.edtAvailBal);
        edtPayTerm = v.findViewById(R.id.edtPayTerm);
        edtAddress = v.findViewById(R.id.edtAddress);
        edtCustType = v.findViewById(R.id.edtCustType);

        edtAvailBal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtAvailBal.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtAvailBal = edtAvailBal.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtAvailBal = "";
                }
            }
        });

        edtPayTerm.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtPayTerm.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtPayTerm = edtPayTerm.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtPayTerm = "";
                }
            }
        });

        edtAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtAddress.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtAddress = edtAddress.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtAddress = "";
                }
            }
        });

        edtCustType.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtCustType.getText().toString().equals("")){
                    Constant.NEW_CUSTOMER.edtCustType = edtCustType.getText().toString();
                } else {
                    Constant.NEW_CUSTOMER.edtCustType = "";
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