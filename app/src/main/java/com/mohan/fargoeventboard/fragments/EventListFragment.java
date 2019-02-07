package com.mohan.fargoeventboard.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.ViewModel.EventListViewModel;
import com.mohan.fargoeventboard.ViewModel.LoginViewModel;
import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.fragments.dummy.DummyContent;
import com.mohan.fargoeventboard.fragments.dummy.DummyContent.DummyItem;

import javax.inject.Inject;

/**
 * A fragment representing a list of Events.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EventListFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EventListViewModel viewModel;
    private RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventListViewModel.class);
        viewModel.getEvents().observe(this, events -> {
            recyclerView.setAdapter(new EventRecyclerViewAdapter(events, mListener));
        });

    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Event event);
    }
}
