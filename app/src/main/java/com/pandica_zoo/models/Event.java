package com.pandica_zoo.models;

public class Event {
    private String name;
    private String date;
    private String description;
    private Integer numOfLikes;
    private String image;
    private Integer id;

    public Event(String name, String date, String description, Integer numOfLikes, String image, Integer id) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.numOfLikes = numOfLikes;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(Integer numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
