package com.example.wwr;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wwr.ui.main.SectionsPagerAdapter;

import java.util.TreeSet;


public class RouteListsActivity extends AppCompatActivity {

    private static final String TAG = "RouteListsActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_lists);
        Log.d(TAG, "onCreate: Starting.");

        // contain user data for teammate's routes
        /*SharedPreferences sp = getSharedPreferences("teammateRoutes", MODE_PRIVATE);
        if(!sp.contains("routeNames")){
            System.err.println("routeNames StringSet created.");
            SharedPreferences.Editor editor = sp.edit();

            // String Set of route list from teammates the user has walked on before
            editor.putStringSet("routeNames", new TreeSet<String>()).apply();
        }
        UserSharePreferences.setTeamRouteSP(sp);*/

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "My Routes");
        adapter.addFragment(new Tab2Fragment(), "Team Routes");
        adapter.addFragment(new Tab3Fragment(), "Proposed Routes");
        viewPager.setAdapter(adapter);
    }

}