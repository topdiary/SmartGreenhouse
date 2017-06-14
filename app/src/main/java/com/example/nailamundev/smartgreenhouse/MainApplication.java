package com.example.nailamundev.smartgreenhouse;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.FirebaseDatabase;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import io.fabric.sdk.android.Fabric;

/**
 * Created by BenZDeV on 4/11/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize thing(s) here
        Fabric.with(this, new Crashlytics());
        Contextor.getInstance().init(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
