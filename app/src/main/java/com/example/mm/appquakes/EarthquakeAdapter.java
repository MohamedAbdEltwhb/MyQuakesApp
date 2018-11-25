package com.example.mm.appquakes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakHolder> {

    private Context mContext;

    private ArrayList<Earthquake> mEarthquakesList;

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context mContext, ArrayList<Earthquake> mEarthquakesList) {
        this.mContext = mContext;
        this.mEarthquakesList = mEarthquakesList;
    }

    @NonNull
    @Override
    public EarthquakHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.earthquake_list_item, parent, false);
        return new EarthquakHolder(mView);
    }

    @Override
    public void onBindViewHolder(EarthquakHolder holder, int position) {
        Earthquake mEarthquake = mEarthquakesList.get(position);

        String formattedMagnitude = MagnitudeFormatAndColor.formatMagnitude(mEarthquake.getmMagnitude());
        holder.magnitudeView.setText(formattedMagnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable)holder.magnitudeView.getBackground();
        int magnitudeColor = MagnitudeFormatAndColor.getMagnitudeColor(mContext,mEarthquake.getmMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String originalLocation = mEarthquake.getmLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = mContext.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        holder.primaryLocationView.setText(primaryLocation);
        holder.locationOffsetView.setText(locationOffset);

        Date dateObject = new Date(mEarthquake.getmTimeInMilliseconds());
        String formattedDate = FormatDateTime.formatDate(dateObject);
        holder.dateView.setText(formattedDate);

        String formattedTime = FormatDateTime.formatTime(dateObject);
        holder.timeView.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return mEarthquakesList.size();
    }




    public class EarthquakHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView magnitudeView;
        TextView primaryLocationView;
        TextView locationOffsetView;
        TextView dateView;
        TextView timeView;

        public EarthquakHolder(View itemView) {
            super(itemView);
            // Find the TextViews By ID
             magnitudeView = itemView.findViewById(R.id.magnitude);
             primaryLocationView = itemView.findViewById(R.id.primary_location);
             locationOffsetView = itemView.findViewById(R.id.location_offset);
             dateView = itemView.findViewById(R.id.date);
             timeView = itemView.findViewById(R.id.time);

             itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Earthquake mEarthquake = mEarthquakesList.get(getAdapterPosition());
            Uri earthquakeUri = Uri.parse(mEarthquake.getmUrl());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
            mContext.startActivity(websiteIntent);
        }
    }
}
