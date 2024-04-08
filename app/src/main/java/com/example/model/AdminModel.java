package com.example.model;

public class AdminModel {

    private String confirmationNumber;

    private String uidNo;
    private String name;

    private String house;
    private String rooms;
    private String decisionStatus;

    private String nameDetail;
    private String houseDetail;
    private String roomsDetail;
    private String decisionStatusDetail;

    public String getUidNo() {
        return uidNo;
    }

    public void setUidNo(String uidNo) {
        this.uidNo = uidNo;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }


    public AdminModel() {
        this.name = "name";
        this.house = "house";
        this.rooms = "rooms";

    }

    public AdminModel(String name, String house, String rooms, String decisionStatus, String nameDetail, String houseDetail, String roomsDetail, String decisionStatusDetail) {
        this.name = name;
        this.house = house;
        this.rooms = rooms;
        this.decisionStatus = decisionStatus;
        this.nameDetail = nameDetail;
        this.houseDetail = houseDetail;
        this.roomsDetail = roomsDetail;
        this.decisionStatusDetail = decisionStatusDetail;
    }

    public String getNameDetail() {
        return nameDetail;
    }

    public void setNameDetail(String nameDetail) {
        this.nameDetail = nameDetail;
    }

    public String getHouseDetail() {
        return houseDetail;
    }

    public void setHouseDetail(String houseDetail) {
        this.houseDetail = houseDetail;
    }

    public String getRoomsDetail() {
        return roomsDetail;
    }

    public void setRoomsDetail(String roomsDetail) {
        this.roomsDetail = roomsDetail;
    }

    public String getDecisionStatusDetail() {
        return decisionStatusDetail;
    }

    public void setDecisionStatusDetail(String decisionStatusDetail) {
        this.decisionStatusDetail = decisionStatusDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getDecisionStatus() {
        return decisionStatus;
    }

    public void setDecisionStatus(String decisionStatus) {
        this.decisionStatus = decisionStatus;
    }

}
