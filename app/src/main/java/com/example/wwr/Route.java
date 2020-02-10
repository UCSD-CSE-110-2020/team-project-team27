package com.example.wwr;

public class Route {
    private String name;
    private String startingLocation;
    private int steps;
    private double distance;
    private int[] time;
    boolean fromStartAWalk = false;

    //          versus Out & Back, hill,  trail,  uneven surface, easy/difficult, non-fav
    String[] features = {"Loop", "Flat", "Steets", "Even Surface", "Moderate", "Favorite"};
    String notes;

    Route(String name_input, String startingLoc_input, int steps_input, double distance_input, int[] time_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = steps_input;
        distance = distance_input;
        time = new int []{time_input[0], time_input[1], time_input[2]};
    }

    // overloaded constructor
    Route(String name_input, String startingLoc_input, boolean fromStartAWalk) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = 0;
        distance = 0;
        time = new int []{0, 0, 0};
        this.fromStartAWalk = fromStartAWalk;
    }

    String getName(){
        return name;
    }

    void setName(String name){
        this.name = name;
    }

    void setStartingLocation(String startingLocation){
        this.startingLocation = startingLocation;
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

    boolean isComplete(){
        if(fromStartAWalk == false){
            return false;
        }
        return true;
    }
}
