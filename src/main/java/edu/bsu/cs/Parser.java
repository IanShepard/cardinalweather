package edu.bsu.cs;

import java.util.ArrayList;

public class Parser {
    private PullRequest pull = new PullRequest();
    private WeatherZones wz = pull.pullZones();

    /*
    returns an array of periods representing a 12-hour summary of the forecast from each period
     */
    public Period[] getForecast(WeatherZonesFeatures wzf) {
        WeatherPoints weatherPoints = getWeatherPoints(wzf);
        String forecastUrl = weatherPoints.getProperties().getForecast();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(forecastUrl);
        return weatherGridpoints.getProperties().getPeriods();
    }

    /*
    returns an array of periods representing an hour summary of the forecast from each period
     */
    public Period[] getHourlyForecast(WeatherZonesFeatures wzf) {
        WeatherPoints weatherPoints = getWeatherPoints(wzf);
        String hourlyForecastUrl = weatherPoints.getProperties().getForecastHourly();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(hourlyForecastUrl);
        return weatherGridpoints.getProperties().getPeriods();
    }

    private WeatherPoints getWeatherPoints(WeatherZonesFeatures wzf) {
        String forecastUrl = wzf.getId();
        WeatherZonesForecast weatherZonesForecast = pull.pullZonesForecast(forecastUrl);
        double[] point = weatherZonesForecast.getGeometry().getCoordinates();
        return pull.pullPoints(point[0], point[1]);

    }

    /*
    Takes a string @Param input as a search term. it is searching through the names and states fields of
    WeatherZonesFeatures to find if input contains any name or state. There are two list that are generated, a perfect
    -match list and a match list. If there are any perfect matches then only that list will be returned, otherwise the
    match list will be returned. The perfect-match list will be shorter than the match list because the search
    requirements are more restrictive.
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

}
