package com.example.nailamundev.smartgreenhouse.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemListDao;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class GreenhousesListManager {

    private Context mContext;
    private GreenhousesItemListDao dao;

    public GreenhousesListManager() {
        mContext = Contextor.getInstance().getContext();
        //Load data from Persistent Storage
        loadCache();
    }

    public GreenhousesItemListDao getDao() {
        return dao;
    }

    public void setDao(GreenhousesItemListDao dao) {
        this.dao = dao;
        // Save to Persistent Storage
        saveCache();
    }

    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getmGreenhousesItemDao() == null) {
            return 0;
        }

        return dao.getmGreenhousesItemDao().size();

    }


    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        dao = savedInstanceState.getParcelable("dao");
    }


    private void saveCache() {

        String json = new Gson().toJson(dao);
        //TODO: Save Cache
        SharedPreferences prefs = mContext.getSharedPreferences("Greenhouses",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        //Add/ Edit/ Delete
        editor.putString("json", json);
        editor.apply();

    }

    private void loadCache() {
        // TODO: Load Cache
        SharedPreferences prefs = mContext.getSharedPreferences("Greenhouses",
                Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if (json == null)
            return;
        dao = new Gson().fromJson(json, GreenhousesItemListDao.class);
    }

    public String getIds(int i) {
        return dao.getGreenhousesIds(i);

    }

}
