package com.mohan.fargoeventboard.fragments;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.fragments.EventListFragment.OnListFragmentInteractionListener;
import com.mohan.fargoeventboard.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;
    DateFormat dateFormat;

    public EventRecyclerViewAdapter(List<Event> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.get().load(mValues.get(position).getImage_url()).into(holder.mImageView);
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mStartDateView.setText(dateFormat.format(mValues.get(position).getStart_date_time()));
        holder.mEndDateView.setText(dateFormat.format(mValues.get(position).getEnd_date_time()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTitleView;
        public final TextView mStartDateView;
        public final TextView mEndDateView;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.event_image);
            mTitleView = (TextView) view.findViewById(R.id.event_title);
            mStartDateView = (TextView) view.findViewById(R.id.event_start_date);
            mEndDateView = (TextView) view.findViewById(R.id.event_end_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
