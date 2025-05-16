package com.example.loginsignup.journaling;

public class Journal {
    private String Title2;
    private String Text2;

    public Journal() {
    }

    public Journal(String Title2, String Text2) {
        this.Title2 = Title2;
        this.Text2 = Text2;
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


}
