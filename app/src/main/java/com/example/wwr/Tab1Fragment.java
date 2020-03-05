package com.example.wwr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

//The New Route Page
public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private FloatingActionButton plus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1,container,false);

        plus = view.findViewById(R.id.addRouteBtn);

        Log.d(TAG, "onCreate: Started.");
        ListView routeListUI = view.findViewById(R.id.route_list);
        ArrayList<Route> routes = new ArrayList<>();
        populateList(routes);

        RouteListAdapter adapter = new RouteListAdapter(view.getContext(), R.layout.adapter_view_layout, routes);
        routeListUI.setAdapter(adapter);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddAWalkActivity(view);
                ((Activity)view.getContext()).finish();
            }
        });

        return view;
    }

    public void launchAddAWalkActivity(View view){
        Intent intent = new Intent(view.getContext(), AddAWalkActivity.class);
        startActivity(intent);
    }

    public void populateList(ArrayList<Route> list){
        SharedPreferences routeCount = UserSharePreferences.routeSP;
        Set<String> routeList = routeCount.getStringSet("routeNames", null);
        if(routeList == null){
            System.err.println("Critical Error: routeList does not exist");
            return;
        }

        ArrayList<String> listToSort = new ArrayList<>();
        for(String s: routeList){
            listToSort.add(s);
        }

        java.util.Collections.sort(listToSort, new SortIgnoreCase());

        for(String s: listToSort) {
            String features = routeCount.getString(s + "_features", "");
            boolean favorite = routeCount.getBoolean(s + "_isFavorite", false);
            String location = routeCount.getString(s + "_location", "");
            int steps = routeCount.getInt(s + "_step", 0);
            double dist = Double.parseDouble(routeCount.getString(s + "_dist", "0.0"));
            dist = Math.round(dist * 100.0) / 100.0;

            int[] time = {routeCount.getInt(s + "_hour", 0),
                    routeCount.getInt(s + "_min", 0),
                    routeCount.getInt(s + "_sec", 0)};
            list.add(new Route(s, features, favorite, location, steps, dist, time));
        }
    }

    public class SortIgnoreCase implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }
    }
}
