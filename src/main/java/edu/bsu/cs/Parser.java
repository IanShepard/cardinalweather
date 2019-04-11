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

    public Period[] getForecast(String name) {
        Period[] resultForecast;

        String forecastUrl = getZoneNameUrl(name);

        WeatherZonesForecast weatherZonesForecast = pull.pullZonesForecast(forecastUrl);

        double[] point = weatherZonesForecast.getGeometry().getCoordinates();
        WeatherPoints weatherPoints = pull.pullPoints(point[0], point[1]);

        String forecastUrl1 = weatherPoints.getProperties().getForecast();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(forecastUrl1);

        resultForecast = weatherGridpoints.getProperties().getPeriods();

        return resultForecast;
    }

    public ArrayList<String> searchZoneNames(String input) {
        //Each returned String is formatted "<name>, <state>"
        ArrayList<String> matches = new ArrayList<>();
        input = input.toLowerCase(); //toLowerCase() for non-case sensitive string matching
        WeatherZonesFeatures[] wzf = wz.getFeatures();
        String location;
        for (WeatherZonesFeatures feature : wzf) {
            WeatherZonesProperties wzp = feature.getProperties();
            String wzpName = wzp.getName().toLowerCase(); //toLowerCase() for non-case sensitive string matching
            String wzpState = "The string here doesn't matter; it just can't be a state abbreviation.";
            if (wzp.getState() != null) {
                wzpState = wzp.getState().toLowerCase(); //toLowerCase() for non-case sensitive string matching
            }
            if (input.contains(wzpName) || input.contains(wzpState)) {
                location = wzpName + ", " + wzpState;
                matches.add(location);
            }
        }

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
    searchZoneNames()
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
