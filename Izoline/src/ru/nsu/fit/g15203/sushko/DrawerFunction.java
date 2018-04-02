package ru.nsu.fit.g15203.sushko;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawerFunction {
    private final Color[] funcColors;
    private final Color izolineColor;
    private Function function;
    private final Legend legend = new Legend();
    @Getter @Setter private int countLevels;
    @Getter @Setter private boolean isInterpolation = false;
    @Getter @Setter private boolean haveGrid = false;
    @Getter @Setter private boolean izolines = false;
    private double[] criticalValues;

    public DrawerFunction(ConfigField configField, Color[] funcColors, Color izolineColor, int countLevels)
    {
       function = new Function(configField);
       this.funcColors = funcColors;
       this.izolineColor = izolineColor;
       this.countLevels = countLevels;
    }

    public void drawImageFunc(BufferedImage bufferedImage)
    {

    }

    private void drawFunc(BufferedImage bufferedImage)
    {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        ConfigField configField = function.getConfigField();

        double domX = (configField.getB() - configField.getA()) / width;
        double domY = (configField.getD() - configField.getC()) / height;

        double[] points = new double[width * height];
        double max = configField.getMax();
        double min = configField.getMin();

        int[] pixelPoints = new int[width * height];
        bufferedImage.getRGB(0,0, width, height, pixelPoints, 0, width);

        for(int x = 0; x < height; ++x){
            for(int y = 0; y < width; ++y){
                points[x * width + y] = function.f(x * domX, y * domY);
            }
        }


        criticalValues = new double[countLevels];

        double offset = (configField.getMax() - configField.getMin()) / countLevels;
        for (int i = 0; i < countLevels; i++) {
            criticalValues[i] = configField.getMin() + i * offset;
        }





    }


}
