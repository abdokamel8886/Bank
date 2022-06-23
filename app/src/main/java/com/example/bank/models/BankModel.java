package com.example.bank.models;

public class BankModel {


    private String address;
    private int bankingTeller;
    private int customerTeller;
    private int id;
    private int loanTeller;
    private LocationDTO location;
    private String name;
    private int waiting;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBankingTeller() {
        return bankingTeller;
    }

    public void setBankingTeller(int bankingTeller) {
        this.bankingTeller = bankingTeller;
    }

    public int getCustomerTeller() {
        return customerTeller;
    }

    public void setCustomerTeller(int customerTeller) {
        this.customerTeller = customerTeller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanTeller() {
        return loanTeller;
    }

    public void setLoanTeller(int loanTeller) {
        this.loanTeller = loanTeller;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public static class LocationDTO {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
