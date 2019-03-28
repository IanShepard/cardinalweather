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
        WeatherPoints weatherJson = requester.pullWeather(exampleSearch);

        //System.out.println(weatherJson.getProperties().getForecast());
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

    @Test
    public void testPullZonesForecast() {
        PullRequest pull = new PullRequest();
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/INZ041");
        double latitude = wzf.getGeometry().getCoordinates()[0];

        double expected0 = -85.444297700000007;
        Assert.assertEquals(expected0, latitude, 0.00002);
    }

    @Test
    public void testPullPoints() {
        PullRequest pull = new PullRequest();
        WeatherPoints weatherPoints = pull.pullPoints(-85.4443, 40.3792);

        String expectedId = "https://api.weather.gov/points/40.3792,-85.4443";
        String expectedForecast = "https://api.weather.gov/gridpoints/IND/80,97/forecast";

        Assert.assertEquals(expectedId, weatherPoints.getId());
        Assert.assertEquals(expectedForecast, weatherPoints.getProperties().getForecast());
    }
}