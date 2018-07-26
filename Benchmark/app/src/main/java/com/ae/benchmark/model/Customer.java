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

    public String getCust_num() {
        return cust_num;
    }

    public void setCust_num(String cust_num) {
        this.cust_num = cust_num;
    }

    public String getCust_name_en() {
        return cust_name_en;
    }

    public void setCust_name_en(String cust_name_en) {
        this.cust_name_en = cust_name_en;
    }

    public String getCust_name_ar() {
        return cust_name_ar;
    }

    public void setCust_name_ar(String cust_name_ar) {
        this.cust_name_ar = cust_name_ar;
    }

    public String getCust_dist_channel() {
        return cust_dist_channel;
    }

    public void setCust_dist_channel(String cust_dist_channel) {
        this.cust_dist_channel = cust_dist_channel;
    }

    public String getCust_division() {
        return cust_division;
    }

    public void setCust_division(String cust_division) {
        this.cust_division = cust_division;
    }

    public String getCust_sales_org() {
        return cust_sales_org;
    }

    public void setCust_sales_org(String cust_sales_org) {
        this.cust_sales_org = cust_sales_org;
    }

    public String getCust_credit_limit() {
        return cust_credit_limit;
    }

    public void setCust_credit_limit(String cust_credit_limit) {
        this.cust_credit_limit = cust_credit_limit;
    }

    public String getCust_avail_bal() {
        return cust_avail_bal;
    }

    public void setCust_avail_bal(String cust_avail_bal) {
        this.cust_avail_bal = cust_avail_bal;
    }

    public String getCust_payment_term() {
        return cust_payment_term;
    }

    public void setCust_payment_term(String cust_payment_term) {
        this.cust_payment_term = cust_payment_term;
    }

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }

    public String getCust_possessed_empty_bottle() {
        return cust_possessed_empty_bottle;
    }

    public void setCust_possessed_empty_bottle(String cust_possessed_empty_bottle) {
        this.cust_possessed_empty_bottle = cust_possessed_empty_bottle;
    }

    public String getCust_possessed_filled_bottle() {
        return cust_possessed_filled_bottle;
    }

    public void setCust_possessed_filled_bottle(String cust_possessed_filled_bottle) {
        this.cust_possessed_filled_bottle = cust_possessed_filled_bottle;
    }

    public String getCust_latitude() {
        return cust_latitude;
    }

    public void setCust_latitude(String cust_latitude) {
        this.cust_latitude = cust_latitude;
    }

    public String getCust_longitude() {
        return cust_longitude;
    }

    public void setCust_longitude(String cust_longitude) {
        this.cust_longitude = cust_longitude;
    }

    public String getCust_sale() {
        return cust_sale;
    }

    public void setCust_sale(String cust_sale) {
        this.cust_sale = cust_sale;
    }

    public String getCust_order() {
        return cust_order;
    }

    public void setCust_order(String cust_order) {
        this.cust_order = cust_order;
    }

    public String getCust_collection() {
        return cust_collection;
    }

    public void setCust_collection(String cust_collection) {
        this.cust_collection = cust_collection;
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