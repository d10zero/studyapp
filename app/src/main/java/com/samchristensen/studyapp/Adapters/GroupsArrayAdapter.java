package com.samchristensen.studyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samchristensen.studyapp.GroupActivity;
import com.samchristensen.studyapp.R;

import java.util.ArrayList;

import Model.Group;

/**
 * Created by Sam on 3/9/15.
 */
public class GroupsArrayAdapter extends BaseAdapter {

    private ArrayList<Group> groups = new ArrayList<Group>();
    private Context context;

    public GroupsArrayAdapter(ArrayList<Group> groups, Context context){
        this.groups = groups;
        this.context = context;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.mygroups_row, parent,false);
        }

        TextView name, instructor;
        name = (TextView) convertView.findViewById(R.id.mygroups_groupname);
        instructor = (TextView) convertView.findViewById(R.id.mygroups_instructorname);

        name.setText(groups.get(position).getClassName());
        instructor.setText(groups.get(position).getInstructor());

        return convertView;
    }
}