package com.samchristensen.studyapp.Adapters;
;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samchristensen.studyapp.R;

import java.util.ArrayList;

import Model.Backend;
import Model.Group;

/**
 * Created by Sam on 3/9/15.
 */
public class GroupsArrayAdapter extends BaseAdapter {

    private ArrayList<Group> groups = new ArrayList<Group>();
    private final String username;
    private final boolean displayAdd;

    public GroupsArrayAdapter(ArrayList<Group> groups, String username, boolean displayAdd){
        this.groups = groups;
        this.username = username;
        this.displayAdd = displayAdd;
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
            convertView = inflater.inflate(R.layout.groups_row, parent,false);
        }

        TextView name, instructor, numberofmembers, add;
        name = (TextView) convertView.findViewById(R.id.mygroups_groupname);
        instructor = (TextView) convertView.findViewById(R.id.mygroups_instructorname);
        numberofmembers = (TextView) convertView.findViewById(R.id.mygroups_nummembers);
        add = (TextView) convertView.findViewById(R.id.mygroups_addgroup);

        name.setText( groups.get(position).getClassName());
        instructor.setText( groups.get(position).getInstructor());
        numberofmembers.setText("Members : " + groups.get(position).getMembers().size());

        if(displayAdd) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Backend.addNewGroupToUser(new Backend.BackendCallback() {
                        @Override
                        public void onRequestCompleted(Object result) {
                            Log.d("ConnectionManager", "Successfully added group to user");
                        }

                        @Override
                        public void onRequestFailed(String message) {
                            Log.d("ConnectionManager", "Failed to add group to user");
                        }
                    }, groups.get(position).getGroupId(), username);
                }
            });
        }
        else{
            add.setVisibility(View.GONE);
        }

        return convertView;
    }
}
