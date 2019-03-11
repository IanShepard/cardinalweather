package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        ArrayList<Period> exampleForecast = parser.getCurrentForecast(weatherPoints);
        HashMap<String, String> forecastToday = exampleForecast.get(0).getForecast();

        String displayString = forecastToday.get("name") + "\nTemperature: " + forecastToday.get("temperature") + "°" + forecastToday.get("temperatureUnit") + "\nWind Speed is " + forecastToday.get("windSpeed") + " going " + forecastToday.get("windDirection") + "\nCloud cover is " + forecastToday.get("shortForecast");
        Label test = new Label(displayString);
        Scene scene = new Scene(test);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
