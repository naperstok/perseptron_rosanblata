package com.example.neural_networks;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Perceptron {
    private ArrayList<Neuron> neurons;
    private int bias;
    private ArrayList<String> predictions;

    public Perceptron(int bias, int neuronCount) {
        this.bias = bias;
        neurons = new ArrayList<>();
        for(int i = 0; i < neuronCount; i++){
            neurons.add(new Neuron());
        }
    }

    public ArrayList<String> getPredictions() {
        return predictions;
    }
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void resultPrediction(String path) throws IOException {
        predictions = new ArrayList<>();
        Image32 image = new Image32(path);
        for(int i = 0; i <  neurons.size(); i++){
            double value = neurons.get(i).neuronValue(image.getPixels());
            if(value > bias){
                predictions.add(String.valueOf(i));
            }
        }
        ArrayList<String> sortedPredictions = new ArrayList<>();
        int num = -1;

        while (predictions.size() != 0) {
            double max = 0;
            for (int j = 0; j < predictions.size(); j++) {
                double value = neurons.get(Integer.parseInt(predictions.get(j))).neuronValue(image.getPixels());
                if (value > max) {
                    max = value;
                    num = j;
                }
            }
            sortedPredictions.add(String.valueOf(predictions.get(num)));
            predictions.remove(num);
        }
        predictions = sortedPredictions;
    }
    public void resultPrediction(BufferedImage bufferedImage) throws IOException {
        predictions = new ArrayList<>();
        Image32 image = new Image32(bufferedImage);
        for(int i = 0; i <  neurons.size(); i++){
            double value = neurons.get(i).neuronValue(image.getPixels());
            if(value > bias){
                predictions.add(String.valueOf(i));
            }
        }
        ArrayList<String> sortedPredictions = new ArrayList<>();
        int num = -1;
        while (predictions.size() != 0) {
            double max = 0;
            for (int j = 0; j < predictions.size(); j++) {
                double value = neurons.get(Integer.parseInt(predictions.get(j))).neuronValue(image.getPixels());
                if (value > max) {
                    max = value;
                    num = j;
                }
            }
            sortedPredictions.add(String.valueOf(predictions.get(num)));
            predictions.remove(num);
        }
        predictions = sortedPredictions;
    }

    public void training(int epoch) throws IOException {
        ArrayList<ArrayList<String>> dataSet = getDataSet();
        for(int i = 0; i < epoch; i++){ //по всем эпохам
            System.out.println(i);
            for(int n = 0; n < neurons.size(); n++){ //по всем нейронам
               // for(int j = 0; j < dataSet.size(); j++){ //по всем папкам
                for(int j = 0; j < 6; j++){ //
                    for(int k = 0; k < dataSet.get(j).size(); k++){ //по всем файлам внутри
                        Image32 image = new Image32(dataSet.get(j).get(k));
                        double value = neurons.get(n).neuronValue(image.getPixels());
                        if(value > bias && n != image.getName()){
                            neurons.get(n).weightCorrectionDown(image.getPixels(), 0.01);
                            neurons.get(image.getName()).weightCorrectionUp(image.getPixels(), 0.01);
                        }
                    }
                }
            }
        }
        saveWeights();
    }

    public void test() throws IOException {
        ArrayList<ArrayList<String>> dataSet = getDataSet();
        int success = 0;
        int count = 0;
        for(int j = 6; j < 10; j++){ //по всем папкам
            for(int k = 0; k < dataSet.get(j).size(); k++){ //по всем файлам внутри
            Image32 image = new Image32(dataSet.get(j).get(k));
            resultPrediction(dataSet.get(j).get(k));
                if(Integer.parseInt(predictions.get(0)) == image.getName()) {
                    success++;
                }
            count ++;
            }
        }
        float stats = (success * 100 / count );
        System.out.println("Статистика угадываний:");
        System.out.println(stats + "%");
    }

    public void saveWeights() throws IOException {
        StringBuilder sb = new StringBuilder();
        for(int n = 0; n < neurons.size(); n++) {
            sb.append(n).append("\n");
            for(int i = 0; i < neurons.get(n).getWeights().size(); i++){
                sb.append(neurons.get(n).getWeights().get(i)).append("\n");
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("weights.txt"));
        writer.write(String.valueOf(sb));
        writer.close();
    }

    public ArrayList<ArrayList<String>> getDataSet() {
        String path = "data_set";
        File folder = new File(path);
        int folderCount = Objects.requireNonNull(folder.listFiles()).length;
        ArrayList<ArrayList<String>> files = new ArrayList<>();
        for(int i = 0; i < folderCount; i++){ //идем по всем папкам
            files.add(new ArrayList<>());
            String deepPath = path + "/" + i;
            File deepFolder = new File(deepPath);
            if(deepFolder.isDirectory()){ //проверяем, что есть такая директория
                for(File item : Objects.requireNonNull(deepFolder.listFiles())) {
                    files.get(i).add(item.getPath());
                }
            }
        }
        return files;
    }
    public void restoreWeights() throws IOException {
        String file ="weights.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        ArrayList<Double> weight = new ArrayList<>();
        while (currentLine != null){
            for(int n = 0; n < neurons.size(); n++){
                if(Integer.parseInt(currentLine) == n){
                    currentLine = reader.readLine();
                    for(int i = 0; i < 1024; i++){
                        weight.add(Double.parseDouble(currentLine));
                        currentLine = reader.readLine();
                    }
                    neurons.get(n).setWeights(weight);
                    weight.clear();
                }
            }
        }
        reader.close();
    }
}
