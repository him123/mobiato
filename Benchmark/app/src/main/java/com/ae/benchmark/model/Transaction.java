package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class Transaction implements Parcelable {

    public String tr_id;
    public String tr_type;
    public String tr_date_time;
    public String tr_customer_num;
    public String tr_customer_name;
    public String tr_salesman_id;
    public String tr_invoice_id;
    public String tr_order_id;
    public String tr_collection_id;
    public String tr_pyament_id;
    public String tr_is_posted;


    public Transaction() {
    }

    protected Transaction(Parcel in) {
        tr_id = in.readString();
        tr_type = in.readString();
        tr_date_time = in.readString();
        tr_customer_num = in.readString();
        tr_customer_name = in.readString();
        tr_salesman_id = in.readString();
        tr_invoice_id = in.readString();
        tr_order_id = in.readString();
        tr_collection_id = in.readString();
        tr_pyament_id = in.readString();
        tr_is_posted = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tr_id);
        dest.writeString(tr_type);
        dest.writeString(tr_date_time);
        dest.writeString(tr_customer_num);
        dest.writeString(tr_customer_name);
        dest.writeString(tr_salesman_id);
        dest.writeString(tr_invoice_id);
        dest.writeString(tr_order_id);
        dest.writeString(tr_collection_id);
        dest.writeString(tr_pyament_id);
        dest.writeString(tr_is_posted);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
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