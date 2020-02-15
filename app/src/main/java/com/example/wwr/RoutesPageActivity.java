package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class RoutesPageActivity extends AppCompatActivity {

    private static final String TAG = "RoutesPageActivity";
    private FloatingActionButton plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_page);

        plus = findViewById(R.id.fab);

        Log.d(TAG, "onCreate: Started.");
        ListView routeListUI = findViewById(R.id.route_list);
        ArrayList<Route> routes = new ArrayList<>();
        populateList(routes);

        RouteListAdapter adapter = new RouteListAdapter(this, R.layout.adapter_view_layout, routes);
        routeListUI.setAdapter(adapter);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddAWalkActivity();
                finish();
            }
        });

    }

    public void launchAddAWalkActivity(){
        Intent intent = new Intent(this, AddAWalkActivity.class);
        startActivity(intent);
    }

    public void populateList(ArrayList<Route> list){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
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
            list.add(new Route(s, routeCount.getString(s + "_features", ""), routeCount.getBoolean(s + "_isFavorite", false)));
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
