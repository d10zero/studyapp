package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import Model.Backend;
import Model.InvalidInputException;
import Model.School;
import Model.User;


public class CreateAccountActivity extends Activity {

    EditText firstName, lastName, email, password;
    String firstNameWord, lastNameWord, emailWord, usernameWord, passwordWord;
    String TAG = "CreateAccountServer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Button create;

        //Set the register and cancel button click handlers
        final Button register = (Button) findViewById(R.id.loginpage_registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(register.getApplicationWindowToken(), 0);

                //attempt to register the user
                register();

            }
        });

        //Set the objects equal to the correct view objects
        firstName = (EditText) findViewById(R.id.firstNameField);

        lastName = (EditText) findViewById(R.id.lastNameField);

        email = (EditText) findViewById(R.id.emailField);

        password = (EditText) findViewById(R.id.passField);

    }

    //Click Handler for Register Button
    public void register(){
        try{
            //Check input provided is valid
            validateInput();

            School school = new School();
            User user =  new User(firstNameWord, lastNameWord, usernameWord, emailWord, passwordWord, school);
            Log.d(TAG, "we are here");
            Backend.createNewUser(new Backend.BackendCallback() {
                @Override
                public void onRequestCompleted(Object result) {
                    Log.d(TAG, "New User Successfully Created");
                }

                @Override
                public void onRequestFailed(String message) {
                    Log.d(TAG, "New User Failed To Be Created");
                }
            }, user);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("username", usernameWord);
            editor.putString("password", passwordWord);

            editor.commit();

            Log.d(TAG, "User account was saved");



            //Congratulate the user on creation of account
            Toast toast = Toast.makeText(this,
                    "Congratulations " + firstName.getText().toString().trim() + ", you registered!"
                    , Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            //Move user to the homepage, passing along the user object and school
            Intent intent = new Intent(this, HomePageActivity.class);
            intent.putExtra("User", user);
            intent.putExtra("School", school);
            startActivity(intent);

        }
        catch(Exception e) {
            errorMessage(e.getMessage());
        }
    }

    //Toast Correct Error Message
    private void errorMessage(String e) {
        //Error with first name
        if(e == "f") {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(firstName);
            firstName.requestFocus();
            Toast toast = Toast.makeText(this, R.string.firstNameError, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

        //Error with last name
        else if(e == "l") {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(lastName);
            lastName.requestFocus();

            //Notify user of the error
            Toast toast = Toast.makeText(this, R.string.lastNameError, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

        //Error with email address
        else if(e == "e") {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(email);
            email.requestFocus();

            //Notify user of the error
            Toast toast = Toast.makeText(this, R.string.emailError, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

        //Error with password
        else {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(password);
            password.requestFocus();

            //Notify user of the error
            Toast toast = Toast.makeText(this, R.string.passwordError, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    //Validate User Supplied Account Information
    private boolean validateInput() throws InvalidInputException{

        firstNameWord = firstName.getText().toString();
        lastNameWord = lastName.getText().toString();
        emailWord = email.getText().toString();
        usernameWord = emailWord.substring(0, emailWord.length() - 9);
        passwordWord = password.getText().toString();

        //Check if firstName and lastName are provided
        if(firstNameWord.length()  < 1)
            throw new InvalidInputException("f");
        else if(lastNameWord.length() < 1)
            throw new InvalidInputException("l");

        //Make sure wisc email address is used
        String wisc = "@wisc.edu";

        if(emailWord.length() < 10){
            throw new InvalidInputException("e");
        }
        if(!(emailWord.substring(emailWord.length()-9,emailWord.length()).equalsIgnoreCase(wisc))){
            throw new InvalidInputException("e");
        }
        //Check to make sure password is proper length
        if(passwordWord.length() < 6)
            throw new InvalidInputException("p");

        return true;
    }
}
