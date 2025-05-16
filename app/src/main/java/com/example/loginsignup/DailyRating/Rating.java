package com.example.loginsignup.DailyRating;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rating {
    private String seekbarPercent;
    private String createdDate;
    public Rating() {
    }

    public Rating(String currentDate, String seekbarPercent) {
        this.seekbarPercent = seekbarPercent;
        this.createdDate = currentDate;
    }
    public String getCurrentDate() {
        return createdDate;
    }

    public void setCurrentDate(String currentDate) {
        this.createdDate = currentDate;
    }

    public String getSeekbarPercent() {
        return seekbarPercent;
    }

    public void setSeekbarPercent(String seekbarPercent) {
        this.seekbarPercent = seekbarPercent;
    }






}
