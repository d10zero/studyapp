package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.samchristensen.studyapp.Adapters.GroupsArrayAdapter;
import com.samchristensen.studyapp.GroupDetailView;
import com.samchristensen.studyapp.R;

import java.util.ArrayList;

import Model.Group;
import Model.Post;
import Model.User;

/**
 * Created by Sam on 4/17/15.
 */
public class MyGroupsPopup extends Dialog {

    private User user;
    private ArrayList<Group> groups;
    private Context context;

    public MyGroupsPopup(Context context, User user, ArrayList<Group> groups) {
        super(context);
        this.user = user;
        this.groups = groups;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mygroups);

        //Hook all elements up to the coressponding UI elements
        ListView lv;

        lv = (ListView) findViewById(R.id.groupview_listview);

        GroupsArrayAdapter adapter = new GroupsArrayAdapter(groups, user.getUserName(), false);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, GroupDetailView.class);
                intent.putExtra("User", user);
                intent.putExtra("Group", groups.get(position));
                context.startActivity(intent);
            }
        });
    }
}
