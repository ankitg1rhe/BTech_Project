package com.android.iitfriends.bas.Orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getName() ;
    public static final String ORDER_TABLE = "response";
    public static final String JSON_RESPONSE = "json_response";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    public static void saveData(String response, Context context){
        OrderDbHelper mDbHelper = new OrderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(ORDER_TABLE, null, null);
        // Create a ContentValues object where column names are the keys,
        // and login attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(JSON_RESPONSE, response);
        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(ORDER_TABLE, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(context, "Error while getting info", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<Order> orderDataFromResponse(String response){

        List<Order> orders = new ArrayList<Order>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray orderArray = root.getJSONArray("orders");

            for(int i=orderArray.length()-1 ; i>=0 ; i--){
                JSONObject obj = (JSONObject) orderArray.getJSONObject(i);
                int orderNumber = obj.getInt("number");
                int autoID = obj.getInt("auto_id");
                int mSource = obj.getInt("source");
                int mDestination = obj.getInt("destination");
                int noOfCustomer = obj.getInt("noofcust");
                int mStatus = obj.getInt("status");
                String regDate = obj.getString("reg_date");
                orders.add(new Order(orderNumber, autoID, mSource, mDestination, noOfCustomer, mStatus, regDate)) ;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return (ArrayList<Order>) orders;
    }
}
