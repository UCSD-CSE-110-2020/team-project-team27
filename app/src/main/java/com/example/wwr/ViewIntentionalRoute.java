package com.example.wwr;

public class ViewIntentionalRoute {
    private static Route lastRoute;

    public static Route getRoute(){
        return lastRoute;
    }

    public static void setRoute(Route route){
        lastRoute = route;
    }
}
