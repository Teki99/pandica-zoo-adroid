package com.example.padnica_zoo.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.padnica_zoo.R;

public class EventsFragment extends Fragment {
    private EventsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        viewModel = new EventsViewModel(this.getActivity().getApplication());

        LinearLayout layout = (LinearLayout) root.findViewById(R.id.packages);
        TextView t[] = new TextView[viewModel.getEventsSize()];
        LinearLayout.LayoutParams dim=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<viewModel.getEventsSize();i++)
        {
            t[i]=new TextView(this.getActivity().getApplication());
            t[i].setLayoutParams(dim);
            t[i].setText(viewModel.getEventAtIndex(i).getDescription());
            layout.addView(t[i]);
        }
        return root;
    }
}
