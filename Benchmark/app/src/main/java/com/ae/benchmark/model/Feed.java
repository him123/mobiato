package com.ae.benchmark.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Himm on 3/13/2018.
 */

public class Feed implements Parcelable {

    public String name;
    public String address;
    public String cust_id;
    public String desc;
    public int type;
    public String date;

    public Feed() {
    }

    protected Feed(Parcel in) {
        name = in.readString();
        address = in.readString();
        cust_id = in.readString();
        desc = in.readString();
        type = in.readInt();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(cust_id);
        dest.writeString(desc);
        dest.writeInt(type);
        dest.writeString(date);
    }

    @SuppressWarnings("unused")
    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}

