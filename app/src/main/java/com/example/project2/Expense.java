package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.db.AppDatabase;

import java.util.Date;

@Entity(tableName= AppDatabase.EXPENSE_TABLE)
public class Expense extends Transaction{

    @PrimaryKey(autoGenerate = true)
    private int mTransactionId;
    private String mType;

    public Expense(double mAmount, Date mDate, String mDescription,String mType, int mUserId) {
        super(mAmount, mDate, mDescription, mUserId);
        this.mType=mType;
    }

    public int getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(int mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "Expense" + "\n" +
                "Transaction ID : " + mTransactionId + "\n"+
                super.toString()+"\n"+
                "Type : "+mType;
    }
}
