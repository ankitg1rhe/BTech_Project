package com.android.iitfriends.badriver.Orders;

public class Order {
    private int mNumber;
    private int mSource;
    private int mDestination;
    private int mNoOfCustomer;
    private String mRegDate;
    private String mCustomerId;

    public Order(int mNumber, int mSource, int mDestination, int mNoOfCustomer, String mRegDate, String mCustomerId) {
        this.mNumber = mNumber;
        this.mSource = mSource;
        this.mDestination = mDestination;
        this.mNoOfCustomer = mNoOfCustomer;
        this.mRegDate = mRegDate;
        this.mCustomerId = mCustomerId;
    }

    public int getmNumber() {
        return mNumber;
    }

    public int getmSource() {
        return mSource;
    }

    public int getmDestination() {
        return mDestination;
    }

    public int getNoOfCustomer() {
        return mNoOfCustomer;
    }

    public String getRegDate() {
        return mRegDate;
    }

    public String getCustomerId() {
        return mCustomerId;
    }
}
