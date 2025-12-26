package com.example.padnica_zoo.ui.animals;

import android.app.Application;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.padnica_zoo.R;
import com.example.padnica_zoo.ui.events.EventsViewModel;
import com.pandica_zoo.models.Animal;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.utils.AssetsUtils;
import com.pandica_zoo.utils.TinyDB;
import android.util.Log;

import java.util.ArrayList;

public class AnimalsFragment extends Fragment {
    private AnimalsViewModel viewModel;
    private JsonFile jsonFile;
    private TinyDB tinydb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new AnimalsViewModel(this.getActivity().getApplication());
        jsonFile = AssetsUtils.readJsonFromFile(this.getActivity());

        tinydb = new TinyDB(this.getActivity());

        LinearLayout containerLayout = root.findViewById(R.id.events);
        for (int i=0;i<viewModel.getAnimalsSize();i++) {
            RelativeLayout relativeLayout = createRelativeLayoutWithTextView(this.getActivity().getApplication(), viewModel.getAnimalsAtIndex(i));
            containerLayout.addView(relativeLayout);
        }
        return root;
    }
    private RelativeLayout createRelativeLayoutWithTextView(Application application, Animal animal) {
        Typeface customTypeface = ResourcesCompat.getFont(application, R.font.irish_grover);

        RelativeLayout relativeLayout = new RelativeLayout(application);
        relativeLayout.setBackgroundResource(R.drawable.dark_green_outline);
        relativeLayout.setBackgroundTintList(ContextCompat.getColorStateList(application, R.color.dark_green));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                800,
                1100
        );
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //NAME
        TextView name = new TextView(application);
        Log.i("nameTag", animal.getName());

        name.setText("The "+animal.getName());
        name.setId(View.generateViewId());
        name.setTextColor(ContextCompat.getColor(application, R.color.white));
        name.setTypeface(customTypeface);
        name.setTextSize(34);

        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        nameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //IMAGE
        ImageView image = new ImageView(this.getActivity());
        int dotIndex = animal.getImage().lastIndexOf(".");
        String imageNameWithoutExtension = animal.getImage().substring(0, dotIndex);
        int resourceId = getResources().getIdentifier(imageNameWithoutExtension, "drawable", this.getActivity().getPackageName());

        if (resourceId != 0) {
            Glide.with(this)
                    .load(resourceId)
                    .into(image);
        } else {
            Log.e("Image", animal.getName()+" resource doesn't exist");
        }
        image.setId(View.generateViewId());

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        imageParams.addRule(RelativeLayout.BELOW,name.getId());

        relativeLayout.addView(name, nameParams);
        relativeLayout.addView(image, imageParams);
        relativeLayout.setLayoutParams(layoutParams);

        return relativeLayout;
    }
}
