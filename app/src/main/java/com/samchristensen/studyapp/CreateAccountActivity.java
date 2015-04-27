package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import Model.InvalidInputException;
import Model.School;
import Model.User;


public class CreateAccountActivity extends Activity {

    EditText firstName, lastName, email, password;
    User user;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

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
            //validateInput();

            School school = new School();
            //Create a new user from fields
            /*user = new User(firstName.getText().toString(), lastName.getText().toString(), "",
                    email.getText().toString(), password.getText().toString(), null, 0, school);*/
            user = new User("Test", "User", "username", "test@wisc.edu", "testpassword", school);

            Toast toast = Toast.makeText(this,
                    "Congratulations " + firstName.getText().toString().trim() + ", you registered!"
                    , Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

            //toast.setMargin(0, 30);
            toast.show();

            //Move user off to the next page, passing along the user object
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
            Toast toast = Toast.makeText(this, R.string.passwordError, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    //Validate User Supplied Account Information
    private boolean validateInput() throws InvalidInputException{
        String testFirstName, testLastName, testEmail, testPassword;
        testFirstName = "Test";
        testLastName = "User";
        testEmail = "test@wisc.edu";
        testPassword = "testpassword";
        //Check if firstName and lastName are provided
        if(/*firstName.getText().toString().length()*/ testFirstName.length() < 1)
            throw new InvalidInputException("f");
        else if(/*lastName.getText().toString().length()*/ testLastName.length() < 1)
            throw new InvalidInputException("l");

        //Make sure wisc email address is used
        String wisc = "@wisc.edu";
        String tempEmail = email.getText().toString();
        /*if(tempEmail.length() < 10){
            throw new InvalidInputException("e");
        }*/
        if(!(testEmail.substring(testEmail.length()-9,testEmail.length()).equalsIgnoreCase(wisc))){
            throw new InvalidInputException("e");
        }
        //Check to make sure password is proper length
        if(testPassword.length() < 6)
            throw new InvalidInputException("p");



        return true;
    }
}
