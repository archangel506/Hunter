package ru.nsu.fit.g15203.sushko;

import java.util.Arrays;

public final class MyFunction extends Function{
    private ConfigField configField;

    private double shiftX;
    private double shiftY;


    public MyFunction(ConfigField configField)
    {
        this.configField = configField;
        findMinAndMax();
    }

    private void findMinAndMax() {
        final double a = configField.getA();
        final double b = configField.getB();
        final double c = configField.getC();
        final double d = configField.getD();
        final int width = configField.getWidth();
        final int height = configField.getHeight();
        shiftX = (b - a) / (width - 1);
        shiftY = (d - c) / (height - 1);

        final double[] points = new double[width * height];
        calculateAllPoints(points);
        Arrays.sort(points);
        configField.setMax(points[points.length - 1]);
        configField.setMin(points[0]);
    }

    private void calculateAllPoints(final double[] points) {
        int width = configField.getWidth();
        int height = configField.getHeight();
        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                points[y * width + x] = f(  x * shiftX, y * shiftY);
            }
        }
    }

    @Override
    public double f(double x, double y){
        return Math.pow(x, 2) - Math.pow(y, 2);
    }

    @Override
    public ConfigField getConfigField() {
        return configField;
    }
}
