package com.varsitycollege.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "NETWORK UTIL IN MAIN";
    Fragment weatherFragment;
    Fragment hourlyForecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherFragment = new WeatherFragment();
        hourlyForecastFragment = new HourlyForecastFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.weather_fragment_container, weatherFragment);
        transaction.replace(R.id.hourly_forecast_fragment_container, hourlyForecastFragment);
        transaction.commit();
    }
}