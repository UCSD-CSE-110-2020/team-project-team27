package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class RoutesPageActivity extends AppCompatActivity {

    private static final String TAG = "RoutesPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_page);
        Log.d(TAG, "onCreate: Started.");
        ListView routeListUI = findViewById(R.id.route_list);
        ArrayList<Route> routes = new ArrayList<>();
        populateList(routes);

        RouteListAdapter adapter = new RouteListAdapter(this, R.layout.adapter_view_layout, routes);
        routeListUI.setAdapter(adapter);
    }

    public void populateList(ArrayList<Route> list){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        Set<String> routeList = routeCount.getStringSet("routeNames", null);
        if(routeList == null){
            System.err.println("Critical Error: routeList does not exist");
            return;
        }
        for(String s: routeList){
            list.add(new Route(s, routeCount.getString(s+"_features", ""), routeCount.getBoolean(s+"_isFavorite", false)));
        }
    }
}
