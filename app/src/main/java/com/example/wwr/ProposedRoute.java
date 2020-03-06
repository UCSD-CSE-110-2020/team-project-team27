package com.example.wwr;

public class ProposedRoute extends Route {

    private String attendee;
    private String date;
    private String time;
    private String isScheduled;
    private String ownerEmail;

    public ProposedRoute(String name, String loc, String attendee, String date, String time, String isScheduled, String ownerEmail){
        super(name, loc);
        this.attendee = attendee;
        this.date = date;
        this.time = time;
        this.isScheduled = isScheduled;
        this.ownerEmail = ownerEmail;
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

}
