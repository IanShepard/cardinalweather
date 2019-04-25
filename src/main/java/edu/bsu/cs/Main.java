package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        Parser parser = new Parser();

        ArrayList<WeatherZonesFeatures> options = parser.searchZoneNamesFor("Delaware, IN");
        SceneBuilder sceneBuilder = new SceneBuilder(primaryStage, options.get(0));
        Forecast muncie = new Forecast(options.get(0));
        Scene homePage = sceneBuilder.getHomePage(muncie);

        primaryStage.setScene(homePage);
        primaryStage.setTitle("Cardinal Weather");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.show();

    }
}