package com.example.project2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.ProjectDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BalanceActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project2.userIdKey";
    private static final String []expenseTypes={"Bill/Utilities","Groceries","Food/Drink","Personal","Gas","Shopping"};

    private ProjectDAO mProjectDAO;
    private Balance mBalance;
    private TextView mSummaryInfo;

    private List<Expense> expenses;
    private List<Deposit> deposits;
    private double totalExpense=0.0;
    private double totalIncome=0.0;
    private double utilitiesAmt,groceriesAmt,foodAmt,personalAmt,gasAmt,shoppingAmt;
    private double mBalanceAmount=0.0;
    private int mUserId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        setUpToolBar();
        getDatabase();

        mSummaryInfo=findViewById(R.id.summaryInfoTextView);
        mUserId=getIntent().getIntExtra(USER_ID_KEY,-1);
        expenses=mProjectDAO.getExpensesByUserId(mUserId);
        deposits=mProjectDAO.getDepositsByUserId(mUserId);
        totalExpense=totalExpense(expenses);
        totalIncome=totalDeposit(deposits);
        mBalance=new Balance(mUserId);
        mBalance.calculateBalance(expenses,deposits);
        mBalanceAmount=mBalance.getRemainingBalance();

        setUpPieChart();

        //display info
        StringBuilder sb=new StringBuilder();
        //sb.append("Summary");
        //sb.append("\n\n");
        sb.append("Spending :  $"+String.format("%.2f", totalExpense));
        sb.append("\n\n");
        sb.append("Income     :  $"+String.format("%.2f", totalIncome));
        sb.append("\n\n");
        if(mBalanceAmount<0){
            sb.append("Shortage  :  $");
        }else{
            sb.append("Extra    :  $");
        }
        sb.append(String.format("%.2f", mBalanceAmount));
        mSummaryInfo.setText(sb.toString());
    }

    private double totalExpense(List<Expense>expenses){
        double total=0.0;
        for(Expense expense:expenses){
            total+=expense.getAmount();
        }
        return total;
    }

    private double totalDeposit(List<Deposit>deposits){
        double total=0.0;
        for(Deposit deposit:deposits){
            total+=deposit.getAmount();
        }
        return total;
    }

    private void setUpToolBar(){
        Toolbar mToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.nonlogout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent=new Intent(context, BalanceActivity.class);
        intent.putExtra(USER_ID_KEY,userId);
        return intent;
    }

    private void getDatabase(){
        mProjectDAO= Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProjectDAO();
    }

    private void setUpPieChart(){
        //set up pie chart
        PieChart pieChart = findViewById(R.id.budgetPieChart);

        //input chart data
        utilitiesAmt=groceriesAmt=foodAmt=personalAmt=gasAmt=shoppingAmt=0.0;
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(Expense expense:expenses){
            switch(expense.getType()){
                case "Bill/Utilities": utilitiesAmt+=expense.getAmount();
                    break;
                case "Groceries": groceriesAmt+=expense.getAmount();
                    break;
                case "Food/Drink": foodAmt+=expense.getAmount();
                    break;
                case "Personal": personalAmt+=expense.getAmount();
                    break;
                case "Gas": gasAmt+=expense.getAmount();
                    break;
                case "Shopping": shoppingAmt+=expense.getAmount();
                    break;
            }
        }
        if(utilitiesAmt!=0){
            entries.add(new PieEntry((float) utilitiesAmt,expenseTypes[0]));
        }
        if(groceriesAmt!=0){
            entries.add(new PieEntry((float) groceriesAmt,expenseTypes[1]));
        }
        if(foodAmt!=0){
            entries.add(new PieEntry((float) foodAmt,expenseTypes[2]));
        }
        if(personalAmt!=0){
            entries.add(new PieEntry((float) personalAmt,expenseTypes[3]));
        }
        if(gasAmt!=0){
            entries.add(new PieEntry((float) gasAmt,expenseTypes[4]));
        }
        if(shoppingAmt!=0){
            entries.add(new PieEntry((float) shoppingAmt,expenseTypes[5]));
        }

        // Create a PieDataSet
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);

        // Create a PieData object
        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Customize the chart
        pieChart.setCenterText("Expense Chart");
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
        pieChart.setDrawSliceText(false);
    }

}