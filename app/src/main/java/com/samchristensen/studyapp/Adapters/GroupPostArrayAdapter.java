package com.samchristensen.studyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samchristensen.studyapp.R;

import java.util.ArrayList;

import Model.Group;
import Model.Post;

/**
 * Created by Sam on 3/9/15.
 */
public class GroupPostArrayAdapter extends BaseAdapter {

    private ArrayList<Post> posts;
    private Context context;

    public GroupPostArrayAdapter(Group group, Context context){
        this.posts = group.getChatboard();
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
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
            convertView = inflater.inflate(R.layout.groupdetail_postrow, parent,false);
        }

        TextView creatorname, postcontent;
        creatorname = (TextView) convertView.findViewById(R.id.groupdetail_usernamepost);
        postcontent = (TextView) convertView.findViewById(R.id.groupdetail_userpostcontent);

        postcontent.setText(posts.get(position).getClassName());
        creatorname.setText(posts.get(position).getCreatorUsername());

        return convertView;
    }
}
