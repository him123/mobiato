package com.ae.benchmark.fragments;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.DashBoardActivity;
import com.ae.benchmark.activities.EndInventoryRITActivity;
import com.ae.benchmark.activities.FreshUnloadActivity;
import com.ae.benchmark.localdb.DBManager;
import com.ae.benchmark.model.Item;
import com.ae.benchmark.model.Transaction;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentIMVUnload extends Fragment {

    @InjectView(R.id.txtFreshUnload)
    TextView txtFreshUnload;
    @InjectView(R.id.txtRto)
    TextView txtRto;
    private FloatingActionMenu fam;
    private FloatingActionButton fab1, fab2, fab3, fab4;

    @InjectView(R.id.btn_checkin)
    Button btn_checkin;

    DBManager dbManager;

    public FragmentIMVUnload() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FragmentIMVUnload fragment = new FragmentIMVUnload();
        return fragment;
    }

    public static FragmentIMVUnload createInstance() {
        FragmentIMVUnload partThreeFragment = new FragmentIMVUnload();
        return partThreeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_im_unload, container, false);
        ButterKnife.inject(this, v);

        dbManager = new DBManager(getActivity());
        getActivity().registerReceiver(broadcastReceiver2, new IntentFilter(EndInventoryRITActivity.BROADCAST_ACTION_END_INVENTORY));
        getActivity().registerReceiver(broadcastReceiver3, new IntentFilter(FreshUnloadActivity.BROADCAST_ACTION_UNLOAD));

        fab3 = (FloatingActionButton) v.findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) v.findViewById(R.id.fab4);

        fam = (FloatingActionMenu) v.findViewById(R.id.fab_menu);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fam.close(true);
                getActivity().startActivity(new Intent(getActivity(), FreshUnloadActivity.class));
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fam.close(true);
                getActivity().startActivity(new Intent(getActivity(), EndInventoryRITActivity.class));
            }
        });


        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
//                if (opened) {
//                    showToast("Menu is opened");
//                } else {
//                    showToast("Menu is closed");
//                }
            }
        });

        try {
            if (UtilApp.ReadSharePrefrence(getActivity(), Constant.SHRED_PR.ISCHECKIN)) {
                btn_checkin.setEnabled(true);
                btn_checkin.setClickable(true);
                btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_green));
            } else {
                btn_checkin.setEnabled(false);
                btn_checkin.setClickable(false);
                btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_gray));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "uload", Toast.LENGTH_SHORT).show();

//                UtilApp.WriteSharePrefrence(getActivity(), Constant.END_DATE, UtilApp.getCurrentDate());
                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, false);
                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISJPLOADED, false);
                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISPAYMET, true);

//                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, false);
//                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, false);
//                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, false);
//                UtilApp.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.ISSALES, false);

                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Your unload is ready!")
                        .setContentText("Please go to payment screen!")
                        .setCancelText("Back")
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
                                sDialog.dismissWithAnimation();

                                UtilApp.clearSharedPreferences(getContext());
                                Transaction transaction = new Transaction();
                                transaction.tr_type = Constant.TRANSACTION_TYPES.TT_UNLOAD;
                                transaction.tr_date_time = UtilApp.getCurrentDate() + " " + UtilApp.getCurrentTime();
                                transaction.tr_customer_num = "";
                                transaction.tr_customer_name = "";
                                transaction.tr_salesman_id = UtilApp.ReadSharePrefrenceString(getContext(), Constant.SHRED_PR.SALESMANID);
                                transaction.tr_invoice_id = "";
                                transaction.tr_order_id = "";
                                transaction.tr_collection_id = "";
                                transaction.tr_pyament_id = "";
                                transaction.tr_is_posted = "No";

                                dbManager = new DBManager(getActivity());
                                dbManager.open();
                                dbManager.insertTransaction(transaction);

                                Intent i = new Intent(getActivity(), DashBoardActivity.class);
                                i.putExtra("end", "1");
                                startActivity(i);
                            }
                        })
                        .show();
            }
        });
        return v;
    }

    private void makeDilog(Context context) {
        List<String> list = new ArrayList<String>();

        LayoutInflater factory = LayoutInflater.from(context);

        final View deleteDialogView = factory.inflate(R.layout.dialog_input_password, null);

        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setView(deleteDialogView);

        deleteDialogView.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

            try {
                if (UtilApp.ReadSharePrefrence(getActivity(), Constant.SHRED_PR.ISCHECKIN)) {
                    btn_checkin.setEnabled(true);
                    btn_checkin.setClickable(true);
                    btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_green));
                } else {
                    btn_checkin.setEnabled(false);
                    btn_checkin.setClickable(false);
                    btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_gray));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn_checkin.setEnabled(true);
            btn_checkin.setClickable(true);
            btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_green));
        }
    };

    private BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn_checkin.setEnabled(false);
            btn_checkin.setClickable(false);
            btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_gray));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(broadcastReceiver2);
        getActivity().unregisterReceiver(broadcastReceiver3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager = new DBManager(getActivity());
        dbManager.open();
        List<Item> itemList = dbManager.getVanStock(true);

        double countRto = 0;
        for (int i = 0; i < itemList.size(); i++) {
            countRto += Double.parseDouble(itemList.get(i).item_qty);
        }
        txtRto.setText(String.valueOf(countRto));


        int countFresh = dbManager.getUnloaded();
        txtFreshUnload.setText(String.valueOf(countFresh));
    }
}