package ru.nsu.fit.g15203.sushko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public final class ActionPanel extends JPanel
{
    private ControlsFunction controlsFunction;
    {
        setBackground(Color.GRAY);
    }

    ActionPanel(final ControlsFunction controlsFunction)
    {
        this.controlsFunction = controlsFunction;

        MyAdapter myAdapter = new MyAdapter();

        addMouseListener(myAdapter);
        addMouseMotionListener(myAdapter);
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        removeAll();

        int width = getWidth();
        int height = getHeight();

        controlsFunction.newFuncImage(width,  height * 8 / 10);
        controlsFunction.newlegendImage(width, height / 10);

        controlsFunction.draw();

        g.drawImage(controlsFunction.getFuncImage(), 0, 0, null);
        g.drawImage(controlsFunction.getLegendImage(), 0, getHeight() * 9 / 10, null);

        drawNumberLegend();
    }

    private void drawNumberLegend()
    {
        double[] legendValues = controlsFunction.getValuesLegend();
        int width = getWidth();
        int height = getHeight();

        JLabel[] labels = new JLabel[legendValues.length];
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(3);

        for (int i = legendValues.length - 1; i >= 0; --i)
        {
            labels[i] = new JLabel(decimalFormat.format(legendValues[i]));
            labels[i].setBounds
                    (
                            (legendValues.length - i) * (width / (legendValues.length + 1)) - height / 50,
                            height * 8 / 10 + height / 30,
                            width / 10,
                            height / 10
                    );

            add(labels[i]);
        }
    }


    private final class MyAdapter extends MouseAdapter
    {
        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getX() < getHeight() * 8 / 10)
            {
                controlsFunction.setMouseIzoline(e.getX(), e.getY());
                ActionPanel.this.repaint();
            }
        }

        @Override
        public void mouseDragged(final MouseEvent e)
        {
            if (e.getX() < getHeight() * 8 / 10) {
                controlsFunction.setMouseIzoline(e.getX(), e.getY());
                ActionPanel.this.repaint();
            }


        }

        @Override
        public void mouseMoved(final MouseEvent e)
        {
            controlsFunction.setValueStatusBar
                    (
                            e.getX(),
                            e.getY(),
                            controlsFunction.calculateValue(e.getX(), e.getY())
                    );
        }
    }
}
