package com.example.wwr;

public class Route {
    private String name;
    private String startingLocation;
    private int steps;
    private double distance;
    private int[] time;
    boolean fromStartAWalk = false;
    boolean favorite = false;
    String features;


    Route(String name_input, String startingLoc_input, int steps_input, double distance_input, int[] time_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = steps_input;
        distance = distance_input;
        time = new int []{time_input[0], time_input[1], time_input[2]};
    }

    // overloaded constructor
    Route(String name_input, String startingLoc_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = 0;
        distance = 0;
        time = new int []{0, 0, 0};
        this.fromStartAWalk = fromStartAWalk;
    }

    // overloaded constructor
    Route(String name_input, String features_input, boolean favorite, String startingLoc_input,
          int steps_input, double distance_input, int[] time_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = steps_input;
        distance = distance_input;
        time = new int []{time_input[0], time_input[1], time_input[2]};
        features = features_input;
        this.favorite = favorite;
    }

    boolean getFavorite(){
        return favorite;
    }

    String getName(){
        return name;
    }

    String getFeatures(){
        return features;
    }

    void setName(String name){
        this.name = name;
    }

    String getStartingLocation(){
        return startingLocation;
    }

    int getSteps(){
        return steps;
    }

    void setSteps(int steps){
        this.steps = steps;
    }

    double getDistance(){
        return distance;
    }

    void setDistance(double distance){
        this.distance = distance;
    }

    int[] getTime(){
        return time;
    }

    void setTime(int[] time){
        this.time = time.clone();
    }

}
