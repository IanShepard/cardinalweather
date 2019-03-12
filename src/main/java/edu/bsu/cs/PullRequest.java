package edu.bsu.cs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
//pulls data
public class PullRequest {

    public WeatherPoints getWeather(String exampleSearch) throws IOException {
        URL url = new URL(exampleSearch);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        return new Gson().fromJson(in, WeatherPoints.class);
    }

    public BufferedReader getDataStream(String urlQuery) throws IOException {
        URL url = new URL(urlQuery);
        URLConnection connection = url.openConnection();
        return new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
    }

}