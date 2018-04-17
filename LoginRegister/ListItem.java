package com.wastedpotential.auctionapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListItem extends AppCompatActivity {

    ListView listView;

    public ArrayList<HashMap<String, Object>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        listView = (ListView)findViewById(R.id.listView);
        new HttpAsyncTaskForList().execute();
    }

    private class HttpAsyncTaskForList extends AsyncTask<Void,Void,List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... params) {
            try{

                final String url = "http://192.168.43.190:8080/auctionapp/items";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<Item> items = restTemplate.getForObject(url, List.class);
                Log.e("---values--",items.toString());

               // JSONObject jsonObj = new JSONObject(jsonStr);


                // Getting JSON Array node
                //JSONArray contacts = jsonObj.getJSONArray("contacts");


                try{
                // looping through All Contacts
                for (int i = 0; i < items.size(); i++) {

                    Item itm = items.get(i);
                    String name = itm.getName();
                    int minbid =itm.getMinBid();
                    String ldate = itm.getLastDate();


                    // Phone node is JSON Object

                    // tmp hash map for single contact
                    HashMap<String, Object> contact = new HashMap<>();
                    // adding each child node to HashMap key => value
                    contact.put("name", name);
                    contact.put("minBid", minbid);
                    contact.put("lastDate", ldate);

                    //contact.put("mobile", mobile);
                    // adding contact to contact list
                    contactList.add(contact);
                }
                }catch(Exception e)

                    {

                    }


                return items;

            }catch (Exception e) {
                e.printStackTrace();
                Log.e("------e",e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            ListAdapter adapter = new SimpleAdapter(


                    ListItem.this, contactList,
                    R.layout.layout, new String[]{"name",
                    "minBid","lastDate"}, new int[]{R.id.item_Name,
                    R.id.item_minBid, R.id.item_lastDate});

            listView.setAdapter(adapter);
        }
    }

}