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
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: CheckoutSummary
//The following class allows a user to view their details, shipping method, card information
//and checkout
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class CheckoutSummary extends AppCompatActivity {

    private TextView shipStreet,shipApt,shipCity,shipState,shipZipCode;
    private TextView billStreet,billApt,billCity,billState,billZipCode;
    private TextView productCost,shippingFee,tax,total;
    private TextView firstName,lastName,cardNumber,expiryDate,cvv;
    private TextView email;
    private Button payment;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_summary);
        setTitle("CHECKOUT SUMMARY");

        shipStreet = (TextView)findViewById(R.id.shipstreet);
        shipApt = (TextView)findViewById(R.id.shipApartment);
        shipCity = (TextView)findViewById(R.id.shipCity);
        shipState = (TextView)findViewById(R.id.shipState);
        shipZipCode = (TextView)findViewById(R.id.shipZipCode);
        billStreet = (TextView)findViewById(R.id.billstreet);
        billApt = (TextView)findViewById(R.id.billApartment);
        billCity = (TextView)findViewById(R.id.billCity);
        billState = (TextView)findViewById(R.id.billState);
        billZipCode = (TextView)findViewById(R.id.billZipCode);
        productCost = (TextView)findViewById(R.id.textViewProductCost);
        shippingFee = (TextView)findViewById(R.id.textViewShippingFee);
        tax = (TextView)findViewById(R.id.textViewTax);
        total = (TextView)findViewById(R.id.textViewGrandTotal);
        firstName = (TextView)findViewById(R.id.firstName);
        lastName = (TextView)findViewById(R.id.lastName);
        cardNumber = (TextView)findViewById(R.id.cardNumber);
        expiryDate = (TextView)findViewById(R.id.expiryDate);
        cvv = (TextView)findViewById(R.id.cvv);
        email = (TextView)findViewById(R.id.emailaddress);
        payment = (Button) findViewById(R.id.purchase);

        shipStreet.setText(Checkout.shipStreet.getText().toString());
        shipApt.setText(Checkout.shipApt.getText().toString());
        shipCity.setText(Checkout.shipCity.getText().toString());
        shipState.setText(Checkout.shipState.getText().toString());
        shipZipCode.setText(Checkout.shipZipCode.getText().toString());
        billStreet.setText(Checkout.billStreet.getText().toString());
        billApt.setText(Checkout.billApt.getText().toString());
        billCity.setText(Checkout.billCity.getText().toString());
        billState.setText(Checkout.billState.getText().toString());
        billZipCode.setText(Checkout.billZipCode.getText().toString());
        productCost.setText(ShoppingCart.totalPrice.getText().toString()+"0");
        String pCostString  = ShoppingCart.totalPrice.getText().toString();
        String pCostStr = pCostString.substring(1);
        double pCost = Double.parseDouble(pCostStr);
       shippingFee.setText("$"+String.format("%.02f",Double.parseDouble(String.valueOf((Checkout.shipFee)))));
        double totalCost = pCost + Double.parseDouble(String.valueOf((Checkout.shipFee)))+ 3.50;
        total.setText("$"+String.format("%.02f",totalCost));
        firstName.setText(Checkout.firstName.getText().toString());
        lastName.setText(Checkout.lastName.getText());
        cardNumber.setText(Checkout.cardNumber.getText());
        expiryDate.setText(Checkout.expiryDate.getText().toString());
        cvv.setText(Checkout.cvv.getText().toString());
        email.setText(Checkout.email.getText().toString());

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),CustomerMessage.class);
                startActivity(i);
                sendEmail();
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: sendEmail()
    //The following function sends confirmation email with the summary details to the user
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {Checkout.email.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Item Purchase Details");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Hi "+Checkout.firstName.getText().toString()+" "+Checkout.lastName.getText()+" ! "+ "Your Item Purchase Details" +"\n"+"\n"+"\n"
                +"SHIPPING ADDRESS :"+"\n"
                +Checkout.shipStreet.getText().toString()+"\t"+Checkout.shipApt.getText().toString()+"\n"
                +Checkout.shipCity.getText().toString()+"\t"+Checkout.shipState.getText().toString()+"\t"+Checkout.shipZipCode.getText().toString()+"\n"+"\n"
                +"BILLING ADDRESS :"+"\n"
                +Checkout.billStreet.getText().toString()+"\t"+Checkout.billApt.getText().toString()+"\n"
                +Checkout.billCity.getText().toString()+"\t"+Checkout.billState.getText().toString()+"\t"+Checkout.billZipCode.getText().toString()+"\n"+"\n"
                +"SHIPPING STATUS :"+"\n"
                +"Cost of Product"+"\t"+":"+"\t"+ShoppingCart.totalPrice.getText().toString()+"0"+"\n"
               +"Shipping Fee   "+"\t"+":"+"\t"+"$"+Double.parseDouble(String.valueOf((Checkout.shipFee)))+"0"+"\n"
                +"Tax            "+"\t"+":"+"\t"+"$3.50"+"\n"
                +"Grand Total    "+"\t"+":"+"\t"+total.getText().toString()+"0"+"\n"+"\n"+"\n"+"\n"
                +"Thank you for Shopping.");


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Toast.makeText(this,"Your Item purchase details will be sent to your mail",Toast.LENGTH_LONG).show();
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CheckoutSummary.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
