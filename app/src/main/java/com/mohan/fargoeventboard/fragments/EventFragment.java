package com.mohan.fargoeventboard.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.EventViewModel;
import com.mohan.fargoeventboard.data.Event;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


public class EventFragment extends Fragment {

    private EventFragment.OnListFragmentInteractionListener mListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EventViewModel viewModel;

    private int eventId;

    @BindView(R.id.event_detail_image)
    ImageView imageView;

    @BindView(R.id.event_detail_title)
    TextView titleTextView;

    @BindView(R.id.event_detail_date)
    TextView dateTextView;

    @BindView(R.id.event_detail_desc)
    TextView descriptionTextView;

    @BindView(R.id.location_btn)
    Button locationButton;

    @BindView(R.id.speaker_list)
    RecyclerView recyclerView;

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventId = EventFragmentArgs.fromBundle(getArguments()).getEventId();
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel.class);
        //Setup the Event details to display
        viewModel.getEvent(eventId).observe(this, event -> {
            Picasso.get().load(event.getImage_url()).into(imageView);
            titleTextView.setText(event.getTitle());
            descriptionTextView.setText(event.getEvent_description());
            dateTextView.setText(event.getPrettyDate());
            locationButton.setText(event.getLocation());
        });
        //Setup the speaker to display
        viewModel.getSpeakers(eventId).observe(this, speakers -> {
            recyclerView.setAdapter(new SpeakerRecyclerViewAdapter(speakers, mListener));
        });

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Event event);
    }

}
