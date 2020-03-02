package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteListAdapter extends ArrayAdapter<Route> {
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
        final String starting = getItem(position).getStartingLocation();
        final double dist = getItem(position).getDistance();
        int[] time = getItem(position).getTime();
        int step = getItem(position).getSteps();
        String initial = getItem(position).getInitials();
        String iconColor = getItem(position).getColor();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView rName = convertView.findViewById(R.id.textView1);
        TextView rTime_Features = convertView.findViewById(R.id.textView2);
        TextView rTime_Step_Dist = convertView.findViewById(R.id.textView3);
        TextView rStarting = convertView.findViewById(R.id.textView4);
        TextView icon = convertView.findViewById(R.id.teamlisticon);
        LinearLayout iconBackground = convertView.findViewById(R.id.iconbackground);

        if(icon != null || iconColor == null){
            icon.setText(initial);
            System.err.println(iconColor);
            ((GradientDrawable)icon.getBackground()).setColor(Integer.parseInt(iconColor));
        }

        if(position % 2 == 0){
            rName.setBackgroundColor(Color.parseColor("#22EB7878"));
            rTime_Features.setBackgroundColor(Color.parseColor("#22EB7878"));
            rTime_Step_Dist.setBackgroundColor(Color.parseColor("#22EB7878"));
            rStarting.setBackgroundColor(Color.parseColor("#22EB7878"));
            if(icon != null) {
                iconBackground.setBackgroundColor(Color.parseColor("#22EB7878"));
            }
        }

        rStarting.setText("from " + starting);
        if(favorite) {
            rName.setText(name + " *");
        }
        else{
            rName.setText(name);
        }
        rTime_Features.setText("Time: " + time[0] + " : " + time[1] + " : " + time[2] + "  |  " +
                features);
        rTime_Step_Dist.setText("" +  step + " steps  |  " + dist + " mi.");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("I'm clicked:" + getItem(index).getName());
                Intent intent = new Intent(getContext(), WalkInfoFromRouteActivity.class);
                intent.putExtra("CLICKED_NAME", name);
                intent.putExtra("notStarted", true);
                v.getContext().startActivity(intent);
                System.err.println("send intent string: " + name);
                ((Activity) v.getContext()).finish();

            }
        });

        return convertView;
    }

}
