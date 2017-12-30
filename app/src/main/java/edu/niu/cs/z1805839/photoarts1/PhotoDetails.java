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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: PhotoDetails
//The following class allows a user to select frame and quantity for a picture and adding it to cart
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class PhotoDetails extends AppCompatActivity   {

    private TextView price, imageNumber;
    private EditText qty;
    int[] noFramePrice = {24,35,50,76,97,164,230};
    int[] FramePrice = {29,46,89,158,189,365,570};
    int totalprice = 0;
    private ArrayList<HashMap<String, String>> data;
    private HashMap<String, String> resultp = new HashMap<String, String>();
    ImageView selectedImage;
    private Spinner spinnerFrame;
    private Spinner spinnerSize;
    private CartDatabaseManager cartDatabaseManager;
    private PreviousItemsDatabaseManager previousItemsDatabaseManager;
    private Button cartBtn;
    private static final String TAG = "database2";
    int q = 0;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates reference to components in user interface within OnCreate()
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_details);
        cartDatabaseManager = new CartDatabaseManager(this);
        previousItemsDatabaseManager = new PreviousItemsDatabaseManager(this);
        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        selectedImage.setImageResource(intent.getIntExtra("image", 0)); // get image from Intent and set it
        data = MainActivity.arraylist;
        resultp = data.get(intent.getIntExtra("pos", 0));
        setTitle(resultp.get(MainActivity.ITEM_NAME));
        cartBtn = (Button) findViewById(R.id.buttonCheckOut);
        price = (TextView) findViewById(R.id.textViewPrice);
        qty = (EditText) findViewById(R.id.editTextQty);
        spinnerFrame = (Spinner) findViewById(R.id.spinnerFrame);
        spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        qty.addTextChangedListener(qtyTextWatcher);
        imageNumber = (TextView)findViewById(R.id.textView4);
        imageNumber.setText(resultp.get(MainActivity.ITEM_NUMBER));

        ArrayAdapter<CharSequence> Frame = ArrayAdapter.createFromResource(this,R.array.FrameColor,android.R.layout.simple_spinner_item);
        Frame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrame.setAdapter(Frame);

        spinnerFrame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //setting frame color as the spinner is getting changed.
                RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.colorView);
                if (id == 0) {
                    relativeLayout.setBackgroundColor(Color.WHITE);
                }
                else if (id == 1) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.MatteBlack));
                }
                else if (id == 2) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.MatteWhite));
                }
                else if (id == 3) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.BrushedSilver));
                }
                else if (id == 4) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.MatteBrass));
                }
                else if (id == 5) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.LightGrey));
                }
                else if (id == 6) {
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.WoodFrame));
                }
                checkFrameSizePrice(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        ArrayAdapter<CharSequence> FrameSize = ArrayAdapter.createFromResource(this, R.array.FrameSize, android.R.layout.simple_spinner_item);
        FrameSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(FrameSize);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               checkFrameColorPrice(id); //checking price for the updated product
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // adding the items to the cart using SQLite database
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Log.d(TAG, price.getText().toString().replaceAll("$24.00","1"));
                try {
                    int userID = MainActivity.uID;
                    int quantity = Integer.parseInt(qty.getText().toString());
                    String priceValueTag = price.getText().toString();
                     String priceValue = priceValueTag.substring(1);
                    double price = Double.parseDouble(priceValue);
                    Cart cart = new Cart(0,imageNumber.getText().toString(),(resultp.get(MainActivity.ITEM_NAME)),  spinnerFrame.getSelectedItem().toString(),
                            spinnerSize.getSelectedItem().toString(),quantity, price,userID);
                   String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    //Toast.makeText(PhotoDetails.this, currentDateTimeString, Toast.LENGTH_LONG).show();
                    History history = new History(0,imageNumber.getText().toString(),(resultp.get(MainActivity.ITEM_NAME)),
                            spinnerFrame.getSelectedItem().toString(), spinnerSize.getSelectedItem().toString(),quantity, price,userID,
                            cart.getId(),currentDateTimeString);
                    cartDatabaseManager.insert(cart);
                   previousItemsDatabaseManager.insert(history);
                    Toast.makeText(PhotoDetails.this, "previousItems has been inserted", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PhotoDetails.this, "item added " +userID, Toast.LENGTH_SHORT).show();
                    Intent i = getIntent();
                    i.putExtra("Cart", (resultp.get(MainActivity.ITEM_NAME)));
                    setResult(RESULT_OK, i);
                    finish();
                }
                catch (Exception e) {
                    Log.e("MYAPP", "exception", e);
                   //String str = new String(price.getText().toString());
                    //String s = str.substring(1);
                    Toast.makeText(PhotoDetails.this, "Error!", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: checkFrameColorPrice()
    //The following function displays the price of the framed/unframed photo
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkFrameColorPrice(long id) {
       int frameId = (int) spinnerFrame.getSelectedItemId();
        if(frameId == 0) {
            if (id == 0) {
                totalprice = (noFramePrice[0]);
                displayPrice(totalprice);
            }
            else if ( id == 1) {
                totalprice = (noFramePrice[1]);
                displayPrice(totalprice);
            }
            else if ( id == 2) {
                totalprice = (noFramePrice[2]);
                displayPrice(totalprice);
            }
            else if ( id == 3) {
                totalprice = (noFramePrice[3]);
                displayPrice(totalprice);
            }
            else if ( id == 4) {
                totalprice = (noFramePrice[4]);
                displayPrice(totalprice);
            }
            else if ( id == 5) {
                totalprice = (noFramePrice[5]);
                displayPrice(totalprice);
            }
            else if (id == 6) {
                totalprice = (noFramePrice[6]);
                displayPrice(totalprice);
            }
        }
        else {
            if ( id == 0) {
                totalprice = (FramePrice[0]);
                displayPrice(totalprice);
            }
            else if ( id == 1) {
                totalprice = (FramePrice[1]);
                displayPrice(totalprice);
            }
            else if ( id == 2) {
                totalprice = (FramePrice[2]);
                displayPrice(totalprice);
            }
            else if ( id == 3) {
                totalprice = (FramePrice[3]);
                displayPrice(totalprice);
            }
            else if ( id == 4) {
                totalprice = (FramePrice[4]);
                displayPrice(totalprice);
            }
            else if ( id == 5) {
                totalprice = (FramePrice[5]);
                displayPrice(totalprice);
            }
            else if (id == 6) {
                totalprice = (FramePrice[6]);
                displayPrice(totalprice);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: checkFrameSizePrice()
    //The following function displays the price of the framed/unframed photo with various sizes
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkFrameSizePrice(long frameId) {
        int id = (int) spinnerSize.getSelectedItemId();
        if(frameId == 0) {
            if ( id == 0) {
                totalprice = (noFramePrice[0]);
                displayPrice(totalprice);
            } else if ( id == 1) {
                totalprice = (noFramePrice[1]);
                displayPrice(totalprice);
            } else if ( id == 2) {
                totalprice = (noFramePrice[2]);
                displayPrice(totalprice);
            } else if ( id == 3) {
                totalprice = (noFramePrice[3]);
                displayPrice(totalprice);
            } else if ( id == 4) {
                totalprice = (noFramePrice[4]);
                displayPrice(totalprice);
            } else if ( id == 5) {
                totalprice = (noFramePrice[5]);
                displayPrice(totalprice);
            } else if (id == 6) {
                totalprice = (noFramePrice[6]);
                displayPrice(totalprice);
            }
        }
        else {
            if ( id == 0) {
                totalprice = (FramePrice[0]);
                displayPrice(totalprice);
            } else if ( id == 1) {
                totalprice = (FramePrice[1]);
                displayPrice(totalprice);
            } else if ( id == 2) {
                totalprice = (FramePrice[2]);
                displayPrice(totalprice);
            } else if ( id == 3) {
                totalprice = (FramePrice[3]);
                displayPrice(totalprice);
            } else if ( id == 4) {
                totalprice = (FramePrice[4]);
                displayPrice(totalprice);
            } else if ( id == 5) {
                totalprice = (FramePrice[5]);
                displayPrice(totalprice);
            } else if (id == 6) {
                totalprice = (FramePrice[6]);
                displayPrice(totalprice);
            }
        }
    }

    private TextWatcher qtyTextWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Function: onTextChanged()
        //The following function changes the price dynamically as the user selects the frame and size
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int total = totalprice;
            try {
                if(s != null) {
                     q = (Integer.parseInt(s.toString()));

                    if (q == 0) {
                        total = 0;
                    } else {
                        total = totalprice * q;
                       // Log.d(TAG, price.getText().toString().replaceAll("$24.00","1"));
                    }
                }
                else {
                    displayQtyPrice(total);
                }
            }
            catch (NumberFormatException nfe) {
                displayQtyPrice(total);
            }
            displayQtyPrice(total);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: displayPrice()
    //The following function displays the price in currency format
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayPrice(int total) {
        double d = total*q;
        price.setText("$" + String.format("%.02f",d));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: displayQtyPrice()
    //The following function displays the price in currency format
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void displayQtyPrice(int total) {
        double d = total;
        price.setText("$" + String.format("%.02f",d));
    }



}
