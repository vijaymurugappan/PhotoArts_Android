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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: PreviousPurchasedItemDetails
//The following class keeps track of the purchase history of a particular user
/////////////////////////////////////////////////////////////////////////////////////////////////////


public class PreviousPurchasedItemDetails extends AppCompatActivity {

    ArrayList<History> ShoppingcCart;
    public static ListView listView;
    private CustomerAdapterHistory adapter;
    private PreviousItemsDatabaseManager previousItemsDatabaseManager;
    private static final String TAG = "Query1";

    public static ArrayList<History> historyDataByID;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previousItemsDatabaseManager = new PreviousItemsDatabaseManager(this);
        setContentView(R.layout.activity_previous_purchased_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PREVIOUS ORDERS");

        listView=(ListView)findViewById(R.id.list);
        updateCart();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: updateCart()
    // The following function displays all the cart data to the cart
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public  void updateCart() {
        Double total = 0.0;
        //ShoppingcCart= new ArrayList<>();
        ArrayList<History> historyData = previousItemsDatabaseManager.selectAll();
        historyDataByID = new ArrayList<>();
        for(int k = 0; k < historyData.size(); k++) {
            History currentCart = historyData.get(k);
            if(currentCart.getUserid() == MainActivity.uID) {
                historyDataByID.add(currentCart);
            }
        }
        Log.d(TAG,historyData.toString());
        for (int j=0; j < historyDataByID.size(); j++) {
            History currentCart = historyDataByID.get(j);
            total = total + currentCart.getPrice();
            //Log.d(TAG, String.valueOf(total));
        }
        //total +=1;
        //totalPrice.setText("$" + total);
        adapter= new CustomerAdapterHistory(historyDataByID,getApplicationContext());
        listView.setAdapter(adapter);

    }

}
