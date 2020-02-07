package com.example.wwr;

public class Route {
    String name;
    String startingLocation;
    int steps;
    double distance;
    // array of boolean values(for features/favorite)
    // notes field

    Route(String name_input, String startingLoc_input, int steps_input, double distance_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = steps_input;
        distance = distance_input;
    }

    // we can have getters and setters
}
