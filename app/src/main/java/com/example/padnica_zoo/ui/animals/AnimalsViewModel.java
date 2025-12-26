package com.example.padnica_zoo.ui.animals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pandica_zoo.models.Animal;
import com.pandica_zoo.models.Event;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.utils.AssetsUtils;

import java.util.List;

public class AnimalsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Animal>> animals = new MutableLiveData<>();

    public AnimalsViewModel(@NonNull Application application) {
        super(application);
        //get the events from database.json file
        JsonFile jsonFile = AssetsUtils.readJsonFromFile(application);
        animals.setValue(jsonFile.getAnimalsList().getAnimals());
    }

    public MutableLiveData<List<Animal>> getAnimals() {
        return animals;
    }
    public int getAnimalsSize()
    {
        return animals.getValue().size();
    }
    public Animal getAnimalsAtIndex(int i)
    {
        return animals.getValue().get(i);
    }
}
