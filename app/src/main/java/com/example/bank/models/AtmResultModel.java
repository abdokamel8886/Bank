package com.example.bank.models;

public class AtmResultModel {

    private Integer id;
    private String address;
    private Integer queue;
    private String status;
    private String type;
    private Integer money;
    private Integer waiting;
    private Integer distance;


    public AtmResultModel(Integer id, String address, Integer queue, String status, String type, Integer money, Integer waiting, Integer distance) {
        this.id = id;
        this.address = address;
        this.queue = queue;
        this.status = status;
        this.type = type;
        this.money = money;
        this.waiting = waiting;
        this.distance = distance;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
