package edu.bsu.cs;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TestParsing {
    Parser parser = new Parser();
    PullRequest pull = new PullRequest();

    private WeatherPoints getSampleWeatherPointsAsJson() {
        InputStream sampleInputStream =
                getClass().getClassLoader().getResourceAsStream("PointsJsonExample");
        BufferedReader sampleIn = new BufferedReader(new InputStreamReader(sampleInputStream));
        return new Gson().fromJson(sampleIn, WeatherPoints.class);
    }

    @Test
    public void testParsingAccessToProperties() {
        WeatherPoints weatherPoints = getSampleWeatherPointsAsJson();

        Assert.assertEquals("https://api.weather.gov/gridpoints/TOP/31,80/forecast",
                weatherPoints.getProperties().getForecast());
    }

    @Test
    public void testGetCurrentWeather() throws IOException {
        WeatherPoints weatherPoints = getSampleWeatherPointsAsJson();
        Period[] exampleForecast = parser.getCurrentForecast(weatherPoints);
        HashMap<String, String> forecastToday = exampleForecast[0].getForecast();

        Assert.assertNotNull(forecastToday);
        Assert.assertNotNull(forecastToday.get("name"));
    }

    @Test
    public void testGetWeather0() {
        Period[] forecast = parser.getForecast("Delaware, IN");

        String expectedName = "";
        int expectedNumber = 1;
        //Assert.assertEquals(expectedName, forecast[0].getName());
        Assert.assertEquals(expectedNumber, forecast[0].getNumber());
    }

    @Test
    public void testGetZones() {
        WeatherZones wz = pull.pullZones();

        String expected0 = "https://api.weather.gov/zones/forecast/AKZ017";
        Assert.assertEquals(expected0, wz.getFeatures()[0].getId());

        String expected1 = "Cape Fairweather to Cape Suckling Coastal Area";
        Assert.assertEquals(expected1, wz.getFeatures()[0].getProperties().getName());
    }

    @Test
    public void testGetZonesForecast() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/AKZ017");


        String expectedId = "https://api.weather.gov/zones/forecast/AKZ017";
        Assert.assertEquals(expectedId, wzf.getId());
    }

    @Test
    public void testParsingGeometryFromWZF() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/AKZ017");
        double[] coords = wzf.getGeometry().getCoordinates();

        double expected1 =   -138.41196360000001;
        Assert.assertEquals(expected1, coords[0], 0.00002);

        double expected2 = 59.091370499999996;
        Assert.assertEquals(expected2, coords[1], 0.00002);
    }

    @Test
    public void testGetPoints() {
        WeatherZonesForecast wzf = pull.pullZonesForecast("https://api.weather.gov/zones/forecast/AKZ017");
        double[] coords = wzf.getGeometry().getCoordinates();
        String url = "https://api.weather.gov/points/" + coords[1] + "," + coords[0];
        WeatherGridpoints wg = pull.pullGridpoints(url);

        Assert.assertNotNull(wg.getProperties());

    }

    @Test
    public void testGetSearchZones0() {
        //testing for the best case scenario (one item in list)
        ArrayList<WeatherZonesFeatures> options = parser.searchZoneNamesFor("Delaware, IN");
        Assert.assertTrue(options.size() == 1);

        String expectedLocation = "Delaware, IN";
        Assert.assertEquals(expectedLocation, options.get(0).getProperties().getLocation());

        //testing for more than one item in list
        ArrayList<WeatherZonesFeatures> options1 = parser.searchZoneNamesFor("Delaware");
        Assert.assertTrue(options1.size() > 1);

        //testing for no items in list (no matches found in zones)
        ArrayList<WeatherZonesFeatures> options2 = parser.searchZoneNamesFor("unreasonable search quarry");
        Assert.assertTrue(options2.size() == 0);
    }
}