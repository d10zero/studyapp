package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import Model.Backend;
import Model.Group;
import Model.InvalidInputException;
import Model.Post;
import Model.School;
import Model.User;


public class Create extends Activity {


    private User user;
    private School school;
    private boolean betweenIsSelected = true;
    private String TAG = "CreateConnection";
    final String[] twentyeightArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                    "13", "14", "15", "16", "17", "18", "19", "21", "22", "23", "24", "25", "26", "28"};
    final String[] thirtyArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "21", "22", "23", "24", "25", "26", "28", "29",
                "30"};
    final String[] thirtyOneArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "21", "22", "23", "24", "25", "26", "28", "29",
            "30", "31"};
    final double[] numbers1 = {8.00, 9.00, 10.00, 11.00, 12.00, 13.00, 14.00, 15.00, 16.00, 17.00,
        18.00, 19.00, 20.00, 21.00, 22.00, 23.00, 24.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00};
    final double[] numbers2 = {13.00, 14.00, 15.00, 16.00, 17.00, 18.00, 19.00, 20.00, 21.00,
            22.00, 23.00, 24.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00, 11.00, 12.00};

    Context context = this;
    EditText classname, location, instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        school = (School) intent.getSerializableExtra("School");
        boolean post = intent.getBooleanExtra("Post", true);

        if(post){
            setContentView(R.layout.createpost);
            handlePost();
        }
        else{
            setContentView(R.layout.creategroup);
            handleGroup();
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void switchViews(Class switchTo){
        Intent intent = new Intent(this, switchTo);
        intent.putExtra("User", user);
        intent.putExtra("School", school);
        intent.putExtra("InsideApp", true);
        startActivity(intent);
    }

    private void validatePostInput() throws InvalidInputException {
        if(classname.getText().toString() == ""){
            throw new InvalidInputException("classname");
        }
        if(instructor.getText().toString() == ""){
            throw new InvalidInputException("instructor");
        }
        if(location.getText().toString() == ""){
            throw new InvalidInputException("location");
        }
    }

    private void handlePost(){

        final Spinner betweenAt, time1, time2, month, day, year;
        final TextView and, cancel, create;

        classname = (EditText) findViewById(R.id.createpost_nameofclass);
        location = (EditText) findViewById(R.id.createpost_location);
        instructor = (EditText) findViewById(R.id.createpost_instructor);
        betweenAt = (Spinner) findViewById(R.id.createpost_typeOfTime);
        time1 = (Spinner) findViewById(R.id.createpost_firstTime);
        time2 = (Spinner) findViewById(R.id.createpost_secondTime);
        month = (Spinner) findViewById(R.id.createpost_month);
        day = (Spinner) findViewById(R.id.createpost_day);
        year = (Spinner) findViewById(R.id.createpost_year);
        and = (TextView) findViewById(R.id.createpost_and);
        cancel = (TextView) findViewById(R.id.createpost_cancel);
        create = (TextView) findViewById(R.id.createpost_create);

        //Respond to clicks on different buttons
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    validatePostInput();
                    String date = month.getSelectedItem().toString() + " " + day.getSelectedItem().toString() + ", "
                            + year.getSelectedItem().toString();
                    double startTime, endTime;
                    if(betweenIsSelected){
                        startTime = numbers1[time1.getSelectedItemPosition()];
                        endTime = numbers2[time2.getSelectedItemPosition()];
                    }
                    else{
                        startTime = 0.00;
                        endTime = numbers1[time1.getSelectedItemPosition()];
                    }
                    Log.d(TAG, "Trying to create new post");
                    Backend.createNewPost(new Backend.BackendCallback() {
                        @Override
                        public void onRequestCompleted(Object result) {
                            switchViews(HomePageActivity.class);
                        }

                        @Override
                        public void onRequestFailed(String message) {
                            Log.d(TAG, message);
                        }
                    }, new Post(classname.getText().toString(), instructor.getText().toString(),
                            location.getText().toString(), startTime,
                            endTime, date, "sjchristens3"/*user.getUserName()*/));
                }
                catch(InvalidInputException e){
                    showError(e.getMessage());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViews(HomePageActivity.class);
            }
        });

        //Listener for the word "At" to be selected so that a change in available time spinners happens
        betweenAt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    time2.setVisibility(View.GONE);
                    and.setVisibility(View.GONE);
                    betweenIsSelected = false;
                } else {
                    time2.setVisibility(View.VISIBLE);
                    and.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        //Listener for the word "At" to be selected so that a change in available time spinners happens
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ArrayAdapter<String> twentyEight = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        twentyeightArray) {
                };
                ArrayAdapter<String> thirty = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        thirtyArray) {
                };
                ArrayAdapter<String> thirtyone = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        thirtyOneArray) {
                };
                switch (position){
                    case 0:
                        day.setAdapter(thirtyone);
                        break;
                    case 1:
                        day.setAdapter(twentyEight);
                        break;
                    case 2:
                        day.setAdapter(thirtyone);
                        break;
                    case 3:
                        day.setAdapter(thirty);
                        break;
                    case 4:
                        day.setAdapter(thirtyone);
                        break;
                    case 5:
                        day.setAdapter(thirty);
                        break;
                    case 6:
                        day.setAdapter(thirtyone);
                        break;
                    case 7:
                        day.setAdapter(thirtyone);
                        break;
                    case 8:
                        day.setAdapter(thirty);
                        break;
                    case 9:
                        day.setAdapter(thirtyone);
                        break;
                    case 10:
                        day.setAdapter(thirty);
                        break;
                    case 11:
                        day.setAdapter(thirtyone);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private void handleGroup(){
        //UI Elements on each Account Info Popup
        final EditText classname, instructorname;
        TextView create, cancel;

        cancel = (TextView) findViewById(R.id.creategroup_cancel);
        create = (TextView) findViewById(R.id.creategroup_create);

        classname = (EditText) findViewById(R.id.creategroup_nameofclass);
        instructorname = (EditText) findViewById(R.id.creategroup_instructor);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ConnectionManager", "trying to create group");
                Backend.createNewGroup(new Backend.BackendCallback() {
                    @Override
                    public void onRequestCompleted(Object result) {
                        switchViews(HomePageActivity.class);
                    }

                    @Override
                    public void onRequestFailed(String message) {
                        Log.d(TAG, message);
                    }
                }, new Group(classname.getText().toString(),
                        instructorname.getText().toString(), user.getUserName()));

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViews(HomePageActivity.class);
            }
        });
    }

    private void showError(String error){
        if(error.equals("classname")){
            YoYo.with(Techniques.Shake).duration(1000).playOn(classname);
        }
        else if(error.equals("instructor")){
            YoYo.with(Techniques.Shake).duration(1000).playOn(instructor);
        }
        else{
            YoYo.with(Techniques.Shake).duration(1000).playOn(location);
        }
    }
}
