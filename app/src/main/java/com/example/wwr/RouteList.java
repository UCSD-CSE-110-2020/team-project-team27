package com.example.wwr;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;


// TODO: We don't need this class anymore. Need to refactor testing code
public class RouteList {

    private static HashMap<String, Route> map = new HashMap<>();
    private static Route latestRoute = null;

    public static boolean addRoute(Route newRoute){

        if(map.containsKey(newRoute.getName())){
            return false; // duplicate Route
            // show toast message
        }
        if(newRoute.isComplete()){
            latestRoute = newRoute;
        }
        map.put(newRoute.getName(), newRoute);
        System.err.println("Add " + newRoute.getName() + " successfully.");
        return true;
    }

    public static Route getLatest(){
        return latestRoute;
    }

    public static Route getRoute(String name){
        return map.get(name);
    }

    public void deleteRoute(String name){
        map.remove(name);
    }

    public static ArrayList<String> getSortedKeys(){
        return new ArrayList<>(map.keySet());
        // sorted
    }

    public int size(){
        return map.size();
    }

}
