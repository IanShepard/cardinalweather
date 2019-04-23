package edu.bsu.cs;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TestParsing {
    private Parser parser = new Parser();
    private PullRequest pull = new PullRequest();
    private ArrayList<WeatherZonesFeatures> muncieWzf = parser.searchZoneNamesFor("Delaware, IN");
    private Period[] muncieForecast = parser.getForecast(muncieWzf.get(0));
    private Period[] muncieHourlyForecast = parser.getHourlyForecast(muncieWzf.get(0));

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


    //Testing date and time manipulation
    @Test
    public void testParsingDateObjFromString() {
        String exampleDateString = "2019-04-15T19:00:00-04:00";
        Calendar calendar = parser.convertStringToCalendar(exampleDateString);

        long expectedEpocTime = 1555369200000L; //"L" at the end because it needs to be a long literal... so weird
        Assert.assertEquals(expectedEpocTime, calendar.getTimeInMillis());
    }

    @Test
    public void testCalendarInPeriod() {
        Period period = muncieForecast[0];

        // the given calendar is during the period
        Calendar now = new GregorianCalendar();
        int expected0 = 0;
        Assert.assertEquals(expected0, parser.calendarInPeriod(now, period));

        parser.truncateToHour(now);
        Assert.assertEquals(expected0, parser.calendarInPeriod(now, period));

        //the given calendar is after the period end time
        Calendar plusTwelveHours = new GregorianCalendar();
        plusTwelveHours.add(Calendar.HOUR, 12);
        int expected1 = 1;
        Assert.assertEquals(expected1, parser.calendarInPeriod(plusTwelveHours, period));

        parser.truncateToHour(plusTwelveHours);
        Assert.assertEquals(expected1, parser.calendarInPeriod(plusTwelveHours, period));

        //the given calendar is before the period start time
        Calendar minusTwelveHours = new GregorianCalendar();
        minusTwelveHours.add(Calendar.HOUR, -12);
        int expected2 = -1;
        Assert.assertEquals(expected2, parser.calendarInPeriod(minusTwelveHours, period));

        parser.truncateToHour(minusTwelveHours);
        Assert.assertEquals(expected2, parser.calendarInPeriod(minusTwelveHours, period));
    }

    @Test
    public void testForecastConstructor() {
        ArrayList<WeatherZonesFeatures> wzf = parser.searchZoneNamesFor("Delaware, IN");
        Calendar now = new GregorianCalendar();
        Forecast forecast = new Forecast(wzf.get(0), now);

        Calendar plusOneHour = new GregorianCalendar();
        plusOneHour.add(Calendar.HOUR, 5);
        Forecast forecast1 = new Forecast(wzf.get(0), plusOneHour);
    }
}