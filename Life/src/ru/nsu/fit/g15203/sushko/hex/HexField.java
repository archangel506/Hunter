package ru.nsu.fit.g15203.sushko.hex;

import ru.nsu.fit.g15203.sushko.field.Field;
import ru.nsu.fit.g15203.sushko.models.LifeField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class HexField implements Field {
    public static final int SHIFT_FIELD = 5;
    public static final int COLOR_DEAD = Color.BLUE.getRGB();
    public static final int COLOR_LIFE = Color.BLACK.getRGB();
    public static final int COLOR_LINE = Color.RED.getRGB();

    private DrawerLine drawerLine;
    private FillerHex fillerHex;
    private BufferedImage bufferedImage;
    private LifeField lifeField;
    private int[][] centerXHexs;
    private int[][] centerYHexs;

    private int radius;
    private int shiftX;
    private int shiftY;

    public HexField(DrawerLine drawerLine, FillerHex fillerHex, int radius, BufferedImage bufferedImage,
                    int width, int height) {
        this.drawerLine = drawerLine;
        this.fillerHex = fillerHex;
        this.bufferedImage = bufferedImage;

        shiftX = (int) Math.ceil(Math.sqrt(3) * radius);
        while (shiftX != (shiftX / 2) * 2) {
            ++radius;
            shiftX = (int) Math.ceil(Math.sqrt(3) * radius);
        }
        this.radius = radius;
        lifeField = new LifeField(height, width);
        centerXHexs = new int[height][width];
        centerYHexs = new int[height][width];
        shiftY = (int) Math.ceil(3 * radius / 2);

        int startCenterX = (int) Math.ceil(Math.sqrt(3) / 2 * radius) + SHIFT_FIELD;
        int centerY = radius;
        for (int i = 0; i < lifeField.getHeight(); ++i) {
            boolean longLine = (i % 2 == 0);
            int centerX = (longLine) ? startCenterX : startCenterX + (int) ((double) shiftX / 2);
            for (int j = 0; j < (longLine ? lifeField.getWidth() : lifeField.getWidth() - 1); ++j) {
                centerXHexs[i][j] = centerX;
                centerYHexs[i][j] = centerY;
                centerX += shiftX;
            }
            centerY += shiftY;
        }
    }

    @Override
    public void drawField() {
        for (int i = 0; i < lifeField.getHeight(); ++i) {
            for (int j = 0; j < ((i % 2 == 0) ? lifeField.getWidth() : lifeField.getWidth() - 1); ++j) {
                drawHexagon(centerXHexs[i][j], centerYHexs[i][j]);
                if (lifeField.isLive(i, j)) {
                    fillerHex.fillHex(centerXHexs[i][j], centerYHexs[i][j], COLOR_LIFE, bufferedImage);
                } else {
                    fillerHex.fillHex(centerXHexs[i][j], centerYHexs[i][j], COLOR_DEAD, bufferedImage);
                }
                drawText(i,j);
            }
        }
    }

    @Override
    public void drawField(int widthCount, int heightCount) {
        int startCenterX = (int) Math.ceil(Math.sqrt(3) / 2 * radius) + SHIFT_FIELD;
        int centerY = radius;

        for (int i = 0; i < heightCount; ++i) {
            boolean longLine = (i % 2 == 0);
            int centerX = (longLine) ? startCenterX : startCenterX + (int) ((double) shiftX / 2);
            for (int j = 0; j < (longLine ? widthCount : widthCount - 1); ++j) {
                drawHexagon(centerX, centerY);
                if (lifeField.isLive(i, j)) {
                    fillerHex.fillHex(centerX, centerY, COLOR_LIFE, bufferedImage);
                } else {
                    fillerHex.fillHex(centerX, centerY, COLOR_DEAD, bufferedImage);
                }
                centerX += shiftX;
            }
            centerY += shiftY;
        }
    }


    @Override
    public void fillElement(int x, int y, int color) {
        if (x >= bufferedImage.getWidth() || bufferedImage.getHeight() <= y
                || x < SHIFT_FIELD || y < 0) {
            return;
        }
        fillerHex.fillHex(x, y, color, bufferedImage);
    }

    @Override
    public void resetField() {
        lifeField.resetField();
    }

    @Override
    public void openField() {

    }

    @Override
    public void saveField() {

    }

    @Override
    public void setParameters() {

    }

    @Override
    public void elemChoise(int widhtPixel, int heightPixel) {
        int guessWidth = (widhtPixel - SHIFT_FIELD) / shiftX;
        int guessHeight = heightPixel / shiftY;

        boolean longLine = (guessWidth % 2 == 0);
        int candidatWidth = guessWidth;
        int candidatHeight = guessHeight;
        if(candidatWidth >= lifeField.getWidth()){
            candidatWidth = lifeField.getWidth() - 1;
        }
        if(candidatHeight >= lifeField.getHeight()){
            candidatHeight = lifeField.getHeight() - 1;
        }

        boolean leftTopNeighbor = (longLine ? guessWidth > 0 : true) && guessHeight > 0;
        boolean rightTopNeighbor = (longLine ? true : guessWidth < lifeField.getWidth() - 1) && guessHeight > 0;
        boolean leftDownNeighbor = (longLine ? guessWidth > 0 : true) && guessHeight < lifeField.getHeight() - 1;
        boolean rightDownNeighbor = (longLine ? true : guessWidth < lifeField.getHeight() - 1)
                && guessHeight < lifeField.getHeight() - 1;
        boolean leftNeighbor = guessWidth > 0;
        boolean rightNeighbor = guessWidth < lifeField.getWidth() - 1;

        if (leftNeighbor &&
                compareDistance(widhtPixel, heightPixel,
                        centerXHexs[guessHeight][guessWidth - 1],
                        centerYHexs[guessHeight][guessWidth - 1],
                        centerXHexs[candidatHeight][candidatWidth],
                        centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = guessWidth - 1;
            candidatHeight = guessHeight;
        }

        if (rightNeighbor && compareDistance(widhtPixel, heightPixel,
                centerXHexs[guessHeight][guessWidth + 1],
                centerYHexs[guessHeight][guessWidth + 1],
                centerXHexs[candidatHeight][candidatWidth],
                centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = guessWidth + 1;
            candidatHeight = guessHeight;
        }

        if (leftTopNeighbor && compareDistance(widhtPixel, heightPixel,
                centerXHexs[guessHeight - 1][longLine ? guessWidth - 1 : guessWidth],
                centerYHexs[guessHeight - 1][longLine ? guessWidth - 1 : guessWidth],
                centerXHexs[candidatHeight][candidatWidth],
                centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth - 1 : guessWidth;
            candidatHeight = guessHeight - 1;
        }

        if (leftDownNeighbor && compareDistance(widhtPixel, heightPixel,
                centerXHexs[guessHeight + 1][longLine ? guessWidth - 1 : guessWidth],
                centerYHexs[guessHeight + 1][longLine ? guessWidth - 1 : guessWidth],
                centerXHexs[candidatHeight][candidatWidth],
                centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth - 1 : guessWidth;
            candidatHeight = guessHeight + 1;
        }

        if (rightTopNeighbor && compareDistance(widhtPixel, heightPixel,
                centerXHexs[guessHeight - 1][longLine ? guessWidth : guessWidth + 1],
                centerYHexs[guessHeight - 1][longLine ? guessWidth : guessWidth + 1],
                centerXHexs[candidatHeight][candidatWidth],
                centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth : guessWidth + 1;
            candidatHeight = guessHeight - 1;
        }

        if (rightDownNeighbor && compareDistance(widhtPixel, heightPixel,
                centerXHexs[guessHeight + 1][longLine ? guessWidth : guessWidth + 1],
                centerYHexs[guessHeight + 1][longLine ? guessWidth : guessWidth + 1],
                centerXHexs[candidatHeight][candidatWidth],
                centerYHexs[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth : guessWidth + 1;
            candidatHeight = guessHeight + 1;
        }


        fillElement(widhtPixel, heightPixel, COLOR_LIFE);
        lifeField.setState(candidatHeight, candidatWidth, 3.0);
        drawText(candidatHeight , candidatWidth);
    }

    @Override
    public void nextStep() {
        lifeField.nextStep();
    }

    private void drawHexagon(int x, int y) {
        int xCoord[] = new int[6];
        int yCoord[] = new int[6];

        xCoord[0] = x;
        xCoord[1] = x + (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[2] = x + (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[3] = x;
        xCoord[4] = x - (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[5] = x - (int) Math.ceil(Math.sqrt(3) * radius / 2);

        yCoord[0] = y - radius;
        yCoord[1] = y - radius / 2;
        yCoord[2] = y + radius / 2;
        yCoord[3] = y + radius;
        yCoord[4] = y + radius / 2;
        yCoord[5] = y - radius / 2;


        for (int i = 0; i < 6; ++i) {
            drawerLine.drawLine(xCoord[i], yCoord[i],
                    xCoord[(i + 1) % 6], yCoord[(i + 1) % 6], bufferedImage);
        }


    }

    private boolean compareDistance(int centerX, int centerY, int firstX, int firstY, int twelveX, int twelveY) {
        return distanceCenter(centerX, centerY, firstX, firstY)
                < distanceCenter(centerX, centerY, twelveX, twelveY);
    }

    private double distanceCenter(int centerX, int centerY, int pressX, int pressY) {
        return Math.sqrt(Math.pow((double) centerX - (double) pressX, (double) 2)
                + Math.pow((double) centerY - (double) pressY, (double) 2));
    }

    private void drawText(int x, int y){
        String pattern = "##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        bufferedImage.getGraphics().drawString(decimalFormat.format(lifeField.getState(x, y)),
                centerXHexs[x][y], centerYHexs[x][y]);
    }
}
