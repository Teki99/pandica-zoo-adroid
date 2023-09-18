package com.example.padnica_zoo.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.padnica_zoo.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel viewModel;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        viewPager = root.findViewById(R.id.viewPager2);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        // Set up ViewPager2 and Adapter
        SlideshowPagerAdapter pagerAdapter = new SlideshowPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        // Observe the current image index from the ViewModel
        viewModel.getCurrentImageIndex().observe(getViewLifecycleOwner(), index -> {
            viewPager.setCurrentItem(index, true); // Auto-scroll to the current image
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}