package com.example.nailamundev.smartgreenhouse.dao;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by BenZDeV on 4/18/2017.
 */
@IgnoreExtraProperties
public class GreenhouseRealTime {
    public int a1;                          //light;
    public int a2;                          //soil1;
    public int a3;                          //soil2;
    public int a4;                          //relativeHumidity1;
    public int a5;                          //temperature1;
    public int a6;                          //relativeHumidity2;
    public int a7;                          //temperature2;
    public int a8;                          //relativeHumidity3;
    public int a9;                          //temperature3;
    public int a10;                         //waterLevel;

    public GreenhouseRealTime() {


    }

    public GreenhouseRealTime(int a1, int a2, int a3, int a4, int a5,
                              int a6, int a7, int a8, int a9, int a10) {

        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.a7 = a7;
        this.a8 = a8;
        this.a9 = a9;
        this.a10 = a10;

    }

}
