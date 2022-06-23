package com.example.bank.models;



public class QModel {


    private String ans;
    private String ques;

    public QModel() {
    }

    public QModel(String ans, String ques) {
        this.ans = ans;
        this.ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }
}
