package com.samchristensen.studyapp.Popups;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.samchristensen.studyapp.R;

import Model.User;


/**
 * Created by Sam on 4/17/15.
 */
public class AccountInfoPopup extends Dialog {

    private final User user;
    private EditText firstname, lastname, wiscmail, password;
    private Button save;

    public AccountInfoPopup(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.accountinfo_popup);

        firstname = (EditText) findViewById(R.id.accountinfo_firstname);
        lastname = (EditText) findViewById(R.id.accountinfo_lastname);
        wiscmail = (EditText) findViewById(R.id.accountinfo_wiscmail);
        password = (EditText) findViewById(R.id.accountinfo_password);
        save = (Button) findViewById(R.id.accountinfo_save);

        firstname.setText(user.getFirstName());
        lastname.setText(user.getLastName());
        wiscmail.setText(user.getEmail());
        String passwordLength = "";
        for(int i = 0; i < user.getPassword().length(); i++)
            passwordLength += "*";
        password.setText(passwordLength);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFirstName(firstname.getText().toString());
                user.setLastName(lastname.getText().toString());
                user.setPassword(password.getText().toString());
                Toast.makeText(getContext(), "Saved Account Changes", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

    }
}
