package com.samchristensen.studyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.samchristensen.studyapp.Adapters.GroupPostArrayAdapter;

import java.util.ArrayList;

import Model.Backend;
import Model.Group;
import Model.User;


public class GroupDetailView extends Activity {

    User user;
    Group group;
    Context context = this;
    ArrayList<String> members;
    String TAG = "GroupDetailConnection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail_view);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");

        Backend.getGroup(new Backend.BackendCallback() {
            @Override
            public void onRequestCompleted(Object result) {
                group = (Group) result;
                members = group.getMembers();
                Log.d(TAG, "Successfully retrieved group " + group.getGroupId());
            }

            @Override
            public void onRequestFailed(String message) {
                Log.d(TAG, "Failed to retrieve group " + group.getGroupId());
            }
        }, ((Group) intent.getSerializableExtra("Group")).getGroupId());



        ImageView logo = (ImageView) findViewById(R.id.groupdetail_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomePageActivity.class);
                intent.putExtra("User", user);
                intent.putExtra("InsideApp", true);
                startActivity(intent);
            }
        });

        LinearLayout left, right;
        left = (LinearLayout) findViewById(R.id.groupdetail_membersholderLeft);
        right = (LinearLayout) findViewById(R.id.groupdetail_membersholderRight);

        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0)
                left.addView(setLayoutParams(new TextView(context), members.get(i)));
            else
                right.addView(setLayoutParams(new TextView(context), members.get(i)));

        }

        if(members.size() > 4){
            TextView view = (TextView) right.getChildAt(1);
            view.append("    ...");
        }

        TextView groupname, addpost;
        groupname = (TextView) findViewById(R.id.groupdetail_groupname);
        addpost = (TextView) findViewById(R.id.groupdetail_addpost);

        groupname.setText(group.getClassName());

        ListView posts = (ListView) findViewById(R.id.groupdetail_listview);

        GroupPostArrayAdapter adapter = new GroupPostArrayAdapter(((Group) intent.getSerializableExtra("Group")), context);
        posts.setAdapter(adapter);

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupAddPost.class);
                intent.putExtra("User", user);
                intent.putExtra("Group", ((Group) intent.getSerializableExtra("Group")));
                startActivity(intent);
            }
        });

    }

    private TextView setLayoutParams(TextView view, String text){
        LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setGravity(Gravity.CENTER);
        paramsExample.setMargins(3, 3, 3, 3);
        view.setText(text);
        view.setLayoutParams(paramsExample);
        return view;
    }

}
