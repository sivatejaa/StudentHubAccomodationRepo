package com.example.model;

public class BookingDetails {

    private String confirmationNumber;
    private String aptInfo;
    private String price;

    private String status;

    public BookingDetails() {
    }

    public BookingDetails(String confirmationNumber, String aptInfo, String price, String status) {
        this.confirmationNumber = confirmationNumber;
        this.aptInfo = aptInfo;
        this.price = price;
        this.status = status;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getAptInfo() {
        return aptInfo;
    }

    public void setAptInfo(String aptInfo) {
        this.aptInfo = aptInfo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
