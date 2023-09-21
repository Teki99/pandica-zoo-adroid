package com.example.padnica_zoo.ui.events;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.pandica_zoo.models.Event;
import com.pandica_zoo.models.EventList;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.User;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Event>> events = new MutableLiveData<>();

    public EventsViewModel(@NonNull Application application) {
        super(application);
        //get the events from database.json file
        JsonFile jsonFile = AssetsUtils.readJsonFromFile(application);
        events.setValue(jsonFile.getEventsList().getEvents());
    }

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }
    public int getEventsSize()
    {
        return events.getValue().size();
    }
    public Event getEventAtIndex(int i)
    {
        return events.getValue().get(i);
    }
}
