package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        routeListUI.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                Toast.makeText(getApplicationContext(), "clicked ", Toast.LENGTH_SHORT).show(); // display the current state for switch's
            }
        });

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
