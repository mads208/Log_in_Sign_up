package com.example.loginsignup.DailyRating;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rating {
    private String seekbarPercent;
    private String currentDate;
    public Rating() {
    }

    public Rating(String currentDate, String seekbarPercent) {
        this.seekbarPercent = seekbarPercent;
        this.currentDate = currentDate;
    }
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSeekbarPercent() {
        return seekbarPercent;
    }

    public void setSeekbarPercent(String seekbarPercent) {
        this.seekbarPercent = seekbarPercent;
    }






}
