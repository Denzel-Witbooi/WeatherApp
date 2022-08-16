package com.varsitycollege.weatherapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        return super.getView(position, convertView, parent);


    }
}
