package com.example.nailamundev.smartgreenhouse.dao;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BenZDeV on 4/16/2017.
 */

public class GreenhousesItemListDao implements Parcelable {

    private List<GreenhousesItemDao> mGreenhousesItemDao = new ArrayList<>();
    private List<String> mGreenhousesIds = new ArrayList<>();

    public GreenhousesItemListDao() {

    }

    protected GreenhousesItemListDao(Parcel in) {
        mGreenhousesItemDao = in.createTypedArrayList(GreenhousesItemDao.CREATOR);
        mGreenhousesIds = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mGreenhousesItemDao);
        dest.writeStringList(mGreenhousesIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GreenhousesItemListDao> CREATOR = new Creator<GreenhousesItemListDao>() {
        @Override
        public GreenhousesItemListDao createFromParcel(Parcel in) {
            return new GreenhousesItemListDao(in);
        }

        @Override
        public GreenhousesItemListDao[] newArray(int size) {
            return new GreenhousesItemListDao[size];
        }
    };

    public List<GreenhousesItemDao> getmGreenhousesItemDao() {
        return mGreenhousesItemDao;
    }

    public void setmGreenhousesItemDao(List<GreenhousesItemDao> mGreenhousesItemDao) {
        this.mGreenhousesItemDao = mGreenhousesItemDao;
    }

    public List<String> getmGreenhousesIds() {
        return mGreenhousesIds;
    }

    public void setmGreenhousesIds(List<String> mGreenhousesIds) {
        this.mGreenhousesIds = mGreenhousesIds;
    }

    public void addGreenhousesItemListDao(GreenhousesItemDao data) {
        mGreenhousesItemDao.add(data);
    }

    public void addGreenhousesIds(String key) {
        mGreenhousesIds.add(key);
    }

    public GreenhousesItemDao getGreenhousesPosition(int i) {
        return mGreenhousesItemDao.get(i);

    }

    public String getGreenhousesIds(int i) {
        return mGreenhousesIds.get(i).toString();

    }

    public void clearmGreenhousesItemDao() {
        if (mGreenhousesItemDao.size() != 0) {
            mGreenhousesItemDao.clear();
        }
        else
            Log.d("clearmGreenhousesItemDao", "Fail" + mGreenhousesItemDao.size());
    }

    public void clearmGreenhousesIds() {
        if (mGreenhousesIds.size() != 0) {
            mGreenhousesIds.clear();
        }
    }

    public void set(int index, GreenhousesItemDao data) {

        mGreenhousesItemDao.get(index).name = data.name;
        mGreenhousesItemDao.get(index).date = data.date;
        mGreenhousesItemDao.get(index).description = data.description;
        mGreenhousesItemDao.get(index).img = data.img;
        mGreenhousesItemDao.get(index).name = data.name;
        mGreenhousesItemDao.get(index).plant = data.plant;


    }
    public String getName(int i){

        return  mGreenhousesItemDao.get(i).name;
    }


}
