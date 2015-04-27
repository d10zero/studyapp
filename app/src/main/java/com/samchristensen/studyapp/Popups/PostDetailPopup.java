package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.samchristensen.studyapp.R;

import Model.Post;

/**
 * Created by Sam on 4/17/15.
 */
public class PostDetailPopup extends Dialog {

    private Post post;

    public PostDetailPopup(Context context, Post post) {
        super(context);
        this.post = post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.post_detailview);

        final TextView createdby, classname, instructorname, location, time, creatorEmail;
        final IconTextView exit;

        createdby = (TextView) findViewById(R.id.postdetail_createdby);
        creatorEmail = (TextView) findViewById(R.id.postdetail_creatoremail);
        classname = (TextView) findViewById(R.id.postdetail_classname);
        instructorname = (TextView) findViewById(R.id.postdetail_instructor);
        location = (TextView) findViewById(R.id.postdetail_location);
        time = (TextView) findViewById(R.id.postdetail_time);

        exit = (IconTextView) findViewById(R.id.postdetail_exit);

        createdby.setText("Created by : " + post.getCreatorUsername());
        creatorEmail.setText("Email : " + post.getCreatorEmail());
        classname.setText(post.getClassName());
        instructorname.setText("Instructor : " + post.getInstructor());
        location.setText("Studying at " + post.getLocation());
        if(post.getUndefined())
            time.setText("Until the end of the night");
        else if(post.getTime() <= 12)
            time.setText("Until " + post.getTime() + "am");
        else {
            String thetime = (post.getTime() - 12) + "";
            time.setText("Until " + thetime + "pm");
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
