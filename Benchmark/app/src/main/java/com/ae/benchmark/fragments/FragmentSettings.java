package com.ae.benchmark.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.DashBoardActivity;
import com.ae.benchmark.activities.LoginActivity;
import com.ae.benchmark.activities.SettingsActivity;
import com.ae.benchmark.adapters.RecyclerAdapterCustomer;
import com.ae.benchmark.localdb.DatabaseHelper;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentSettings extends Fragment {


    @InjectView(R.id.txt_name)
    TextView txt_name;

    @InjectView(R.id.txt_desc)
    TextView txt_desc;

    @InjectView(R.id.ll_clear_data)
    LinearLayout ll_clear_data;

    public FragmentSettings() {
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
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.inject(this, v);


        txt_name.setText(UtilApp.ReadSharePrefrenceString(getActivity(), Constant.SALESMAN.SALESMAN_NAME));
        txt_desc.setText(UtilApp.ReadSharePrefrenceString(getActivity(), Constant.SALESMAN.SALESMAN_ID));


        ll_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Clear All Data!")
                        .setContentText("All transaction and sale will be cleared. Are you sure you want to clear all the data?")
                        .setCancelText("No")
                        .setConfirmText("Yes")

                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                                SQLiteDatabase database = databaseHelper.getWritableDatabase();
                                databaseHelper.clearAll(database);

                                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISLOGIN, false);
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                startActivity(intent);
                                getActivity().finish(); // call this to finish the current activity
                            }
                        })
                        .show();


            }
        });

        return v;
    }

}