package com.booltrip.booltrip.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {

    private String id = "";
    private String name = "";
    private int consumption;


    public Track(){}
    public Track(Parcel in) {
        readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    /** * * Called from the constructor to create this * object from a parcel. * * @param in parcel from which to re-create object */
    private void readFromParcel(Parcel in) {
        //parcel.writeByte((byte) (isConnected ? 1 : 0));
        id = in.readString();
        name  = in.readString();
        consumption = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(consumption);
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