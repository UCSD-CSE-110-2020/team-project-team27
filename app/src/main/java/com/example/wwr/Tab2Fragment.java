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

// Team Routes Page
public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    private static ArrayList<Route> routes;
    static ListView routeListUI;
    static View view;
    FirebaseMediator mediator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_2,container,false);

        Log.d(TAG, "onCreate: Started.");
        routeListUI = view.findViewById(R.id.teamroute_list);

        //Adding to mediator
        mediator = new FirebaseMediator();
        mediator.addRouteView(this);

        mediator.getTeamRoutes();

        return view;
    }

    public void displayTeamList(ArrayList<Route> routes_input){
        routes = routes_input;
        java.util.Collections.sort(routes, new Tab2Fragment.SortIgnoreCase());

        RouteListAdapter adapter = new RouteListAdapter(view.getContext(), R.layout.teamroute_adapter_view_layout, routes);
        routeListUI.setAdapter(adapter);
    }

    public class SortIgnoreCase implements Comparator<Route> {
        public int compare(Route o1, Route o2) {
            String s1 = o1.getName();
            String s2 = o2.getName();
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }
    }
}
