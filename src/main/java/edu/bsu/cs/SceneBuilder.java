package edu.bsu.cs;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        temperatureBox.setPadding(new Insets(50, 100, 50 ,130));
        VBox forecastBox = new VBox(temperatureBox);
        return forecastBox;
    }

    /*
    private HBox getHigh(Period period) {}

    private HBox getLow (Period period) {}

    private VBox getHighLow (Period period) {}

    private HBox getCompact () {}
    */

    /*
    Takes a node with text and sets the font size so that the characters are ptSize pixels tall.
     */
    public void setFontSize(Node n, int ptSize) {
        float scaleFactor = ptSize / 11;
        n.setScaleX(scaleFactor);
        n.setScaleY(scaleFactor);
    }
}
