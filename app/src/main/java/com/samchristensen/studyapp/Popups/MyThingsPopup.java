package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.samchristensen.studyapp.Adapters.GroupsArrayAdapter;
import com.samchristensen.studyapp.GroupActivity;
import com.samchristensen.studyapp.R;

import java.util.ArrayList;

import Model.Group;
import Model.Post;

/**
 * Created by Sam on 4/17/15.
 */
public class MyThingsPopup extends Dialog {

    private ArrayList<Post> myposts;
    private ArrayList<Group> mygroups;
    private Context context;

    public MyThingsPopup(Context context,ArrayList<Post> myposts, ArrayList<Group> mygroups ) {
        super(context);
        this.context = context;
        this.mygroups = mygroups;
        this.myposts = myposts;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mythings_popup);
        ListView lv = (ListView) findViewById(R.id.mythings_listview);
        if(mygroups == null){
            GroupsArrayAdapter adapter = new GroupsArrayAdapter(mygroups, context);
            lv.setAdapter(adapter);
        }
        else{
            GroupsArrayAdapter adapter = new GroupsArrayAdapter(mygroups, context);
            lv.setAdapter(adapter);
        }



    }
}
