package edu.bsu.cs;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class SceneBuilder {


    public Scene getHomeScene(Period period) {
        int WINDOW_WIDTH = 700;
        int WINDOW_HEIGHT = 500;

        //Home Scene Top Section------------------------------
        Button searchButton = new Button("Search");
        searchButton.setMinSize(100, 40);
        /*
        searchButton.fontProperty();
        searchButton.getOnKeyReleased();
        searchButton.getProperties();
        searchButton.resizeRelocate();
        searchButton.setFont();
        searchButton.setMaxSize();
        searchButton.setPadding();
        searchButton.setTextAlignment();
        searchButton.setTooltip();
        searchButton.toBack();
        searchButton.toFront();
        */
        TextArea searchBox = new TextArea();
        searchBox.setMaxSize(200, 40);
        Label location = new Label("Location data goes here");
        //location.setTextAlignment();
        Image weatherIcon = new Image("https://api.weather.gov/icons/land/day/sct?size=small");
        ImageView mainImage = new ImageView();
        mainImage.setImage(weatherIcon);


        HBox header = new HBox(searchBox, searchButton, location, mainImage);


        //Home Scene Middle Section---------------------------

        VBox forecastBox = getForecastBox(period);


        //Home Scene Bottom Section-------------------
        Button forecastButton = new Button("Daily Forecast");


        //Home Screen Parent element
        VBox parent = new VBox(header, forecastBox, forecastButton);

        Scene scene = new Scene(parent, WINDOW_WIDTH, WINDOW_HEIGHT);
        return scene;
    }

    public VBox getHomePage(Forecast forecast) {
        Label location = new Label(forecast.getLocation());
        setFontSize(location, 33);
        location.setPadding(new Insets(10, 0, 0, 200));
        HBox tempCompact = getCompact(forecast);
        VBox cloudCover = getCloudCover(forecast);
        cloudCover.setPadding(new Insets(100, 0, 0, 400));

        return new VBox(location, tempCompact, cloudCover);
    }

    private VBox getForecastBox(Period period) {
        Label currentTemperature = new Label("70");
        //Change font size
        setFontSize(currentTemperature, 70);

        Label highTemperature = new Label("80");
        Label lowTemperature = new Label("60");
        Image icon = new Image("https://api.weather.gov/icons/land/night/tsra,90?size=small");
        ImageView imageViewer = new ImageView();
        imageViewer.setImage(icon);

        VBox highLowBox = new VBox(highTemperature, lowTemperature);
        highLowBox.setPadding( new Insets(0, 0, 0, 100));
        HBox temperatureBox = new HBox(currentTemperature, highLowBox);
        temperatureBox.setPadding(new Insets(50, 100, 550 ,130));
        VBox forecastBox = new VBox(temperatureBox);
        return forecastBox;
    }


    public HBox getHigh(Forecast forecast) {
        Label tempHigh = new Label(forecast.getHighTemperatureAsString());
        setFontSize(tempHigh, 33);
        tempHigh.setPadding(new Insets(0, 0, 0, 8));
        Image upArrow = new Image(getClass().getResourceAsStream("/up_arrow.png"));
        ImageView imageView = new ImageView(upArrow);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        //scaleNodeSize(imageView, 0.05f);

        return new HBox(imageView, tempHigh);
    }

    public HBox getLow (Forecast forecast) {
        Label tempLow = new Label(forecast.getLowTemperatureAsString());
        setFontSize(tempLow, 33);
        tempLow.setPadding(new Insets(0, 0, 0, 8));
        Image downArrow = new Image(getClass().getResourceAsStream("/down_arrow.png"));
        ImageView imageView = new ImageView(downArrow);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        return new HBox(imageView, tempLow);
    }

    private VBox getHighLow (Forecast forecast) {
        HBox high = getHigh(forecast);
        high.setPadding(new Insets(35, 0, 0, 230));
        HBox low = getLow(forecast);
        low.setPadding(new Insets(15, 0, 0, 230));

        return new VBox(high, low);
    }

    private HBox getCompact (Forecast forecast) {
        Label tempCurr = new Label(forecast.getCurrentTemperatureAsString());
        setFontSize(tempCurr, 33);
        tempCurr.setPadding(new Insets(30, 0, 0, 190));
        VBox tempHighLow = getHighLow(forecast);

        return new HBox(tempCurr, tempHighLow);
    }

    private VBox getCloudCover(Forecast forecast) {
        Image icon = new Image(forecast.getIconSmall());
        ImageView imageView = new ImageView(icon);
        scaleNodeSize(imageView,4 );
        Label shortForecast = new Label(forecast.getShortForecast());
        setFontSize(shortForecast, 33);
        shortForecast.setPadding(new Insets(50, 10, 0, 0));

        return new VBox(imageView, shortForecast);
    }

    private void center(Node node) {}

    /*
    Takes a node with text and sets the font size so that the characters are ptSize pixels tall.
     */
    public void setFontSize(Node n, int ptSize) {
        float scaleFactor = ptSize / 11;
        scaleNodeSize(n, scaleFactor);
    }

    public void scaleNodeSize(Node n, float scaler) {
        n.setScaleX(scaler);
        n.setScaleY(scaler);
    }

    public ChoiceBox<String> fillChoiceBox(ArrayList<String> choices) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for(String choice : choices) {
            choiceBox.getItems().add(choice);
        }
        return choiceBox;
    }
}
