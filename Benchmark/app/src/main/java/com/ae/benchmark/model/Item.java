package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ae.benchmark.localdb.DatabaseHelper;

/**
 * Created by Himm on 3/13/2018.
 */

public class Item implements Parcelable {

    public String item_code;
    public String item_name_en;
    public String item_name_ar;
    public String item_type;
    public String item_price;
    public String item_barcode;
    public String item_division;
    public String item_con_fector;
    public String load_no;
    public String item_qty;
    public String item_emp_qty;
    public String item_uom;
    public String load_date;
    public String sales_inv_nun;
    public String item_disc_per;
    public String item_disc_val;
    public String item_vat_per;
    public String item_vat_val;
    public String is_empty;
    public String order_id;

    public Item() {

    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name_en() {
        return item_name_en;
    }

    public void setItem_name_en(String item_name_en) {
        this.item_name_en = item_name_en;
    }

    public String getItem_name_ar() {
        return item_name_ar;
    }

    public void setItem_name_ar(String item_name_ar) {
        this.item_name_ar = item_name_ar;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_barcode() {
        return item_barcode;
    }

    public void setItem_barcode(String item_barcode) {
        this.item_barcode = item_barcode;
    }

    public String getItem_division() {
        return item_division;
    }

    public void setItem_division(String item_division) {
        this.item_division = item_division;
    }

    public String getItem_con_fector() {
        return item_con_fector;
    }

    public void setItem_con_fector(String item_con_fector) {
        this.item_con_fector = item_con_fector;
    }

    public String getLoad_no() {
        return load_no;
    }

    public void setLoad_no(String load_no) {
        this.load_no = load_no;
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public String getItem_emp_qty() {
        return item_emp_qty;
    }

    public void setItem_emp_qty(String item_emp_qty) {
        this.item_emp_qty = item_emp_qty;
    }

    public String getItem_uom() {
        return item_uom;
    }

    public void setItem_uom(String item_uom) {
        this.item_uom = item_uom;
    }

    public String getLoad_date() {
        return load_date;
    }

    public void setLoad_date(String load_date) {
        this.load_date = load_date;
    }

    public String getSales_inv_nun() {
        return sales_inv_nun;
    }

    public void setSales_inv_nun(String sales_inv_nun) {
        this.sales_inv_nun = sales_inv_nun;
    }

    public String getItem_disc_per() {
        return item_disc_per;
    }

    public void setItem_disc_per(String item_disc_per) {
        this.item_disc_per = item_disc_per;
    }

    public String getItem_disc_val() {
        return item_disc_val;
    }

    public void setItem_disc_val(String item_disc_val) {
        this.item_disc_val = item_disc_val;
    }

    public String getItem_vat_per() {
        return item_vat_per;
    }

    public void setItem_vat_per(String item_vat_per) {
        this.item_vat_per = item_vat_per;
    }

    public String getItem_vat_val() {
        return item_vat_val;
    }

    public void setItem_vat_val(String item_vat_val) {
        this.item_vat_val = item_vat_val;
    }

    public String getIs_empty() {
        return is_empty;
    }

    public void setIs_empty(String is_empty) {
        this.is_empty = is_empty;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    protected Item(Parcel in) {
        item_code = in.readString();
        item_name_en = in.readString();
        item_name_ar = in.readString();
        item_type = in.readString();
        item_price = in.readString();
        item_barcode = in.readString();
        item_division = in.readString();
        item_con_fector = in.readString();
        load_no = in.readString();
        item_qty = in.readString();
        item_uom = in.readString();
        load_date = in.readString();
        sales_inv_nun = in.readString();
        item_disc_per = in.readString();
        item_disc_val = in.readString();
        item_vat_per = in.readString();
        item_vat_val = in.readString();
        is_empty = in.readString();
        order_id = in.readString();
        item_emp_qty = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_code);
        dest.writeString(item_name_en);
        dest.writeString(item_name_ar);
        dest.writeString(item_type);
        dest.writeString(item_price);
        dest.writeString(item_barcode);
        dest.writeString(item_division);
        dest.writeString(item_con_fector);
        dest.writeString(load_no);
        dest.writeString(item_qty);
        dest.writeString(item_uom);
        dest.writeString(load_date);

        dest.writeString(sales_inv_nun);
        dest.writeString(item_disc_per);
        dest.writeString(item_disc_val);
        dest.writeString(item_vat_per);
        dest.writeString(item_vat_val);
        dest.writeString(is_empty);
        dest.writeString(order_id);
        dest.writeString(item_emp_qty);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}