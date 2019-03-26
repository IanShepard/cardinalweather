package edu.bsu.cs;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

        //buttonOne details
        Button buttonOne = new Button("Forecast");

            VBox parentInButtonOne = new VBox();
            TextArea areaOne = new TextArea();
            parentInButtonOne.getChildren().add(areaOne);

        Stage buttonOneStage = new Stage();
        Scene sceneOne = new Scene(parentInButtonOne);
        buttonOneStage.setScene(sceneOne);
        buttonOneStage.setTitle("Forecast Page");

        buttonOne.setOnAction(actionEvent -> buttonOneStage.show());

        buttonBox.getChildren().add(buttonOne);
        //buttonOne ends here

        //button2222222
        Button buttonTwo = new Button("Radar");

            VBox parentInButtonTwo = new VBox();
            TextArea areaTwo = new TextArea();
            parentInButtonTwo.getChildren().add(areaTwo);
        Stage buttonTwoStage = new Stage();
        Scene sceneTwo = new Scene(parentInButtonTwo);
        buttonTwoStage.setScene(sceneTwo);
        buttonTwoStage.setTitle("Radar");

        buttonTwo.setOnAction(actionEvent -> buttonTwoStage.show());

        buttonBox.getChildren().add(buttonTwo);
        //ends

        //button333333
        Button buttonThree = new Button("History");
            VBox parentInButtonThree = new VBox();
            TextArea areaThree = new TextArea();
            parentInButtonThree.getChildren().add(areaThree);
        Stage buttonThreeStage = new Stage();
        Scene sceneThree = new Scene(parentInButtonThree);
        buttonThreeStage.setScene(sceneThree);
        buttonThreeStage.setTitle("History");

        buttonThree.setOnAction(actionEvent -> buttonThreeStage.show());

        buttonBox.getChildren().add(buttonThree);
        //ends

        parent.getChildren().add(buttonBox);
        //.............................. the entire buttonBox end here ...........................................

        HBox newArea = new HBox(new Label("Current Temperature"));
        TextField textFieldOne = new TextField();
        newArea.getChildren().add(textFieldOne);
        parent.getChildren().add(newArea);

        parent.getChildren().add(new Label(displayString));

        Scene scene = new Scene(parent, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cardinal Weather");
        primaryStage.show();
    }
}