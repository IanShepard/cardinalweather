package edu.bsu.cs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SceneBuilder {
    private Stage primaryStage;
    private WeatherZonesFeatures currLocation;

    public SceneBuilder(Stage stage, WeatherZonesFeatures currentLoc) {
        primaryStage = stage;
        currLocation = currentLoc;
    }

    //Home page
    //TODO resize nodes to appropriate size
    public Scene getHomePage(Forecast forecast) {
        HBox header = getHeaderPane();
        //header.setAlignment(Pos.TOP_CENTER);
        //TODO make button in top-left, label top-center, and icon top-right
        VBox forecastBox = getForecastPane(forecast);
        forecastBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox forecastButton = getOptionsPane();

        VBox parent = new VBox(header, forecastBox, forecastButton);

        return new Scene(parent);
    }

    private HBox getHeaderPane() {
        Button newLocation = new Button("Change Location");
        scaleNodeSize(newLocation, 1.25f);
        newLocation.setTranslateX(16);
        newLocation.setTranslateY(10);
        newLocation.setOnAction(actionEvent -> {
            ArrayList<String> emptyList = new ArrayList<>();
            Scene searchPage = getSearchPage(emptyList, primaryStage.getScene());
            primaryStage.setScene(searchPage);
        });

        newLocation.setAlignment(Pos.TOP_LEFT);
        Label title = new Label("Cardinal Weather");
        setFontSize(title, 30);
        title.setPadding(new Insets(10,0,0,160));
        title.setAlignment(Pos.TOP_CENTER);
        ImageView logo = imageFromLocal("/edu/bsu/cs/images/up_arrow.png");
        logo.setTranslateX(480);
        logo.setTranslateY(10);
        setDimensions(logo, 30, 30);

        return new HBox(newLocation, title, logo);
    }

    private VBox getForecastPane(Forecast forecast) {
        Label location = new Label(forecast.getLocation());
        setFontSize(location, 30);
        location.setAlignment(Pos.TOP_CENTER);
        location.setPadding(new Insets(15,0,0,0));
        HBox tempCompact = getCompact(forecast);
        tempCompact.setAlignment(Pos.TOP_CENTER);
        VBox cloudCover = getCloudCover(forecast);
        cloudCover.setAlignment(Pos.TOP_CENTER);
        cloudCover.setPadding(new Insets(110, 0, 0, 0));

        return new VBox(location, tempCompact, cloudCover);
    }

    private VBox getOptionsPane() {
        Button hourlyForecast = new Button("Hourly Forecast");
        scaleNodeSize(hourlyForecast, 1.25f);
        hourlyForecast.setTranslateX(16);
        hourlyForecast.setOnAction(actionEvent -> {
            ArrayList<Forecast> forecasts = new ArrayList<>();
            Calendar time = new GregorianCalendar();
            for(int i=0;i<5;i++){
                forecasts.add(new Forecast(currLocation, time));
                time.add(Calendar.HOUR, 1);
            }
            Scene forecastPage = getHourlyForecastPage(primaryStage.getScene(), forecasts);
            primaryStage.setScene(forecastPage);
        });
        Button dailyForecast = new Button("Daily Forecast");
        scaleNodeSize(dailyForecast, 1.25f);
        dailyForecast.setTranslateX(15);
        dailyForecast.setTranslateY(15);

        return new VBox(hourlyForecast, dailyForecast);
    }

    private VBox getCloudCover(Forecast forecast) {
        ImageView cloudCover = imageFromUrl(forecast.getIconSmall());
        scaleNodeSize(cloudCover,4 );
        Label shortForecast = new Label(forecast.getShortForecast());
        setFontSize(shortForecast, 30);
        shortForecast.setPadding(new Insets(60, 0, 0, 0));
        return new VBox(cloudCover, shortForecast);
    }

    private HBox getCompact (Forecast forecast) {
        Label tempCurr = new Label(forecast.getCurrentTemperatureAsString());
        setFontSize(tempCurr, 30);
        VBox tempHighLow = getHighLow(forecast);
        tempCurr.setPadding(new Insets(38, 0, 0, 0));

        return new HBox(tempCurr, tempHighLow);
    }

    private VBox getHighLow (Forecast forecast) {
        HBox high = getHigh(forecast);
        high.setPadding(new Insets(45, 0, 0, 15));
        HBox low = getLow(forecast);
        low.setPadding(new Insets(20, 0, 0, 15));

        return new VBox(high, low);
    }

    private HBox getHigh(Forecast forecast) {
        Label tempHigh = new Label(forecast.getHighTemperatureAsString());
        setFontSize(tempHigh, 30);
        ImageView upArrow = imageFromLocal("/edu/bsu/cs/images/up_arrow.png");
        tempHigh.setPadding(new Insets(3, 0, 0, 8));
        upArrow.setFitHeight(25);
        upArrow.setFitWidth(25);

        return new HBox(upArrow, tempHigh);
    }

    private HBox getLow(Forecast forecast) {
        Label tempLow = new Label(forecast.getLowTemperatureAsString());
        setFontSize(tempLow, 30);
        ImageView downArrow = imageFromLocal("/edu/bsu/cs/images/down_arrow.png");
        tempLow.setPadding(new Insets(2, 0, 0, 8));
        downArrow.setFitWidth(25);
        downArrow.setFitHeight(25);


        return new HBox(downArrow, tempLow);
    }

    //Search page

    public Scene getSearchPage(ArrayList<String> prevSearch, Scene prevScene) {
        Button back = new Button("Back");

        Button newSearch = new Button("New Search");
        Button prevLocs = new Button("Previous Locations");
        HBox locSelector = new HBox(newSearch, prevLocs);
        VBox main = new VBox(back, locSelector);

        back.setOnAction(actionEvent -> {
            primaryStage.setScene(prevScene);
        });
        /*
        newSearch.setOnAction(actionEvent -> {
            HBox searcher = getNewSearch();
            if (main.getChildren().get(2));
        });
        */
        return new Scene(main);
    }

    private HBox getNewSearch() {
        TextField searchField = new TextField();
        Button search = new Button("Search");
        //TODO add functionality to button
        return new HBox(searchField, search);
    }

    private HBox getPrevSearch(ArrayList<String> prevSearch) {
        ChoiceBox<String> prevSearches = fillChoiceBox(prevSearch);
        Button go = new Button("Go");
        //TODO add functionality to button
        return new HBox(prevSearches, go);
    }



    //five-day forecast
    //TODO design five-day forecast and implement similar to getHomePage(). Sections broken down into their own functions.
    //Hourly forecast

    //TODO design hourly forecast and implement similar to getHomePage(). May use same components as five-day forecast.
    public Scene getHourlyForecastPage (Scene prevScene, ArrayList<Forecast> forecasts){
        Button back = new Button("Back");
        scaleNodeSize(back, 1.25f);
        back.setTranslateX(10);
        back.setTranslateY(20);
        HBox header = getHeaderPane();
        back.setOnAction(actionEvent -> {
            primaryStage.setScene(prevScene);
        });
        VBox structure = new VBox(header, back);
        HBox hourlyForecasts = new HBox();
        for(int i =0;i<5;i++){

            VBox hourlyForecast = getHourlyForecast(forecasts.get(i));
            hourlyForecasts.getChildren().add(hourlyForecast);
        }

        structure.getChildren().add(hourlyForecasts);
        return new Scene(structure);
    }
    private VBox getHourlyForecast(Forecast forecast){

        String time;
        if (forecast.getAmPm().equals("1")){
            time=forecast.getHour()+" PM";
        }
        else {
            time=forecast.getHour()+" AM";
        }
        Label text = new Label(time);
        setFontSize(text, 20);
        text.setTranslateX(30);
        text.setTranslateY(30);
        ImageView one = imageFromUrl(forecast.getIconMedium());
        one.setTranslateX(5);
        one.setTranslateY(30);
        Label temp = new Label(forecast.getCurrentTemperatureAsString());
        setFontSize(temp, 20);
        temp.setTranslateX(40);
        temp.setTranslateY(30);

        return new VBox(text, one, temp);
    }
    //helper functions

    /*
    Takes a node with text and sets the font size so that the characters are ptSize pixels tall.
     */
    private void setFontSize(Node n, int ptSize) {
        float scaleFactor = ptSize / 11f;
        scaleNodeSize(n, scaleFactor);
    }

    private void scaleNodeSize(Node n, float scaler) {
        n.setScaleX(scaler);
        n.setScaleY(scaler);
    }

    private void setDimensions(ImageView image, int width, int hight) {
        image.setFitWidth(width);
        image.setFitHeight(hight);
    }

    private ChoiceBox<String> fillChoiceBox(ArrayList<String> choices) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for(String choice : choices) {
            choiceBox.getItems().add(choice);
        }
        return choiceBox;
    }

    private ImageView imageFromLocal(String source) {
        Image image = new Image(getClass().getResourceAsStream(source));
        return new ImageView(image);
    }

    private ImageView imageFromUrl(String url) {
        Image image = new Image(url);
        return new ImageView(image);
    }
}
