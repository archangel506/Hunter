package ru.nsu.fit.g15203.sushko;

import javax.swing.*;
import java.awt.image.BufferedImage;

public final class ControlsFunction
{
    private FileParser parser;
    private DrawerFunction drawerFunction;
    private ConfigField configField;
    private BufferedImage funcImage;
    private BufferedImage legendImage;
    private Main main;

    public ControlsFunction(final JFrame frame, final Main main)
    {
        this.main = main;
        parser = new FileParser(frame);
        loadConfig();
    }

    public void loadConfig()
    {
        parser.loadState();
        configField = parser.getConfigField();
        drawerFunction = new DrawerFunction(configField, parser.getFuncColors(),
                parser.getIzolineColor(), parser.getCountLevels());
    }

    public void newFuncImage(final int width, final int height){
        funcImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void newlegendImage(final int width, final int height){
        legendImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void setValueStatusBar(final int x, final  int y, final double value){
        main.setValueStatusBar(x, y, value);
    }

    public boolean getStateGrid()
    {
        return drawerFunction.isHaveGrid();
    }

    public void draw(){
        drawerFunction.drawImageFunc(funcImage, legendImage);
    }

    public double[] getValuesLegend(){
        return drawerFunction.getLegendValues();
    }

    public void setStateGrid(boolean flag)
    {
        drawerFunction.setHaveGrid(flag);
    }

    public boolean getStateIzoline()
    {
        return drawerFunction.isHaveIzolines();
    }

    public void changeStateIzoline()
    {
        boolean temp = drawerFunction.isHaveIzolines();
        drawerFunction.setHaveIzolines(!temp);
    }

    public boolean getStateInterpolation()
    {
        return drawerFunction.isInterpolation();
    }

    public void changeStateInterpolation()
    {
        boolean temp = drawerFunction.isInterpolation();
        drawerFunction.setInterpolation(!temp);
    }

    public void setMouseIzoline(int x, int y){


        double value =  calculateValue(x,y);

        drawerFunction.setMouseValue(value);
    }

    public void flushMouseIzoline(){
        drawerFunction.setMouseValue(DrawerFunction.NOT_CLICKED);
    }

    public double calculateValue(final int x, final int y)
    {
        MyFunction function = new MyFunction(configField);

        return function.f
                (
                        x * (configField.getB() - configField.getA()) / funcImage.getWidth(),
                        y * (configField.getD() - configField.getC()) / funcImage.getHeight()
                );
    }


    public ConfigField getConfigField() {
        return configField;
    }

    public BufferedImage getFuncImage() {
        return funcImage;
    }

    public BufferedImage getLegendImage() {
        return legendImage;
    }
    public void setConfigField(final ConfigField configField) {
        this.configField = configField;
    }




}
