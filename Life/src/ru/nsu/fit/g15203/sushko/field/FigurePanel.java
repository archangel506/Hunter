package ru.nsu.fit.g15203.sushko.field;

import ru.nsu.fit.g15203.sushko.hex.HexField;
import ru.nsu.fit.g15203.sushko.hex.bresenhem.BresDrawLine;
import ru.nsu.fit.g15203.sushko.hex.span.FillerSpan;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class FigurePanel extends JPanel{
    private static final int widthCount = 7;
    private static final int heightCount = 8;
    private static final int radiusFigure = 25;

    private boolean isPlay = false;

    private Field field;

    private Timer timer = new Timer();
    private TimerTask timerTask = new MyTask();

    public FigurePanel() {
        this.field = new HexField(new BresDrawLine(), new FillerSpan(), radiusFigure, widthCount, heightCount);
        JLabel label = new JLabel(new ImageIcon(field.getBufferedImage()));

        MyMouseListener myMouseListener = new MyMouseListener();
        label.addMouseListener(myMouseListener);
        label.addMouseMotionListener(myMouseListener);

        add(label);
        repaintField();
    }

    public void nextStep(){
        if(isPlay){
            return;
        }
        field.nextStep();
        repaintField();
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

    private void stopGame(){
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timerTask = new MyTask();
        isPlay = false;
    }


    private class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(isPlay || e.getX() < 0 || e.getY() < 0){
                return;
            }
            field.elemChoise(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(isPlay || e.getX() < 0 || e.getY() < 0){
                return;
            }
            field.elemChoise(e.getX(), e.getY());
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
