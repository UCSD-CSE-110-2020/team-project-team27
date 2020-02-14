package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        final String name = getItem(position).getName();
        String features = getItem(position).getFeatures();
        boolean favorite = getItem(position).getFavorite();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView rName = convertView.findViewById(R.id.textView1);
        TextView rFeatures = convertView.findViewById(R.id.textView2);
        TextView rFavorite = convertView.findViewById(R.id.textView3);

        if(position % 2 == 0){
            rName.setBackgroundColor(Color.parseColor("#22EB7878"));
            rFeatures.setBackgroundColor(Color.parseColor("#22EB7878"));
            rFavorite.setBackgroundColor(Color.parseColor("#22EB7878"));
        }

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
                Intent intent = new Intent(getContext(), WalkInfoFromRouteActivity.class);
                intent.putExtra("CLICKED_NAME", name);
                intent.putExtra("notStarted", true);
                v.getContext().startActivity(intent);
                System.err.println("send intent string: " + name);

                //((Activity) v.getContext()).finish();
            }
        });

        return convertView;
    }

}
