package com.example.padnica_zoo.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.padnica_zoo.R;

import java.util.Arrays;
import java.util.List;

public class SlideshowViewModel extends ViewModel {
    private MutableLiveData<Integer> currentImageIndex = new MutableLiveData<>();
    private int[] imageResIds = { R.drawable.carousel1, R.drawable.carousel2, R.drawable.carousel3 }; // Add your image resource IDs here

    public LiveData<Integer> getCurrentImageIndex() {
        return currentImageIndex;
    }

    public void setCurrentImageIndex(int index) {
        currentImageIndex.setValue(index);
    }

    public int getImageResId(int index) {
        return imageResIds[index];
    }
}