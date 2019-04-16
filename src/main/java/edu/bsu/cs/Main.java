package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
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

        Period[] asdf = new Parser().getForecast("Delaware, IN");
        SceneBuilder sceneBuilder = new SceneBuilder();
        Scene homeScene = sceneBuilder.getHomeScene(asdf[0]);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Cardinal Weather");
        primaryStage.show();
    }
}