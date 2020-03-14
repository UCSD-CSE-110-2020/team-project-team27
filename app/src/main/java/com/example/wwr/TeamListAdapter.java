package com.example.wwr;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TeamListAdapter extends ArrayAdapter<Teammate> {

    private Context mContext;
    int mResource;

    public TeamListAdapter(Context context, int resource, List<Teammate> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // get Teammate info
        String name = getItem(position).getName();
        String email = getItem(position).getEmail().replace('-', '@');
        int color = getItem(position).getColor();
        boolean pending = getItem(position).getPending();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView TIcon = convertView.findViewById(R.id.icon);
        TextView TName = convertView.findViewById(R.id.teammate_name);
        TextView TEmail = convertView.findViewById(R.id.teammate_mail);

        TIcon.setText(getInitials(name));
        ((GradientDrawable)TIcon.getBackground()).setColor(color);
        TName.setText(name);
        TEmail.setText(email);

        if(pending){
            TName.setTextColor(Color.parseColor("#a7b0a9"));
            TEmail.setTextColor(Color.parseColor("#a7b0a9"));
            TName.setTypeface(null, Typeface.ITALIC);
            TEmail.setTypeface(null, Typeface.ITALIC);
            ((GradientDrawable)TIcon.getBackground()).setColor(Color.parseColor("#a7b0a9"));
        }

        return convertView;
    }

    public String getInitials(String fullName) {
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
}
