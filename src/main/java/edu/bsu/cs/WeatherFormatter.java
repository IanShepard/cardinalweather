package edu.bsu.cs;

import java.util.HashMap;

public class WeatherFormatter {

    public String simpleFormat(HashMap<String, String> forecast) {
        String result;
        result = forecast.get("name") + "\n";
        result += "Current Weather\n";
        result += forecast.get("temperature") + "°" + forecast.get("temperatureUnit") + "\n";
        result += forecast.get("shortForecast");

        return result;
    }
}
