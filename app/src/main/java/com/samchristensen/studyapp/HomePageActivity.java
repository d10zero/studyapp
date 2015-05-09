package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samchristensen.studyapp.Adapters.GroupsArrayAdapter;
import com.samchristensen.studyapp.Adapters.PostsArrayAdapter;
import com.samchristensen.studyapp.Popups.AccountInfoPopup;
import com.samchristensen.studyapp.Popups.MyGroupsPopup;
import com.samchristensen.studyapp.Popups.PostDetailPopup;
import com.samchristensen.studyapp.Popups.CreatePopup;

import java.util.ArrayList;

import Model.Backend;
import Model.Group;
import Model.Post;
import Model.School;
import Model.User;


public class HomePageActivity extends Activity {

    public User user;
    Context context = this;
    ListView postslv, groupslv;
    TextView title, postsbutton, groupsbutton, searchexit;
    ImageView createIcon, accountIcon, searchIcon, myGroups;
    EditText searchfield;
    Button reserveRoom;
    boolean searchOpen = false;
    PostsArrayAdapter postsAdapter;
    GroupsArrayAdapter groupsadapter;
    String TAG = "ConnectionManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set view to correct Layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Intent intent = getIntent();

        boolean InsideApp = intent.getBooleanExtra("InsideApp", false);
        if (InsideApp) {
            if (intent.getSerializableExtra("User") != null) {
                user = (User) intent.getSerializableExtra("User");
                user.setSchool(new School());
            }
        }
        Log.d(TAG, "Attempting to load in groups");
        Backend.getGroups(new Backend.BackendCallback() {
            @Override
            public void onRequestCompleted(Object result) {
                Log.d(TAG, "Server loaded groups");
                user.getSchool().addGroupGroup((ArrayList<Group>) result);
            }

            @Override
            public void onRequestFailed(String message) {
                Log.d(TAG, "Server could not load groups");
            }
        });

        Log.d(TAG, "Attempting to load in posts");
        Backend.getPosts(new Backend.BackendCallback() {
            @Override
            public void onRequestCompleted(Object result) {
                Log.d(TAG, "Server loaded posts");
                user.getSchool().addPostsGroup((ArrayList<Post>) result);
            }

            @Override
            public void onRequestFailed(String message) {
                Log.d(TAG, "Server could not load posts");
            }
        });


        //Hookup ImageView to corresponding UI elements
        createIcon = (ImageView) findViewById(R.id.homepage_createPost);
        accountIcon = (ImageView) findViewById(R.id.homepage_accountinfo);
        searchIcon = (ImageView) findViewById(R.id.homepage_search);
        myGroups = (ImageView) findViewById(R.id.homepage_myGroups);

        //Hookup EditText to corresponding UI element
        searchfield = (EditText) findViewById(R.id.homepage_searchfield);

        //Hookup TextView to corresponding UI element
        searchexit = (TextView) findViewById(R.id.homepage_exitsearch);
        groupsbutton = (TextView) findViewById(R.id.homepage_groupbutton);
        postsbutton = (TextView) findViewById(R.id.homepage_postsbutton);
        title = (TextView) findViewById(R.id.homepage_textView);

        postsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchListView(View.VISIBLE, View.GONE, "Posts");
            }
        });

        groupsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchListView(View.GONE, View.VISIBLE, "Groups");
            }
        });

        //Hookup Button to corresponding UI element and set clickaction
        reserveRoom = (Button) findViewById(R.id.homepage_reserveTable);
        reserveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReserveTable.class);
                startActivity(intent);
            }
        });


        //Hookup ListView to corresponding UI Element
        postslv = (ListView) findViewById(R.id.homepage_listviewposts);
        groupslv = (ListView) findViewById(R.id.homepage_listviewgroups);

        //Populate the ListView with temporary postContent

        postsAdapter = new PostsArrayAdapter(user.getSchool().getPosts());
        postslv.setAdapter(postsAdapter);

        groupsadapter = new GroupsArrayAdapter(user.getSchool().getGroups(),
                user.getUserName(), true);
        groupslv.setAdapter(groupsadapter);


        //setOnClick Actions for each of the UI elements
        myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyGroupsPopup popup = new MyGroupsPopup(context, user, user.getGroups());
                popup.show();
            }
        });


        //setOnClick action for Creating new Post Popup
        createIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePopup popup = new CreatePopup(context, user);
                popup.show();
            }
        });

        //setOnClick action for launching Account Popup
        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfoPopup popup = new AccountInfoPopup(context, user);
                popup.show();
            }
        });

        //Add search box and set the footer to searchBox view
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchOpen) {
                    searchField(View.GONE, View.VISIBLE, RelativeLayout.ALIGN_PARENT_RIGHT,
                            RelativeLayout.ALIGN_PARENT_LEFT);
                    searchOpen = true;
                } else {
                    String searchterm = searchfield.getText().toString().toUpperCase();
                    searchLists(searchterm, groupslv, true);
                    searchLists(searchterm, postslv, false);
                }
            }
        });

        //Remove search box and reset the footer to original view
        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchField(View.VISIBLE, View.GONE, RelativeLayout.ALIGN_PARENT_LEFT,
                        RelativeLayout.ALIGN_PARENT_RIGHT);
                searchfield.setText("");
                searchfield.setHint("Search");
                postslv.setAdapter(postsAdapter);
                groupslv.setAdapter(groupsadapter);
                searchOpen = false;
            }
        });


        //setOnClick action for each ListView row
        postslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Get the post that was clicked and pass it on to the delegate popup
                PostDetailPopup popup = new PostDetailPopup(context, user.getSchool().getPosts().get(position));
                popup.show();
            }
        });

    }


    //Method used to switch between different Activities in the App
    private void switchViews(Class switchTo){
        Intent intent = new Intent(this, switchTo);
        intent.putExtra("User", user);
        intent.putExtra("Groups", user.getGroups());
        startActivity(intent);
    }

    private void switchListView(int first, int second, String word){
        postslv.setVisibility(first);
        groupslv.setVisibility(second);
        title.setText(word);

        if(first == View.GONE){
            postsbutton.setBackgroundResource(R.drawable.blueborderwhitebackground);
            groupsbutton.setBackgroundResource(R.drawable.whiteborderbluebackground);
            postsbutton.setTextColor(Color.parseColor("#2875A7"));
            groupsbutton.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else{
            postsbutton.setBackgroundResource(R.drawable.whiteborderbluebackground);
            groupsbutton.setBackgroundResource(R.drawable.blueborderwhitebackground);
            postsbutton.setTextColor(Color.parseColor("#FFFFFF"));
            groupsbutton.setTextColor(Color.parseColor("#2875A7"));
        }
    }

    private void searchField(int first, int second, int third, int fourth){
        //Remove footer elements from view
        myGroups.setVisibility(first);
        createIcon.setVisibility(first);

        //Move search icon to left of footer box
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)searchIcon.getLayoutParams();
        params.removeRule(third);
        params.addRule(fourth);
        searchIcon.setLayoutParams(params);

        //Make search box and 'x' visible
        searchfield.setVisibility(second);
        searchexit.setVisibility(second);
    }

    private void searchLists(String searchterm, ListView lv, boolean x){
        if(x) {
            ArrayList<Group> searchedGroups = new ArrayList<Group>();
            for (int i = 0; i < user.getSchool().getGroups().size(); i++) {
                if (user.getSchool().getGroups().get(i).getClassName().toUpperCase().contains(searchterm)) {
                    searchedGroups.add(user.getSchool().getGroups().get(i));
                }
            }
            GroupsArrayAdapter adapter = new GroupsArrayAdapter(searchedGroups, user.getUserName(), true);
            lv.setAdapter(adapter);

        }
        else{
            ArrayList<Post> searchedPosts = new ArrayList<Post>();
            for(int i = 0; i < user.getSchool().getPosts().size(); i++){
                if(user.getSchool().getPosts().get(i).getClassName().toUpperCase().contains(searchterm)){
                    searchedPosts.add(user.getSchool().getPosts().get(i));
                }
            }
            PostsArrayAdapter adapter = new PostsArrayAdapter(searchedPosts);
            lv.setAdapter(adapter);
        }
    }
}
