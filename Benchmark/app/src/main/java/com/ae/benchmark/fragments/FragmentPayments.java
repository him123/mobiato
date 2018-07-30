package com.ae.benchmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.DashBoardActivity;
import com.ae.benchmark.activities.EndInventoryRITActivity;
import com.ae.benchmark.activities.LoginActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Payment;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentPayments extends Fragment {


    @InjectView(R.id.btn_day_end)
    Button btn_day_end;
    @InjectView(R.id.txtAmtDue)
    TextView txtAmtDue;
    @InjectView(R.id.txtCheque)
    TextView txtCheque;
    @InjectView(R.id.txtCash)
    TextView txtCash;

    DBManager db;

    public FragmentPayments() {
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
        View v = inflater.inflate(R.layout.fragment_end_trip, container, false);
        ButterKnife.inject(this, v);
        db = new DBManager(getActivity());

        ArrayList<Payment> payments = db.getAllPaymentToday();

        double due_amt = 0.0, cheque = 0.0, cash = 0.0;

        for (int i = 0; i < payments.size(); i++) {
            due_amt += Double.parseDouble(payments.get(i).getPayment_amount());

            if (payments.get(i).getPayment_type().equals("Cash")) {
                cash += Double.parseDouble(payments.get(i).getPayment_amount());
            }

            if (payments.get(i).getPayment_type().equals("Cheque")) {
                cheque += Double.parseDouble(payments.get(i).getPayment_amount());
            }
        }

        txtAmtDue.setText(String.valueOf(due_amt));
        txtCash.setText(String.valueOf(cash));
        txtCheque.setText(String.valueOf(cheque));

        btn_day_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Done")
                        .setContentText("Your day ended, See you Tomorrow!")
                        .setConfirmText("Ok!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.END_DATE, UtilApp.getCurrentDate());
                                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.IS_DAY_END, true);
                                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISPAYMET, false);
                                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISJPLOADED, false);


                                Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                getActivity().finish(); // call this to finish the current activity
                            }
                        })
                        .show();



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