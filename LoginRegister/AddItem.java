package com.wastedpotential.auctionapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.v4.content.ContextCompat.startActivity;

public class AddItem extends AppCompatActivity {

    EditText itemName, minBid, lastDate;
    Button addItemButton;
    TextView goBack;
    String itemNameValue, lastDateValue; int minBidValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = (EditText)findViewById(R.id.itemName);
        minBid = (EditText)findViewById(R.id.minBidBox);
        lastDate = (EditText)findViewById(R.id.lastDateBox);

        goBack = (TextView)findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddItem.this,Dashboard.class));
            }
        });

        addItemButton = (Button)findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemNameValue = itemName.getText().toString();
                minBidValue = Integer.parseInt(minBid.getText().toString());
                lastDateValue = lastDate.getText().toString();
                new HttpRequestTaskItem(itemNameValue,minBidValue,lastDateValue).execute();

            }
        });
    }

private class HttpRequestTaskItem extends AsyncTask<Void, Void, Item> {

    private String itemName;
    private int minBid;
    private String lastDate;

    public String getItemName() {
        return this.itemName;
    }

    public int getMinBid() {
        return this.minBid;
    }

    public String getLastDate() {
        return this.lastDate;
    }

    public HttpRequestTaskItem(String itemName, int minBid, String lastDate) {
        this.itemName = itemName;
        this.minBid = minBid;
        this.lastDate = lastDate;
    }

    @Override
    protected Item doInBackground(Void... params) {
        try {
            final String url = "http://192.168.43.190:8080/auctionapp/items/" + this.getItemName() + "/" + this.getMinBid() + "/" + this.getLastDate();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Item item = restTemplate.getForObject(url, Item.class);
            return item;
        } catch (Exception e) {
            Log.e("Add Item", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Item item) {
        if(item!=null){
            Toast.makeText(getApplicationContext(),"Item Added Successfully",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(AddItem.this,Dashboard.class));
        }else{
            Toast.makeText(getApplicationContext(),"Something Went Wrong, Please Try Again",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddItem.this,AddItem.class));
        }
    }
}
}


