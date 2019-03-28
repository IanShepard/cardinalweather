package edu.bsu.cs;

public class WeatherZonesForecast {
    private String id;
    private String type;
    private WeatherZonesForecastGeometry geometry;
    private WeatherZonesForecastProperties properties;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public WeatherZonesForecastGeometry getGeometry() {
        return geometry;
    }

    public WeatherZonesForecastProperties getProperties() {
        return properties;
    }
}

class WeatherZonesForecastGeometry {
    private String type;
    private double[][][][] coordinates;

    public String getType() {
        return type;
    }

    public double[] getCoordinates() {
        return coordinates[0][0][0];
    }
}

class WeatherZonesForecastProperties {
    private String id;
    private String type;
    private String name;
    private String state;

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
}