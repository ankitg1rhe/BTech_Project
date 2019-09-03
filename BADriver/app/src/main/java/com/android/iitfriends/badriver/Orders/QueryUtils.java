package com.android.iitfriends.badriver.Orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String CONFIRM_ORDER_TABLE = "confirmordertable";
    public static final String CONFIRM_ORDER_DATA = "confirmorderdata";

    public static final String DATA_ORDER_TABLE = "dataordertable";
    public static final String DATA_ORDER_DATA = "dataorderdata";

    public static void saveConfirmOrderData(String response, Context context){
        ConfirmOrderDbHelper cDbHelper = new ConfirmOrderDbHelper(context);
        SQLiteDatabase db = cDbHelper.getWritableDatabase();
        db.delete(CONFIRM_ORDER_TABLE, null, null);

        ContentValues values = new ContentValues();
        values.put(CONFIRM_ORDER_DATA, response);
        long newRowId = db.insert(CONFIRM_ORDER_TABLE, null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error while fetching data", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveDataOrderData(String response, Context context){
        DataOrderDbHelper cDbHelper = new DataOrderDbHelper(context);
        SQLiteDatabase db = cDbHelper.getWritableDatabase();
        db.delete(DATA_ORDER_TABLE, null, null);

        ContentValues values = new ContentValues();
        values.put(DATA_ORDER_DATA, response);
        long newRowId = db.insert(DATA_ORDER_TABLE, null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error while fetching data", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<Order> orderDataFromResponse(String response, Context context){

        List<Order> orders = new ArrayList<Order>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray orderArray = root.getJSONArray("orders");

            for(int i=orderArray.length()-1 ; i>=0 ; i--){
                JSONObject obj = (JSONObject) orderArray.getJSONObject(i);
                int orderNumber = obj.getInt("number");
                int mSource = obj.getInt("source");
                int mDestination = obj.getInt("destination");
                int noOfCustomer = obj.getInt("noofcust");
                String regDate = obj.getString("reg_date");
                String mCustomer = obj.getString("customer");
                orders.add(new Order(orderNumber, mSource, mDestination, noOfCustomer, regDate, mCustomer)) ;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return (ArrayList<Order>) orders;
    }
}
