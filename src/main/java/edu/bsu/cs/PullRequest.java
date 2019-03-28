package edu.bsu.cs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PullRequest {

    /*
    takes a string to be used as a query to pull from online
     */
    public BufferedReader pullDataStreamFrom(String urlQuery) throws IOException {
        URL url = new URL(urlQuery);
        URLConnection connection = url.openConnection();
        return new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
    }

    public BufferedReader pullFrom(String urlQuery) {
        BufferedReader buffer = null;

        try {
            URL url = new URL(urlQuery);
            URLConnection connection = url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return buffer;
    }

    public WeatherPoints pullWeather(String exampleSearch) throws IOException {
        URL url = new URL(exampleSearch);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        return new Gson().fromJson(in, WeatherPoints.class);
    }

    public WeatherZones pullZones() {
        BufferedReader zones;
        zones = pullFrom("https://api.weather.gov/zones/forecast");

        return new Gson().fromJson(zones, WeatherZones.class);

    }

    public WeatherZonesForecast pullZonesForecast(String forecastUrl) {
        //example forecastUrl: "https://api.weather.gov/zones/forecast/INZ041"
        WeatherZonesForecast wzf = null;
        if (!forecastUrl.isBlank()) {
            wzf = new Gson().fromJson(pullFrom(forecastUrl), WeatherZonesForecast.class);
        }

        return wzf;
    }


    public WeatherPoints pullPoints(double x, double y) {
        BufferedReader buffer = pullFrom("https://api.weather.gov/points/" + y + "," + x);
        return new Gson().fromJson(buffer, WeatherPoints.class);
    }

    public WeatherGridpoints pullGridpoints(String forecastUrl) {
        BufferedReader buffer = pullFrom(forecastUrl);
        return new Gson().fromJson(buffer, WeatherGridpoints.class);
    }
}