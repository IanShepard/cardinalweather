package edu.bsu.cs;


import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TestConnection {
    private PullRequest pull = new PullRequest();

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
    public void testPullZones() {
        WeatherZones wz = pull.pullZones();

        String expected0 = "https://api.weather.gov/zones/forecast/AKZ017";
        Assert.assertEquals(expected0, wz.getFeatures()[0].getId());

        String expected1 = "Cape Fairweather to Cape Suckling Coastal Area";
        Assert.assertEquals(expected1, wz.getFeatures()[0].getProperties().getName());
    }

    @Test
    public void testPullZonesForecast0() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/AKZ017");

        String expectedId = "https://api.weather.gov/zones/forecast/AKZ017";
        Assert.assertEquals(expectedId, wzf.getId());
    }

    @Test
    public void testPullZonesForecast1() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/INZ041");
        double latitude = wzf.getGeometry().getCoordinates()[0];

        double expected0 = -85.444297700000007;
        Assert.assertEquals(expected0, latitude, 0.00002);
    }

    @Test
    public void testPullPoints0() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/AKZ017");
        double[] coords = wzf.getGeometry().getCoordinates();
        String url = "https://api.weather.gov/points/" + coords[1] + "," + coords[0];
        WeatherGridpoints wg = pull.pullGridpoints(url);

        Assert.assertNotNull(wg.getProperties());
    }

    @Test
    public void testPullPoints1() {
        WeatherPoints weatherPoints = pull.pullPoints(-85.4443, 40.3792);

        String expectedId = "https://api.weather.gov/points/40.3792,-85.4443";
        String expectedForecast = "https://api.weather.gov/gridpoints/IND/80,97/forecast";

        Assert.assertEquals(expectedId, weatherPoints.getId());
        Assert.assertEquals(expectedForecast, weatherPoints.getProperties().getForecast());
    }
}