package edu.bsu.cs;

import java.util.ArrayList;
import java.util.HashMap;
//provides sorts parsed data under the category named period
public class WeatherGridpoints {
    private String type;
    private WeatherGridpointsProperties properties;

    public WeatherGridpointsProperties getProperties() {
        return properties;
    }
}

class WeatherGridpointsProperties {
    private String updated;
    private String units;
    private ArrayList<Period> periods;

    public ArrayList<Period> getPeriods() {
        return periods;
    }
}

class Period {
    private int number;
    private String name;
    private String isDayTime;
    private int temperature;
    private String temperatureUnit;
    private String windSpeed;
    private String windDirection;
    private String icon;
    private String shortForecast;
    private String detailedForecast;

    public HashMap<String, String> getForecast() {
        HashMap<String, String> collection = new HashMap<>();
        collection.put("number", getNumberAsString());
        collection.put("name", name);
        collection.put("isDayTime", isDayTime);
        collection.put("temperature", getTemperatureAsString());
        collection.put("temperatureUnit", temperatureUnit);
        collection.put("windSpeed", windSpeed);
        collection.put("windDirection", windDirection);
        collection.put("icon", icon);
        collection.put("shortForecast", shortForecast);
        collection.put("detailedForecast", detailedForecast);

        return collection;
    }

    public int getNumber() {
        return number;
    }
    public String getNumberAsString() {
        return Integer.toString(number);
    }

    public String getName() {
        return name;
    }

    public int getTemperature() {
        return temperature;
    }
    public String getTemperatureAsString() {
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
