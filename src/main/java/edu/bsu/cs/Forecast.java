package edu.bsu.cs;

public class Forecast {
    private String location;
    private String time;
    private int highTemperature;
    private int lowTemperature;
    private int currentTemperature;
    private String tempUnit;
    private String icon;
    private String shortForecast;
    private String detailedForecast;
    private String windSpeed;
    private String windDirection;

    private int dewPoint;
    private int relativeHumidity;
    private int apparentTemperature;
    private int skyCover;
    private int windGust;
    private Hazard hazards;
    private int probOfPrecip; //probability of precipitation in inches
    private int amntOfPrecip; // amount of precipitation in inches
    private int amntOfSnow; //amount of snow in inches
    private int visibility; //in miles

    public Forecast(WeatherZonesFeatures wzf) {
        PullRequest pull = new PullRequest();
        Parser parser = new Parser();

        location = wzf.getProperties().getLocation();


    }
}

class Hazard {
    String[] values;
}
