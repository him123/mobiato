package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class Collection implements Parcelable {

    public String coll_doc_no;
    public String coll_inv_no;
    public String coll_cust_no;
    public String coll_cust_name;
    public String coll_cust_pay_method;
    public String coll_amount;
    public String coll_inv_date;
    public String coll_due_date;
    public String coll_due_amt;
    public String coll_type;
    public String coll_last_col_amt;
    public String coll_is_payable;

    public Collection() {
    }

    protected Collection(Parcel in) {
        coll_doc_no = in.readString();
        coll_inv_no = in.readString();
        coll_cust_no = in.readString();
        coll_cust_name = in.readString();
        coll_cust_pay_method = in.readString();
        coll_amount = in.readString();
        coll_inv_date = in.readString();
        coll_due_date = in.readString();
        coll_due_amt = in.readString();
        coll_type = in.readString();
        coll_last_col_amt = in.readString();
        coll_is_payable = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coll_doc_no);
        dest.writeString(coll_inv_no);
        dest.writeString(coll_cust_no);
        dest.writeString(coll_cust_name);
        dest.writeString(coll_cust_pay_method);
        dest.writeString(coll_amount);
        dest.writeString(coll_inv_date);
        dest.writeString(coll_due_date);
        dest.writeString(coll_due_amt);
        dest.writeString(coll_type);
        dest.writeString(coll_last_col_amt);
        dest.writeString(coll_is_payable);
    }

    @SuppressWarnings("unused")
    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}