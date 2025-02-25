package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
        final String name = getItem(position).getName();
        final String email = getItem(position).getEmail().replace('-', '@');

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView msg = convertView.findViewById(R.id.invitation);

        msg.setText(name + " (" + email + ") \nsent you a team invitation");

        convertView.findViewById(R.id.acceptIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("Accepted an Invitation from invitation page");
                UpdateFirebase.acceptInvite(email.replace('@', '-'), name);
                Toast.makeText(getContext(),
                        "Accepted invitation", Toast.LENGTH_SHORT).show();
                ((Activity) v.getContext()).onBackPressed();
                ((Activity) v.getContext()).finish();
            }
        });

        convertView.findViewById(R.id.rejectIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("Rejected an Invitation from invitation page");
                UpdateFirebase.rejectInvite(email.replace('@', '-'));
                Toast.makeText(getContext(),
                        "Rejected invitation", Toast.LENGTH_SHORT).show();
                ((Activity) v.getContext()).onBackPressed();
                ((Activity) v.getContext()).finish();
            }
        });


        return convertView;
    }
}
