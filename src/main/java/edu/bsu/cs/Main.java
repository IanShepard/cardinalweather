package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    public void start(Stage primaryStage) throws IOException {
        WeatherPoints weatherPoints = getSampleWeatherPointsAsJson();
        Parser parser = new Parser();
        Period[] exampleForecast = parser.getCurrentForecast(weatherPoints);
        HashMap<String, String> forecastToday = exampleForecast[0].getForecast();

        WeatherFormatter formatter = new WeatherFormatter();
        String displayString = formatter.simpleFormat(forecastToday);

        Label test = new Label(displayString);
        Scene scene = new Scene(test, 500, 500);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("current weather"), test);
        Scene scene1 = new Scene(hBox);

        primaryStage.setTitle("Cardinal Weather");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}