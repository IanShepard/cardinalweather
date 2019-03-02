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

public class testParsing {

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
    public void testGetWeather() throws IOException {
        WeatherPoints weatherPoints = getSampleWeatherPointsAsJson();
        Parser parser = new Parser();
        ArrayList<Period> exampleForecast = parser.getCurrentForecast(weatherPoints);
        HashMap<String, String> forecastToday = exampleForecast.get(0).getForecast();

        Assert.assertNotNull(forecastToday);
        Assert.assertNotNull(forecastToday.get("name"));
    }

}