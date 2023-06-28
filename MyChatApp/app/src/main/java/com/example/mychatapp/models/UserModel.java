package com.example.mychatapp.models;

public class UserModel {
    private String name;
    private String email;
    private String uId;
    private String lastMessage;

    public UserModel(String name, String email, String uId, String lastMessage) {
        this.name = name;
        this.email = email;
        this.uId = uId;
        this.lastMessage = lastMessage;
    }

    public UserModel(String uId, String name, String email) {
        this.uId = uId;
        this.name = name;
        this.email = email;
    }

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uId='" + uId + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                '}';
    }
}
