package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
     //retrieves weather data
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

        WeatherFormatter formatter = new WeatherFormatter();
        String displayString = formatter.simpleFormat(forecastToday);

        VBox parent = new VBox();

        //this is a buttonBox in the main page
        HBox buttonBox = new HBox();

        Button buttonOne = new Button("Forecast");
        buttonBox.getChildren().add(buttonOne);

        Button buttonTwo = new Button("Radar");
        buttonBox.getChildren().add(buttonTwo);

        Button buttonThree = new Button("History");
        buttonBox.getChildren().add(buttonThree);

        parent.getChildren().add(buttonBox);
        // buttonBox end here

        HBox newArea = new HBox(new Label("Current Temperature"));
        TextField textFieldOne = new TextField();
        newArea.getChildren().add(textFieldOne);
        parent.getChildren().add(newArea);

        parent.getChildren().add(new Label(displayString));

        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}