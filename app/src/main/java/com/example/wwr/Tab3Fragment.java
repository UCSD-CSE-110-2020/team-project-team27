package com.example.wwr;

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

import java.util.ArrayList;
import java.util.Comparator;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    FirebaseMediator mediator;
    static ListView routeListUI;
    private static ArrayList<ProposedRoute> routes;
    static View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_3,container,false);

        routeListUI = view.findViewById(R.id.proposedRoute_list);

        //Adding to mediator
        mediator = new FirebaseMediator();
        mediator.addTab3(this);

        mediator.getProposedWalks();

        return view;
    }

    public void displayProposedRoutes(ArrayList<ProposedRoute> proposedRouteArrayList){
        routes = proposedRouteArrayList;
        java.util.Collections.sort(routes, new Tab3Fragment.SortIgnoreCase());

        ProposeRouteListAdapter adapter = new ProposeRouteListAdapter(view.getContext(), R.layout.teamroute_adapter_view_layout, routes);
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