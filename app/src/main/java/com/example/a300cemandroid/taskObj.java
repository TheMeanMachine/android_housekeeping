package com.example.a300cemandroid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class taskObj {
    private String title;
    private boolean completed;
    private String dateMade;
    private String timeMade;
    private Integer ID;

    private User madeBy;
    private Boolean editing = false;

    public taskObj(){
        Date d = Calendar.getInstance().getTime();
        dateMade = getStringDateMade(d);
        timeMade = getStringTimeMade(d);
    }

//    /**
//     * Formats string date to date
//     * @param date
//     * @return date formatted
//     * @return null if error thrown
//     */
//    private Date formatDateMade(String date){
//        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
//
//        try {
//            Date result = format.parse(date);
//            return result;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * Formats string time to date obj
//     * @param time string time to convert
//     * @return date formatted
//     * @return null if error thrown
//     */
//    private Date formatTimeMade(String time){
//        DateFormat format = new SimpleDateFormat("hh.mm.ss", Locale.ENGLISH);
//
//        try {
//            Date result = format.parse(time);
//            return result;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    /**
//     * Sets date made string to new date
//     * @param date - String date to set
//     */
//    public void setDateMadeString(String date){
//        this.dateMade = formatDateMade(date);
//    }
//    /**
//     * Sets time made string to new time
//     * @param time - String time to set
//     */
//    public void setTimeMadeString(String time){
//        this.timeMade = formatTimeMade(time);
//    }


    /**
     * Gets title of task
     * @return - Title of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task
     * @param title - title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks if task is completed
     * @return bool - true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets completed to new value
     * @param completed - New completed boolean value
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

//    /**
//     * Gets the date made
//     * @return datemade
//     */
//    public Date getDateMade() {
//        return dateMade;
//    }
//
//    /**
//     * Sets the date made to new Date obj
//     * @param dateMade
//     */
//    public void setDateMade(Date dateMade) {
//        this.dateMade = dateMade;
//    }
//    /**
//     * Gets the time made
//     * @return timeMade
//     */
//    public Date getTimeMade() {
//        return timeMade;
//    }
//    /**
//     * Sets the timeMade to new Date obj
//     * @param timeMade
//     */
//    public void setTimeMade(Date timeMade) {
//        this.timeMade = timeMade;
//    }


    /**
     * Gets string value of time made
     * @return string value of time made
     */
    private String getStringTimeMade(Date datemade){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(datemade);
    }
    /**
     * Gets string value of date made
     * @return string value of date made
     */
    private String getStringDateMade(Date datemade){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datemade);
    }



    /**
     * Gets user madeby
     * @return user Obj of author of the task
     */
    public User getMadeBy() {
        return madeBy;
    }

    /**
     * Sets the user author of task
     * @param madeBy - User obj to set
     */
    public void setMadeBy(User madeBy) {
        this.madeBy = madeBy;
    }

    /**
     * gets the editing value of task
     * @return true if editing mode active
     */
    public Boolean getEditing() {
        return editing;
    }

    /**
     * Sets the editing mode
     * @param editing - boolean
     */
    public void setEditing(Boolean editing) {
        this.editing = editing;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTimeMade() {
        return timeMade;
    }

    public void setTimeMade(String timeMade) {
        this.timeMade = timeMade;
    }

    public String getDateMade() {
        return dateMade;
    }

    public void setDateMade(String dateMade) {
        this.dateMade = dateMade;
    }
}
