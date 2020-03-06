package com.example.wwr;

public class Route {
    private String name;
    private String startingLocation;
    private int steps;
    private double distance;
    private int[] time;
    boolean favorite = false;
    String features;
    String [] teammateInfo;// which teammate this route belongs to (name, email, color)

    // overloaded constructor
    Route(String name_input, String startingLoc_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = 0;
        distance = 0;
        time = new int []{0, 0, 0};
    }

    // overloaded constructor
    Route(String name_input, String startingLoc_input, String features_input) {
        name = name_input;
        startingLocation = startingLoc_input;
        steps = 0;
        distance = 0;
        time = new int []{0, 0, 0};
        features = features_input;
    }

    boolean getFavorite(){ return favorite; }

    Route setFavorite(boolean favorite){ this.favorite = favorite; return this;}

    String getName(){ return name; }

    String getFeatures(){ return features; }

    Route setFeatures(String fea){this.features = fea; return this;}

    Route setName(String name){ this.name = name; return this;}

    String getStartingLocation(){ return startingLocation; }

    int getSteps(){ return steps; }

    Route setSteps(int steps){ this.steps = steps; return this;}

    double getDistance(){ return distance; }

    Route setDistance(double distance){ this.distance = distance; return this;}

    int[] getTime(){ return time; }

    Route setTime(int[] time){ this.time = time.clone(); return this;}

    String[] getTeammateInfo(){return teammateInfo;}

    Route setTeammateInfo(String[] info){this.teammateInfo = info.clone(); return this;}

    public String getInitials() {
        if(teammateInfo == null){return null;}
        String initials="";
        String[] parts;
        try {
            parts = teammateInfo[0].split(" ");
        } catch ( NullPointerException e){
            parts = new String[]{};
        }
        char initial;
        for (int i=0; i<parts.length; i++){
            initial=parts[i].charAt(0);
            initials+=initial;
        }
        return(initials.toUpperCase());
    }

    public String getColor(){
        if(teammateInfo == null){return null;}
        return teammateInfo[2];
    }
}
