package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    private String selectedRoomType;
    private String numberOfRooms;
    private String checkinDate;
    private String checkoutDate;



    public Room(String selectedRoomType, String numberOfRooms, String checkinDate, String checkoutDate) {
        this.selectedRoomType = selectedRoomType;
        this.numberOfRooms = numberOfRooms;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    protected Room(Parcel in) {
        selectedRoomType = in.readString();
        numberOfRooms = in.readString();
        checkinDate = in.readString();
        checkoutDate = in.readString();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };


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

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedRoomType);
        dest.writeString(numberOfRooms);
        dest.writeString(checkinDate);
        dest.writeString(checkoutDate);
    }
}

