package com.example.padnica_zoo.ui.events;

import android.app.Application;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.example.padnica_zoo.R;
import com.pandica_zoo.models.Event;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.utils.AssetsUtils;
import com.pandica_zoo.utils.TinyDB;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    private EventsViewModel viewModel;
    private JsonFile jsonFile;
    private ArrayList<Boolean> wasLiked;
    private TinyDB tinydb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new EventsViewModel(this.getActivity().getApplication());
        jsonFile = AssetsUtils.readJsonFromFile(this.getActivity());

        tinydb = new TinyDB(this.getActivity());
        wasLiked = tinydb.getListBoolean("wasLiked");

        LinearLayout containerLayout = root.findViewById(R.id.events);
        for (int i=0;i<viewModel.getEventsSize();i++) {
            RelativeLayout relativeLayout = createRelativeLayoutWithTextView(this.getActivity().getApplication(), viewModel.getEventAtIndex(i));
            containerLayout.addView(relativeLayout);
        }
        return root;
    }

    private RelativeLayout createRelativeLayoutWithTextView(Application application, Event event) {
        Typeface customTypeface = ResourcesCompat.getFont(application, R.font.irish_grover);
        Drawable outlineLightGreen = ContextCompat.getDrawable(application, R.drawable.light_green_outline);

        RelativeLayout relativeLayout = new RelativeLayout(application);
        relativeLayout.setBackgroundResource(R.drawable.dark_green_outline);
        relativeLayout.setBackgroundTintList(ContextCompat.getColorStateList(application, R.color.dark_green));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                850,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.topMargin = 20;
        layoutParams.bottomMargin = 20;

        //DATE
        TextView date = new TextView(application);
        date.setText(event.getDate());
        date.setId(View.generateViewId());
        date.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        date.setTypeface(customTypeface);
        date.setPadding(20,10,20,10);

        RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        dateParams.bottomMargin=10;

        //NAME
        TextView name = new TextView(application);
        name.setText(event.getName());
        name.setId(View.generateViewId());
        name.setTextColor(ContextCompat.getColor(application, R.color.white));
        name.setTypeface(customTypeface);
        name.setTextSize(20);
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.BELOW,date.getId());
        nameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        nameParams.bottomMargin=10;

        //IMAGE
        ImageView image = new ImageView(this.getActivity());
        int dotIndex = event.getImage().lastIndexOf(".");
        String imageNameWithoutExtension = event.getImage().substring(0, dotIndex);
        int resourceId = getResources().getIdentifier(imageNameWithoutExtension, "drawable", this.getActivity().getPackageName());

        if (resourceId != 0) {
            Glide.with(this)
                    .load(resourceId)
                    .into(image);
        } else {
            // Handle the case where the resource does not exist
        }
        image.setId(View.generateViewId());

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        imageParams.addRule(RelativeLayout.BELOW,name.getId());

        //DESCRIPTION
        TextView description = new TextView(application);
        description.setText(event.getDescription());
        description.setId(View.generateViewId());
        description.setTextColor(ContextCompat.getColor(application, R.color.white));
        description.setTypeface(customTypeface);
        description.setPadding(20,10,20,10);

        RelativeLayout.LayoutParams descriptionParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        descriptionParams.addRule(RelativeLayout.BELOW,image.getId());

        //-----------------------------------LIKE BUTTON--------------------------------------------
        ImageButton likeButton = new ImageButton(this.getActivity().getApplication());
        //!!!need it here to be visible!
        TextView numOfLikes = new TextView(application);
        likeButton.setImageResource(R.drawable.ic_like);
        if(!wasLiked.get(event.getId()-1)) //not liked
        {
            likeButton.setColorFilter(ContextCompat.getColor(application, R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        else
        {
            likeButton.setColorFilter(ContextCompat.getColor(application, R.color.light_green), PorterDuff.Mode.SRC_ATOP);
        }
            likeButton.setBackgroundColor(ContextCompat.getColor(application, R.color.dark_green));
        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!wasLiked.get(event.getId()-1)) //LIKE
                {
                    numOfLikes.setText(""+(event.getNumOfLikes()+1)+"");
                    event.setNumOfLikes(event.getNumOfLikes()+1);
                    likeButton.setColorFilter(ContextCompat.getColor(application, R.color.light_green), PorterDuff.Mode.SRC_ATOP);
                    wasLiked.set(event.getId()-1,true);
                }
                else //UNLIKE
                {
                    numOfLikes.setText(""+(event.getNumOfLikes()-1)+"");
                    event.setNumOfLikes(event.getNumOfLikes()-1);
                    likeButton.setColorFilter(ContextCompat.getColor(application, R.color.white),PorterDuff.Mode.SRC_ATOP);
                    wasLiked.set(event.getId()-1,false);
                }
                //update wasLiked
                tinydb.putListBoolean("wasLiked", wasLiked);
                //change the event in the jsonFile
                jsonFile.getEventsList().getEvents().set(event.getId()-1,event);
                //update json file
                AssetsUtils.updateJsonFile(jsonFile,application);

            }
        });

        likeButton.setId(View.generateViewId());

        RelativeLayout.LayoutParams likeButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        likeButtonParams.addRule(RelativeLayout.BELOW,description.getId());

        //--------------------------------NUMBER OF LIKES-------------------------------------------
        numOfLikes.setText(""+event.getNumOfLikes()+"");
        numOfLikes.setId(View.generateViewId());
        numOfLikes.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        numOfLikes.setTypeface(customTypeface);
        numOfLikes.setTextSize(25);
        numOfLikes.setBackground(outlineLightGreen);
        numOfLikes.setPadding(20,10,20,10);

        RelativeLayout.LayoutParams numOfLikesParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        numOfLikesParams.addRule(RelativeLayout.BELOW,description.getId());
        numOfLikesParams.addRule(RelativeLayout.RIGHT_OF,likeButton.getId());
        numOfLikesParams.leftMargin=20;
        numOfLikesParams.addRule(RelativeLayout.CENTER_VERTICAL);

        relativeLayout.addView(date, dateParams);
        relativeLayout.addView(name, nameParams);
        relativeLayout.addView(image, imageParams);
        relativeLayout.addView(description, descriptionParams);
        relativeLayout.addView(likeButton, likeButtonParams);
        relativeLayout.addView(numOfLikes, numOfLikesParams);
        relativeLayout.setLayoutParams(layoutParams);

        return relativeLayout;
    }
}
