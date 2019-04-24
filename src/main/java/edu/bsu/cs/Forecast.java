package edu.bsu.cs;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
This class is a container. It is the conglomeration of all weather data from different sources
 */
public class Forecast {
    private String location;
    private Calendar timedGenerated;
    private Calendar startTime;
    private Calendar endTime;
    private int highTemperature;
    private int lowTemperature;
    private int currentTemperature;
    private String name;
    private String temperatureUnit;
    private String iconSmall;
    private String iconMedium;
    private String iconLarge;
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
    private int amntOfPrecip; //amount of precipitation in inches
    private int amntOfSnow; //amount of snow in inches
    private int visibility; //in miles

    public Forecast(WeatherZonesFeatures wzf, Calendar calendar) {
        Parser parser = new Parser();
        parser.truncateToHour(calendar);

        //information from wzf and automatically generated information
        location = wzf.getProperties().getLocation();
        timedGenerated = new GregorianCalendar();

        //information from hourly forecast
        Period[] hourlyForecast = parser.getHourlyForecast(wzf);
        for (Period period : hourlyForecast) {
            if (parser.calendarInPeriod(calendar, period) == 0) {
                startTime = parser.convertStringToCalendar(period.getStartTime());
                endTime = parser.convertStringToCalendar(period.getEndTime());
                currentTemperature = period.getTemperature();
                temperatureUnit = period.getTemperatureUnit();
                windSpeed = period.getWindSpeed();
                windDirection = period.getWindDirection();
                iconSmall = period.getIcon();
                shortForecast = period.getShortForecast();
                detailedForecast = period.getDetailedForecast();
                break;
            }

        }

        //information from bi-daily forecast
        Period[] dailyForecast = parser.getForecast(wzf);
        for (int i=0; i<dailyForecast.length; i++) {
            Period period = dailyForecast[i];
            if (parser.calendarInPeriod(calendar, period) == 0) {
                name = period.getName();
                iconMedium = period.getIcon();

                if (endTime.get(Calendar.HOUR) == 18) {
                    highTemperature = period.getTemperature();
                    lowTemperature = dailyForecast[i+1].getTemperature();
                    break;
                } else {
                    highTemperature = currentTemperature;
                    lowTemperature = period.getTemperature();
                    break;
                }
            }
        }

        //TODO information from... the third branch
    }

    public Forecast(WeatherZonesFeatures wzf) {
        this(wzf, new GregorianCalendar());
    }

    public String getLocation() {
        return location;
    }

    public Calendar getTimedGenerated() {
        return timedGenerated;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public String getHour() {
        return String.valueOf(startTime.get(Calendar.HOUR));
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public String getHighTemperatureAsString() {
        return String.valueOf(highTemperature);
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public String getLowTemperatureAsString() {
        return String.valueOf(lowTemperature);
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public String getCurrentTemperatureAsString() {
        return String.valueOf(currentTemperature);
    }

    public String getName() {
        return name;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getIconSmall() {
        return iconSmall;
    }

    public String getIconMedium() {
        return iconMedium;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public String getDetailedForecast() {
        return detailedForecast;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }
}

class Hazard {
    String[] values;
}
