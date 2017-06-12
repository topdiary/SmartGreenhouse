package com.example.nailamundev.smartgreenhouse.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by BenZDeV on 4/16/2017.
 */
@IgnoreExtraProperties
public class GreenhousesItemDao implements Parcelable {

    public String address;
    public String date;
    public String description;
    public String img;
    public String map;
    public String name;
    public String plant;

    public GreenhousesItemDao() {

    }

    public GreenhousesItemDao(String address, String date, String description, String img,
                              String map, String name, String plant) {

        this.address = address;
        this.date = date;
        this.description = description;
        this.img = img;
        this.map = map;
        this.name = name;
        this.plant = plant;

    }


    protected GreenhousesItemDao(Parcel in) {
        address = in.readString();
        date = in.readString();
        description = in.readString();
        img = in.readString();
        map = in.readString();
        name = in.readString();
        plant = in.readString();
    }

    public static final Creator<GreenhousesItemDao> CREATOR = new Creator<GreenhousesItemDao>() {
        @Override
        public GreenhousesItemDao createFromParcel(Parcel in) {
            return new GreenhousesItemDao(in);
        }

        @Override
        public GreenhousesItemDao[] newArray(int size) {
            return new GreenhousesItemDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(img);
        dest.writeString(map);
        dest.writeString(name);
        dest.writeString(plant);
    }
}
