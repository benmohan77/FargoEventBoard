package com.mohan.fargoeventboard.fragments;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohan.fargoeventboard.R;
import com.mohan.fargoeventboard.data.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView Adapter for Speakers in the Event Fragment.
 */
public class SpeakerRecyclerViewAdapter extends  RecyclerView.Adapter<SpeakerRecyclerViewAdapter.ViewHolder> {

    private List<Speaker> mValues;
    private final EventFragment.OnListFragmentInteractionListener mListener;

    public SpeakerRecyclerViewAdapter(List<Speaker> items, EventFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public SpeakerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speaker_entry, parent, false);
        return new SpeakerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Speaker speaker =  mValues.get(position);
        holder.mItem = speaker;
        holder.mNameView.setText(speaker.getFirst_name() + " " + speaker.getLast_name());
        holder.mBioView.setText(speaker.getBio());
        Picasso.get().load(speaker.getImage_url()).into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mBioView;
        public final ImageView mImageView;
        public Speaker mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView)view.findViewById(R.id.speaker_name);
            mBioView = (TextView)view.findViewById(R.id.speaker_bio);
            mImageView = (ImageView)view.findViewById(R.id.speaker_image);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
