package com.booltrip.booltrip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {

    private String id = "";
    private String name = "";
    private String rank = "";
    private String email = "";
    private String deviceId = "";
    private boolean isConnected = false;

    public User(){}
    public User(Parcel in) {
        readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /** * * Called from the constructor to create this * object from a parcel. * * @param in parcel from which to re-create object */
    private void readFromParcel(Parcel in) {
        //parcel.writeByte((byte) (isConnected ? 1 : 0));
        id = in.readString();
        email = in.readString();
        name  = in.readString();
        deviceId = in.readString();
        isConnected = in.readByte() == 1;
        //intValue = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(deviceId);
        parcel.writeByte((byte) (isConnected ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

