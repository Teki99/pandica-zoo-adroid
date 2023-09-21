package com.example.padnica_zoo.ui.packages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.pandica_zoo.models.Event;
import com.pandica_zoo.models.EventList;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.models.PackageList;
import com.pandica_zoo.utils.AssetsUtils;

import java.util.List;

public class PackagesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Package>> packages = new MutableLiveData<>();

    public PackagesViewModel(@NonNull Application application) {
        super(application);
        //get the packages from database.json file
        JsonFile jsonFile = AssetsUtils.readJsonFromFile(application);
        packages.setValue(jsonFile.getPackagesList().getPackages());

    }
    public MutableLiveData<List<Package>> getPackages() {
        return packages;
    }
    public int getPackagesSize()
    {
        return packages.getValue().size();
    }
    public Package getPackageAtIndex(int i)
    {
        return packages.getValue().get(i);
    }
}
