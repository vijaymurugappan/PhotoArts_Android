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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by KIRAN on 11/27/2017.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: PreviousItemsDatabaseManagerDatabaseManager
//The following class handles the database for db table - creation,deletion,updation,selection
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class PreviousItemsDatabaseManager extends SQLiteOpenHelper {


    private static final String DATABASENAME = "userDB";
    private static final int DBVERSION = 4;
    private static final String TBLHISTORY = "history";
    private static final String TBLCART = "cart";
    private static final String TBLUSER = "user";
    private static final String HISTORYID = "historyid";
    private static final String USERID = "userid";
    private static final String CARTID = "cartid";
    private static final String ITEMNAME = "itemname";
    private static final String ITEMNUMBER = "itemnumber";
    private static final String FRAME = "frame";
    private static final String SIZE = "size";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String DATETIME = "datetime";
    private static final String TAG = "database1";


    //Constructor
    public PreviousItemsDatabaseManager(Context ctx) {
        super(ctx,DATABASENAME,null,DBVERSION);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates database table
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreate = "create table " +
                TBLHISTORY + " (" + HISTORYID;
        sqlCreate += " integer primary key autoincrement, " + ITEMNUMBER;
        sqlCreate += " text, " + ITEMNAME;
        sqlCreate += " text, " + FRAME;
        sqlCreate += " text, " + SIZE;
        sqlCreate += " text, " + QUANTITY;
        sqlCreate += " number, " + PRICE;
        sqlCreate += " number, " + USERID;
        sqlCreate += " real, " + CARTID;
        sqlCreate += " real, " + DATETIME + " text )";
        // sqlCreate += " integer, " + "FOREIGN KEY (" + USERID + ") REFERENCES " + TBLUSER + "(userid) " + ")";
        //put in a log statement to show the sqlCreate string
        ///Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(PreviousItemsDatabaseManager.DATETIME.toString(), "", Toast.LENGTH_SHORT).show();
        Log.d(TAG,sqlCreate);
        db.execSQL(sqlCreate);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onUpgrade()
    //The following function if the db version upgrades the table will be dropped
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TBLHISTORY);
        onCreate(db);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: insert()
    //The following function inserts data values into the database
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void insert(History c) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TBLHISTORY;
        sqlInsert += " values( null, '" + c.getItemnumber();
        sqlInsert += "','" + c.getItemname();
        sqlInsert += "','" + c.getFrame();
        sqlInsert += "','" + c.getSize();
        sqlInsert += "','" + c.getQuantity();
        sqlInsert += "','" + c.getPrice();
        sqlInsert += "','" + c.getUserid();
        sqlInsert += "','" + c.getCartid();
        sqlInsert += "','" + c.getDatetime() + "')";
        db.execSQL(sqlInsert);
        db.close();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: selectAll()
    //The following function selects all the data values from the database
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<History> selectAll() {
        String sqlQuery = "select * from " + TBLHISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(sqlQuery, null);
        ArrayList<History> historyData = new ArrayList<>();
        while (c.moveToNext()) {

            History currentData = new History(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3), c.getString(4), Integer.parseInt(c.getString(5)), Double.parseDouble(c.getString(6)),
                    Integer.parseInt(c.getString(7)),Integer.parseInt(c.getString(8)), c.getString(9));
            //Cart currentData = new Cart(Integer.parseInt(c.getString(0)),Integer.parseInt(c.getString(1)),Integer.parseInt(c.getString(2)),c.getString(3),c.getString(4),c.getString(5),c.getString(6),Double.parseDouble(c.getString(7)));
            Log.d("cart", "hi");

            historyData.add(currentData);
        }
        db.close();
        Log.d("cart",historyData.toString());
        return historyData;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: deleteById()
    //The following function deletes data values from the database by ID
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteById(int id) {
        String sqlDelete = "delete from " + TBLHISTORY + " where " + CARTID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }
}

