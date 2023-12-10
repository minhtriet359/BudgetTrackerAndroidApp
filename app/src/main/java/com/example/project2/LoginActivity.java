package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.ProjectDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mSignupButton;
    private ProjectDAO mProjectDAO;

    private String mUsername;
    private String mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wireupDisplay();
        getDatabase();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent=MainActivity.intentFactory(getApplicationContext(),mUser.getUserId());
                        startActivity(intent);
                    }
                }
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "lets go", Toast.LENGTH_SHORT).show();
                Intent intent=SignupActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPassword);
    }

    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser= mProjectDAO.getUserByUsername(mUsername);
        if(mUser==null){
            Toast.makeText(this, "no user "+mUsername+" found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void wireupDisplay(){
        mUsernameField =findViewById(R.id.editTextLoginUserName);
        mPasswordField =findViewById(R.id.editTextLoginPassword);
        mLoginButton =findViewById(R.id.loginPageLoginButton);
        mSignupButton=findViewById(R.id.loginPageSignupButton);
    }

    private void getDatabase(){
        mProjectDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProjectDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        return intent;
    }
}