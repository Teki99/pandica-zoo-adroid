package com.example.padnica_zoo.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SlideshowPagerAdapter extends FragmentStateAdapter {
    public SlideshowPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SlideshowImageFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}