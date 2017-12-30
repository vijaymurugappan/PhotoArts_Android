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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: MainActivity
//The following class allows a user to login to the PhotoArts+ application
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity {

    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ProgressDialog mProgressDialog;
    private UserDatabaseManager userdbManager;
    public static User user;
    public static ArrayList<HashMap<String, String>> arraylist;
    static String ITEM_NAME = "item_name";
    static String ITEM_NUMBER = "item_number";
    static String LARGE_IMAGE = "large_image";
    static String SMALL_IMAGE = "small_image";
    private EditText username, password;
    public static Integer uID;
    public static ArrayList<User> login;
    private Button loginBtn, registerBtn, btnPwd;
    private static final Integer CODEA = 1;
    private static final String TAG = "Query";

    SharedPreferences preferences;
    String p;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> activityClass;
        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", MainActivity.class.getName()));
        } catch(ClassNotFoundException ex) {
            activityClass = MainActivity.class;
        }
        startActivity(new Intent(this, activityClass));

        setContentView(R.layout.activity_main);
        setTitle("LOGIN");
        userdbManager = new UserDatabaseManager(this);
        username = (EditText)findViewById(R.id.loginName);
        password = (EditText)findViewById(R.id.loginPwd);
        loginBtn = (Button) findViewById(R.id.loginButton);
        registerBtn = (Button) findViewById(R.id.loginRegBtn);
        btnPwd = (Button) findViewById(R.id.buttonpwd);

        preferences = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE);
        p = preferences.getString("key","");
       // Toast.makeText(this, "The name is " +p , Toast.LENGTH_LONG).show();

        login = userdbManager.selectAll();
        Log.d(TAG,login.toString());
        for (int j=0; j < login.size() ; j++) {
            user = login.get(j);
            Log.d(TAG, user.getUsername());
            if ((p.equals(user.getUsername()))) {
                //Toast.makeText(MainActivity.this, "Welcome Back " + user.getUsername(), Toast.LENGTH_SHORT).show();
                uID = user.getId();
               // Toast.makeText(MainActivity.this, "Previous User Id " + uID, Toast.LENGTH_SHORT).show();
                Log.d(TAG, uID.toString());
                break;
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: setOnClickListener
        //The following function navigates from the main activity to Photo Collection activity by authenticating
        //with username and password
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                String name = username.getText().toString();
               // Toast.makeText(getApplicationContext(), " Hello "+name, Toast.LENGTH_SHORT).show();
                editor.putString("key",name);
                editor.commit();
               //ArrayList<User> login = userdbManager.selectAll();
                login = userdbManager.selectAll();
                Log.d(TAG,login.toString());
                for (int j=0; j < login.size() ; j++) {
                    user = login.get(j);
                    Log.d(TAG, user.getUsername());
                    if ((username.getText().toString().equals(user.getUsername()) && (password.getText().toString().equals(user.getPassword())))) {
                        Toast.makeText(MainActivity.this, "Welcome Back "+user.getUsername(), Toast.LENGTH_SHORT).show();
                        uID = user.getId();
                        //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, uID.toString());
                        Intent i = new Intent(MainActivity.this, PhotoCollection.class);
                        startActivity(i);
                        break;
                    }
                    else if((j==login.size()-1)) {
                        Toast.makeText(MainActivity.this, "Wrong Username or password", Toast.LENGTH_LONG).show();
                    }

            }
               //verify
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: setOnClickListener
        //The following function navigates from the main activity to Forgot Pasword activity.
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        btnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: setOnClickListener
        //The following function navigates from the main activity to Registration Page activity.
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegistrationPage.class);
                startActivityForResult(i,CODEA);
            }
        });
        new DownloadJSON().execute();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: OnActivityResult
    //The following function returns a data from the intent
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CODEA)
        {
            if (resultCode == RESULT_OK)
            {
                String name = data.getStringExtra("User");
                Toast.makeText(getApplicationContext(), "User Registered " + name, Toast.LENGTH_LONG).show();
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: Download JSON
    //The following function retreives json from web and parses it storing them in map
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: On Pre Execute
        //The following function sets up a dialog box till the information gets loaded up in the background queue
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android JSON Parsing....");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: Do In Background
        //The following function retreives json from web and parses it storing them in map
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("http://faculty.cs.niu.edu/~krush/ios/photoarts-json");

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("photoarts");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("item_name", jsonobject.getString("item_name"));
                    map.put("item_number", jsonobject.getString("item_number"));
                    map.put("large_image", jsonobject.getString("large_image"));
                    map.put("small_image", jsonobject.getString("small_image"));

                    // Set the JSON Objects into the array
                    arraylist.add(map);

                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: On Post Execute
        //The following function dismisses the dialog box after the information is loaded
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        @Override
        protected void onPostExecute(Void args) {
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: On Pause
    //The following function creates a session where it opens up the last visited page when loaded
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

}
