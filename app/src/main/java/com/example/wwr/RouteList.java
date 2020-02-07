package com.example.wwr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RouteList {

    private static HashMap<String, Route> map;
    private static Route latestRoute;

    public RouteList(){
        map = null;
        latestRoute = null;
    }

    public static boolean addRoute(Route newRoute){
        if(map.containsKey(newRoute.name)){
            return false; // duplicate Route
        }
        latestRoute = newRoute;
        map.put(newRoute.name, newRoute);
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
    }

    public int size(){
        return map.size();
    }

}
