package com.ae.benchmark.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.ae.benchmark.R;
import com.ae.benchmark.activities.EndInventoryRITActivity;
import com.ae.benchmark.activities.FreshUnloadActivity;
import com.ae.benchmark.activities.LoginActivity;
import com.ae.benchmark.util.Constant;
import com.ae.benchmark.util.UtilApp;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Himm on 3/13/2018.
 */

public class FragmentIMVUnload extends Fragment {

    private FloatingActionMenu fam;
    private FloatingActionButton fab1, fab2, fab3, fab4;

    @InjectView(R.id.btn_checkin)
    Button btn_checkin;

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

        fab1 = (FloatingActionButton) v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab2);
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

        if (UtilApp.ReadSharePrefrence(getActivity(), Constant.SHRED_PR.ISCHECKIN)) {
            btn_checkin.setEnabled(true);
            btn_checkin.setClickable(true);
            btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_green) );
        }else{
            btn_checkin.setEnabled(false);
            btn_checkin.setClickable(false);
            btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_gray) );
        }

        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "uload", Toast.LENGTH_SHORT).show();
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
//            makeDilog(getActivity());
            if (UtilApp.ReadSharePrefrence(getActivity(), Constant.SHRED_PR.ISCHECKIN)) {
                btn_checkin.setEnabled(true);
                btn_checkin.setClickable(true);
                btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_green) );
            }else{
                btn_checkin.setEnabled(false);
                btn_checkin.setClickable(false);
                btn_checkin.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner_gray) );
            }

        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}