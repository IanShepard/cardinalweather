package edu.bsu.cs;

public class WeatherPoints {
    private Object context;
    private String id;
    private String type;
    private Object geometry;
    private WeatherPointsProperties properties;

    public String getId() {
        return this.id;
    }

    public WeatherPointsProperties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return context+"\n"+id+"\n"+type+"\n"+geometry+"\n"+properties+"\n";
    }
}

class WeatherPointsProperties {
    private String id;
    private String type;
    private String cwa;
    private String forecastOffice;
    private int gridX;
    private int gridY;
    private String forecast;
    private String forecastHourly;
    private String forecastGridData;
    private String observationStations;
    private Object relativeLocation;
    private String forecastZone;
    private String county;
    private String fireWeatherZone;
    private String timeZone;
    private String radarStation;

    public String getCwa() {
        return cwa;
    }

    public String getForecast() {
        return forecast;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "THIS IS THE PROPERTIES OBJECT";
    }

    public String getForecastHourly() {
        return forecastHourly;
    }

    public String getForecastGridData() {
        return forecastGridData;
    }
}