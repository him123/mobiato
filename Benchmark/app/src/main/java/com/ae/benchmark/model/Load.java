package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class Load implements Parcelable {

    public String load_no;
    public String del_date;
    public String is_verified;

    public Load() {
    }

    protected Load(Parcel in) {
        del_date = in.readString();
        load_no = in.readString();
        is_verified = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(del_date);
        dest.writeString(load_no);
        dest.writeString(is_verified);
    }

    @SuppressWarnings("unused")
    public static final Creator<Load> CREATOR = new Creator<Load>() {
        @Override
        public Load createFromParcel(Parcel in) {
            return new Load(in);
        }

        @Override
        public Load[] newArray(int size) {
            return new Load[size];
        }
    };
}

