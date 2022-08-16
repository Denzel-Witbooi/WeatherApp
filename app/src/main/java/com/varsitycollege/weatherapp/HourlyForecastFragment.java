package com.varsitycollege.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class HourlyForecastFragment extends Fragment {

    private TextView testTextView;
    private String BASE_URL = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/305605";
    ListView listView;
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);

        testTextView = view.findViewById(R.id.hourly_forecast_text);
        testTextView.setText("Hourly Forecasts");
        listView = view.findViewById(R.id.hourlyForecastList);
        cardView = view.findViewById(R.id.hourly_forecast_cardView);

        URL url = NetworkUtil.buildURLForWeather(BASE_URL);

        new HourlyForecastFragment.FetchWeatherData().execute(url);

        return view;
    }

    class FetchWeatherData extends AsyncTask<URL, Void, String> {
        private String TAG = "HourlyForecastDATA";

        ArrayList<HourlyForecast> forecastArrayList = new ArrayList<>();

        @Override
        protected String doInBackground(URL... urls) {
            URL hourlyForecastURL = urls[0];
            String hourlyForecastData = null;

            try {
                hourlyForecastData = NetworkUtil.getResponseFromHttpUrl(hourlyForecastURL);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            Log.i(TAG, "ourDATA" + hourlyForecastData);
            return hourlyForecastData;
        }

        @Override
        protected void onPostExecute(String hourlyForecastData) {
            if (hourlyForecastData != null) {
                consumeJson(hourlyForecastData);
            }
            super.onPostExecute(hourlyForecastData);
        }

        public ArrayList<HourlyForecast> consumeJson(String hourlyForecastJSON) {
            if (forecastArrayList != null) {
                forecastArrayList.clear();
            }

            if (hourlyForecastJSON != null) {
                try {
                    JSONArray hourlyForecast = new JSONArray(hourlyForecastJSON);

                    for (int i = 0; i < hourlyForecast.length(); i++) {
                        HourlyForecast forecastObject = new HourlyForecast();
                        JSONObject dailyWeather = hourlyForecast.getJSONObject(i);

                        // get iconPhrase
                        String iconPhrase = dailyWeather.getString("IconPhrase");
                        Log.i(TAG, "consumeJson: iconPhrase " + iconPhrase);
                        forecastObject.setIconPhrase(iconPhrase);
                        
                        forecastArrayList.add(forecastObject);

                        if (forecastArrayList != null) {
                            HourlyForecastAdapter adapter = new HourlyForecastAdapter(getContext(), forecastArrayList);
                            listView.setAdapter(adapter);
                        }
                    }
                    return forecastArrayList;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}