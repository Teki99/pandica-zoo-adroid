package com.pandica_zoo.models;

public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String contact;
    private String address;
    private String type; //visitor, employee
    private Package[] packages;
    private Notification[] notifications;
    private Integer id;

    //Constructor
    public User(String firstname, String lastname, String username, String password, String contact, String address, String type, Package[] packages, Notification[] notifications, Integer id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.type = type;
        this.packages = packages;
        this.notifications = notifications;
        this.id = id;
    }

    // Getters and Setters for the fields
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Package[] getPackages() {
        return packages;
    }

    public void setPackages(Package[] packages) {
        this.packages = packages;
    }

    public Notification[] getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification[] notifications) {
        this.notifications = notifications;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
