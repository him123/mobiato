package com.ae.benchmark.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ae.benchmark.activities.SplashActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Himm on 4/20/2018.
 */

public class UtilApp {

    //Getting lat long
    public static boolean ReadSharePrefrence(Context context, String key) {
        @SuppressWarnings("static-access")
        SharedPreferences read_data = null;
        try {

            read_data = context.getSharedPreferences(
                    Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return read_data.getBoolean(key, false);
    }

    public static String getString(InputStream in) {
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        try {
            String read = br.readLine();
            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
        } catch (Exception e) {
            Log.e("", "ERROR WHILE PARSING RESPONSE TO STRING :: " + e.getMessage());
        } finally {
            try {
                if (is != null) is.close();
                if (br != null) br.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    //Getting lat long
    public static String ReadSharePrefrenceString(Context context, String key) {
        @SuppressWarnings("static-access")
        SharedPreferences read_data = null;
        try {

            read_data = context.getSharedPreferences(
                    Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return read_data.getString(key, "");
    }

    public static void WriteSharePrefrence(Context context, String key, boolean values) {
        @SuppressWarnings("static-access")
        SharedPreferences write_Data = context.getSharedPreferences(
                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = write_Data.edit();
        editor.putBoolean(key, values);
        editor.commit();
        editor.apply();
    }

    public static void WriteSharePrefrence(Context context, String key, String values) {
        @SuppressWarnings("static-access")
        SharedPreferences write_Data = context.getSharedPreferences(
                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = write_Data.edit();
        editor.putString(key, values);
        editor.commit();
        editor.apply();
    }

    public static boolean checkPermission(String permission, Context context) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        String formattedDate = df.format(c);

        return formattedDate;
    }


    public static void clearSharedPreferences(Context context) {
        File sharedPreferenceFile = new File("/data/data/" +
                context.getPackageName() + "/shared_prefs/");
        File[] listFiles = sharedPreferenceFile.listFiles();
        for (File file : listFiles) {
            file.delete();
        }

//        UtilApp.ReadSharePrefrence(SplashActivity.this, Constant.SHRED_PR.ISINTRO
        UtilApp.WriteSharePrefrence(context, Constant.SHRED_PR.ISINTRO, true);

    }
}
