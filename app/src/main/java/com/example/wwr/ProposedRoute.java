package com.example.wwr;

public class ProposedRoute extends Route {

    private String attendee;
    private String date;
    private String time;
    private String isScheduled;
    private String ownerEmail;
    private String ownerColor;
    private String ownerName;

    public ProposedRoute(String name, String loc, String fea, String attendee, String date,
                         String time, String isScheduled, String ownerEmail, String ownerColor, String ownerName){
        super(name, loc, fea);
        this.attendee = attendee;
        this.date = date;
        this.time = time;
        this.isScheduled = isScheduled;
        this.ownerEmail = ownerEmail;
        this.ownerColor = ownerColor;
        this.ownerName = ownerName;
    }

    public String getAttendee(){
        return attendee;
    }

    public String getProposedDate(){
        return date;
    }

    public String getProposedTime(){
        return time;
    }

    public String getIsScheduled(){
        return isScheduled;
    }

    public String getOwnerEmail(){
        return ownerEmail;
    }

    public String getOwnerColor(){ return ownerColor; }

    public String getOwnerName(){ return ownerName; }

}
