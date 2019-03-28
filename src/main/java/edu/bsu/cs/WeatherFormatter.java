package edu.bsu.cs;

import java.util.HashMap;
//sorts parsed data into a result
public class WeatherFormatter {
    /*
    takes a HashMap of one forecast and returns a readable string foremat
     */
    public String simpleFormat(HashMap<String, String> forecast) {
        String result;
        result = forecast.get("name") + "\n";
        result += "Current Weather\n";
        result += forecast.get("temperature") + forecast.get("temperatureUnit") + "\n";
        result += forecast.get("shortForecast");

        return result;
    }
}
