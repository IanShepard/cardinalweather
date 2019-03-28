package edu.bsu.cs;

import java.net.MalformedURLException;
import java.net.URL;
//forms the url to retrieve data
public class URLFormer {
    /*
    takes a string in the format {x, y} and retrns a URL to where x is the lattitude and y is the longitude.
     */
    public URL formPointsUrlFrom(String coordinates) throws MalformedURLException {
        String base = "https://api.weather.gov/points/";
        base += coordinates;
            return new URL(base);
    }
}
