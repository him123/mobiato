package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class Transaction implements Parcelable {

    public String act;
    public String time;
    public String date;
    public String cust_id;

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        act = in.readString();
        time = in.readString();
        date = in.readString();
        cust_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(act);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(cust_id);
    }

    @SuppressWarnings("unused")
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}

