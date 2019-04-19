package edu.bsu.cs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private PullRequest pull = new PullRequest();
    private WeatherZones wz = pull.pullZones();

    /*
    takes weatherpoints and returns a list of Period
    Used only in tests. DO NOT USE IN MAIN
     */
    public Period[] getCurrentForecast(WeatherPoints weatherPoints) throws IOException {
        PullRequest request = new PullRequest();
        Gson gson = new Gson();

        String forecastUrl = weatherPoints.getProperties().getForecast();
        BufferedReader forecastIn = request.pullDataStreamFrom(forecastUrl);
        WeatherGridpoints forecasts = gson.fromJson(forecastIn, WeatherGridpoints.class);

        return forecasts.getProperties().getPeriods();
    }

    public Period[] getForecast(String location) {
        WeatherGridpoints weatherGridpoints = getGridpoints(location);
        return weatherGridpoints.getProperties().getPeriods();
    }

    public WeatherGridpoints getGridpoints(String name) {
        String forecastUrl = getZoneNameUrl(name);
        if (forecastUrl == null || forecastUrl.length() == 0 ) {
            //return new Period[0];
        }
        WeatherZonesForecast weatherZonesForecast = pull.pullZonesForecast(forecastUrl);

        double[] point = weatherZonesForecast.getGeometry().getCoordinates();
        WeatherPoints weatherPoints = pull.pullPoints(point[0], point[1]);

        String forecastUrl1 = weatherPoints.getProperties().getForecast();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(forecastUrl1);

        return weatherGridpoints;

    }

    /*
    Takes a string @Param input as a search term. it is searching through the names and states fields of
    WeatherZonesFeatures to find if input contains any name or state. There are two list that are generated, a perfect
    -match list and a match list. If there are any perfect matches then only that list will be returned, otherwise the
    match list will be returned. The perfect-match list will be shorter than the match list.
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

    public ArrayList<String> getZoneNames() {
        ArrayList<String> result = new ArrayList<>();
        WeatherZonesFeatures[] wzf = wz.getFeatures();
        String location;
        WeatherZonesProperties wzp;
        for (WeatherZonesFeatures feature : wzf) {
            wzp = feature.getProperties();
            location = wzp.getName() + ", " + wzp.getState();
            result.add(location);
        }

        return result;
    }

    /*
    Only used in getForecast(). Takes in string in the format "<name>, <state>". this string is produced by
    searchZoneNamesFor()
     */
    private String getZoneNameUrl(String location) {
        String resultUrl = "";
        int locLength = location.length();
        String locState = location.substring(locLength-2, locLength);
        String locName = location.substring(0, locLength-4);
        WeatherZonesFeatures[] wzf = wz.getFeatures();
        WeatherZonesProperties wzp; String wzpName; String wzpState;
        for (WeatherZonesFeatures feature : wzf) {
            wzp = feature.getProperties();
            wzpName = wzp.getName();
            wzpState = wzp.getState();

            if (locName.equals(wzpName) && locState.equals(wzpState)) {
                resultUrl = feature.getId();
            }


        }
        return resultUrl;
    }
}
