package com.example.wwr;

class User {
    public static long steps;
    private static int heightft = 5;
    private static int heightin = 8;

    public User(int ft, int in){
        if(isValid(ft, in)){
            heightft = ft;
            heightin = in;
        }
        else{
            // error, this is bad design
        }
    }

    public static double returnDistance(){
        // got the conversion formula from https://www.inchcalculator.com/steps-distance-calculator/
        int heightinInches = heightft * 12 + heightin;
        double distance = ((double) heightinInches) * 0.43 * ((double) steps); // need to convert from inch to ft to mil
        distance = distance/12/5280;
        distance = Math.round(distance * 100.0) / 100.0;
        return distance; // fixed value for testing
    }

    public boolean isValid(int ft, int in){
        if(0 <= ft && ft <= 9 && 0 <= in && in <= 11){
            return true;
        }
        return false;
    }
}
