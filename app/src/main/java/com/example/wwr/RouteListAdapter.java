package com.example.wwr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RouteListAdapter extends ArrayAdapter<Route> {
    private static final String TAG = "RouteListAdapter";
    private Context mContext;
    int mResource;

    public RouteListAdapter(Context context, int resource, ArrayList<Route> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        final int index = position;


        // get Routes info
        String name = getItem(position).getName();
        String features = getItem(position).getFeatures();
        boolean favorite = getItem(position).getFavorite();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView rName = convertView.findViewById(R.id.textView1);
        TextView rFeatures = convertView.findViewById(R.id.textView2);
        TextView rFavorite = convertView.findViewById(R.id.textView3);


        rName.setText(name);
        rFeatures.setText(features);
        if(favorite) {
            rFavorite.setText("favorite");
        }
        else{
            rFavorite.setText("");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("I'm clicked:" + getItem(index).getName());
            }
        });

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }

}
