package com.example.bank.models;

public class UpcomingModel {


    String time;
    String code ;
    String date;

    String des;


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public UpcomingModel() {
    }

    public UpcomingModel(String time, String code, String date , String des) {
        this.time = time;
        this.code = code;
        this.date = date;
        this.des = des;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
