package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samchristensen.studyapp.R;

import Model.Post;
import Model.User;

/**
 * Created by Sam on 4/17/15.
 */
public class CreatePostPopup extends Dialog {

    private  EditText classname, instructorname, location;
    private Button cancel, create;
    private User user;

    public CreatePostPopup(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.createpost_popup);

        classname = (EditText) findViewById(R.id.createpost_nameofclass);
        instructorname = (EditText) findViewById(R.id.createpost_instructor);
        location = (EditText) findViewById(R.id.createpost_location);

        cancel = (Button) findViewById(R.id.createpost_cancel);
        create = (Button) findViewById(R.id.createpost_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(classname.getText().toString(), instructorname.getText().toString(),
                        location.getText().toString(), 13, false, user);
                Toast.makeText(getContext(), "Post Created!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
