package com.example.wwr;

public class Teammate{
    private String email;
    private String name;
    private int color;
    private boolean pending; // true means the teammate hasn't responded the invite

    public Teammate(String name, String email, int color){
        this.email = email;
        this.name = name;
        this.color = color;
    }

    public Teammate(String name, String email, int color, boolean pending){
        this.email = email;
        this.name = name;
        this.color = color;
        this.pending = pending;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public int getColor(){ return color;}

    public boolean getPending(){ return pending;}

}
