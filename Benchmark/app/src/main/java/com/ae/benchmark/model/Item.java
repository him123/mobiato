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