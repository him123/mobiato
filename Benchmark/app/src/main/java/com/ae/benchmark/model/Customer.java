package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ae.benchmark.localdb.DatabaseHelper;

/**
 * Created by Himm on 3/13/2018.
 */

public class Customer implements Parcelable {

    public String cust_num;
    public String cust_name_en;
    public String cust_name_ar;
    public String cust_dist_channel;
    public String cust_division;
    public String cust_sales_org;
    public String cust_credit_limit;
    public String cust_avail_bal;
    public String cust_payment_term;
    public String cust_address;
    public String cust_type;
    public String cust_possessed_empty_bottle;
    public String cust_possessed_filled_bottle;
    public String cust_latitude;
    public String cust_longitude;
    public String cust_sale;
    public String cust_order;
    public String cust_collection;

    public Customer() {
    }

    protected Customer(Parcel in) {
        cust_num = in.readString();
        cust_name_en = in.readString();
        cust_name_ar = in.readString();
        cust_dist_channel = in.readString();
        cust_division = in.readString();
        cust_sales_org = in.readString();
        cust_credit_limit = in.readString();
        cust_avail_bal = in.readString();
        cust_payment_term = in.readString();
        cust_address = in.readString();
        cust_type = in.readString();
        cust_possessed_empty_bottle = in.readString();
        cust_possessed_filled_bottle = in.readString();
        cust_latitude = in.readString();
        cust_longitude = in.readString();

        cust_sale = in.readString();
        cust_order = in.readString();
        cust_collection = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cust_num);
        dest.writeString(cust_name_en);
        dest.writeString(cust_name_ar);
        dest.writeString(cust_dist_channel);
        dest.writeString(cust_division);
        dest.writeString(cust_sales_org);
        dest.writeString(cust_credit_limit);
        dest.writeString(cust_avail_bal);
        dest.writeString(cust_payment_term);
        dest.writeString(cust_address);
        dest.writeString(cust_type);
        dest.writeString(cust_possessed_empty_bottle);
        dest.writeString(cust_possessed_filled_bottle);
        dest.writeString(cust_latitude);
        dest.writeString(cust_longitude);

        dest.writeString(cust_sale);
        dest.writeString(cust_order);
        dest.writeString(cust_collection);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}