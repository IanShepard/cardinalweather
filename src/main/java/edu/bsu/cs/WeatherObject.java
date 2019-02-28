package edu.bsu.cs;

public class WeatherObject {
    private Object context;
    private String id;
    private String type;
    private Object geometry;
    private Object properties;

    public String getId() {
        return this.id;
    }

    public Object getProperties() {
        return properties;
    }
}
