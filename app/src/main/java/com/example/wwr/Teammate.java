package com.example.wwr;

public class Teammate{
    private String email;
    private String name;

    public Teammate(String name, String email){
        this.email = email;
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }
}
