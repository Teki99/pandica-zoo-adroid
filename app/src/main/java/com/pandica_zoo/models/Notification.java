package com.pandica_zoo.models;

public class Notification {
    private Package[] packages;
    private Integer price;
    private String status;
    private String dateOfConfirmation;

    public Notification(Package[] packages, Integer price, String status, String dateOfConfirmation) {
        this.packages = packages;
        this.price = price;
        this.status = status;
        this.dateOfConfirmation = dateOfConfirmation;
    }

    public Package[] getPackages() {
        return packages;
    }

    public void setPackages(Package[] packages) {
        this.packages = packages;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateOfConfirmation() {
        return dateOfConfirmation;
    }

    public void setDateOfConfirmation(String dateOfConfirmation) {
        this.dateOfConfirmation = dateOfConfirmation;
    }
}
