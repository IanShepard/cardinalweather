package edu.bsu.cs;

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
        newLocation.setOnAction(actionEvent -> {
            ArrayList<String> emptyList = new ArrayList<>();
            Scene searchPage = getSearchPage(emptyList, primaryStage.getScene());
            primaryStage.setScene(searchPage);
        });

        newLocation.setAlignment(Pos.TOP_LEFT);
        Label title = new Label("Cardinal Weather");
        title.setAlignment(Pos.TOP_CENTER);
        ImageView logo = imageFromLocal("/edu/bsu/cs/images/up_arrow.png");
        setDimensions(logo, 30, 30);

        return new HBox(newLocation, title, logo);
    }

    private VBox getForecastPane(Forecast forecast) {
        Label location = new Label(forecast.getLocation());
        location.setAlignment(Pos.TOP_CENTER);
        HBox tempCompact = getCompact(forecast);
        tempCompact.setAlignment(Pos.TOP_CENTER);
        VBox cloudCover = getCloudCover(forecast);
        cloudCover.setAlignment(Pos.TOP_CENTER);

        return new VBox(location, tempCompact, cloudCover);
    }

    private VBox getOptionsPane() {
        Button hourlyForecast = new Button("Hourly Forecast");
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

        return new VBox(hourlyForecast, dailyForecast);
    }

    private VBox getCloudCover(Forecast forecast) {
        ImageView cloudCover = imageFromUrl(forecast.getIconSmall());
        Label shortForecast = new Label(forecast.getShortForecast());

        return new VBox(cloudCover, shortForecast);
    }

    private HBox getCompact (Forecast forecast) {
        Label tempCurr = new Label(forecast.getCurrentTemperatureAsString());
        VBox tempHighLow = getHighLow(forecast);

        return new HBox(tempCurr, tempHighLow);
    }

    private VBox getHighLow (Forecast forecast) {
        HBox high = getHigh(forecast);
        HBox low = getLow(forecast);

        return new VBox(high, low);
    }

    private HBox getHigh(Forecast forecast) {
        Label tempHigh = new Label(forecast.getHighTemperatureAsString());
        setFontSize(tempHigh, 30);
        ImageView upArrow = imageFromLocal("/edu/bsu/cs/images/up_arrow.png");
        upArrow.setFitHeight(25);
        upArrow.setFitWidth(25);

        return new HBox(upArrow, tempHigh);
    }

    private HBox getLow(Forecast forecast) {
        Label tempLow = new Label(forecast.getLowTemperatureAsString());
        ImageView downArrow = imageFromLocal("/edu/bsu/cs/images/down_arrow.png");
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
        ImageView one = imageFromUrl(forecast.getIconMedium());
        Label temp = new Label(forecast.getCurrentTemperatureAsString());

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
