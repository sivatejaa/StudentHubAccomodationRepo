package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Accommodation implements Parcelable {

    private PersonalInfo personalInfo;
    private Room roomInfo;
    private int imageResource;

    private String uidNo;
    private String confirmationNumber;

    private String decisionStatus;
    private String name;
    private String description;

    private String selectedRoomType;
    private String numberOfRooms;
    private String checkinDate;

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    private String checkoutDate;

    private String leaseTerm;

    public String getUidNo() {
        return uidNo;
    }

    public void setUidNo(String uidNo) {
        this.uidNo = uidNo;
    }


    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }


    public Room getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(Room roomInfo) {
        this.roomInfo = roomInfo;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDecisionStatus() {
        return decisionStatus;
    }

    public void setDecisionStatus(String decisionStatus) {
        this.decisionStatus = decisionStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedRoomType() {
        return selectedRoomType;
    }

    public void setSelectedRoomType(String selectedRoomType) {
        this.selectedRoomType = selectedRoomType;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    private double price;

    public Accommodation(PersonalInfo personalInfo, Room roomInfo, int imageResource, String confirmationNumber, String decisionStatus, String name, String description, String selectedRoomType, String numberOfRooms, String checkinDate, String checkoutDate, double price) {
        this.personalInfo = personalInfo;
        this.roomInfo = roomInfo;
        this.imageResource = imageResource;
        this.confirmationNumber = confirmationNumber;
        this.decisionStatus = decisionStatus;
        this.name = name;
        this.description = description;
        this.selectedRoomType = selectedRoomType;
        this.numberOfRooms = numberOfRooms;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
    }

    public Accommodation(int imageResource, String name, String description) {
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
    }

    protected Accommodation(Parcel in) {
        imageResource = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<Accommodation> CREATOR = new Creator<Accommodation>() {
        @Override
        public Accommodation createFromParcel(Parcel in) {
            return new Accommodation(in);
        }

        @Override
        public Accommodation[] newArray(int size) {
            return new Accommodation[size];
        }
    };

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(name);
        dest.writeString(description);
    }
}
