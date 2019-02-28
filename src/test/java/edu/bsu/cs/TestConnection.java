package edu.bsu.cs;


import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestConnection {

    @Test
    public void testConnection() throws IOException {
        String exampleSearch = "https://api.weather.gov/points/39.7456,-97.0892";
        //String exampleSearch2 = "https://api.weather.gov/zones/forecast";
        URL url = new URL(exampleSearch);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        Assert.assertNotNull(in);
    }

    @Test
    public void testPullRequest() throws IOException {
        String exampleSearch = "https://api.weather.gov/points/39.7456,-97.0892";
        PullRequest requester = new PullRequest();
        WeatherObject weatherJson = requester.getWeather(exampleSearch);

        Assert.assertNotNull(weatherJson);
    }

    @Test
    public void testURLBuilder() throws MalformedURLException {
        String exampleSearch = "-31,80";
        URLFormer urlFormer = new URLFormer();
        URL url = urlFormer.formPointsUrlFrom(exampleSearch);

        URL correctURL = new URL("https://api.weather.gov/points/-31,80");
        Assert.assertEquals(url, correctURL);
    }
}