package com.example.a300cemandroid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task {
    private String title;
    private boolean completed;
    private SimpleDateFormat dateMade = new SimpleDateFormat("dd-MM-yy");
    private SimpleDateFormat timeMade = new SimpleDateFormat("HH:mm:ss");
    private User madeBy;


    public Task(){

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

    public SimpleDateFormat getDateMade() {
        return dateMade;
    }

    public void setDateMade(SimpleDateFormat dateMade) {
        this.dateMade = dateMade;
    }

    public SimpleDateFormat getTimeMade() {
        return timeMade;
    }

    public void setTimeMade(SimpleDateFormat timeMade) {
        this.timeMade = timeMade;
    }



    public String getStringTimeMade(){
        return timeMade.format(new Date());
    }

    public String getStringDateMade(){
        return dateMade.format(new Date());
    }

    public User getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(User madeBy) {
        this.madeBy = madeBy;
    }
}
