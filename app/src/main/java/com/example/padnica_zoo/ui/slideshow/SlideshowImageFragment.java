package com.example.padnica_zoo.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.padnica_zoo.R;

public class SlideshowImageFragment extends Fragment {

    private static final String ARG_IMAGE_INDEX = "image_index";
    private SlideshowViewModel viewModel;

    public static SlideshowImageFragment newInstance(SlideshowViewModel viewModel) {
        SlideshowImageFragment fragment = new SlideshowImageFragment();
        fragment.viewModel = viewModel; // Pass the viewModel to the fragment
        return fragment;
    }
    public static SlideshowImageFragment newInstance(int imageIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_INDEX, imageIndex);
        SlideshowImageFragment fragment = new SlideshowImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow_image, container, false);
        // Load and display the image here using the argument image index
        int imageIndex = getArguments().getInt(ARG_IMAGE_INDEX);

        viewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        SlideshowImageFragment fragment = SlideshowImageFragment.newInstance(viewModel);
        int imageRes = fragment.viewModel.getImageResId(imageIndex);

        // Use an ImageView to display the image
        ImageView imageView = root.findViewById(R.id.slideshowImageView);
        imageView.setImageResource(imageRes);
        return root;
    }
}