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

    public String empty_bottles;
    public String empty_type;

    public SalesInvoice() {

    }

    public String getInv_no() {
        return inv_no;
    }

    public void setInv_no(String inv_no) {
        this.inv_no = inv_no;
    }

    public String getInv_type() {
        return inv_type;
    }

    public void setInv_type(String inv_type) {
        this.inv_type = inv_type;
    }

    public String getInv_type_code() {
        return inv_type_code;
    }

    public void setInv_type_code(String inv_type_code) {
        this.inv_type_code = inv_type_code;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
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

    public String getInv_date() {
        return inv_date;
    }

    public void setInv_date(String inv_date) {
        this.inv_date = inv_date;
    }

    public String getCust_name_en() {
        return cust_name_en;
    }

    public void setCust_name_en(String cust_name_en) {
        this.cust_name_en = cust_name_en;
    }

    public String getTot_amnt_sales() {
        return tot_amnt_sales;
    }

    public void setTot_amnt_sales(String tot_amnt_sales) {
        this.tot_amnt_sales = tot_amnt_sales;
    }

    public String getInv_header_dis_per() {
        return inv_header_dis_per;
    }

    public void setInv_header_dis_per(String inv_header_dis_per) {
        this.inv_header_dis_per = inv_header_dis_per;
    }

    public String getInv_header_dis_val() {
        return inv_header_dis_val;
    }

    public void setInv_header_dis_val(String inv_header_dis_val) {
        this.inv_header_dis_val = inv_header_dis_val;
    }

    public String getInv_header_vat_per() {
        return inv_header_vat_per;
    }

    public void setInv_header_vat_per(String inv_header_vat_per) {
        this.inv_header_vat_per = inv_header_vat_per;
    }

    public String getInv_header_vat_val() {
        return inv_header_vat_val;
    }

    public void setInv_header_vat_val(String inv_header_vat_val) {
        this.inv_header_vat_val = inv_header_vat_val;
    }

    public String getCust_sales_org() {
        return cust_sales_org;
    }

    public void setCust_sales_org(String cust_sales_org) {
        this.cust_sales_org = cust_sales_org;
    }

    public String getDel_date() {
        return del_date;
    }

    public void setDel_date(String del_date) {
        this.del_date = del_date;
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

        empty_bottles = in.readString();
        empty_type = in.readString();
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

        dest.writeString(empty_bottles);
        dest.writeString(empty_type);
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