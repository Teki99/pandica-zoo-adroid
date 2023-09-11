package com.example.padnica_zoo.ui.slideshow;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SlideshowPagerAdapter extends FragmentStateAdapter {
    public SlideshowPagerAdapter(Fragment fragment) {
        super(fragment);
    }

/*   @Override
    public Fragment getItem(int position) {
        return SlideshowImageFragment.newInstance(position);
    }

     @Override
    public int getCount() {
        //return imageResIds.size(); // Number of images in the slideshow
        return 0;
    }*/

    @Override
    public Fragment createFragment(int position) {
        // Return the fragment for the corresponding position
      /*  switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            // Add more cases for additional fragments
            default:
                return new DefaultFragment();
        }*/
        return SlideshowImageFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}