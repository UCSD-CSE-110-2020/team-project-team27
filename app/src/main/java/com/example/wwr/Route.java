package com.example.wwr;

public class Route {
    String name;
    String startingLocation;
    int steps;
    double distance;
    int[] time;
    // array of boolean values(for features/favorite)
    // notes field

    Route(String name_input, String startingLoc_input, int steps_input, double distance_input, int[] time_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = steps_input;
        distance = distance_input;
        time = time_input; // can I do this?
    }

    // we can have getters and setters
}
