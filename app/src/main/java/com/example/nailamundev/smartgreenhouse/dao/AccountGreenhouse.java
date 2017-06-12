package com.example.nailamundev.smartgreenhouse.dao;

/**
 * Created by BenZDeV on 5/28/2017.
 */

public class AccountGreenhouse {


    int amount;
    String category;
    String note;
    String timeStamp;


    public AccountGreenhouse(){

    }

    public AccountGreenhouse( int amount, String category,
                              String note, String timeStamp){

        this.amount = amount;
        this.category = category;
        this.note = note;
        this.timeStamp = timeStamp;
    }

}
