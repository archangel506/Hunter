package ru.nsu.fit.g15203.sushko.field;

import ru.nsu.fit.g15203.sushko.hex.HexField;
import ru.nsu.fit.g15203.sushko.hex.bresenhem.BresDrawLine;
import ru.nsu.fit.g15203.sushko.hex.span.FillerSpan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class FigurePanel extends JPanel{
    private static final int widthCount = 35;
    private static final int heightCount = 35;
    private static final int radiusFigure = 20;

    private boolean isPlay = false;


    private Graphics graphics;
    private BufferedImage bufferedImage;
    private Field field;

    private Timer timer = new Timer();
    private TimerTask timerTask = new MyTask();

    public FigurePanel() {
        int widthField = (int)((double)widthCount  * (double)radiusFigure * 3);
        int heightField = (int)((double) heightCount * (double) radiusFigure * Math.sqrt(3));
        bufferedImage = new BufferedImage(widthField, heightField, BufferedImage.TYPE_INT_ARGB);
        this.field = new HexField(new BresDrawLine(), new FillerSpan(), radiusFigure, bufferedImage,
                widthCount, heightCount);//некультурно, но быстро, по хорошему нужно через сеттер

        graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.GRAY);
        JLabel label = new JLabel(new ImageIcon(bufferedImage));

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

    private void stopGame(){
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timerTask = new MyTask();
        isPlay = false;
    }


    private class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            field.elemChoise(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e){
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
