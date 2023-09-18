package com.example.padnica_zoo.ui.events;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
import com.pandica_zoo.models.Event;

public class EventsFragment extends Fragment {
    private EventsViewModel viewModel;

    private boolean wasLiked[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new EventsViewModel(this.getActivity().getApplication());

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
        //need it here to be visible!
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
                    //TODO: implement the changes in the db.json, with the write in the json!
                }
                else //UNLIKE
                {
                    numOfLikes.setText(""+(event.getNumOfLikes()-1)+"");
                    event.setNumOfLikes(event.getNumOfLikes()-1);
                    likeButton.setColorFilter(ContextCompat.getColor(application, R.color.white),PorterDuff.Mode.SRC_ATOP);
                    wasLiked[event.getId()-1]=false;
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
