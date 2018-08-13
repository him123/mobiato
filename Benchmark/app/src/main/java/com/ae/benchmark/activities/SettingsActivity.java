package com.ae.benchmark.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.benchmark.R;
import com.ae.benchmark.fragments.FragmentAddCustOne;
import com.ae.benchmark.fragments.FragmentAddCustTwo;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.localdb.DatabaseHelper;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;

import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @InjectView(R.id.ll_clear_data)
    LinearLayout ll_clear_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ll_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(SettingsActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                                DatabaseHelper databaseHelper = new DatabaseHelper(SettingsActivity.this);
                                SQLiteDatabase  database = databaseHelper.getWritableDatabase();
                                databaseHelper.clearAll(database);

                                UtilApp.WriteSharePrefrence(SettingsActivity.this, Constant.SHRED_PR.ISLOGIN, false);
                                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                startActivity(intent);
                                finish(); // call this to finish the current activity
                            }
                        })
                        .show();


            }
        });
    }
}
