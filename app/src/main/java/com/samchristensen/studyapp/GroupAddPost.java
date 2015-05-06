package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import Model.Group;
import Model.Post;
import Model.User;


public class GroupAddPost extends Activity {

    User user;
    Group group;
    Intent intentf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_post);

        intentf = getIntent();
        final User user = (User) intentf.getSerializableExtra("User");

        //Hook all elements up to the coressponding UI elements
        final EditText content;
        TextView exit, create;

        exit = (TextView) findViewById(R.id.groupcreatepost_cancel);
        create = (TextView) findViewById(R.id.groupcreatepost_create);

        content = (EditText) findViewById(R.id.groupcreatepost_content);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(content.getText().toString(), "", "", 0, "", user.getUserName());
                ((Group)intentf.getSerializableExtra("Group")).addPost(post);
                switchViews(GroupDetailView.class);
            }
        });

        //Set exit on click listener
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViews(GroupDetailView.class);
            }
        });
    }
    private void switchViews(Class switchTo){
        Intent intent = new Intent(this, switchTo);
        intent.putExtra("User", user);
        intent.putExtra("Group", ((Group)intentf.getSerializableExtra("Group")));
        startActivity(intent);
    }
}
