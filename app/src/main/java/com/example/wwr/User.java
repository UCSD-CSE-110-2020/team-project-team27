package com.example.wwr;

import android.util.Log;

class User {
    private static long steps;
    private static int heightft = 0;
    private static int heightin = 0;
    private static Route currentRoute = null;
    static long setTime = 0;
    private static String gmail;
    private static String name;
    private static String color;

    public static double returnDistance(){
        // got the conversion formula from https://www.inchcalculator.com/steps-distance-calculator/
        int heightinInches = heightft * 12 + heightin;
        // need to convert from inch to ft to mil
        double distance = ((double) heightinInches) * 0.43 * ((double) steps);
        distance = distance/12/5280;
        distance = Math.round(distance * 100.0) / 100.0;
        return distance; // fixed value for testing
    }

    public static void setEmail(String email){
        Log.d("User", "Set email to " + email + " in User class." );
        gmail = email.replace('@', '-');
    }

    public static void setName(String userName){
        Log.d("User", "Set name to " + userName + " in User class." );
        name = userName;
    }

    public static String getEmail (){
        return gmail;
    }

    public static String getName (){
        return name;
    }

    public static String getColor() {
        return color;
    }

    public static void setColor(String color1) {
        color = color1;
    }

    public static boolean hasHeight(){
        return !((heightft==0) && (heightin==0));
    }

    public static void setHeight(int ft, int in){
        System.err.println("Set Height to:" + ft + " " + in);
        heightft = ft;
        heightin = in;
    }

    public static int [] getHeight(){
        int [] result = {heightft, heightin};
        return result;
    }

    public static void setCurrentRoute(Route route){
        currentRoute = route;
    }

    public static Route getCurrentRoute(){
        return currentRoute;
    }

    public static void setSteps(long steps_in){
        steps = steps_in;
    }

    public static long getSteps(){
        return steps;
    }

}