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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaCodec;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: RegistrationPage
//The following class allows a user to fill in their details and create a user database in Sqlite database
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class RegistrationPage extends AppCompatActivity {

    private EditText regUser, regPwd, regCpwd, regSt, regApt, regCity, regState, regZip, regEmail;
    private Button regBtn;
    private UserDatabaseManager userdbManager;
    private String username, password, cpassword, street, apartment, city, state, zip, email;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("REGISTRATION PAGE");

        userdbManager = new UserDatabaseManager(this);
        setContentView(R.layout.activity_registration_page);
        regUser = (EditText)findViewById(R.id.regName);
        regPwd = (EditText)findViewById(R.id.regPwd);
        regCpwd = (EditText)findViewById(R.id.regCpwd);
        regSt = (EditText)findViewById(R.id.regSt);
        regApt = (EditText)findViewById(R.id.regApt);
        regCity = (EditText)findViewById(R.id.regCity);
        regState = (EditText)findViewById(R.id.regState);
        regZip = (EditText)findViewById(R.id.regZip);
        regEmail = (EditText)findViewById(R.id.regEmail);
        regBtn = (Button)findViewById(R.id.regButton);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = regUser.getText().toString();
                password = regPwd.getText().toString();
                cpassword = regCpwd.getText().toString();
                street = regSt.getText().toString();
                apartment = regApt.getText().toString();
                city = regCity.getText().toString();
                state = regState.getText().toString();
                zip = regZip.getText().toString();
                email = regEmail.getText().toString();
                try {
                    if(validUser(username)) {
                        regUser.setBackgroundColor(Color.WHITE);
                        if (password.equals(cpassword)) {
                            regCpwd.setBackgroundColor(Color.WHITE);
                            regUser.setBackgroundColor(Color.WHITE);
                            regPwd.setBackgroundColor(Color.WHITE);
                            regSt.setBackgroundColor(Color.WHITE);
                            regState.setBackgroundColor(Color.WHITE);
                            regCity.setBackgroundColor(Color.WHITE);
                            regCity.setBackgroundColor(Color.WHITE);
                            if (isValidEmail(email) && isValidUsername(username) && isValidPassword(password) && isValidAddress(street) && isValidCity(city) && isValidState(state) && isValidZipCode(zip)) {
                                int zipValue = Integer.parseInt(zip);
                                User user = new User(0, username, password, street, apartment, city, state, zipValue, email);
                                userdbManager.insert(user);
                                Intent i = getIntent();
                                i.putExtra("User", username);
                                setResult(RESULT_OK, i);
                                finish();
                            } else {
                                Toast.makeText(RegistrationPage.this, "Please Enter Valid Details", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegistrationPage.this, "Password did not match", Toast.LENGTH_LONG).show();
                            regCpwd.setBackgroundColor(Color.RED);
                        }
                    }
                    else {
                        regUser.setBackgroundColor(Color.RED);
                        Toast.makeText(RegistrationPage.this, "Sorry! This Username is already in use.Please create another Username", Toast.LENGTH_LONG).show();
                    }


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidEmail()
    //The following function does the validation for email address
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            Toast.makeText(RegistrationPage.this, "Please Enter Valid a Email Address", Toast.LENGTH_LONG).show();
            regEmail.setBackgroundColor(Color.RED);
            return false;
        } else {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
                return true;
            }
            else{
                Toast.makeText(RegistrationPage.this, "Please Enter Valid a Email Address", Toast.LENGTH_LONG).show();
                regEmail.setBackgroundColor(Color.RED);
                return false;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidUsername()
    //The following function does the validation for username
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidUsername(CharSequence target) {
        if (target == null || target.length() < 4) {
            Toast.makeText(RegistrationPage.this, "Please Enter Valid UserName. It should be more than 4 characters", Toast.LENGTH_LONG).show();
            regUser.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidPassword()
    //The following function does the validation for password whether it matches with confirm password
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidPassword(CharSequence target) {
        if (target == null || target.length() < 5) {
            Toast.makeText(RegistrationPage.this, "Please Enter Valid Password It should be more than  5 characters", Toast.LENGTH_LONG).show();
            regPwd.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidAddress()
    //The following function does the validation for address - street
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidAddress(CharSequence target) {
        if (target == null || target.length() < 10) {
            Toast.makeText(RegistrationPage.this, "Please Enter Valid Address. It should be more than  10 characters", Toast.LENGTH_LONG).show();
            regSt.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidCity()
    //The following function does the validation for address - city
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidCity(CharSequence target) {

        if (target == null || target.length() < 4 || (!Pattern.matches("[a-zA-Z]+", target)) ){

            Toast.makeText(RegistrationPage.this, "Please Enter Valid City. It should be more than  4 characters and only alphabets", Toast.LENGTH_LONG).show();
            regCity.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidState()
    //The following function does the validation for address - state
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidState(CharSequence target) {
        if (target == null || target.length() < 1 || (!Pattern.matches("[a-zA-Z]+", target))){

            Toast.makeText(RegistrationPage.this, "Please Enter Valid State. It should be more than  1 character and only alphabets", Toast.LENGTH_LONG).show();
            regState.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidZipCode()
    //The following function does the validation for address - zip code
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidZipCode(CharSequence target) {
        if (target == null || target.length() !=5 || (!Pattern.matches("[0-9]+", target))){

            Toast.makeText(RegistrationPage.this, "Please Enter Valid ZipCode. It should be equal to 5 characters and only numbers", Toast.LENGTH_LONG).show();
            regZip.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: validUser()
    //The following function does the validation for user - whether it already exists in the database or not
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean validUser(CharSequence target) {
        ArrayList<User> login;
        User user;
        login = userdbManager.selectAll();
        //Log.d(TAG,login.toString());
        for (int j=0; j < login.size() ; j++) {
            user = login.get(j);
            //Log.d(TAG, user.getUsername());
            if ((target.equals(user.getUsername()))) {
                Toast.makeText(RegistrationPage.this, "Sorry! This Username is already in use. Please create another Username", Toast.LENGTH_LONG).show();
                regUser.setBackgroundColor(Color.RED);
                return false;
            }
        }

            return true;
    }


}
