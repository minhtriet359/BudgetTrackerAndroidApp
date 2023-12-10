package com.example.project2;

import java.util.Date;

public abstract class Transaction {
    private double mAmount;
    private Date mDate;
    private String mDescription;

    private int mUserId;

    public Transaction(double mAmount, Date mDate, String mDescription, int mUserId) {
        this.mAmount = mAmount;
        this.mDate = mDate;
        this.mDescription = mDescription;
        this.mUserId = mUserId;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", mAmount=" + mAmount +
                ", mDate=" + mDate +
                ", mDescription='" + mDescription + '\'' +
                ", mUserId=" + mUserId +
                '}';
    }
}
