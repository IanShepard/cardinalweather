package edu.bsu.cs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestJavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("C:\\Users\\Ian\\Pictures\\Wiki page data src.png");
        Image image = new Image(input);
        ImageView viewer = new ImageView();
        viewer.setImage(image);

        HBox hbox = new HBox(viewer);
        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        Stage stage0 = new Stage();
        stage0.show();
    }
}
