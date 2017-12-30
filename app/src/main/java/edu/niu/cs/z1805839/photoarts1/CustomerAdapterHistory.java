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
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by KIRAN on 11/27/2017.
 */

public class CustomerAdapterHistory extends ArrayAdapter<History> implements View.OnClickListener {

    private ArrayList<History> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtNumber;
        TextView frame;
        TextView qty;
        TextView price;
        TextView datetime;
        Button reOrder;
        ImageView info;
    }

    public CustomerAdapterHistory( ArrayList<History> dataSet, Context mContext) {
        super(mContext, R.layout.history_item,dataSet);
        this.dataSet = dataSet;
        this.mContext = mContext;
    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        History shoppingCart=(History) object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, " " +shoppingCart.getItemname(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
            case R.id.buttonReorder:
                Snackbar.make(v, " " +shoppingCart.getItemname()+ " has been added to your cart", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        History dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.number);
            viewHolder.qty = (TextView) convertView.findViewById(R.id.qty);
            viewHolder.frame = (TextView) convertView.findViewById(R.id.frame);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.datetime=(TextView) convertView.findViewById(R.id.dateTime);
            viewHolder.reOrder=(Button) convertView.findViewById(R.id.buttonReorder);
            //viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getItemname());
        viewHolder.txtNumber.setText(dataModel.getItemnumber());
        Integer qtyValue = dataModel.getQuantity();
        viewHolder.qty.setText(qtyValue.toString());
        viewHolder.frame.setText(dataModel.getFrame());
        Double priceValue = dataModel.getPrice();
        viewHolder.price.setText(priceValue.toString());
        //viewHolder.datetime.setText(dataModel.getDatetime());
        //viewHolder.txtVersion.setText(dataModel.getVersion_number());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

}
