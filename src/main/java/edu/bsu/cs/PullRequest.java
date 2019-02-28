package edu.bsu.cs;//**package edu.bsu.cs222;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PullRequest {

    public WeatherObject getWeather(String exampleSearch) throws IOException {
        URL url = new URL(exampleSearch);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        //WeatherObject weatherJson = new Gson().fromJson(in, WeatherObject.class);
        return new Gson().fromJson(in, WeatherObject.class);
    }
}

//**/