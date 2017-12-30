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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.regex.Pattern;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: Checkout
//The following class allows a user to fill in their details, select shipping method, card information
//and checkout
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class Checkout extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    public static double shipFee=0.00;
    User user ;
    public static EditText email,shipStreet,shipApt,shipCity,shipState,shipZipCode,billStreet,billApt,billCity,billState,billZipCode;
    public static EditText firstName,lastName,cardNumber,expiryDate,cvv;
    private Switch shipDetails;
    private Button confirmPayment;
    public static int shipId = 0;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setTitle("CHECKOUT");

        email = (EditText) findViewById(R.id.editTextEmail);
        shipStreet = (EditText) findViewById(R.id.editTextShipStreet);
        shipApt = (EditText) findViewById(R.id.editTextShipApartment);
        shipCity = (EditText) findViewById(R.id.editTextShipCity);
        shipState = (EditText) findViewById(R.id.editTextShipState);
        shipZipCode = (EditText) findViewById(R.id.editTextShipZipCode);
        billStreet = (EditText) findViewById(R.id.editTextBillStreet);
        billApt = (EditText) findViewById(R.id.editTextBillApartment);
        billCity = (EditText) findViewById(R.id.editTextBillCity);
        billState = (EditText) findViewById(R.id.editTextBillState);
        billZipCode = (EditText) findViewById(R.id.editTextBillZipCode);
        firstName = (EditText) findViewById(R.id.editTextFirstName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        cardNumber = (EditText) findViewById(R.id.editTextCardNumber);
        expiryDate = (EditText) findViewById(R.id.editTextExpDate);
        cvv = (EditText) findViewById(R.id.editTextCVV);
        confirmPayment = (Button) findViewById(R.id.buttonConfirmPayment);
        shipDetails = (Switch) findViewById(R.id.switchInform);

        fillDetails();
        shipDetails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    user = MainActivity.login.get(MainActivity.uID-1);
                    billStreet.setText(shipStreet.getText().toString());
                    billApt.setText(shipApt.getText().toString());
                    billCity.setText(shipCity.getText().toString());
                    billState.setText(shipState.getText().toString());
                    billZipCode.setText(shipZipCode.getText().toString());
                }
                else {
                    user = MainActivity.login.get(MainActivity.uID-1);
                    billStreet.setText("");
                    billApt.setText("");
                    billCity.setText("");
                    billState.setText("");
                    billZipCode.setText("");
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerShipFee);
        ArrayAdapter<CharSequence> shippingFee = ArrayAdapter.createFromResource(this,R.array.shipFee,android.R.layout.simple_spinner_item);
        shippingFee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(shippingFee);
        spinner.setOnItemSelectedListener(this);
       // Toast.makeText(this, ""+MainActivity.uID, Toast.LENGTH_LONG).show();

        confirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAddress() && validateCardDetails()) {
                    Intent i = new Intent(getApplicationContext(), CheckoutSummary.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Checkout.this, "Please fill the correct Details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onItemSelected
    //The following function is called when an item is selected from the spinner and assigns shipping fee
    //accordingly
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"The Shipping Fee you selected is" + parent.getItemAtPosition(position)+" ",Toast.LENGTH_LONG).show();
        if(id == 0) {
            shipFee = 19.95;
        }
        else if(id == 1) {
            shipFee = 24.95;

        }
        else if(id == 2) {
            shipFee = 34.95;
        }
        else if(id == 3) {
            shipFee = 50.00;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: fillDetails()
    //The following function allows the user data to be filled in text fields during load screen
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void fillDetails() {
        user = MainActivity.login.get(MainActivity.uID-1);
        shipStreet.setText(user.getStreet().toString());
        shipApt.setText(user.getApartment().toString());
        shipCity.setText(user.getCity().toString());
        shipState.setText(user.getState().toString());
        shipZipCode.setText(user.getZip().toString());
        email.setText(user.getEmail().toString());

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: validateAddress()
    //The following function does the validation for street,city,state,apartment and zip fields
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean validateAddress() {

        shipStreet.setBackgroundColor(Color.WHITE);
        shipCity.setBackgroundColor(Color.WHITE);
        shipState.setBackgroundColor(Color.WHITE);
        shipZipCode.setBackgroundColor(Color.WHITE);

        if(isValidAddress(shipStreet.getText().toString()) && isValidCity(shipCity.getText().toString()) && isValidState(shipState.getText().toString()) && isValidZipCode(shipZipCode.getText().toString())) {
            return true;
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: validateCardDetails()
    //The following function does the validation for card details such as number,expiry date, security code fields
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean validateCardDetails() {

        firstName.setBackgroundColor(Color.WHITE);
        lastName.setBackgroundColor(Color.WHITE);
        cardNumber.setBackgroundColor(Color.WHITE);
        expiryDate.setBackgroundColor(Color.WHITE);
        cvv.setBackgroundColor(Color.WHITE);
        email.setBackgroundColor(Color.WHITE);

        if(isValidUsername(firstName.getText().toString()) && isValidUsername(lastName.getText().toString()) && isValidCardNumber(cardNumber.getText().toString())
                && isValidExpiryDate(expiryDate.getText().toString()) && isValidCvv(cvv.getText().toString()) && isValidEmail(email.getText().toString())) {
            return true;
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidEmail()
    //The following function does the validation for email address
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            Toast.makeText(Checkout.this, "Please Enter Valid a Email Address", Toast.LENGTH_LONG).show();
            email.setBackgroundColor(Color.RED);
            return false;
        } else {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
                return true;
            }
            else{
                Toast.makeText(Checkout.this, "Please Enter Valid a Email Address", Toast.LENGTH_LONG).show();
                email.setBackgroundColor(Color.RED);
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
            Toast.makeText(Checkout.this, "Please Enter Valid UserName. It should be more than 4 characters", Toast.LENGTH_LONG).show();
            firstName.setBackgroundColor(Color.RED);
            lastName.setBackgroundColor(Color.RED);
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
            Toast.makeText(Checkout.this, "Please Enter Valid Address. It should be more than  10 characters", Toast.LENGTH_LONG).show();
            shipStreet.setBackgroundColor(Color.RED);
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

            Toast.makeText(Checkout.this, "Please Enter Valid City. It should be more than  4 characters and only alphabets", Toast.LENGTH_LONG).show();
            shipCity.setBackgroundColor(Color.RED);
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

            Toast.makeText(Checkout.this, "Please Enter Valid State. It should be more than  1 character and only alphabets", Toast.LENGTH_LONG).show();
            shipState.setBackgroundColor(Color.RED);
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

            Toast.makeText(Checkout.this, "Please Enter Valid ZipCode. It should be equal to 5 digits and only numbers", Toast.LENGTH_LONG).show();
            shipZipCode.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidCardNumber()
    //The following function does the validation for card details - card number
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidCardNumber(CharSequence target) {
        if (target == null || target.length() !=16 || (!Pattern.matches("[0-9]+", target))){

            Toast.makeText(Checkout.this, "Please Enter Valid Card Number. It should be equal to 16 digits and only numbers", Toast.LENGTH_LONG).show();
            cardNumber.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidExpiryDate()
    //The following function does the validation for card details - card expiry date
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidExpiryDate(CharSequence target) {
        if (target == null || target.length() !=7 || (!Pattern.matches("[0-1]+", target.subSequence(0,1))) || (!Pattern.matches("[0-9]+", target.subSequence(1,2)))
                || (!Pattern.matches("[-]+", target.subSequence(2,3))) || (!Pattern.matches("[2]+", target.subSequence(3,4))) ||
                (!Pattern.matches("[0]+", target.subSequence(4,5))) || (!Pattern.matches("[2]+", target.subSequence(5,6))) ||
                (!Pattern.matches("[0-9]+", target.subSequence(0,1)))) {

            Toast.makeText(Checkout.this, "Please Enter Valid Card Expiry Date", Toast.LENGTH_LONG).show();
            expiryDate.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: isValidCVV()
    //The following function does the validation for card details - card security code
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public final  boolean isValidCvv(CharSequence target) {
        if (target == null || (target.length() < 3 || target.length() > 4) || (!Pattern.matches("[0-9]+", target))){
            Toast.makeText(Checkout.this, "Please Enter Valid CVV Number", Toast.LENGTH_LONG).show();
            cvv.setBackgroundColor(Color.RED);
            return false;
        }
        else {
            return true;
        }
    }



}
