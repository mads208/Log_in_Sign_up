package com.example.loginsignup.journaling;

public class Journal {
    private String Title2;
    private String Text2;
    private String createdDateJournal;
    private String userId;


    public Journal() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Journal(String Title2, String Text2, String createdDateJournal, String userId) {
        this.Title2 = Title2;
        this.Text2 = Text2;
        this.createdDateJournal = createdDateJournal;
        this.userId=userId;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }

    public String getTitle2() {
        return Title2;
    }

    public void setTitle2(String title2) {
        Title2 = title2;
    }
    public String getCreatedDateJournal() {
        return createdDateJournal;
    }

    public void setCreatedDateJournal(String createdDateJournal) {
        this.createdDateJournal = createdDateJournal;
    }


}
