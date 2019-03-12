package edu.bsu.cs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
//parses weather data
public class Parser {

    public ArrayList<Period>getCurrentForecast(WeatherPoints weatherPoints) throws IOException {
        PullRequest request = new PullRequest();
        Gson gson = new Gson();

        String forecastUrl = weatherPoints.getProperties().getForecast();
        BufferedReader forecastIn = request.getDataStream(forecastUrl);
        WeatherGridpoints forecasts = gson.fromJson(forecastIn, WeatherGridpoints.class);

        return forecasts.getProperties().getPeriods();
    }
}
