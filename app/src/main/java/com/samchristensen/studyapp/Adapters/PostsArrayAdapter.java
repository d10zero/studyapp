package com.samchristensen.studyapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samchristensen.studyapp.R;

import java.util.ArrayList;
import Model.Post;

/**
 * Created by Sam on 3/9/15.
 */
public class PostsArrayAdapter extends BaseAdapter {

    private ArrayList<Post> posts = new ArrayList<Post>();

    public PostsArrayAdapter(ArrayList<Post> posts){
        this.posts = posts;
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
            convertView = inflater.inflate(R.layout.post_row, parent,false);
        }

        TextView subject = (TextView) convertView.findViewById(R.id.post_class);
        TextView location = (TextView) convertView.findViewById(R.id.post_location);
        TextView instructor = (TextView) convertView.findViewById(R.id.post_instructor);
        TextView time = (TextView) convertView.findViewById(R.id.post_time);

        subject.setText(posts.get(position).getClassName());
        location.setText(posts.get(position).getLocation());
        instructor.setText(posts.get(position).getInstructor());

        time.setText("All day/night");


        return convertView;
    }
}
