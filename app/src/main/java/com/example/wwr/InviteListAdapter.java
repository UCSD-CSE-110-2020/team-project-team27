package com.example.wwr;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InviteListAdapter extends ArrayAdapter<Teammate> {

    private Context mContext;
    int mResource;

    public InviteListAdapter(Context context, int resource, List<Teammate> objects){
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

        TextView msg = convertView.findViewById(R.id.invitation);

        msg.setText(name+ " (" + email + ") \nsended you a team invitation");

        return convertView;
    }
}
