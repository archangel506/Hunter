package ru.nsu.fit.g15203.sushko.field;

import ru.nsu.fit.g15203.sushko.hex.HexField;
import ru.nsu.fit.g15203.sushko.hex.bresenhem.BresDrawLine;
import ru.nsu.fit.g15203.sushko.hex.span.FillerSpan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FigurePanel extends JPanel{
    private boolean isPlay = false;

    private HexField field;

    private boolean stateChange = false;
    private Timer timer = new Timer();
    private TimerTask timerTask = new MyTask();
    private FileMng fileMng;
    private JLabel label;

    public FigurePanel() {
        int widthCount = 7;
        int heightCount = 8;
        int radiusFigure = 30;
        this.field = new HexField(new BresDrawLine(), new FillerSpan(), radiusFigure, widthCount, heightCount);
        this.fileMng = new FileMng(field);
        label = new JLabel(new ImageIcon(field.getBufferedImage()));

        MyMouseListener myMouseListener = new MyMouseListener();
        label.addMouseListener(myMouseListener);
        label.addMouseMotionListener(myMouseListener);

        add(label);
        stateChange = false;

        repaintField();
    }

    public String lastLoadFile(){
        return fileMng.getLastParseFile();
    }

    public void nextStep(){
        if(isPlay){
            return;
        }
        stateChange = true;
        field.nextStep();
        repaintField();
    }

    public void recalculate(){
        field.reCalculateField();
    }

    public void repaintField(){
        field.drawField();
        repaint();
    }

    public void resetField(){
        if(isPlay){
            stopGame();
        }
        field.resetField();
        stateChange = false;
        repaintField();
    }

    public void play(){
        if(isPlay){
            stopGame();
            return;
        }

        isPlay = true;
        timer.schedule(timerTask, 0, 500);
    }

    public void showImpact(){
        field.showImpact();
        repaintField();
    }

    public void stopGame(){
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timerTask = new MyTask();
        isPlay = false;
    }

    public void saveState(String file){
        if(file.equals("nullnull")){
            return;
        }
        stateChange = false;
        fileMng.saveState(file);
    }

    public void loadState(String file){
        if(file.equals("nullnull")){
            return;
        }
        try {
            fileMng.loadState(file);
            stateChange = false;
        } catch (ParseFileException e){
            e.printStackTrace();
            return;
        }

        reloadImage();
        repaintField();
    }

    public void reloadImage(){
        label.setIcon(new ImageIcon(field.getBufferedImage()));
        label.revalidate();

        add(label);
        revalidate();
    }

    public boolean isXorMode(){
        return field.isXorEnable();
    }

    public double getFstImpact() {
        return field.getFstImpact();
    }

    public double getSndImpact(){
        return field.getSndImpact();
    }

    public double getLiveBegin() {
        return field.getLiveBegin();
    }

    public double getLiveEnd() {
        return field.getLiveEnd();
    }

    public double getBirthBegin() {
        return field.getBirthBegin();
    }

    public double getBirthEnd() {
        return field.getBirthEnd();
    }

    public int getRadiusFigure() {
        return field.getRadius();
    }

    public int getWidthLine(){
        return field.getWidthLine();
    }

    public int getWidthCount(){
        return field.getWidth();
    }

    public int getHeightCount(){
        return field.getHeight();
    }

    public boolean isStateChange() {
        return stateChange;
    }

    public void setImpact(double first, double second, double liveBeg, double liveEnd, double birthBeg, double birthEnd){
        field.setFstImpact(first);
        field.setSndImpact(second);
        field.setLiveBegin(liveBeg);
        field.setLiveEnd(liveEnd);
        field.setBirthBegin(birthBeg);
        field.setBirthEnd(birthEnd);
    }

    public void setXor(boolean flag){
        field.setXorEnable(flag);
    }

    public void setRadius(int radius){
        field.setRadiusHex(radius);
    }

    public void setField(int widthCount, int heightCount){
        field.setSizeField(widthCount, heightCount);
    }

    public void setWidthLine(int widthLine){
        field.setWidthLine(widthLine);
    }


    private class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(isPlay || e.getX() < 0 || e.getY() < 0){
                return;
            }
            stateChange = true;
            field.elemChoise(e.getX(), e.getY(), false);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            field.offMove();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(isPlay || e.getX() < 0 || e.getY() < 0){
                return;
            }
            stateChange = true;
            field.elemChoise(e.getX(), e.getY(), true);
            repaint();
        }
    }

    private class MyTask extends TimerTask{

        @Override
        public void run() {
            field.nextStep();
            repaintField();
        }
    }
}
