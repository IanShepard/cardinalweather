package edu.bsu.cs;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestParsing {
    private Parser parser = new Parser();
    private PullRequest pull = new PullRequest();

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
    public void testGetSearchZones0() {
        //testing for the best case scenario (one item in list)
        ArrayList<WeatherZonesFeatures> options = parser.searchZoneNamesFor("Delaware, IN");
        int expectedSize0 = 1;
        Assert.assertEquals(expectedSize0, options.size());

        String expectedLocation = "Delaware, IN";
        Assert.assertEquals(expectedLocation, options.get(0).getProperties().getLocation());

        //testing for more than one item in list
        ArrayList<WeatherZonesFeatures> options1 = parser.searchZoneNamesFor("Delaware");
        Assert.assertTrue(options1.size() > 1);

        //testing for no items in list (no matches found in zones)
        ArrayList<WeatherZonesFeatures> options2 = parser.searchZoneNamesFor("unreasonable search quarry");
        int expectedSize1 = 0;
        Assert.assertEquals(expectedSize1, options2.size());
    }

    @Test
    public void testGetForecast() {
        ArrayList<WeatherZonesFeatures> wzf = parser.searchZoneNamesFor("Delaware, IN");
        Period[] forecast = parser.getForecast(wzf.get(0));

        int expectedNumber = 1;
        Assert.assertEquals(expectedNumber, forecast[0].getNumber());
    }

    @Test
    public void testGetHourlyForecast() {
        ArrayList<WeatherZonesFeatures> wzf = parser.searchZoneNamesFor("Delaware, IN");
        Period[] forecast = parser.getHourlyForecast(wzf.get(0));

        int expected = 1;
        Assert.assertEquals(expected, forecast[0].getNumber());
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

}