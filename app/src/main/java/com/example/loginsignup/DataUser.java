package com.example.loginsignup;

public class DataUser {
    private String MusicGenre;
    private String Goal;
    private String TaskDays;
   // private String photo;

    public DataUser(String musicGenre, String goal, String taskDays) {
        MusicGenre = musicGenre;
        Goal = goal;
        TaskDays = taskDays;
        //this.photo = photo;
    }

    public String getMusicGenre() {
        return MusicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        MusicGenre = musicGenre;
    }

    public String getGoal() {
        return Goal;
    }

    public void setGoal(String goal) {
        Goal = goal;
    }

    public String getTaskDays() {
        return TaskDays;
    }

    public void setTaskDays(String taskDays) {
        TaskDays = taskDays;
    }

    /*public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }*/
}
