package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.DEPOSIT_TABLE)
public class Deposit extends Transaction{

    @PrimaryKey(autoGenerate = true)
    private int mTransactionId;

    public Deposit(int mTransactionId, double mAmount, Date mDate, String mDescription, int mUserId) {
        super(mAmount, mDate, mDescription, mUserId);
        this.mTransactionId=mTransactionId;
    }

    public int getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(int mTransactionId) {
        this.mTransactionId = mTransactionId;
    }


}
