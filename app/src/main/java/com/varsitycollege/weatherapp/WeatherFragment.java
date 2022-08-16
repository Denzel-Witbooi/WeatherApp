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


public class WeatherFragment extends Fragment {

    private TextView testTextView;
    ListView listView;
    CardView cardView;

    private String BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        testTextView = view.findViewById(R.id.weather_text);
        testTextView.setText("WEATHER");
        listView = view.findViewById(R.id.weatherList);
        cardView = view.findViewById(R.id.weather_cardView);

        URL url = NetworkUtil.buildURLForWeather(BASE_URL);

        new FetchWeatherData().execute(url);

       return view;
    }

     class FetchWeatherData extends AsyncTask<URL, Void, String> {
        private String TAG = "weatherDATA";

        ArrayList<Forecast> fiveDayList = new ArrayList<>();

         @Override
         protected String doInBackground(URL... urls) {
             URL weatherURL = urls[0];
             String weatherData = null;

             try {
                 weatherData = NetworkUtil.getResponseFromHttpUrl(weatherURL);
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }

             Log.i(TAG, "ourDATA" + weatherData);
             return weatherData;
         }

         @Override
         protected void onPostExecute(String weatherData) {
             if (weatherData != null) {
                 consumeJson(weatherData);
             }
             super.onPostExecute(weatherData);
         }

         public ArrayList<Forecast> consumeJson(String weatherJSON) {
             if (fiveDayList != null) {
                 fiveDayList.clear();
             }

             if (weatherJSON != null) {
                 try {
                     JSONObject rootWeatherData = new JSONObject(weatherJSON);
                     JSONArray fivedayForecast = rootWeatherData.getJSONArray("DailyForecasts");

                     for (int i = 0; i < fivedayForecast.length(); i++) {
                         Forecast forecastObject = new Forecast();
                         JSONObject dailyWeather = fivedayForecast.getJSONObject(i);

                         // get date
                         String date = dailyWeather.getString("Date");
                         Log.i(TAG, "consumeJson: Date" + date);
                         forecastObject.setfDate(date);

                         // get Min

                         JSONObject temperatureObject = dailyWeather.getJSONObject("Temperature");
                         JSONObject minTempObject = temperatureObject.getJSONObject("Minimum");
                         String minTemp = minTempObject.getString("Value");
                         Log.i(TAG, "consumeJson: minTemp" + minTemp);
                         forecastObject.setfMin(minTemp);

                         // get Max:

                         JSONObject maxTempObject = temperatureObject.getJSONObject("Maximum");
                         String maxTemp = maxTempObject.getString("Value");
                         Log.i(TAG, "consumeJson: maxTemp" + maxTemp);
                         forecastObject.setfMax(maxTemp);

                         fiveDayList.add(forecastObject);

                         if (fiveDayList != null) {
                             ForecastAdapter adapter = new ForecastAdapter(getContext(), fiveDayList);
                             listView.setAdapter(adapter);
                         }
                     }
                     return  fiveDayList;
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