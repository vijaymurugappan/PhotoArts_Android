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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerMessage extends AppCompatActivity {

    private User user;
    private CartDatabaseManager cartDatabaseManager;
    private CustomerAdapterCart adapter;
    private PreviousItemsDatabaseManager previousItemsDatabaseManager;
    private CustomerAdapterHistory historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_message);
        setTitle("Thank You");

        Toast.makeText(this, "Hello "+MainActivity.uID, Toast.LENGTH_SHORT).show();
        cartDatabaseManager = new CartDatabaseManager(this);
        previousItemsDatabaseManager = new PreviousItemsDatabaseManager(this);

       // History history = new History(0,imageNumber.getText().toString(),(resultp.get(MainActivity.ITEM_NAME)),  spinnerFrame.getSelectedItem().toString(), spinnerSize.getSelectedItem().toString(),quantity, price,userID);
       // previousItemsDatabaseManager.insert(history);
        cartDatabaseManager.deleteByUserId(MainActivity.uID);
        Double total = 0.0;
        ArrayList<Cart> cartData = cartDatabaseManager.selectAll();
        ArrayList <Cart>cartList = new ArrayList<>();
        for(int k = 0; k < cartData.size(); k++) {
            Cart currentCart = cartData.get(k);
            if(currentCart.getUserid() == MainActivity.uID) {
                cartList.add(currentCart);
            }
        }

        for (int j=0; j < cartList.size(); j++) {
            Cart currentCart = cartList.get(j);
            total = total + currentCart.getPrice();
            //Log.d(TAG, String.valueOf(total));
        }
        //total +=1;
        ShoppingCart.totalPrice.setText("$" + total);
        adapter= new CustomerAdapterCart(cartList,getApplicationContext());
        ShoppingCart.listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "You have successfully Signed Out", Toast.LENGTH_LONG).show();
        return true;*/
        //return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_home:
                i = new Intent(this,PhotoCollection.class);
                this.startActivity(i);
                return true;
            case R.id.action_history:
                i = new Intent(this, PreviousPurchasedItemDetails.class);
                this.startActivity(i);
                return true;
            case R.id.action_logout:
                i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        } // end switch

    }
}
