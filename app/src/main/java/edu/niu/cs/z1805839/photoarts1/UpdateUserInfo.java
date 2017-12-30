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
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: UpdateUserInfo
//The following class allows a user to edit his user information and update the data to Sqlite database
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class UpdateUserInfo extends AppCompatActivity {

    private EditText regUser, regPwd, regSt, regApt, regCity, regState, regZip, regEmail;
    private Button regBtn;
    private UserDatabaseManager userdbManager;
    private String username, password,street, apartment, city, state, zip, email;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        userdbManager = new UserDatabaseManager(this);
        regUser = (EditText)findViewById(R.id.regName);
        regPwd = (EditText)findViewById(R.id.regPwd);
        regSt = (EditText)findViewById(R.id.regSt);
        regApt = (EditText)findViewById(R.id.regApt);
        regCity = (EditText)findViewById(R.id.regCity);
        regState = (EditText)findViewById(R.id.regState);
        regZip = (EditText)findViewById(R.id.regZip);
        regEmail = (EditText)findViewById(R.id.regEmail);
        regBtn = (Button)findViewById(R.id.updateButton);
        // setting existing user information to text fields
        regUser.setText(MainActivity.user.getUsername());
        regPwd.setText(MainActivity.user.getPassword());
        regSt.setText(MainActivity.user.getStreet());
        regApt.setText(MainActivity.user.getApartment());
        regCity.setText(MainActivity.user.getCity());
        regState.setText(MainActivity.user.getState());
        regZip.setText(MainActivity.user.getZip().toString());
        regEmail.setText(MainActivity.user.getEmail());

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUser.getText().toString();
                password = regPwd.getText().toString();
                street = regSt.getText().toString();
                apartment = regApt.getText().toString();
                city = regCity.getText().toString();
                state = regState.getText().toString();
                zip = regZip.getText().toString();
                email = regEmail.getText().toString();

                try {
                    userdbManager.updateById(MainActivity.uID,username,password,street,apartment,city,state,Integer.parseInt(zip),email);
                    Toast.makeText(UpdateUserInfo.this, "User Information Updated", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
