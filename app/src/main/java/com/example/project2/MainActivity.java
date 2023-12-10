package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.ProjectDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project2.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.project2.PREFERENCES_KEY";

    private TextView mWelcomeText;
    private Button mAdminButton;
    private ProjectDAO mProjectDAO;

    private int mUserId=-1;
    private SharedPreferences mPreferences=null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getDatabase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);

        mWelcomeText=findViewById(R.id.welcomeMessageTextView);
        mAdminButton=findViewById(R.id.adminButton);
        refreshDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Logout){
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDatabase() {
        mProjectDAO= Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProjectDAO();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent=new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY,userId);
        return intent;
    }

    private void loginUser(int mUserId) {
        mUser = mProjectDAO.getUserByUserId(mUserId);
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId=-1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //nothing is done here
                    }
                });
        alertBuilder.create().show();
    }

    private void getPrefs() {
        mPreferences=this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if (mPreferences==null){
            getPrefs();
        }
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putInt(USER_ID_KEY,userId);
    }

    private void checkForUser() {
        //do we have a user in the intent
        mUserId=getIntent().getIntExtra(USER_ID_KEY,-1);
        if(mUserId!=-1){
            return;
        }
        //do we have a user in the preferences;
        if(mPreferences==null){
            getPrefs();
        }
        mUserId=mPreferences.getInt(USER_ID_KEY,-1);
        if(mUserId!=-1){
            return;
        }
        //do we have any users at all;
        List<User> users=mProjectDAO.getAllUsers();
        if(users.size()<=0){
            User defaultUser=new User("admin2","admin2",true);
            User altUser=new User("testuser1","testuser1",false);
            mProjectDAO.insert(defaultUser,altUser);
        }

        Intent intent=LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
    }

    private void refreshDisplay() {
        mWelcomeText.setText("Welcome, "+ mUser.getUserName()+"!");
        if(mUser.isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }else{
            mAdminButton.setVisibility(View.INVISIBLE);
        }
    }

}