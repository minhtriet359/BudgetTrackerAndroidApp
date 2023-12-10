package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppDatabase.BALANCE_TABLE)
public class Balance {

    @PrimaryKey(autoGenerate = true)
    private int mBalanceId;

    //private List<Expense>expenses;
    //private List<Deposit>deposits;
    private double mRemainingBalance=0.0;

    private int mUserId;
    private int mTransactionId;

    public Balance(double mRemainingBalance, int mUserId) {
        this.mRemainingBalance = mRemainingBalance;
        this.mUserId = mUserId;
        //expenses=new ArrayList<>();
        //deposits=new ArrayList<>();
    }

    public void calculateBalance(List<Expense>expenses,List<Deposit>deposits){
        double expenseTotal=0.0;
        double depositTotal=0.0;
        for(Expense expense:expenses){
            expenseTotal+=expense.getAmount();
        }
        for(Deposit deposit:deposits){
            depositTotal+=deposit.getAmount();
        }
        mRemainingBalance=depositTotal-expenseTotal;
    }

    public int getBalanceId() {
        return mBalanceId;
    }

    public void setBalanceId(int mBalanceId) {
        this.mBalanceId = mBalanceId;
    }

    public double getRemainingBalance() {
        return mRemainingBalance;
    }

    public void setRemainingBalance(double mRemainingBalance) {
        this.mRemainingBalance = mRemainingBalance;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(int mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    @Override
    public String toString() {
        return "The remaining balance is: "+mRemainingBalance;
    }
}
