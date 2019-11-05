package com.example.a300cemandroid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class taskObj {
    private String title;
    private boolean completed;
    private Date dateMade = new Date();
    private Date timeMade = new Date();








    private User madeBy;
    private Boolean editing = false;


    public taskObj(){

    }
    /*
    Formats a string to date
     */
    private Date formatDateMade(String date){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        try {
            Date result = format.parse(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Formats a string to date
     */
    private Date formatTimeMade(String time){
        DateFormat format = new SimpleDateFormat("hh.mm.ss", Locale.ENGLISH);

        try {
            Date result = format.parse(time);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDateMadeString(String date){
        this.dateMade = formatDateMade(date);
    }

    public void setTimeMadeString(String time){
        this.timeMade = formatTimeMade(time);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDateMade() {
        return dateMade;
    }

    public void setDateMade(Date dateMade) {
        this.dateMade = dateMade;
    }

    public Date getTimeMade() {
        return timeMade;
    }

    public void setTimeMade(Date timeMade) {
        this.timeMade = timeMade;
    }



    public String getStringTimeMade(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(timeMade);
    }

    public String getStringDateMade(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(timeMade);
    }

    public User getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(User madeBy) {
        this.madeBy = madeBy;
    }

    public Boolean getEditing() {
        return editing;
    }

    public void setEditing(Boolean editing) {
        this.editing = editing;
    }
}
