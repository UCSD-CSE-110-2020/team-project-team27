package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProposeRouteListAdapter extends ArrayAdapter<ProposedRoute> {
    private Context mContext;
    int mResource;

    public ProposeRouteListAdapter(Context context, int resource, ArrayList<ProposedRoute> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        final int index = position;

        // get Routes info
        final String name = getItem(position).getName();
        final String starting = getItem(position).getStartingLocation();
        String date = getItem(position).getProposedDate();
        String time = getItem(position).getProposedTime();
        boolean isScheduled = Boolean.parseBoolean(getItem(position).getIsScheduled());

        String initial = getInitials(getItem(position).getOwnerName());
        String iconColor = getItem(position).getOwnerColor();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView rName = convertView.findViewById(R.id.textView1);
        TextView rTime = convertView.findViewById(R.id.textView2);
        TextView rDate = convertView.findViewById(R.id.textView3);
        TextView rStarting = convertView.findViewById(R.id.textView4);
        TextView icon = convertView.findViewById(R.id.teamlisticon);
        LinearLayout iconBackground = convertView.findViewById(R.id.iconbackground);

        if(icon != null && iconColor != null){
            icon.setText(initial);
            System.err.println(iconColor);
            ((GradientDrawable)icon.getBackground()).setColor(Integer.parseInt(iconColor));
        }

        if(position % 2 == 0){
            rName.setBackgroundColor(Color.parseColor("#22EB7878"));
            rTime.setBackgroundColor(Color.parseColor("#22EB7878"));
            rDate.setBackgroundColor(Color.parseColor("#22EB7878"));
            rStarting.setBackgroundColor(Color.parseColor("#22EB7878"));
            if(icon != null) {
                iconBackground.setBackgroundColor(Color.parseColor("#22EB7878"));
            }
        }

        rStarting.setText("from " + starting);
        rName.setText(name);
        rTime.setText("Time: " + time);
        rDate.setText("Date: " + date);

        if(!isScheduled){
            greyOut(rStarting);
            greyOut(rName);
            greyOut(rTime);
            greyOut(rDate);
            ((GradientDrawable)icon.getBackground()).setColor(Color.parseColor("#a7b0a9"));
        }

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("I'm clicked:" + getItem(index).getName());
                Intent intent = new Intent(getContext(), WalkInfoFromRouteActivity.class);
                intent.putExtra("CLICKED_NAME", name);
                intent.putExtra("CLICKED_LOC", starting);
                intent.putExtra("CLICKED_FEATURE", features);

                intent.putExtra("notStarted", true);
                v.getContext().startActivity(intent);
                System.err.println("send intent string: " + name);
                ((Activity) v.getContext()).finish();

            }
        });*/

        return convertView;
    }

    private String getInitials(String fullName) {
        String initials="";
        String[] parts;
        try {
            parts = fullName.split(" ");
        } catch ( NullPointerException e){
            parts = new String[]{};
        }
        char initial;
        for (int i=0; i<parts.length; i++){
            initial=parts[i].charAt(0);
            initials+=initial;
        }
        return(initials.toUpperCase());
    }

    private void greyOut(TextView view){
        view.setTextColor(Color.parseColor("#a7b0a9"));
        view.setTypeface(null, Typeface.ITALIC);
    }

}
