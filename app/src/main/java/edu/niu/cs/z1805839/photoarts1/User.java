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

/**
 * Created by KIRAN on 11/17/2017.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: User
//The following class is model for the user information
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class User {
    private int id;
    private String username, password, street, apartment, city, state, email;
    private Integer zip;

    //Constructor
    public User(int id, String username, String password, String street, String apartment, String city, String state, Integer zip, String email) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setStreet(street);
        setApartment(apartment);
        setCity(city);
        setState(state);
        setZip(zip);
        setEmail(email);
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Class: toString
    //The following class converts all the attributes format to string
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        return id + " " + username + " " + password + " " + street + " " + apartment + " " + city + " " + state + " " + zip + " " + email;
    }
}
