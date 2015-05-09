package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import Model.Backend;
import Model.School;
import Model.User;


public class SplashScreen extends Activity {

    String TAG = "ConnectionManager";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", null);
        final String password = sharedPreferences.getString("password", null);

        if(username == null || password == null){
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }
        else{
            Log.d(TAG, "Trying to login");
            Backend.login(new Backend.BackendCallback() {
                @Override
                public void onRequestCompleted(Object result) {
                    Log.d(TAG, "Successfully retrieved user");

                    SharedPreferences sharedPreferences2 = getSharedPreferences("UserData", MODE_PRIVATE);
                    String firstname = sharedPreferences2.getString("firstname", null);
                    String lastname = sharedPreferences2.getString("lastname", null);

                    User user = new User(firstname, lastname, username + "@wisc.edu", password, new School());


                    Intent intent = new Intent(context, HomePageActivity.class);
                    intent.putExtra("User", user);
                    intent.putExtra("School", user.getSchool());
                    intent.putExtra("InsideApp", true);
                    startActivity(intent);
                }

                @Override
                public void onRequestFailed(String message) {
                    Log.d(TAG, "Failed to retrieve user");
                    Toast.makeText(context, "Error With Server", Toast.LENGTH_SHORT).show();
                }
            }, username, password);
        }

    }

}
