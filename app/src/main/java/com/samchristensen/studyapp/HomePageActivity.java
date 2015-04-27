package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samchristensen.studyapp.Adapters.CustomArrayAdapter;
import com.samchristensen.studyapp.Popups.AccountInfoPopup;
import com.samchristensen.studyapp.Popups.CreatePostPopup;
import com.samchristensen.studyapp.Popups.PostDetailPopup;

import java.util.ArrayList;
import Model.Post;
import Model.School;
import Model.User;


public class HomePageActivity extends Activity {

    Handler handler = new Handler();
    ListView studyingposts;
    ArrayList<Post> posts;
    School school;
    User user;
    Context context;
    CustomArrayAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        context = this;

        //get user that was passed over
        //Intent intent = getIntent();
        //user = (User) intent.getSerializableExtra("User");
        //school = (School) intent.getSerializableExtra("School");

        user = new User("Sam", "Christensen", "sjchristens3", "sjchristens3@wisc.edu", "testpassword", school);
        school = new School();

        ImageView createIcon, accountIcon, searchIcon;
        EditText searchfield;
        TextView myGroups, myClasses, searchexit;
        Button reserveRoom;


        createIcon = (ImageView) findViewById(R.id.homepage_createPost);
        accountIcon = (ImageView) findViewById(R.id.homepage_accountinfo);
        searchIcon = (ImageView) findViewById(R.id.homepage_search);
        searchfield =(EditText) findViewById(R.id.homepage_searchfield);
        searchexit = (TextView) findViewById(R.id.homepage_exitsearch);

        reserveRoom = (Button) findViewById(R.id.homepage_reserveTable);
        myGroups = (TextView) findViewById(R.id.homepage_myGroups);
        myClasses = (TextView) findViewById(R.id.homepage_myClasses);

        reserveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Reserve Room Popup Here", Toast.LENGTH_SHORT).show();
            }
        });

        myClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "My Classes Popup Here", Toast.LENGTH_SHORT).show();
            }
        });

        myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "My Groups Popup Here", Toast.LENGTH_SHORT).show();
            }
        });

        createIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostPopup popup = new CreatePostPopup(context, user);
                popup.show();
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfoPopup popup = new AccountInfoPopup(context, user);
                popup.show();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGroups.setVisibility(View.GONE);
                createIcon.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)searchIcon.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                searchIcon.setLayoutParams(params);
                searchfield.setVisibility(View.VISIBLE);
                searchexit.setVisibility(View.VISIBLE);
            }
        });

        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIcon.setVisibility(View.VISIBLE);
                myGroups.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)searchIcon.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                searchIcon.setLayoutParams(params);
                searchfield.setVisibility(View.GONE);
                searchexit.setVisibility(View.GONE);
            }
        });


        //populate my groups list view
        studyingposts = (ListView) findViewById(R.id.homepage_listview);
        posts = school.getPosts();

        postsAdapter = new CustomArrayAdapter(posts);
        studyingposts.setAdapter(postsAdapter);

        studyingposts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Post post = posts.get(position);
                PostDetailPopup popup = new PostDetailPopup(context, post);
                popup.show();
            }
        });
    }

    private void switchViews(Class switchTo, int i){
        final Intent intent = new Intent(this, switchTo);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}
