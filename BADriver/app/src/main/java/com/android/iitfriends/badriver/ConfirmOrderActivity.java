package com.android.iitfriends.badriver;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.badriver.utility.Constants;
import com.android.iitfriends.badriver.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {
    private String reg_date = "";
    private int src = 0, dest = 0;
    private int orderNum = 0, noOfCus = 0;
    private String mCustomer = "";
    private String mobile = "";
    private int autoId = 0;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        progressDialog = new ProgressDialog(this);

        Intent startingIntent = getIntent();
        if(startingIntent.hasExtra(Intent.EXTRA_TEXT)){
            String sentObject = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            setAllValues(sentObject);
        }else {
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
            finish();
        }

        Button confirmOrderButton = (Button) findViewById(R.id.confirm_order_button);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMyDialog();
            }
        });

    }

    public void createMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.builder_message);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setOrderAsConfirm(mobile, String.valueOf(autoId), String.valueOf(src), String.valueOf(dest),
                        String.valueOf(noOfCus), String.valueOf(orderNum), mCustomer);
            }
        });

        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setOrderAsConfirm(final String mobile, final String auto_id, final String source,
                                   final String destination, final String noofcust, final String number, final String customer){
        progressDialog.setMessage("Confirming...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_CONFIRM_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!jsonObject.getBoolean("error")){
                                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("auto_id", auto_id);
                params.put("source", source);
                params.put("destination", destination);
                params.put("noofcust", noofcust);
                params.put("number", number);
                params.put("customer", customer);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setAllValues(String orderResponse){

        try {
            JSONObject jsonObject = new JSONObject(orderResponse);
            reg_date = jsonObject.getString("reg_date");
            mCustomer = jsonObject.getString("customer") ;
            orderNum = jsonObject.getInt("number");
            src = jsonObject.getInt("source");
            dest = jsonObject.getInt("destination");
            noOfCus = jsonObject.getInt("noofcust");
            mobile = jsonObject.getString("mobile");
            autoId = jsonObject.getInt("auto_id");
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }

        String[] arr = reg_date.split(" ");
        String[] tm = arr[1].split(":");
        int hr = Integer.parseInt(tm[0].trim());
        String when = " AM";
        if(hr > 12){
            hr -= 12;
            if(hr > 9) tm[0] = String.valueOf(hr);
            else  tm[0] = "0" + String.valueOf(hr);
            when = " PM";
        }
        reg_date = "दिनांक: " + arr[0].split("-")[2] + "-" + arr[0].split("-")[1]
                + "-" + arr[0].split("-")[0];
        arr[1] = "समय: " + tm[0] + ":" + tm[1] + when;

/*

समय
रामानुजन
विश्वकर्मा
धनराजगिरी
मोर्वी
लिम्बडी
विवेकानंद
स्वतंत्रता भवन
विश्वनाथ मंदिर
स्वास्थ्य संकुल
मुख्य द्वार or लंका गेट
विश्वेश्वरैया
छात्रावास
सिवी रमन
दिनांक
से तक
प्रवासि संख्या
काॅर्नर
*/

        String srcString = "";
        switch (src){
            case 1:
                srcString = "सिवी रमन छात्रावास";
                break;
            case 2:
                srcString = "धनराजगिरी काॅर्नर";
                break;
            case 3:
                srcString = "स्वास्थ्य संकुल/Health Center";
                break;
            case 4:
                srcString = "लिम्बडी काॅर्नर";
                break;
            case 5:
                srcString = "मोर्वी छात्रावास";
                break;
            case 6:
                srcString = "रामानुजन छात्रावास";
                break;
            case 7:
                srcString = "स्वतंत्रता भवन";
                break;
            case 8:
                srcString = "विश्वकर्मा छात्रावास";
                break;
            case 9:
                srcString = "विवेकानंद छात्रावास";
                break;
            case 10:
                srcString = "लंका, मुख्य द्वार";
                break;
            case 11:
                srcString = "विश्वनाथ मंदिर";
        }

        String destString = "";
        switch (dest){
            case 1:
                destString = "सिवी रमन छात्रावास";
                break;
            case 2:
                destString = "धनराजगिरी काॅर्नर";
                break;
            case 3:
                destString = "स्वास्थ्य संकुल /Health Center";
                break;
            case 4:
                destString = "लिम्बडी काॅर्नर";
                break;
            case 5:
                destString = "मोर्वी छात्रावास";
                break;
            case 6:
                destString = "रामानुजन छात्रावास";
                break;
            case 7:
                destString = "स्वतंत्रता भवन";
                break;
            case 8:
                destString = "विश्वकर्मा छात्रावास";
                break;
            case 9:
                destString = "विवेकानंद छात्रावास";
                break;
            case 10:
                destString = "लंका, मुख्य द्वार";
                break;
            case 11:
                destString = "विश्वनाथ मंदिर";
        }

        TextView timeView = (TextView) findViewById(R.id.order_time_in_confirm);
        timeView.setText(arr[1]);
        TextView dateView = (TextView) findViewById(R.id.order_date_in_confirm);
        dateView.setText(reg_date);
        TextView sourceView = (TextView) findViewById(R.id.from_source_in_confirm);
        sourceView.setText(srcString + " से");
        TextView destinationView = (TextView) findViewById(R.id.to_destination_in_confirm);
        destinationView.setText("->  " + destString);
        TextView numOfCusView = (TextView) findViewById(R.id.no_of_customer_in_confirm);
        numOfCusView.setText("प्रवासि संख्या: " + noOfCus);
        TextView customerNameView = (TextView) findViewById(R.id.customer_name_in_confirm);
        customerNameView.setText("प्रवासि ID:  " + mCustomer);
    }
}
