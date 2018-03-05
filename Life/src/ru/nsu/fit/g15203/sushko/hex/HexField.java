package ru.nsu.fit.g15203.sushko.hex;

import ru.nsu.fit.g15203.sushko.models.LifeField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HexField{
    public static final int COLOR_DEAD = Color.GRAY.getRGB();
    public static final int COLOR_LIFE = Color.ORANGE.getRGB();
    public static final int COLOR_LINE = Color.RED.getRGB();
    private static final int MINIMUM__RADIUS_SHOW_IMPACT = 14;

    private DrawerLine drawerLine;
    private FillerHex fillerHex;
    private BufferedImage bufferedImage;
    private LifeField lifeField;
    private int[][] widthCenterHex;
    private int[][] heightCenterHex;

    private int widthLine = 7;
    private boolean showImpact = false;

    private boolean xorEnable = true;
    private Point lastIndex = null;
    private int enterRadius;
    private int shiftFieldX;
    private int shiftFieldY;

    private int radius;
    private int shiftX;
    private int shiftY;

    public HexField(DrawerLine drawerLine, FillerHex fillerHex, int radius, int width, int height) {
        this.drawerLine = drawerLine;
        this.fillerHex = fillerHex;

        lifeField = new LifeField(height, width);
        setRadiusHex(radius);
        reCalculateField();
    }

    public void drawField() {

        for (int i = 0; i < lifeField.getHeight(); ++i) {
            for (int j = 0; j < ((i % 2 == 0) ? lifeField.getWidth() : lifeField.getWidth() - 1); ++j) {
                drawHexagon(i, j);
            }
        }
    }

    public void resetField() {
        lifeField.resetField();
    }

    public void offMove(){
        lastIndex = null;
    }

    public void elemChoise(int widhtPixel, int heightPixel) {
        if (bufferedImage.getWidth() <= widhtPixel || bufferedImage.getHeight() <= heightPixel
                || !inHex(widhtPixel, heightPixel)) {
            offMove();
            return;
        }

        int guessWidth = (widhtPixel - shiftFieldX) / shiftX;
        int guessHeight = (heightPixel - shiftFieldY) / shiftY;

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
            if(lastIndex == null || lastIndex.x != candidatHeight || lastIndex.y != candidatWidth){
                lastIndex = new Point(candidatHeight, candidatWidth);
                isLifeElem = !lifeField.getLifeState(candidatHeight, candidatWidth);
            } else{
                return;
            }
        } else {
            isLifeElem = true;
        }
        lifeField.setState(candidatHeight, candidatWidth, isLifeElem);
        drawHexAndNeighbors(candidatHeight, candidatWidth);
    }

    private void drawHexAndNeighbors(int candidatHeight, int candidatWidth) {
        drawHexagon(candidatHeight, candidatWidth);
        ArrayList<Point> firstNeighbors = lifeField.getFirstNeighbor(candidatHeight, candidatWidth);
        ArrayList<Point> secondNeighbors = lifeField.getSecondNeighbor(candidatHeight, candidatWidth, candidatHeight % 2 == 0);
        for(Point neighbor: firstNeighbors){
            drawHexagon(neighbor.x, neighbor.y);
        }
        for(Point neighbor: secondNeighbors){
            drawHexagon(neighbor.x, neighbor.y);
        }
    }

    public void nextStep() {
        lifeField.nextStep();
    }

    public void showImpact() {
        showImpact = !showImpact;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getWidthCount(){
        return lifeField.getWidth();
    }

    public int getHeightCount(){
        return lifeField.getHeight();
    }

    public void setSizeField(int witdhCount, int heightCount){
        lifeField.resizeField(witdhCount, heightCount);
    }

    public void setRadiusHex(int radius){
        enterRadius = radius;
        shiftX = (int) Math.ceil(Math.sqrt(3) * radius);

        while (shiftX != (shiftX / 2) * 2) {
            ++radius;
            shiftX = (int) Math.ceil(Math.sqrt(3) * radius);
        }
        shiftY = (int) Math.ceil(3 * radius / 2);
        this.radius = radius;
    }

    public void setWidthLine(int widthLine){
        this.widthLine = widthLine;
    }



    public int getWidthLine(){
        return widthLine;
    }

    public int getRadiusHex(){
        return enterRadius;
    }

    public ArrayList<Point> getLifeHex(){
        return lifeField.getLifeList();
    }

    public void setLifeHex(int x, int y){
        lifeField.setState(x, y, true);
    }

    public void reCalculateField(){
        widthCenterHex = new int[lifeField.getHeight()][lifeField.getWidth()];
        heightCenterHex = new int[lifeField.getHeight()][lifeField.getWidth()];

        shiftFieldX = widthLine / 2;
        shiftFieldY = widthLine / 2 + 1;

        int startCenterX = (int) Math.ceil(Math.sqrt(3) / 2 * radius) + shiftFieldX;
        int centerY = radius + shiftFieldY;
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
        int coordLastCenterY = (lifeField.getHeight() % 2 == 0) ? lifeField.getWidth() - 2 : lifeField.getWidth() - 1;
        int widthField =
                widthCenterHex[lifeField.getHeight() - 1][coordLastCenterY]
                        + (int) Math.ceil(Math.sqrt(3) * radius / 2) + widthLine
                        + ((lifeField.getHeight() % 2 == 0) ? shiftX / 2 : 0);
        int heightField = heightCenterHex[lifeField.getHeight() - 1][coordLastCenterY]
                + radius + widthLine;
        this.bufferedImage = new BufferedImage(widthField, heightField, BufferedImage.TYPE_INT_ARGB);


    }

    public boolean isXorEnable() {
        return xorEnable;
    }

    public void setXorEnable(boolean flag){
        xorEnable = flag;
    }

    public double getFstImpact() {
        return lifeField.getFstImpact();
    }

    public void setFstImpact(double impact){
        lifeField.setFstImpact(impact);
    }

    public double getSndImpact(){
        return lifeField.getSndImpact();
    }
    public void setSndImpact(double impact){
        lifeField.setSndImpact(impact);
    }

    public double getLiveBegin() {
        return lifeField.getLiveBegin();
    }
    public void setLiveBegin(double live) {
        lifeField.setLiveBegin(live);
    }

    public double getLiveEnd() {
        return lifeField.getLiveEnd();
    }
    public void setLiveEnd(double live) {
        lifeField.setLiveEnd(live);
    }

    public double getBirthBegin() {
        return lifeField.getBirthBegin();
    }
    public void setBirthBegin(double birth) {
        lifeField.setBirthBegin(birth);
    }

    public double getBirthEnd() {
        return lifeField.getBirthEnd();
    }
    public void setBirthEnd(double birth) {
        lifeField.setBirthEnd(birth);
    }

    public int getRadius() {
        return enterRadius;
    }

    public int getWidth(){
        return lifeField.getWidth();
    }

    public int getHeight(){
        return lifeField.getHeight();
    }

    private boolean inHex(int x, int y) {
        if(bufferedImage.getRGB(x, y) == HexField.COLOR_LINE){
            return false;
        }
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

        if (showImpact) {
            drawText(i, j);
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
        int innerRadius = radius - widthLine;
        if(innerRadius < MINIMUM__RADIUS_SHOW_IMPACT){
            return;
        }
        String pattern;
        if (lifeField.getImpactState(x,y) % 1 == 0) {
            pattern = "##0";
        } else {
            pattern = "##0.0";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        Font font = new Font("Plain", Font.PLAIN,innerRadius / 2);

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();

        String str = decimalFormat.format(lifeField.getImpactState(x, y));
        int xPoz = widthCenterHex[x][y] - metrics.stringWidth(str) / 2;
        int yPoz = heightCenterHex[x][y] + innerRadius / 4 - 1;
        graphics.drawString(str, xPoz, yPoz);
        bufferedImage.setRGB(xPoz, yPoz, Color.BLUE.getRGB());
    }

}
