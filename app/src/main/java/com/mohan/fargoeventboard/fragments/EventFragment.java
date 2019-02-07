package com.mohan.fargoeventboard.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.EventListViewModel;
import com.mohan.fargoeventboard.ViewModel.EventViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


public class EventFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EventViewModel viewModel;

    private int eventId;

    @BindView(R.id.event_detail_image)
    ImageView imageView;

    @BindView(R.id.event_detail_title)
    TextView titleTextView;

    @BindView(R.id.event_detail_start_date)
    TextView startDateTextView;

    @BindView(R.id.event_detail_end_date)
    TextView endDateTextView;

    @BindView(R.id.event_detail_desc)
    TextView descriptionTextView;

    @BindView(R.id.event_detail_loc)
    TextView locationTextView;

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel.class);
        viewModel.getEvent(eventId).observe(this, event -> {
            Picasso.get().load(event.getImage_url()).into(imageView);
            titleTextView.setText(event.getTitle());
            startDateTextView.setText(event.getStart_date_time().toString());
            endDateTextView.setText(event.getEnd_date_time().toString());
            locationTextView.setText(event.getLocation());
        });

    }

}
