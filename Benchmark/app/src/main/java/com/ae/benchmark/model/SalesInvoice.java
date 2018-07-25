package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class SalesInvoice implements Parcelable {

    public String inv_no;
    public String inv_type;
    public String inv_type_code;
    public String cust_code;
    public String cust_dist_channel;
    public String cust_division;
    public String inv_date;
    public String cust_name_en;
    public String tot_amnt_sales;
    public String inv_header_dis_per;
    public String inv_header_dis_val;
    public String inv_header_vat_per;
    public String inv_header_vat_val;
    public String cust_sales_org;
    public String del_date;

    public SalesInvoice() {

    }

    protected SalesInvoice(Parcel in) {
        inv_no = in.readString();
        inv_type = in.readString();
        inv_type_code = in.readString();
        cust_code = in.readString();
        cust_dist_channel = in.readString();
        cust_division = in.readString();
        inv_date = in.readString();
        cust_name_en = in.readString();
        tot_amnt_sales = in.readString();
        inv_header_dis_per = in.readString();
        inv_header_dis_val = in.readString();
        inv_header_vat_per = in.readString();
        inv_header_vat_val = in.readString();
        cust_sales_org = in.readString();
        del_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inv_no);
        dest.writeString(inv_type);
        dest.writeString(inv_type_code);
        dest.writeString(cust_code);
        dest.writeString(cust_dist_channel);
        dest.writeString(cust_division);
        dest.writeString(inv_date);
        dest.writeString(cust_name_en);
        dest.writeString(tot_amnt_sales);
        dest.writeString(inv_header_dis_per);
        dest.writeString(inv_header_vat_per);
        dest.writeString(inv_header_vat_val);
        dest.writeString(cust_sales_org);
        dest.writeString(del_date);
        dest.writeString(inv_header_dis_val);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SalesInvoice> CREATOR = new Parcelable.Creator<SalesInvoice>() {
        @Override
        public SalesInvoice createFromParcel(Parcel in) {
            return new SalesInvoice(in);
        }

        @Override
        public SalesInvoice[] newArray(int size) {
            return new SalesInvoice[size];
        }
    };
}