/* ******************************************************************
Class:     CSCI 522
Program:   Graduate Project
Author:    KiranKumar Reddy, Baitapalli
           Subbiah, Vijay Murugappan
Z-number:  Z1805839
           Z1807314
Date Due:  12/04/2017

Purpose:   The purpose of PhotoArts+ is that user can purchase photos
           from a collection of photos and added purchase of frame for
           that particular photo is included. User once checks out can
           review previous orders.
*********************************************************************/

package edu.niu.cs.z1805839.photoarts1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: ForgotPassword
//The following class prompts for user email address to send both username and password to the user mail
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class ForgotPassword extends AppCompatActivity {

    public static ArrayList<User> login;
    private UserDatabaseManager userdbManager;
    private User user;
    public static Integer uID;
    private static final String TAG = "Query2";

    private EditText email;
    private Button submit;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("FORGOT USERNAME/PASSWORD");
        userdbManager = new UserDatabaseManager(this);

        email = (EditText)findViewById(R.id.checkEmail);
        submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = userdbManager.selectAll();
                Log.d(TAG,login.toString());
                for (int j=0; j < login.size() ; j++) {
                    user = login.get(j);
                    Log.d(TAG, user.getUsername());
                    if ((email.getText().toString().equals(user.getEmail()))) {
                        Toast.makeText(ForgotPassword.this, "UserName is "+user.getUsername(), Toast.LENGTH_SHORT).show();
                        uID = user.getId();
                        //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, uID.toString()+"Hello");

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        sendEmail();
                        break;
                    }
                    else if((j==login.size()-1)) {
                        Toast.makeText(ForgotPassword.this, "Sorry! The Email Address is not associated with your account", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: sendEmail()
    //The following function sends confirmation email with the summary details to the user
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {user.getEmail()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Login Credentials");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"UserName : "+"\t"+user.getUsername()+"\n"
                +"Password : "+"\t"+user.getPassword()
                );


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Toast.makeText(this,"Your Login Credentials will be sent to your mail",Toast.LENGTH_LONG).show();
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForgotPassword.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
