package edu.bsu.cs;

public class WeatherForecast {
    private String id;
    private String type;
    private Geometry geometry;
    private WeatherForecastProperties properties;

    public Geometry getGeometry() {
        return geometry;
    }

    public WeatherForecastProperties getProperties() {
        return properties;
    }
}

class Geometry {
    private String type;
    private WeatherForecastCoordiantes coordinates;
}

class WeatherForecastProperties {
    private String id;
    private String type;
    private String Name;
    private String State;
}

class WeatherForecastCoordiantes {
    private String id;
}