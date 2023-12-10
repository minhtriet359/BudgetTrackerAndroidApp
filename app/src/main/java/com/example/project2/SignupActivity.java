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

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String confirmPassword;

    private ProjectDAO mProjectDAO;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSignupButton;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        wireUpDisplay();
        getDatabase();


        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(validateUsername()){
                    if(validatePassword()){
                        User user=new User(username,password,false);
                        mProjectDAO.insert(user);
                        Toast.makeText(SignupActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent=LoginActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignupActivity.this, "Password does not match. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Username already exists. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void wireUpDisplay(){
        mUsername=findViewById(R.id.editTextSignupUsername);
        mPassword=findViewById(R.id.editTextSignupPassword);
        mConfirmPassword=findViewById(R.id.editTextSignupConfirmPassword);
        mSignupButton=findViewById(R.id.signupPageSignupButton);
        mLoginButton=findViewById(R.id.signupPageLoginButton);
    }

    private void getValuesFromDisplay(){
        username=mUsername.getText().toString();
        password=mPassword.getText().toString();
        confirmPassword=mConfirmPassword.getText().toString();
    }

    private void getDatabase(){
        mProjectDAO= Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProjectDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent=new Intent(context, SignupActivity.class);
        return intent;
    }

    private boolean validatePassword(){
        if(password.equals(confirmPassword)){
            return true;
        }else{
            return false;
        }
    }

    private boolean validateUsername(){
        List<User> users=mProjectDAO.getAllUsers();
        for(User user:users){
            if(user.getUserName().equals(username)){
                return false;
            }
        }
        return true;
    }

}