package edu.bsu.cs;

import java.net.MalformedURLException;
import java.net.URL;

public class URLFormer {
    public URL formPointsUrlFrom(String coordinates) throws MalformedURLException {
        String base = "https://api.weather.gov/points/";
        base += coordinates;
            return new URL(base);
    }
}
