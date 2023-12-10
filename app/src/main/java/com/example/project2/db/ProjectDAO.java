package com.example.project2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.Balance;
import com.example.project2.Deposit;
import com.example.project2.Expense;
import com.example.project2.User;

import java.util.List;

@Dao
public interface ProjectDAO {
    @Insert
    void insert(Deposit... deposits);

    @Update
    void update(Deposit... deposits);

    @Delete
    void delete(Deposit deposit);

    @Query("SELECT*FROM "+AppDatabase.DEPOSIT_TABLE+" ORDER BY mDate DESC")
    List<Deposit> getAllDeposits();

    @Query("SELECT*FROM "+AppDatabase.DEPOSIT_TABLE+" WHERE mTransactionId=:transactionId")
    Deposit getDepositById(int transactionId);

    @Query("SELECT*FROM "+AppDatabase.DEPOSIT_TABLE+" WHERE mUserId=:userId ORDER BY mDate DESC")
    List<Deposit> getDepositsByUserId(int userId);

    @Insert
    void insert(Expense... expenses);

    @Update
    void update(Expense... expenses);

    @Delete
    void delete(Expense expense);

    @Query("SELECT*FROM "+AppDatabase.EXPENSE_TABLE+" ORDER BY mDate DESC")
    List<Expense> getAllExpenses();

    @Query("SELECT*FROM "+AppDatabase.EXPENSE_TABLE+" WHERE mTransactionId=:transactionId")
    Expense getExpenseById(int transactionId);

    @Query("SELECT*FROM "+AppDatabase.EXPENSE_TABLE+" WHERE mUserId=:userId ORDER BY mDate DESC")
    List<Expense> getExpensesByUserId(int userId);

    @Insert
    void insert(Balance... balances);

    @Update
    void update(Balance... balances);

    @Delete
    void delete(Balance balance);

    @Query("SELECT*FROM "+AppDatabase.BALANCE_TABLE)
    List<Balance> getAllBalances();

    @Query("SELECT*FROM "+AppDatabase.BALANCE_TABLE+" WHERE mBalanceId=:balanceId")
    Balance getBalanceById(int balanceId);

    @Query("SELECT*FROM "+AppDatabase.BALANCE_TABLE+" WHERE mUserId=:userId")
    Balance getBalanceByUserId(int userId);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT*FROM "+AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT*FROM "+AppDatabase.USER_TABLE+" WHERE mUsername=:username")
    User getUserByUsername(String username);

    @Query("SELECT*FROM "+AppDatabase.USER_TABLE+" WHERE mUserId=:userId")
    User getUserByUserId(int userId);
}
