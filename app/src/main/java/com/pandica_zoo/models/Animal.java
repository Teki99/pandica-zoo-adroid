package com.pandica_zoo.models;

public class Animal {
    private String name;
    private String description;
    private String image;
    private Comment[] comments;
    private Integer id;

    public Animal(String name, String description, String image, Comment[] comments, Integer id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.comments = comments;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
