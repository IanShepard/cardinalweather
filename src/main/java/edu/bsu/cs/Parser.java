package edu.bsu.cs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Parser {
    private PullRequest pull = new PullRequest();
    private WeatherZones wz = pull.pullZones();

    /*
    Returns an array of periods representing a 12-hour summary of the forecast for each period
     */
    public Period[] getForecast(WeatherZonesFeatures wzf) {
        WeatherPoints weatherPoints = getWeatherPoints(wzf);
        String forecastUrl = weatherPoints.getProperties().getForecast();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(forecastUrl);
        return weatherGridpoints.getProperties().getPeriods();
    }

    /*
    Returns an array of periods representing a 1-hour summary of the forecast for each period
     */
    public Period[] getHourlyForecast(WeatherZonesFeatures wzf) {
        WeatherPoints weatherPoints = getWeatherPoints(wzf);
        String hourlyForecastUrl = weatherPoints.getProperties().getForecastHourly();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(hourlyForecastUrl);
        return weatherGridpoints.getProperties().getPeriods();
    }

    /*
    Returns WeatherPoints which can then branch into different info within that particular WeatherZonesFeature including
    the hourly forecast, morning/night forecast, and less commonly needed data (not used in this program)
    used in getForecast() and getHourlyForecast() because they share a similar block of code
     */
    private WeatherPoints getWeatherPoints(WeatherZonesFeatures wzf) {
        String forecastUrl = wzf.getId();
        WeatherZonesForecast weatherZonesForecast = pull.pullZonesForecast(forecastUrl);
        double[] point = weatherZonesForecast.getGeometry().getCoordinates();
        return pull.pullPoints(point[0], point[1]);

    }

    /*
    Takes a string as a search term. It is searching through the names and states fields of
    WeatherZonesFeatures to find if input contains any name or state. There are two list that are generated, a perfect
    -match list and a match list. If there are any perfect matches then only that list will be returned, otherwise the
    match list will be returned.
     */
    public ArrayList<WeatherZonesFeatures> searchZoneNamesFor(String input) {
        //setup
        ArrayList<WeatherZonesFeatures> matches = new ArrayList<>();
        ArrayList<WeatherZonesFeatures> perfectMatches = new ArrayList<>();
        input = input.toLowerCase();
        String[] inputwords = input.split(" ");
        String wzpName;
        String wzpState;
        WeatherZonesFeatures[] wzf = wz.getFeatures();

        //
        for (WeatherZonesFeatures feature : wzf) {
            WeatherZonesProperties wzp = feature.getProperties();
            wzpName = wzp.getName().toLowerCase(); //toLowerCase() for non-case sensitive string matching
            //if{}else{} is for the assignment of wzpState because it is possible that .getState() will return null
            if (wzp.getState() != null)
                wzpState = wzp.getState().toLowerCase(); //toLowerCase() for non-case sensitive string matching
            else {
                wzpState = "!!!"; //string needs to not match anything in name or state field in wzp
            }

            //creates a smaller list that matches the input more exactly
            if (input.contains(wzpName) && input.contains(wzpState)) {
                perfectMatches.add(feature);
            }

            //creates a larger list that matches the input more loosely
            for (String inputWord :inputwords) { //for each word in input
                if (wzpName.matches(inputWord) || wzpState.matches(inputWord)) { //check if any single word is contained in a name or state
                    matches.add(feature);
                }
            }
        }
        //if there are any perfect matches return that list
        if (!perfectMatches.isEmpty()) {
            return perfectMatches;
        }
        //otherwise, return the larger, more generic list
        return matches;
    }

    /*
    Takes a date in string form with the format of "yyyy-MM-dd'T'HH:mm:ssZ" and converts it to a Calendar object. To
    understand the representation of the letters, go to:
    https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    or search "simpledateformat codes in java".
     */
    public Calendar convertStringToCalendar(String dateInString) {
        dateInString = dateInString.substring(0, 22) + dateInString.substring(23, 25); // to remove the colon (":") in the Z part of the format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public void truncateToHour(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
    }

    /*
    If the time is between the start and end times of the period then it returns 0. Start time inclusive, end time
    exclusive. If time is before start time, then it returns -1. If time is after end time of period then it returns 1.
     */
    public int calendarInPeriod(Calendar time, Period period) {
        Calendar start = convertStringToCalendar(period.getStartTime());
        Calendar end = convertStringToCalendar(period.getEndTime());
        if (time.compareTo(start) >= 0) {
            if (time.compareTo(end) < 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}
