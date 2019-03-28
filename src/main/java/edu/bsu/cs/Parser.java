package edu.bsu.cs;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

    /*
    takes weatherpoints and returns a list of Period
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
        Period[] forecast;
        PullRequest pull = new PullRequest();

        WeatherZones wz = pull.pullZones();
        WeatherZonesFeatures[] wzf = wz.getFeatures();
        String forecastUrl = "";
        for (int i=0; i<wzf.length; i++) {
            WeatherZonesProperties wzp = wzf[i].getProperties();
            String wzpName = wzp.getName();
            String wzpState = wzp.getState();

            if (name.contains(wzpName) && name.contains(wzpState)) {
                forecastUrl = wzf[i].getId();
            }
        }
        WeatherZonesForecast weatherZonesForecast = pull.pullZonesForecast(forecastUrl);

        double[] point = weatherZonesForecast.getGeometry().getCoordinates();
        WeatherPoints weatherPoints = pull.pullPoints(point[0], point[1]);

        String forecastUrl1 = weatherPoints.getProperties().getForecast();
        WeatherGridpoints weatherGridpoints = pull.pullGridpoints(forecastUrl1);

        forecast = weatherGridpoints.getProperties().getPeriods();

        return forecast;
    }
}
