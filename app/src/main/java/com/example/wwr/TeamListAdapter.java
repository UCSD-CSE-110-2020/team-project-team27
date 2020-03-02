package com.example.wwr;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

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
        String email = getItem(position).getEmail();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView TIcon = convertView.findViewById(R.id.icon);
        TextView TName = convertView.findViewById(R.id.teammate_name);
        TextView TEmail = convertView.findViewById(R.id.teammate_mail);

        TIcon.setText(getInitials(name));
        ((GradientDrawable)TIcon.getBackground()).setColor(RandomColorGenerator());
        TName.setText(name);
        TEmail.setText(email);

        return convertView;
    }

    public String getInitials(String fullName) {
        String initials="";
        String[] parts = fullName.split(" ");
        char initial;
        for (int i=0; i<parts.length; i++){
            initial=parts[i].charAt(0);
            initials+=initial;
        }
        return(initials.toUpperCase());
    }

    public int RandomColorGenerator(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
