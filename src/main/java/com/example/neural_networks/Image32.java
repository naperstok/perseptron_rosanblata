package com.example.neural_networks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Image32 {

    private String path;
    private int name;
    private ArrayList<Double> pixels = new ArrayList<>();

    public Image32(String path) throws IOException {
        this.path = path;
        setPixels(path);
        this.name = setName();
    }

    public Image32(BufferedImage bufferedImage) throws IOException {
        Color c1 = Color.WHITE;
        for(int i = 0; i < bufferedImage.getHeight(); i++) {
            for(int j = 0; j < bufferedImage.getWidth(); j++) {
                Color c = new Color(bufferedImage.getRGB(j, i));
                if(c.equals(c1)) {
                    pixels.add(0.0);
                } else {
                    pixels.add(1.0);
                }
            }
        }
    }

    public ArrayList<Double> getPixels() {
        return pixels;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPixels(String path) throws IOException {
        File input = new File(path);
        BufferedImage image = ImageIO.read(input);
        Color c1 = Color.WHITE;

        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j, i));
                if(c.equals(c1)) {
                    pixels.add(0.0);
                } else {
                    pixels.add(1.0);
                }
            }
        }
    }

    public int setName(){
        String s = getPath();
        int index = s.lastIndexOf('\\');

        String buffName = s.substring(index+1);
        //System.out.println(buffName);

        int secondIndex = buffName.lastIndexOf('_');
        String name = buffName.substring(0, secondIndex);
        //System.out.println(name);
        return Integer.parseInt(name);
    }

    public int getName() {
        return name;
    }
}
