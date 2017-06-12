package com.example.nailamundev.smartgreenhouse.dao;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by BenZDeV on 4/19/2017.
 */
@IgnoreExtraProperties
public class GreenhouseControl {


    public int evap;
    public int fan1;
    public int fan2;
    public int fogger;
    public int led;
    public int manual;
    public int watering;



    public GreenhouseControl() {

    }

    public GreenhouseControl(int evap, int fan1, int fan2, int fogger, int led,
                             int manual, int watering) {

        this.evap       = evap;
        this.fan1       = fan1;
        this.fan2       = fan2;
        this.fogger     = fogger;
        this.led        = led;
        this.manual     = manual;
        this.watering   = watering;

    }

}
