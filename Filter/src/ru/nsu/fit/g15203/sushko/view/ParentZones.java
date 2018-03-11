package ru.nsu.fit.g15203.sushko.view;


import ru.nsu.fit.g15203.sushko.controls.filters.*;
import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ParentZones extends JPanel {
    public static final int sizePanel = 350;

    private JPanel select = new JPanel();

    private ChildZones zoneA;
    private ChildZones zoneB;
    private ChildZones zoneC;

    private boolean enableSelect = false;


    public ParentZones(){
        Container pane = new Container();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setPreferredSize(new Dimension(sizePanel, sizePanel));

        initInnerPanel();
        initListeners();

        select.setOpaque(false);

    }

    public void clearZones(){
        zoneA.setImage(null);
        zoneB.setImage(null);
        zoneC.setImage(null);
        revalidate();
        repaint();
    }

    public ImageBmp getFinalImage() {
        return zoneC.getImage();
    }

    public void setImage(ImageBmp image){
        zoneA.setImage(image);
    }

    public void setEnableSelect(boolean enableSelect){
        this.enableSelect = enableSelect;
    }

    public boolean getEnableSelect(){
        return enableSelect;
    }

    public void copyLeft(){
        if(zoneC.getImage() == null){
            return;
        }

        zoneB.setImage(new ImageBmp(zoneC.getImage()));
        zoneB.repaint();
    }

    public void copyRight(){
        if(zoneB.getImage() == null){
            return;
        }
        zoneC.setImage(new ImageBmp(zoneB.getImage()));
        zoneC.repaint();
    }

    public void negative(){
        zoneC.setImage(new NegativeFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void aquarelle(){
        zoneC.setImage(new AquarelleFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void doubleZoom(){
        zoneC.setImage(new DoubleZoomFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void embassing(){
        zoneC.setImage(new EmbassingFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void blackWhite(){
        zoneC.setImage(new BlackWhiteFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void gauss(){
        zoneC.setImage(new GaussFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void floydStanberg(int red, int green, int blue){
        zoneC.setImage(new FloydStanbergFilter().algroritm(zoneB.getImage(), red, green, blue));
        zoneC.repaint();
    }

    public void orderDither(){
        zoneC.setImage(new OrderDitherFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public void sobel(int limit){
        ImageBmp imageBmp = new DualSupportFilter().algroritm(zoneB.getImage(), limit);
        zoneC.setImage(new SobelFilter().algroritm(imageBmp));
        zoneC.repaint();
    }

    public void roberts(int limit){
        ImageBmp imageBmp = new DualSupportFilter().algroritm(zoneB.getImage(), limit);
        zoneC.setImage(new RobertsFilter().algroritm(imageBmp));
        zoneC.repaint();
    }

    public void gamma(int value){
        zoneC.setImage(new GammaFilter().algroritm(zoneB.getImage(), value / 10.0));
        zoneC.repaint();
    }

    public void definition(){
        zoneC.setImage(new DefinitionFilter().algroritm(zoneB.getImage()));
        zoneC.repaint();
    }

    public boolean isChoiseFragment(){
        return zoneB.getImage() != null;
    }



    private void initInnerPanel() {
        initZoneA();
        initZoneB();
        initZoneC();
    }

    private void initListeners() {
        ZoneAMouseListener zoneAMouseListener = new ZoneAMouseListener();
        zoneA.addMouseMotionListener(zoneAMouseListener);
        zoneA.addMouseListener(zoneAMouseListener);
        zoneA.add(select);
    }

    private void initZoneA() {
        Container pane = new Container();
        zoneA = new ChildZones();
        zoneA.addMouseMotionListener(new ZoneAMouseListener());
        JPanel originBorderPanel = new JPanel(new GridLayout(1, 1));
        originBorderPanel.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createDashedBorder(Color.BLACK, 1, 20, 5, true)));
        originBorderPanel.setPreferredSize(new Dimension(sizePanel, sizePanel));
        originBorderPanel.add(zoneA);
        add(originBorderPanel, pane);


    }

    private void initZoneB() {
        Container pane = new Container();
        zoneB = new ChildZones();
        JPanel originBorderPanel = new JPanel(new GridLayout(1, 1));
        originBorderPanel.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createDashedBorder(Color.BLACK, 1, 20, 5, true)));
        originBorderPanel.setPreferredSize(new Dimension(sizePanel, sizePanel));
        originBorderPanel.add(zoneB);
        add(originBorderPanel, pane);
    }

    private void initZoneC() {
        Container pane = new Container();
        zoneC = new ChildZones();
        JPanel originBorderPanel = new JPanel(new GridLayout(1, 1));
        originBorderPanel.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createDashedBorder(Color.BLACK, 1, 20, 5, true)));
        originBorderPanel.setPreferredSize(new Dimension(sizePanel, sizePanel));
        originBorderPanel.add(zoneC);
        add(originBorderPanel, pane);
    }

    private class ZoneAMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectPartFrame(e.getX(), e.getY());
            select.setVisible(false);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            select.setVisible(false);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            selectPartFrame(e.getX(), e.getY());
        }

        private void selectPartFrame(int x, int y){
            ImageBmp image = zoneA.getImage();
            if (zoneA.getImage() == null || !enableSelect) return;

            setPozitionSelect(x, y);

            float k = (float) (image.getWidth()) / sizePanel;
            x = (int) (x * k) - sizePanel / 2;
            y = (int) (y * k) - sizePanel / 2;

            if (x < 0) {
                x = 0;
            } else if (x + sizePanel > image.getWidth()) {
                x = image.getWidth() - sizePanel;
            }

            if (y < 0) {
                y = 0;
            } else if (y + sizePanel > image.getHeight()) {
                y = image.getHeight() - sizePanel;
            }

            zoneB.setImage(image.copyFragment( x, y, sizePanel, sizePanel));
            zoneB.repaint();
        }

        private void setPozitionSelect(int x, int y){
            int leftBorderSelect = x - select.getWidth() / 2;
            int topBorderselect = y - select.getHeight() / 2;

            select.setVisible(true);
            select.setOpaque(false);
            select.setBorder(BorderFactory.createDashedBorder(Color.BLUE.brighter(), 1, 20, 5, true));
            select.setPreferredSize(new Dimension(zoneA.getSizeSelect(), zoneA.getSizeSelect()));

            if (leftBorderSelect < 0)
                leftBorderSelect = 0;
            if (leftBorderSelect > 350 - zoneA.getSizeSelect())
                leftBorderSelect = 350 - zoneA.getSizeSelect();
            if (topBorderselect < 0)
                topBorderselect = 0;
            if (topBorderselect > 350 - zoneA.getSizeSelect())
                topBorderselect = 350 - zoneA.getSizeSelect();

            select.setLocation(leftBorderSelect, topBorderselect);
        }
    }
}
