package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.samchristensen.studyapp.Create;
import com.samchristensen.studyapp.R;

import Model.School;
import Model.User;


/**
 * Created by Sam on 4/17/15.
 */
public class CreatePopup extends Dialog {

    private User user;
    private School school;
    private Context context;

    public CreatePopup(Context context, User user, School school) {
        super(context);
        this.context = context;
        this.user = user;
        this.school = school;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_popup);

        //UI Elements on each Account Info Popup
        final TextView group, post;

        group = (TextView) findViewById(R.id.choose_group);
        post = (TextView) findViewById(R.id.choose_post);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Create.class);
                intent.putExtra("User", user);
                intent.putExtra("School", school);
                intent.putExtra("Post", false);
                context.startActivity(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Create.class);
                intent.putExtra("User", user);
                intent.putExtra("School", school);
                intent.putExtra("Post", true);
                context.startActivity(intent);
            }
        });

    }
}
