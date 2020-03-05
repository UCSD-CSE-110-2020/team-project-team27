package com.example.wwr;

public class Teammate{
    private String email;
    private String name;
    private int color;

    public Teammate(String name, String email, int color){
        this.email = email;
        this.name = name;
        this.color = color;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public int getColor(){ return color;}
}
