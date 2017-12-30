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

import java.util.ArrayList;

/**
 * Created by KIRAN on 11/17/2017.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: UserDatabaseManager
//The following class handles the database for db table - creation,deletion,updation,selection
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class UserDatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASENAME = "userDB";
    private static final int DBVERSION = 4;
    private static final String TBLUSER = "user";
    private static final String USERID = "userid";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String STREET = "street";
    private static final String APARTMENT = "apartment";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String EMAIL = "email";
    private static final String ZIP = "zip";
    private static final String TAG = "database";

    //Constructor
    public UserDatabaseManager(Context ctx) {
        super(ctx,DATABASENAME,null,DBVERSION);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onCreate()
    //The following function creates database table
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " +
                TBLUSER + " (" + USERID;
        sqlCreate += " integer primary key autoincrement, " + USERNAME;
        sqlCreate += " text, " + PASSWORD;
        sqlCreate += " text, " + STREET;
        sqlCreate += " text, " + APARTMENT;
        sqlCreate += " text, " + CITY;
        sqlCreate += " text, " + STATE;
        sqlCreate += " text, " + ZIP;
        sqlCreate += " number, " + EMAIL + " text )";
        //put in a log statement to show the sqlCreate string
        Log.d(TAG,sqlCreate);
        db.execSQL(sqlCreate);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: updateById()
    //The following function updates the database by id
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateById(int userId, String userName,String password,String street,String apartment,String city,String state,int zipcode,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateSQL = "update " + TBLUSER;
        updateSQL += " set " + USERNAME + " = '" + userName + "', ";
        updateSQL += PASSWORD + " = '" + password + "', ";
        updateSQL += STREET + " = '" + street + "', ";
        updateSQL += APARTMENT + " = '" + apartment + "', ";
        updateSQL += CITY + " = '" + city + "', ";
        updateSQL += STATE + " = '" + state + "', ";
        updateSQL += ZIP + " = '" + zipcode + "', ";
        updateSQL += EMAIL + " = '" + email + "'";
        updateSQL += " where " + USERID + " = " + userId;
        db.execSQL(updateSQL);
        db.close();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: onUpgrade()
    //The following function if the db version upgrades the table will be dropped
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TBLUSER);
        onCreate(db);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: insert()
    //The following function inserts data values into the database
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void insert(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TBLUSER;
        sqlInsert += " values( null, '" + u.getUsername();
        sqlInsert += "','" + u.getPassword();
        sqlInsert += "','" + u.getStreet();
        sqlInsert += "','" + u.getApartment();
        sqlInsert += "','" + u.getCity();
        sqlInsert += "','" + u.getState();
        sqlInsert += "','" + u.getZip();
        sqlInsert += "','" + u.getEmail() + "')";
        db.execSQL(sqlInsert);
        db.close();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: selectAll()
    //The following function selects all the data values from the database
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<User> selectAll() {
        String sqlQuery = "select * from " + TBLUSER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(sqlQuery,null);
        ArrayList<User> userData = new ArrayList<>();
        while (c.moveToNext()) {
            User currentUser = new User(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2),c.getString(3),c.getString(4),
            c.getString(5),c.getString(6),Integer.parseInt(c.getString(7)),c.getString(8));
            userData.add(currentUser);
        }
        db.close();
        return userData;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Function: selectIDByName()
    //The following function selects data values from the database by ID
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public int selectIDByName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "select " + USERID + " from " + TBLUSER;
        sqlQuery += " where " + USERNAME + " = " + username;
        Cursor c = db.rawQuery(sqlQuery,null);
        Integer userid = 0;
        if(c.moveToFirst()) {
            userid = Integer.parseInt(c.getString(0));
        }
        db.close();
        return userid;
    }



}
