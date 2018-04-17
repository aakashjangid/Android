package com.wastedpotential.auctionapp;

/**
 * Created by SP on 4/13/2018.
 */

public class Item {
    private int id;
    private String name;
    private int minBid;
    private String lastDate;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getMinBid() {
        return this.minBid;
    }

    public String getLastDate() {
        return this.lastDate;
    }
}
