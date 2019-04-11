package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /*
    uses a local json text file and converts it to weather points to use for tests
     */
    private WeatherPoints getSampleWeatherPointsAsJson() {
        InputStream sampleInputStream =
                getClass().getClassLoader().getResourceAsStream("PointsJsonExample");
        BufferedReader sampleIn = new BufferedReader(new InputStreamReader(sampleInputStream));
        return new Gson().fromJson(sampleIn, WeatherPoints.class);
    }

    @Override
    public void start(Stage primaryStage) {
        //Home Scene Top Section
        Button searchButton = new Button("Search");
        TextArea searchBox = new TextArea();
        Label location = new Label("Location data goes here");
        Image weatherIcon = new Image("https://api.weather.gov/icons/land/day/sct?size=small");

        HBox header = new HBox(searchBox, searchButton, location);


        //Home Scene Middle Section
        Label currentTemperature = new Label("Example 70");
        Label highTemperature = new Label("Example 80");
        Label lowTemperature = new Label("Example 60");
        Image icon = new Image("https://api.weather.gov/icons/land/night/tsra,90?size=small");

        VBox highLowBox = new VBox(highTemperature, lowTemperature);
        HBox temperatureBox = new HBox(currentTemperature, highLowBox);
        VBox forecastBox = new VBox(temperatureBox);


        //Home Scene Bottom Section
        Button forecastButton = new Button("Daily Forecast");


        //Home Screen Parent element
        VBox parent = new VBox(header, forecastBox, forecastButton);

        Scene scene = new Scene(parent, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cardinal Weather");
        primaryStage.show();
    }
}