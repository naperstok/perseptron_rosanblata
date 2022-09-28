package com.example.neural_networks;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Button save;
    @FXML
    private ToggleButton drowbtn;
    @FXML
    private Button erase;
    @FXML
    private Button punish;
    @FXML
    private Button go;
    @FXML
    private TextArea result;
    @FXML
    private ChoiceBox<Integer> choice;

    @FXML
    void initialize(){

        //save
        save.setOnAction((e)->{
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");

            Stage stage = Main.getPrimaryStage();
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(32, 32);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);

                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }

        });

        //draw
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

        canvas.setOnMousePressed(e->{
            if(drowbtn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            }
        });

        canvas.setOnMouseDragged(e->{
            if(drowbtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        canvas.setOnMouseReleased(e->{
            if(drowbtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }
        });

        //clear
        erase.setOnAction((e)->{
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            result.clear();
            choice.getSelectionModel().clearSelection();
        });

        go.setOnAction((e)->{
            WritableImage writableImage = new WritableImage(32, 32);
            canvas.snapshot(null, writableImage);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

            try {
                Main.getPerceptron().resultPrediction(bufferedImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            /*try {
                Main.getPerceptron().resultPrediction("C:\\Users\\User\\IdeaProjects\\neural_networks\\data_set\\0\\0_0.jpg");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }*/

            StringBuilder sb = new StringBuilder();
            sb.append("Result: \t");
            ArrayList<String> predicts = Main.getPerceptron().getPredictions();
            for (int i = 0; i < predicts.size(); i++) {
                sb.append(predicts.get(i));
                sb.append("\n");
            }
            result.appendText(String.valueOf(sb));
        });

        choice.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        punish.setOnAction((e)->{
            Integer correctNeuron = choice.getSelectionModel().getSelectedItem();
            ArrayList<String> predicts = Main.getPerceptron().getPredictions();
            ArrayList<Neuron> neurons = Main.getPerceptron().getNeurons();

            for(int n = 0; n < neurons.size(); n++) { //по всем нейронам
                if (n == correctNeuron) {
                    Main.getPerceptron().getNeurons().get(n).weightCorrectionUp(0.01);
                }
            }

            for(int n = 0; n < neurons.size(); n++) { //по всем нейронам
                for(int p = 0; p < predicts.size(); p++) {
                    if (n == Integer.parseInt(predicts.get(p)) && Integer.parseInt(predicts.get(p)) != correctNeuron) {
                        Main.getPerceptron().getNeurons().get(n).weightCorrectionDown(0.01);
                    }
                }
            }
            try {
                Main.getPerceptron().saveWeights();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });




    }

}