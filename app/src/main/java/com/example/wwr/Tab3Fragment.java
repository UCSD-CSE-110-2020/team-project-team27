package com.example.wwr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private Button btnTEST;
    FirebaseMediator mediator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_3,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST3);

        Log.d(TAG, "onCreate: Started.");
        //Adding to mediator
        mediator = new FirebaseMediator();
        mediator.addTab3(this);

        mediator.getProposedWalks();

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 3",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void displayProposedRoutes(ArrayList<ProposedRoute> proposedRouteArrayList){
        for(ProposedRoute route: proposedRouteArrayList) {
            System.err.println("Proposed Route: " + route.getName());
        }
    }
}