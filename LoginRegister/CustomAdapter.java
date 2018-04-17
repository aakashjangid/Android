package com.wastedpotential.auctionapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SP on 4/15/2018.
 */

public class CustomAdapter extends BaseAdapter{

    Context context;
    List<Item> items;
    public CustomAdapter(Context context,List<Item> items){
        this.context=context;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( int i, View lview, ViewGroup viewGroup) {
       View view;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout,null, false);
        TextView item_name = (TextView)view.findViewById(R.id.item_Name);
        TextView item_minBid = (TextView)view.findViewById(R.id.item_minBid);
        TextView item_lastDate = (TextView)view.findViewById(R.id.item_lastDate);
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        final int j=i;

        Log.e("---items-",items.get(i).toString());

//        Item item = items.get(i);
//        item_name.setText(item.getName());
//        item_minBid.setText(item.getMinBid());
//        item_lastDate.setText(item.getLastDate());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, " "+j, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
