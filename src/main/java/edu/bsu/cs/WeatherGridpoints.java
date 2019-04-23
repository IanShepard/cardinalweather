package edu.bsu.cs;

import java.util.HashMap;
//provides sorts parsed data under the category named period
public class WeatherGridpoints {
    private String type;
    private WeatherGridpointsProperties properties;

    public String getType() {
        return type;
    }

    public WeatherGridpointsProperties getProperties() {
        return properties;
    }
}

class WeatherGridpointsProperties {
    private String updated;
    private String units;
    private Period[] periods;

    public Period[] getPeriods() {
        return periods;
    }
}

class Period {
    private int number;
    private String name;
    private String startTime;
    private String endTime;
    private String isDayTime;
    private int temperature;
    private String temperatureUnit;
    private String windSpeed;
    private String windDirection;
    private String icon;
    private String shortForecast;
    private String detailedForecast;

    public HashMap<String, String> getForecast() {
        HashMap<String, String> forecast = new HashMap<>();
        forecast.put("number", getNumberAsString());
        forecast.put("name", name);
        forecast.put("startTime", startTime);
        forecast.put("isDayTime", isDayTime);
        forecast.put("temperature", getTemperatureAsString());
        forecast.put("temperatureUnit", temperatureUnit);
        forecast.put("windSpeed", windSpeed);
        forecast.put("windDirection", windDirection);
        forecast.put("icon", icon);
        forecast.put("shortForecast", shortForecast);
        forecast.put("detailedForecast", detailedForecast);

        return forecast;
    }

    public int getNumber() {
        return number;
    }

    private String getNumberAsString() {
        return Integer.toString(number);
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getTemperature() {
        return temperature;
    }

    private String getTemperatureAsString() {
        return Integer.toString(temperature);
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getIcon() {
        return icon;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public String getDetailedForecast() {
        return detailedForecast;
    }
}
