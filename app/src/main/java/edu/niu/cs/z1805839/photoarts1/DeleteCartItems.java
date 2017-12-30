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

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: DeleteCartItems
//The following class allows a user to delete cart items
/////////////////////////////////////////////////////////////////////////////////////////////////////


public class DeleteCartItems extends AppCompatActivity {

    private CustomerAdapterCart adapter;
    private CartDatabaseManager cartDatabaseManager;
    private PreviousItemsDatabaseManager previousItemsDatabaseManager;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_delete_cart_items);
        setTitle("DELETE CART ITEMS");
        cartDatabaseManager = new CartDatabaseManager(this);
        previousItemsDatabaseManager = new PreviousItemsDatabaseManager(this);
        updateView();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: updateView()
    //The following function selects and displays all the cart items
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateView()
    {
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
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);
        for(Cart cart:cartList) {
            RadioButton rb = new RadioButton(this);
            rb.setId(cart.getId());
            rb.setText(cart.toString());
            group.addView(rb);
        }// end for
        RadioButtonHandler rbh = new RadioButtonHandler();
        group.setOnCheckedChangeListener(rbh);
        Button backBtn = new Button(this);
        backBtn.setText("BACK");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteCartItems.this.finish();

            }
        }

        );
        scrollView.addView(group);
        layout.addView(scrollView);
        //add the button to the bottom
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0,0,0,50);
        layout.addView(backBtn,params);
        setContentView(layout);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: RadioButtonHandler()
    //The following function deletes from cart database using cart id
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
            cartDatabaseManager.deleteById(checkedId);
            previousItemsDatabaseManager.deleteById(checkedId);
            Toast.makeText(DeleteCartItems.this, "Photo Item is deleted", Toast.LENGTH_LONG).show();
            updateView();
        }
    }
}
