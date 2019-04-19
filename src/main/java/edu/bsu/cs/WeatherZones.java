package edu.bsu.cs;

import java.util.ArrayList;

public class WeatherZones {

    private String type;
    private WeatherZonesFeatures[] features;

    public WeatherZonesFeatures[] getFeatures() {
        return features;
    }
}

class WeatherZonesFeatures {
    private String id;
    private String type;
    private WeatherZonesProperties properties;

    public String getId() {
        return id;
    }

    public WeatherZonesProperties getProperties() {
        return properties;
    }
}

class WeatherZonesProperties {
    private String id;
    private String type;
    private String name;
    private String state;
    private ArrayList<String> forecastOffices;
    private ArrayList<String> timeZone;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getLocation() {
        return name + ", " + state;
    }
}