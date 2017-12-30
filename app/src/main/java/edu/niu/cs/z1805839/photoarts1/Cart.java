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
 * Created by KIRAN on 11/24/2017.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////
//Class: Cart
//The following class is model for the cart items
/////////////////////////////////////////////////////////////////////////////////////////////////////

public class Cart {
    private int id,userid,quantity;
    private String itemname, itemnumber, frame, size;
    private double price;

    //Constructor
    public Cart(int id, String itemnumber, String itemname, String frame, String size,int quantity, double price,int userid) {
        setId(id);
        setUserid(userid);
        setQuantity(quantity);
        setItemname(itemname);
        setItemnumber(itemnumber);
        setFrame(frame);
        setSize(size);
        setPrice(price);
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Class: toString
    //The following class converts all the attributes format to string
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return id + " " + itemnumber + " " + itemname + " " + frame + " " + size + " " + quantity + " " + price + " " + userid;
        /*return "Cart{" +
                "id=" + id +
                ", userid=" + userid +
                ", quantity=" + quantity +
                ", itemname='" + itemname + '\'' +
                ", itemnumber='" + itemnumber + '\'' +
                ", frame='" + frame + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';*/
    }
}
