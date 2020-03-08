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

        // get Routes info
        final String name = getItem(position).getName();
        final String starting = getItem(position).getStartingLocation();
        final String date = getItem(position).getProposedDate();
        final String time = getItem(position).getProposedTime();
        final String feature = getItem(position).getFeatures();
        boolean isScheduled = Boolean.parseBoolean(getItem(position).getIsScheduled());
        final String ownerName = getItem(position).getOwnerName();
        final String initial = getInitials(getItem(position).getOwnerName());
        final String iconColor = getItem(position).getOwnerColor(); // TODO: this color is zero
        final String attendee = getItem(position).getAttendee();
        final String email = getItem(position).getOwnerEmail();

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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WalkInfoFromProposeWalkActivity.class);
                intent.putExtra("RW_NAME", name);
                intent.putExtra("PW_LOC", starting);
                intent.putExtra("PW_FEA", feature);
                intent.putExtra("PW_DATE", date);
                intent.putExtra("PW_TIME", time);
                intent.putExtra("PW_USER_NM", ownerName);
                intent.putExtra("PW_USER_INI", initial);
                intent.putExtra("PW_COLOR", iconColor);
                intent.putExtra("PW_ATTENDEE", attendee);
                intent.putExtra("PW_EMAIL", email);

                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });

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
