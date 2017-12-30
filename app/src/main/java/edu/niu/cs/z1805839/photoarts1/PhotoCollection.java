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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: PhotoCollection
//The following class allows a user to select a picture from list of image collection
/////////////////////////////////////////////////////////////////////////////////////////////////////


public class PhotoCollection extends AppCompatActivity {

    private static final int CODEA = 1;
    GridView simpleGrid;
    int logos[] = {R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5,R.drawable.r6,R.drawable.r7, R.drawable.r8, R.drawable.r9,
            R.drawable.r10,R.drawable.r11, R.drawable.r12, R.drawable.r13, R.drawable.r14,R.drawable.r15,R.drawable.r16, R.drawable.r17, R.drawable.r18,
            R.drawable.r19,R.drawable.r20, R.drawable.r21, R.drawable.r22, R.drawable.r23,R.drawable.r24,R.drawable.r25, R.drawable.r26, R.drawable.r27};

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
                    prefs.getString("lastActivity", PhotoCollection.class.getName()));
        } catch(ClassNotFoundException ex) {
            activityClass = PhotoCollection.class;
        }
        //startActivity(new Intent(this, activityClass));



        setTitle("PHOTO COLLECTION");
        setContentView(R.layout.activity_photo_collection);

        simpleGrid = (GridView) findViewById(R.id.photoGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(PhotoCollection.this, PhotoDetails.class);
                intent.putExtra("image", logos[position]); // put image data in Intent
                intent.putExtra("pos", position);
               startActivityForResult(intent,CODEA); // start Intent
               // startActivity(intent);
            }
        });


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: OnCreateOptionsMenu
    //The following function creates menu icons
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onOptionsItemSelected
    //The following function navigates from the main activity to various activities from each menu
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        Intent i = new Intent(getApplicationContext(),ShoppingCart.class);
        startActivity(i);
        return true;*/
        //return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add:
                i = new Intent(this,ShoppingCart.class);
                this.startActivity(i);
                return true;
            case R.id.action_logout:
                i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                return true;
            case R.id.action_updateUserInfo:
                i = new Intent(this, UpdateUserInfo.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        } // end switch

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
                String name = data.getStringExtra("Cart");
                Toast.makeText(getApplicationContext(), "Item added in Cart " + name, Toast.LENGTH_LONG).show();
            }
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
