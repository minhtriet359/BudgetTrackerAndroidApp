package com.example.project2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.ProjectDAO;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project2.userIdKey";

    private ProjectDAO mProjectDAO;
    private Button mViewButton;
    private Button mRemoveButton;
    private TextView mWelcomeMessage;

    private int mUserId;
    private User user;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDatabase();
        wireUpDisplay();
        setUpToolBar();
        getUser();

        mWelcomeMessage.setText("Welcome back, "+user.getUserName()+"!" +"\n"+"What would you like to do?");
        mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserDialog();
            }
        });
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUserDialog();
            }
        });
    }

    private void getUser() {
        mUserId=getIntent().getIntExtra(USER_ID_KEY,-1);
        user=mProjectDAO.getUserByUserId(mUserId);
    }

    private void setUpToolBar(){
        Toolbar mToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void wireUpDisplay(){
        mWelcomeMessage=findViewById(R.id.adminMenuWelcomeTextView);
        mViewButton=findViewById(R.id.viewUserButton);
        mRemoveButton=findViewById(R.id.removeUserButton);
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
        Intent intent=new Intent(context, AdminActivity.class);
        intent.putExtra(USER_ID_KEY,userId);
        return intent;
    }

    private void getDatabase(){
        mProjectDAO= Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProjectDAO();
    }

    private void viewUserDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.view_user_dialog, null);
        // Set up the views in the custom layout
        TextView textViewInfo = dialogView.findViewById(R.id.viewUserEditText);
        Button mDismissButton = dialogView.findViewById(R.id.dismissButton);
        // Set the information you want to display
        mUsers=mProjectDAO.getAllUsers();
        if(mUsers.size()<=0){
            textViewInfo.setText("No users found.");
        }else{
            StringBuilder sb=new StringBuilder();
            for(User user:mUsers){
                sb.append(user);
                sb.append("\n");
                sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                sb.append("\n");
            }
            textViewInfo.setText(sb.toString());
        }
        // Set up the dialog
        builder.setView(dialogView);
        // Create and show the dialog
        final AlertDialog dialog = builder.create();
        // Set a listener for the OK button
        mDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when OK is clicked
                dialog.dismiss();
            }
        });
        // Show the dialog
        dialog.show();
    }

    private void removeUserDialog(){
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.remove_user_dialog, null);
        // Set up the views in the custom layout
        EditText mEditText = dialogView.findViewById(R.id.getUserIdEditText);
        Button mConfirmButton = dialogView.findViewById(R.id.confirmButton);
        Button mCancelButton=dialogView.findViewById(R.id.cancelButton);
        // Set up the dialog
        builder.setView(dialogView);
        // Create and show the dialog
        final AlertDialog dialog = builder.create();
        // Set a listener for the cancel button
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when OK is clicked
                dialog.dismiss();
            }
        });
        // Set a listener for the confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=mEditText.getText().toString();
                User user=mProjectDAO.getUserByUserId(Integer.parseInt(userId));
                if(user==null){
                    Toast.makeText(AdminActivity.this, "User not found. Try again.", Toast.LENGTH_SHORT).show();
                }else{
                    mProjectDAO.delete(user);
                    Toast.makeText(AdminActivity.this, "User removed successfully.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        // Show the dialog
        dialog.show();
    }
}