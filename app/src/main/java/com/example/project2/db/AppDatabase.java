package com.example.project2.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2.Balance;
import com.example.project2.Deposit;
import com.example.project2.Expense;
import com.example.project2.User;
import com.example.project2.db.typeConverters.DateTypeConverter;

@Database(entities = {Expense.class, Deposit.class,Balance.class, User.class},version=1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME="PROJECT_DATABASE";
    public static final String EXPENSE_TABLE="EXPENSE_TABLE";
    public static final String DEPOSIT_TABLE="DEPOSIT_TABLE";
    public static final String BALANCE_TABLE="BALANCE_TABLE";
    public static final String USER_TABLE="USER_TABLE";

    public abstract ProjectDAO getProjectDAO();
}
