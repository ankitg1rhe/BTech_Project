package com.android.iitfriends.bas.Orders;
/**
 * Created by Ankit on 1/10/2018.
 */
public class Order {
    private int orderNumber;
    private int autoID;
    private int mSource;
    private int mDestination;
    private int noOfCustomer;
    private int mStatus;
    private String regDate;

    public Order(int orderNumber, int autoID, int mSource, int mDestination, int noOfCustomer, int mStatus, String regDate) {
        this.orderNumber = orderNumber;
        this.autoID = autoID;
        this.mSource = mSource;
        this.mDestination = mDestination;
        this.noOfCustomer = noOfCustomer;
        this.mStatus = mStatus;
        this.regDate = regDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public int getAutoID() {
        return autoID;
    }
    public int getSource() {
        return mSource;
    }
    public int getDestination() {
        return mDestination;
    }
    public int getNoOfCustomer() {
        return noOfCustomer;
    }
    public int getStatus() {
        return mStatus;
    }
    public String getRegDate() {
        return regDate;
    }
}
