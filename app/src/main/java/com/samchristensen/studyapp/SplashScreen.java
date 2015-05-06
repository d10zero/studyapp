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

    String TAG = "SplashScreenConnection";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        if(username == null || password == null){
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }

        else{
            Backend.getUser(new Backend.BackendCallback() {
                @Override
                public void onRequestCompleted(Object result) {
                    Log.d(TAG, "Successfully retrieved user");
                    Intent intent = new Intent(context, HomePageActivity.class);
                    intent.putExtra("User",((User) result));
                    intent.putExtra("School", new School());
                    startActivity(intent);
                }

                @Override
                public void onRequestFailed(String message) {
                    Log.d(TAG, "Failed to retrieve user");
                    Toast.makeText(context, "Error With Server", Toast.LENGTH_SHORT).show();
                }
            }, username);
        }

    }

}
