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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: ShoppingCart
//The following class allows a user to select frame and quantity for a picture and adding it to cart
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShoppingCart extends AppCompatActivity {

    ArrayList<Cart> ShoppingcCart;
    public static ListView listView;
    //private Cart cart;
     private CustomerAdapterCart adapter;
    private CartDatabaseManager cartDatabaseManager;
    private static final String TAG = "Query1";
    public static TextView totalPrice;
    private Button checkOut;

    public static ArrayList<Cart> cartDataByID;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        cartDatabaseManager = new CartDatabaseManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("SHOPPING CART");

        totalPrice = (TextView)findViewById(R.id.textView2);
        checkOut = (Button) findViewById(R.id.buttonCheckOut);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Checkout.class);
                startActivity(i);
            }
        });
        listView=(ListView)findViewById(R.id.list);
        updateCart();

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
        inflater.inflate(R.menu.menu_delete,menu);
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: OnOptionsItemSelected
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add:
                i = new Intent(this,PhotoCollection.class);
                this.startActivity(i);
                return true;
            case R.id.action_delete:
                i = new Intent(this, DeleteCartItems.class);
                this.startActivity(i);
                updateCart();
                return true;
            case R.id.action_logout:
                i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        } // end switch

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: updateCart()
    // The following function displays all the cart data to the cart
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public  void updateCart() {
        Double total = 0.0;
        //ShoppingcCart= new ArrayList<>();
        ArrayList<Cart> cartData = cartDatabaseManager.selectAll();
        cartDataByID = new ArrayList<>();
        for(int k = 0; k < cartData.size(); k++) {
            Cart currentCart = cartData.get(k);
            if(currentCart.getUserid() == MainActivity.uID) {
                cartDataByID.add(currentCart);
            }
        }
        Log.d(TAG,cartData.toString());
        for (int j=0; j < cartDataByID.size(); j++) {
            Cart currentCart = cartDataByID.get(j);
            total = total + currentCart.getPrice();
            //Log.d(TAG, String.valueOf(total));
        }
        //total +=1;
        totalPrice.setText("$" + total);
        adapter= new CustomerAdapterCart(cartDataByID,getApplicationContext());
        listView.setAdapter(adapter);

    }

}
