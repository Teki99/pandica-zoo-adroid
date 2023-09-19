package com.pandica_zoo.models;

public class JsonFile {

    private UserList usersList;
    private EventList eventsList;
    private PackageList packagesList;
    private AnimalList animalsList;

    public JsonFile(UserList usersList, EventList eventsList, PackageList packagesList, AnimalList animalsList) {
        this.usersList = usersList;
        this.eventsList = eventsList;
        this.packagesList = packagesList;
        this.animalsList = animalsList;
    }

    public UserList getUsersList() {
        return usersList;
    }

    public void setUsersList(UserList usersList) {
        this.usersList = usersList;
    }

    public EventList getEventsList() {
        return eventsList;
    }

    public void setEventsList(EventList eventsList) {
        this.eventsList = eventsList;
    }

    public PackageList getPackagesList() {
        return packagesList;
    }

    public void setPackagesList(PackageList packagesList) {
        this.packagesList = packagesList;
    }

    public AnimalList getAnimalsList() {
        return animalsList;
    }

    public void setAnimalsList(AnimalList animalsList) {
        this.animalsList = animalsList;
    }
}
