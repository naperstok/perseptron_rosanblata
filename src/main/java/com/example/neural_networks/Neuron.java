package com.example.neural_networks;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private ArrayList<Double> weights = new ArrayList<>();
    private int count = 1024;
    public Neuron() {
        Random rnd = new Random();
        double min = -1;
        double max = 1;
        for (int i = 0; i < count; i++) {
            weights.add(min + (max - min) * rnd.nextDouble());
        }
    }
    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = (ArrayList<Double>) weights.clone();
    }

    public void weightCorrectionDown(ArrayList<Double> pixels, double step){
        for(int i = 0; i < weights.size(); i++){
            if(pixels.get(i) != 0) {
                weights.set(i, weights.get(i) - step);
            }
        }
    }
    public void weightCorrectionDown(double step){
        for(int i = 0; i < weights.size(); i++){
           weights.set(i, weights.get(i) - step);
        }
    }

    public void weightCorrectionUp(ArrayList<Double> pixels, double step){
        for(int i = 0; i < weights.size(); i++){
            if(pixels.get(i) != 0) {
                weights.set(i, weights.get(i) + step);
            }
        }
    }
    public void weightCorrectionUp(double step){
        for(int i = 0; i < weights.size(); i++){
            weights.set(i, weights.get(i) + step);
        }
    }

    public double neuronValue(ArrayList<Double> pixels){
        double sum = 0;
        for(int i = 0; i < weights.size(); i++){
            sum += pixels.get(i) * weights.get(i);
        }
        return sum;
    }


}
