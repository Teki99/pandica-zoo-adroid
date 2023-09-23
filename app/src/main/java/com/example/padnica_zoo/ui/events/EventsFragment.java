package com.example.padnica_zoo.ui.events;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import com.example.padnica_zoo.R;
import com.google.gson.Gson;
import com.pandica_zoo.models.AnimalList;
import com.pandica_zoo.models.Event;
import com.pandica_zoo.models.EventList;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.PackageList;
import com.pandica_zoo.models.User;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EventsFragment extends Fragment {
    private EventsViewModel viewModel;

    //private String json;
    private Gson gson;
    private JsonFile jsonFile;
    private boolean wasLiked[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new EventsViewModel(this.getActivity().getApplication());

        String json = AssetsUtils.readJsonFromFile(this.getActivity());
        gson = new Gson();
        //jsonFile = gson.fromJson(json,JsonFile.class);


        UserList userList = gson.fromJson(json, UserList.class);
        EventList eventList = gson.fromJson(json, EventList.class);
        PackageList packageList = gson.fromJson(json, PackageList.class);
        AnimalList animalList = gson.fromJson(json, AnimalList.class);
        jsonFile=new JsonFile(userList,eventList,packageList,animalList);
        //List<User> users = userList.getUsers();


        wasLiked = new boolean[viewModel.getEventsSize()];

        LinearLayout containerLayout = root.findViewById(R.id.packages);

        for (int i=0;i<viewModel.getEventsSize();i++) {
            RelativeLayout relativeLayout = createRelativeLayoutWithTextView(this.getActivity().getApplication(), viewModel.getEventAtIndex(i));
            containerLayout.addView(relativeLayout);
        }

        return root;
    }

    private RelativeLayout createRelativeLayoutWithTextView(Application application, Event event) {
        RelativeLayout relativeLayout = new RelativeLayout(application);
        relativeLayout.setBackgroundColor(ContextCompat.getColor(application, R.color.dark_green));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                800,1200
        );
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //DATE
        TextView date = new TextView(application);
        date.setText(event.getDate());
        date.setId(View.generateViewId());
        date.setTextColor(ContextCompat.getColor(application, R.color.light_green));


        RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //NAME
        TextView name = new TextView(application);
        name.setText(event.getName());
        name.setId(View.generateViewId());
        name.setTextColor(ContextCompat.getColor(application, R.color.white));

        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.BELOW,date.getId());
        nameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

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

        RelativeLayout.LayoutParams descriptionParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        descriptionParams.addRule(RelativeLayout.BELOW,image.getId());

        //LIKE BUTTON
        ImageButton likeButton = new ImageButton(this.getActivity().getApplication());
        //!!!need it here to be visible!
        TextView numOfLikes = new TextView(application);
        likeButton.setImageResource(R.drawable.thumb_up);
        likeButton.setBackgroundColor(ContextCompat.getColor(application, R.color.dark_green));
        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!wasLiked[event.getId()-1]) //LIKE
                {
                    numOfLikes.setText(""+(event.getNumOfLikes()+1)+"");
                    event.setNumOfLikes(event.getNumOfLikes()+1);
                    likeButton.setColorFilter(ContextCompat.getColor(application, R.color.light_green), PorterDuff.Mode.SRC_ATOP);
                    wasLiked[event.getId()-1]=true;
                }
                else //UNLIKE
                {
                    numOfLikes.setText(""+(event.getNumOfLikes()-1)+"");
                    event.setNumOfLikes(event.getNumOfLikes()-1);
                    likeButton.setColorFilter(ContextCompat.getColor(application, R.color.white),PorterDuff.Mode.SRC_ATOP);
                    wasLiked[event.getId()-1]=false;
                }
                //change the event in the jsonFile
                jsonFile.getEventsList().getEvents().set(event.getId()-1,event);
                String json1 = gson.toJson(jsonFile.getUsersList());
                String json2 = gson.toJson(jsonFile.getPackagesList());
                String json3 = gson.toJson(jsonFile.getEventsList());
                String json4 = gson.toJson(jsonFile.getAnimalsList());

                String json = json1.substring(0,json1.length()-1) + ","
                        + json2.substring(1,json2.length()-1) + ","
                        + json3.substring(1,json3.length()-1) + ","
                        + json4.substring(1,json4.length());

                //write the whole jsonFile, but will it overwrite the existing db.json?
                //String json = gson.toJson(jsonFile);
                String filename = "db.json";
                if (AssetsUtils.isValidJson(json)) {
                    boolean success = AssetsUtils.writeJsonFile(application, json);
                    Log.d("WriteJsonFile", "Write success: " + success);
                    if (success) {
                        // File was written successfully.
                    } else {
                        // There was an error writing the file.
                    }
                }


            }
        });


        likeButton.setId(View.generateViewId());

        RelativeLayout.LayoutParams likeButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        likeButtonParams.addRule(RelativeLayout.BELOW,description.getId());

        //NUMBER OF LIKES
        numOfLikes.setText(""+event.getNumOfLikes()+"");
        numOfLikes.setId(View.generateViewId());
        numOfLikes.setTextColor(ContextCompat.getColor(application, R.color.white));

        RelativeLayout.LayoutParams numOfLikesParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        numOfLikesParams.addRule(RelativeLayout.BELOW,description.getId());
        numOfLikesParams.addRule(RelativeLayout.RIGHT_OF,likeButton.getId());

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

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new EventsViewModel(this.getActivity().getApplication());

        LinearLayout layout = root.findViewById(R.id.packages);

        for (int i = 0; i < viewModel.getEventsSize(); i++) {

            RelativeLayout card = new RelativeLayout(this.getActivity().getApplication());
            RelativeLayout.LayoutParams cardParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            // Date TextView
            TextView date = new TextView(this.getActivity().getApplication());
            date.setText(viewModel.getEventAtIndex(i).getDate());
            date.setId(View.generateViewId()); // Generate a unique ID for this view
            card.addView(date, cardParams);

            // Uncomment and add other views like name, image, description, likeButton, numOfLikes as needed

            // Add the card to the parent LinearLayout
            layout.addView(card, cardParams);
        }

        return root;
    }*/
