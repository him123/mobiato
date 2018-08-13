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
    public String tr_cash_empty_bottles;
    public String tr_custody_empty_bottles;

    public Transaction() {
    }

    public String getTr_id() {
        return tr_id;
    }

    public void setTr_id(String tr_id) {
        this.tr_id = tr_id;
    }

    public String getTr_type() {
        return tr_type;
    }

    public void setTr_type(String tr_type) {
        this.tr_type = tr_type;
    }

    public String getTr_date_time() {
        return tr_date_time;
    }

    public void setTr_date_time(String tr_date_time) {
        this.tr_date_time = tr_date_time;
    }

    public String getTr_customer_num() {
        return tr_customer_num;
    }

    public void setTr_customer_num(String tr_customer_num) {
        this.tr_customer_num = tr_customer_num;
    }

    public String getTr_customer_name() {
        return tr_customer_name;
    }

    public void setTr_customer_name(String tr_customer_name) {
        this.tr_customer_name = tr_customer_name;
    }

    public String getTr_salesman_id() {
        return tr_salesman_id;
    }

    public void setTr_salesman_id(String tr_salesman_id) {
        this.tr_salesman_id = tr_salesman_id;
    }

    public String getTr_invoice_id() {
        return tr_invoice_id;
    }

    public void setTr_invoice_id(String tr_invoice_id) {
        this.tr_invoice_id = tr_invoice_id;
    }

    public String getTr_order_id() {
        return tr_order_id;
    }

    public void setTr_order_id(String tr_order_id) {
        this.tr_order_id = tr_order_id;
    }

    public String getTr_collection_id() {
        return tr_collection_id;
    }

    public void setTr_collection_id(String tr_collection_id) {
        this.tr_collection_id = tr_collection_id;
    }

    public String getTr_pyament_id() {
        return tr_pyament_id;
    }

    public void setTr_pyament_id(String tr_pyament_id) {
        this.tr_pyament_id = tr_pyament_id;
    }

    public String getTr_is_posted() {
        return tr_is_posted;
    }

    public void setTr_is_posted(String tr_is_posted) {
        this.tr_is_posted = tr_is_posted;
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
        tr_cash_empty_bottles= in.readString();
        tr_custody_empty_bottles = in.readString();
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

        dest.writeString(tr_cash_empty_bottles);
        dest.writeString(tr_custody_empty_bottles);
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