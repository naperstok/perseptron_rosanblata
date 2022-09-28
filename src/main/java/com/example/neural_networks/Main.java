package com.example.neural_networks;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    private static Stage primaryStage;
    private static Perceptron perceptron;
    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
    public static Perceptron getPerceptron() {
        return perceptron;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Разработка однослойной нейронной сети прямого распространения сигнала");
        stage.setScene(scene);
        stage.show();

        perceptron = new Perceptron(0, 10);
        //perceptron.training(50);
        perceptron.restoreWeights();
        perceptron.test();
    }

    public static void main(String[] args) {
        launch();
    }
}