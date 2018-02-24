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
    private int[][] widthCenterHex;
    private int[][] heightCenterHex;
    private int widthLine = 3;
    private boolean showImpact = false;
    private boolean xorEnable = true;

    private int radius;
    private int shiftX;
    private int shiftY;

    public HexField(DrawerLine drawerLine, FillerHex fillerHex, int radius, int width, int height) {
        this.drawerLine = drawerLine;
        this.fillerHex = fillerHex;

        shiftX = (int) Math.ceil(Math.sqrt(3) * radius);
        while (shiftX != (shiftX / 2) * 2) {
            ++radius;
            shiftX = (int) Math.ceil(Math.sqrt(3) * radius);
        }
        this.radius = radius;
        lifeField = new LifeField(height, width);
        widthCenterHex = new int[height][width];
        heightCenterHex = new int[height][width];
        shiftY = (int) Math.ceil(3 * radius / 2);

        int startCenterX = (int) Math.ceil(Math.sqrt(3) / 2 * radius) + SHIFT_FIELD;
        int centerY = radius;
        for (int i = 0; i < lifeField.getHeight(); ++i) {
            boolean longLine = (i % 2 == 0);
            int centerX = (longLine) ? startCenterX : startCenterX + (int) ((double) shiftX / 2);
            for (int j = 0; j < (longLine ? lifeField.getWidth() : lifeField.getWidth() - 1); ++j) {
                widthCenterHex[i][j] = centerX;
                heightCenterHex[i][j] = centerY;
                centerX += shiftX;
            }
            centerY += shiftY;
        }
        int coordLastCenterY = (height % 2 == 0) ? lifeField.getWidth() - 2 : lifeField.getWidth() - 1;
        int widthField =
                widthCenterHex[lifeField.getHeight() - 1][coordLastCenterY]
                        + (int) Math.ceil(Math.sqrt(3) * radius / 2) + widthLine
                        + ((height % 2 == 0) ? shiftX / 2 : 0);
        int heightField = heightCenterHex[lifeField.getHeight() - 1][coordLastCenterY]
                + radius + widthLine;
        this.bufferedImage = new BufferedImage(widthField, heightField, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void drawField() {

        for (int i = 0; i < lifeField.getHeight(); ++i) {
            for (int j = 0; j < ((i % 2 == 0) ? lifeField.getWidth() : lifeField.getWidth() - 1); ++j) {
                drawHexagon(i, j);
                if (showImpact) {
                    drawText(i, j);
                }
            }
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

        if (bufferedImage.getWidth() <= widhtPixel || bufferedImage.getHeight() <= heightPixel
                || !inHex(widhtPixel, heightPixel)) {
            return;
        }

        int guessWidth = (widhtPixel - SHIFT_FIELD) / shiftX;
        int guessHeight = heightPixel / shiftY;

        boolean longLine = (guessHeight % 2 == 0);

        if (guessWidth >= (longLine ? lifeField.getWidth() : lifeField.getWidth() - 1)) {
            guessWidth = (longLine ? lifeField.getWidth() - 1 : lifeField.getWidth() - 2);
        }
        if (guessHeight >= lifeField.getHeight()) {
            guessHeight = lifeField.getHeight() - 1;
        }
        int candidatWidth = guessWidth;
        int candidatHeight = guessHeight;

        boolean leftTopNeighbor = (longLine ? guessWidth > 0 : true) && guessHeight > 0;
        boolean rightTopNeighbor = (longLine ? true : guessWidth < lifeField.getWidth() - 1) && guessHeight > 0;
        boolean leftDownNeighbor = (longLine ? guessWidth > 0 : true) && guessHeight < lifeField.getHeight() - 1;
        boolean rightDownNeighbor = (longLine ? guessWidth < lifeField.getWidth() - 1 : true)
                && guessHeight < lifeField.getHeight() - 1;
        boolean leftNeighbor = guessWidth > 0;
        boolean rightNeighbor = guessWidth < lifeField.getWidth() - 1;

        if (leftNeighbor &&
                compareDistance(widhtPixel, heightPixel,
                        widthCenterHex[guessHeight][guessWidth - 1],
                        heightCenterHex[guessHeight][guessWidth - 1],
                        widthCenterHex[candidatHeight][candidatWidth],
                        heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = guessWidth - 1;
            candidatHeight = guessHeight;
        }

        if (rightNeighbor && compareDistance(widhtPixel, heightPixel,
                widthCenterHex[guessHeight][guessWidth + 1],
                heightCenterHex[guessHeight][guessWidth + 1],
                widthCenterHex[candidatHeight][candidatWidth],
                heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = guessWidth + 1;
            candidatHeight = guessHeight;
        }

        if (leftTopNeighbor && compareDistance(widhtPixel, heightPixel,
                widthCenterHex[guessHeight - 1][longLine ? guessWidth - 1 : guessWidth],
                heightCenterHex[guessHeight - 1][longLine ? guessWidth - 1 : guessWidth],
                widthCenterHex[candidatHeight][candidatWidth],
                heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth - 1 : guessWidth;
            candidatHeight = guessHeight - 1;
        }

        if (leftDownNeighbor && compareDistance(widhtPixel, heightPixel,
                widthCenterHex[guessHeight + 1][longLine ? guessWidth - 1 : guessWidth],
                heightCenterHex[guessHeight + 1][longLine ? guessWidth - 1 : guessWidth],
                widthCenterHex[candidatHeight][candidatWidth],
                heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth - 1 : guessWidth;
            candidatHeight = guessHeight + 1;
        }

        if (rightTopNeighbor && compareDistance(widhtPixel, heightPixel,
                widthCenterHex[guessHeight - 1][longLine ? guessWidth : guessWidth + 1],
                heightCenterHex[guessHeight - 1][longLine ? guessWidth : guessWidth + 1],
                widthCenterHex[candidatHeight][candidatWidth],
                heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth : guessWidth + 1;
            candidatHeight = guessHeight - 1;
        }

        if (rightDownNeighbor && compareDistance(widhtPixel, heightPixel,
                widthCenterHex[guessHeight + 1][longLine ? guessWidth : guessWidth + 1],
                heightCenterHex[guessHeight + 1][longLine ? guessWidth : guessWidth + 1],
                widthCenterHex[candidatHeight][candidatWidth],
                heightCenterHex[candidatHeight][candidatWidth])) {
            candidatWidth = longLine ? guessWidth : guessWidth + 1;
            candidatHeight = guessHeight + 1;
        }
        boolean isLifeElem;
        if(xorEnable){
            isLifeElem = !lifeField.getLifeState(candidatHeight, candidatWidth);
        } else {
            isLifeElem = true;
        }
        lifeField.setState(candidatHeight, candidatWidth, isLifeElem);
        drawField();
    }

    @Override
    public void nextStep() {
        lifeField.nextStep();
    }

    @Override
    public void showImpact() {
        showImpact = !showImpact;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private boolean inHex(int x, int y) {
        boolean haveLineLeft = false;
        boolean haveLineRight = false;
        boolean haveLineTop = false;
        boolean haveLineDown = false;
        for (int left = x; left >= 0; --left) {
            if (bufferedImage.getRGB(left, y) == HexField.COLOR_LINE) {
                haveLineLeft = true;
                break;
            }
        }
        for (int right = x; right < bufferedImage.getWidth(); ++right) {
            if (bufferedImage.getRGB(right, y) == HexField.COLOR_LINE) {
                haveLineRight = true;
                break;
            }
        }
        for (int top = y; top >= 0; --top) {
            if (bufferedImage.getRGB(x, top) == HexField.COLOR_LINE) {
                haveLineTop = true;
                break;
            }
        }
        for (int down = y; down < bufferedImage.getHeight(); ++down) {
            if (bufferedImage.getRGB(x, down) == HexField.COLOR_LINE) {
                haveLineDown = true;
                break;
            }
        }

        return haveLineDown && haveLineTop && haveLineLeft && haveLineRight;

    }

    private void drawHexagon(int i, int j) {
        int xCoord[] = getXCoordHex(widthCenterHex[i][j]);
        int yCoord[] = getYCoordHex(heightCenterHex[i][j]);

        if (widthLine == 1) {
            for (int k = 0; k < 6; ++k) {
                drawerLine.drawLine(xCoord[k], yCoord[k],
                        xCoord[(k + 1) % 6], yCoord[(k + 1) % 6], bufferedImage);
            }
        } else {
            Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
            graphics2D.setStroke(new BasicStroke(widthLine));
            graphics2D.setColor(Color.red);
            graphics2D.drawPolygon(xCoord, yCoord, 6);
        }
        if (lifeField.isLive(i, j)) {
            fillerHex.fillHex(widthCenterHex[i][j], heightCenterHex[i][j], COLOR_LIFE, bufferedImage);
        } else {
            fillerHex.fillHex(widthCenterHex[i][j], heightCenterHex[i][j], COLOR_DEAD, bufferedImage);
        }
    }

    private int[] getXCoordHex(int centerX) {

        int xCoord[] = new int[6];

        xCoord[0] = centerX;
        xCoord[1] = centerX + (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[2] = centerX + (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[3] = centerX;
        xCoord[4] = centerX - (int) Math.ceil(Math.sqrt(3) * radius / 2);
        xCoord[5] = centerX - (int) Math.ceil(Math.sqrt(3) * radius / 2);
        return xCoord;
    }

    private int[] getYCoordHex(int centerY) {

        int yCoord[] = new int[6];

        yCoord[0] = centerY - radius;
        yCoord[1] = centerY - radius / 2;
        yCoord[2] = centerY + radius / 2;
        yCoord[3] = centerY + radius;
        yCoord[4] = centerY + radius / 2;
        yCoord[5] = centerY - radius / 2;
        return yCoord;
    }

    private boolean compareDistance(int centerX, int centerY, int firstX, int firstY, int twelveX, int twelveY) {
        return distanceCenter(centerX, centerY, firstX, firstY)
                < distanceCenter(centerX, centerY, twelveX, twelveY);
    }

    private double distanceCenter(int centerX, int centerY, int pressX, int pressY) {
        return Math.sqrt(Math.pow((double) centerX - (double) pressX, (double) 2)
                + Math.pow((double) centerY - (double) pressY, (double) 2));
    }

    private void drawText(int x, int y) {
        String pattern;
        if (lifeField.getImpactState(x,y) % 1 == 0) {
            pattern = "##0";
        } else {
            pattern = "##0.0";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        bufferedImage.getGraphics().drawString(decimalFormat.format(lifeField.getLifeState(x, y)), widthCenterHex[x][y] - 3,
                heightCenterHex[x][y] + 3);
    }
}
