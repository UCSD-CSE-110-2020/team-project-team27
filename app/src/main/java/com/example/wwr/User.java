package com.example.wwr;

class User {
    private static long steps;
    private static int heightft;
    private static int heightin;


    public static double returnDistance(){
        // got the conversion formula from https://www.inchcalculator.com/steps-distance-calculator/
        int heightinInches = heightft * 12 + heightin;
        double distance = ((double) heightinInches) * 0.43 * ((double) steps); // need to convert from inch to ft to mil
        distance = distance/12/5280;
        distance = Math.round(distance * 100.0) / 100.0;
        return distance; // fixed value for testing
    }

    public static boolean isValid(int ft, int in){
        if(0 <= ft && ft <= 9 && 0 <= in && in <= 11){
            return true;
        }
        return false;
    }

    public static void setHeight(int ft, int in){
        if(isValid(ft, in)){
            heightft = ft;
            heightin = in;
        }
    }

    public static void setSteps(long steps_in){
        steps = steps_in;
    }

    public static long getSteps(){
        return steps;
    }

}
