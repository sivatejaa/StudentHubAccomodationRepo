package com.example.model;

public class ChatUser {
    String mail, name, password, uId;

    public ChatUser() {

    }

    public ChatUser(String mail, String name, String password, String uId) {
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.uId = uId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}