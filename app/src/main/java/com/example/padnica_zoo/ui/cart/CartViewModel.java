package com.example.padnica_zoo.ui.cart;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.models.User;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private MutableLiveData<List<Package>> packagesInCart = new MutableLiveData<>();
    public CartViewModel(@NonNull Application application) {
        super(application);

        //get logged in user
        User user = AssetsUtils.getLoggedUser(application);
        packagesInCart.setValue(user.getPackages());

    }

    public MutableLiveData<List<Package>> getPackages() {
        return packagesInCart;
    }
    public int getPackagesSize()
    {
        return packagesInCart.getValue().size();
    }
    public Package getPackageAtIndex(int i)
    {
        return packagesInCart.getValue().get(i);
    }
}
