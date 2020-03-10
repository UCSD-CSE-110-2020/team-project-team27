package com.example.wwr;

public class ProposedRoute extends Route {

    private String attendee;
    private String date;
    private String time;
    private String isScheduled;
    private String ownerEmail;
    private String ownerColor;
    private String ownerName;
    private String rejected;

    public ProposedRoute(String name, String loc, String fea, String attendee, String date,
                         String time, String isScheduled, String ownerEmail, String ownerColor, String ownerName, String rejected){
        super(name, loc, fea);
        this.attendee = attendee;
        this.date = date;
        this.time = time;
        this.isScheduled = isScheduled;
        this.ownerEmail = ownerEmail;
        this.ownerColor = ownerColor;
        this.ownerName = ownerName;
        this.rejected = rejected;
    }

    public String getAttendee(){ return attendee; }

    public String getRejected(){ return rejected; }


    public String getProposedDate(){ return date; }

    public String getProposedTime(){ return time; }

    public String getIsScheduled(){ return isScheduled; }

    public String getOwnerEmail(){ return ownerEmail; }

    public String getOwnerColor(){ return ownerColor; }

    public String getOwnerName(){ return ownerName; }

    public void setAttendee(String attendee_input){attendee = attendee_input;}

    public void setReject(String reject_input){rejected = reject_input;}

    // we have attendee_input, and reject_input as output param
    public static String[] updateAttendee(String user_name, String attendee_input, String reject_input){
        String[] arrAttendReject = new String[2];

        arrAttendReject[0] = attendee_input + user_name + ",";

        if(reject_input.contains(user_name + "\n(bad time)")){
            // remove user from reject
            arrAttendReject[1] = reject_input.replace(user_name + "\n(bad time)," , "");
        }else if(reject_input.contains(user_name + "\n(not a good route)")){
            arrAttendReject[1] = reject_input.replace(user_name + "\n(not a good route)," , "");
        }
        else{
            arrAttendReject[1] = reject_input;
        }

        return arrAttendReject;
    }

    public static String[] updateReject(String user_name, String attendee_input, String reject_input, String reason){
        String[] arrAttendReject = new String[2];

        String otherReason = (reason.equals("\n(bad time)"))? "\n(not a good route)" : "\n(bad time)";

        if(reject_input.contains(user_name + otherReason + ",")){
            arrAttendReject[1] = reject_input.replace(user_name + otherReason + ",", user_name + reason + ",");
        }else {
            arrAttendReject[1] = reject_input + user_name + reason + ",";
        }

        if(attendee_input.contains(user_name)){
            // remove user from attendee
            arrAttendReject[0] = attendee_input.replace(user_name + "," , "");
        }else{
            arrAttendReject[0] = attendee_input;
        }

        return arrAttendReject;
    }

    public static String getFormattedList(String list){
        return list.replace(',', '\n');
    }

}
