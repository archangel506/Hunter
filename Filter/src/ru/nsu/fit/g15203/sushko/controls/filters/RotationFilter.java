package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotationFilter {

    public ImageBmp algroritm(ImageBmp imageBmp, int angle1) {
        int startAngle = angle1;
        double angle = (Math.PI * angle1) / 180;
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp temp = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] tempBitmap = temp.getBitMap();
        ImageBmp.ImageRGB[][] resultBitmap = result.getBitMap();

        BufferedImage bufferedImage = new BufferedImage(350,350, BufferedImage.TYPE_INT_RGB);

        if (startAngle % 90 == 0) tempBitmap = bitmap;
        else {
            for (int i = 0; i < result.getHeight(); i++) {
                for (int j = 0; j < result.getWidth(); j++) {
                    resultBitmap[i][j] = new ImageBmp.ImageRGB(255, 255, 255);
                    tempBitmap[i][j] = new ImageBmp.ImageRGB(255, 255, 255);
                    bufferedImage.setRGB(i, j,
                            new Color(bitmap[i][j].red, bitmap[i][j].green, bitmap[i][j].blue).getRGB());
                }
            }
            int scaled = 248;
            if (startAngle % 45 != 0) {
                double a = (Math.PI * ((startAngle % 90) + 45D)) / 180D;
                scaled = (int)Math.sqrt(Math.pow((175 / Math.tan(a) - 175), 2) + Math.pow(350 - Math.abs(175 / Math.tan(a) - 175), 2));
            }

            Image image1 = bufferedImage.getScaledInstance(scaled, scaled, Image.SCALE_SMOOTH);
            bufferedImage = toBufferedImage(image1);
            for (int i = 0; i < scaled; i++)
                for (int j = 0; j < scaled; j++) {
                    Color c = new Color(bufferedImage.getRGB(i, j));
                    tempBitmap[i + (350 - scaled) / 2][j + (350 - scaled) / 2].red = c.getRed();
                    tempBitmap[i + (350 - scaled) / 2][j + (350 - scaled) / 2].blue = c.getBlue();
                    tempBitmap[i + (350 - scaled) / 2][j + (350 - scaled) / 2].green = c.getGreen();
                }
        }
        int y0 = result.getHeight() / 2;
        int x0 = result.getWidth() / 2;

        for (int i = 0; i < 350; i++) {
            for (int j = 0; j < 350; j++) {
                double tempX = i - x0;
                double tempY = y0 - j;

                double distance = Math.sqrt(tempX * tempX + tempY * tempY);
                double polarAngle = 0;

                if (tempX == 0)
                    if (tempY == 0){
                        resultBitmap[i][j] = tempBitmap[i][j];
                    } else if (tempY < 0) {
                        polarAngle = 1.5 * Math.PI;
                    }
                    else
                        polarAngle = 0.5 * Math.PI;
                else
                    polarAngle = Math.atan2(tempY,tempX);

                polarAngle -= angle;
                double trueX = distance * Math.cos(polarAngle);
                double trueY = distance * Math.sin(polarAngle);

                trueX += x0;
                trueY = y0 - trueY;

                int floorX = (int)(Math.floor(trueX));
                int floorY = (int)(Math.floor(trueY));

                int ceilX = (int)(Math.ceil(trueX));
                int ceilY = (int)(Math.ceil(trueY));

                if (floorX < 0 || ceilX < 0 || floorX >= imageBmp.getWidth()
                        || ceilX >= imageBmp.getWidth() || floorY < 0 || ceilY < 0
                        || floorY >= imageBmp.getHeight() || ceilY >= imageBmp.getHeight())
                    continue;

                double fDeltaX = trueX - (double)floorX;
                double fDeltaY = trueY - (double)floorY;

                Color clrTopLeft = new Color(tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(floorY)].red,
                        tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(floorY)].green,
                        tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(floorY)].blue);
                Color clrTopRight = new Color(tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(floorY)].red,
                        tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(floorY)].green,
                        tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(floorY)].blue);
                Color clrBottomLeft = new Color(tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(ceilY)].red,
                        tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(ceilY)].green,
                        tempBitmap[(int)Math.ceil(floorX)][(int)Math.ceil(ceilY)].blue);
                Color clrBottomRight = new Color(tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(ceilY)].red,
                        tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(ceilY)].green,
                        tempBitmap[(int)Math.ceil(ceilX)][(int)Math.ceil(ceilY)].blue);

                double fTopRed = (1 - fDeltaX) * clrTopLeft.getRed() + fDeltaX * clrTopRight.getRed();
                double fTopGreen = (1 - fDeltaX) * clrTopLeft.getGreen() + fDeltaX * clrTopRight.getGreen();
                double fTopBlue = (1 - fDeltaX) * clrTopLeft.getBlue() + fDeltaX * clrTopRight.getBlue();

                double fBottomRed = (1 - fDeltaX) * clrBottomLeft.getRed() + fDeltaX * clrBottomRight.getRed();
                double fBottomGreen = (1 - fDeltaX) * clrBottomLeft.getGreen() + fDeltaX * clrBottomRight.getGreen();
                double fBottomBlue = (1 - fDeltaX) * clrBottomLeft.getBlue() + fDeltaX * clrBottomRight.getBlue();

                int red = (int)(Math.round((1 - fDeltaY)* fTopRed + fDeltaY * fBottomRed));
                int green = (int)(Math.round((1 - fDeltaY)* fTopGreen + fDeltaY * fBottomGreen));
                int blue = (int)(Math.round((1 - fDeltaY)* fTopBlue + fDeltaY * fBottomBlue));

                if (red > 255) red  = 255;
                if (green > 255) green = 255;
                if (blue > 255) blue = 255;

                if (red < 0) red  = 0;
                if (green < 0) green = 0;
                if (blue < 0) blue = 0;

                resultBitmap[i][j].red = red;
                resultBitmap[i][j].blue = blue;
                resultBitmap[i][j].green = green;
            }
        }

        return result;
    }

    public BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bufferedImage;
    }
}