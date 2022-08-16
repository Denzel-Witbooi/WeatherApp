package com.varsitycollege.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HourlyForecastAdapter extends ArrayAdapter<HourlyForecast> {


    public HourlyForecastAdapter(@NonNull Context context, ArrayList<HourlyForecast> hourlyForecastArrayList) {
        super(context, 0, hourlyForecastArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HourlyForecast hourlyForecast = getItem(position);
       Context context;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.hourly_forecast_items, parent, false);
        }

        TextView iconPhrase = convertView.findViewById(R.id.txt_rain_or_sun);

        iconPhrase.setText(hourlyForecast.getIconPhrase());
        
        context = convertView.getContext();

        return convertView;
    }
}
