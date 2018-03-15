package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class FloydStanbergFilter {
    private int[] redPalette;
    private int[] greenPalette;
    private int[] bluePalette;

    public ImageBmp algroritm(ImageBmp imageBmp, int countRed, int countGreen, int countBlue){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp temp = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp.ImageRGB[][] tempBitmap = temp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResult = result.getBitMap();

        redPalette = new int[countRed];
        for (int i = 0; i < countRed; ++i) {
            redPalette[i] = (256 / (countRed - 1)) * i;
        }
        redPalette[countRed - 1] = 256;

        greenPalette = new int[countGreen];
        for (int i = 0; i < countGreen; ++i) {
            greenPalette[i] = (256 / (countGreen - 1)) * i;
        }
        greenPalette[countGreen - 1] = 256;

        bluePalette = new int[countBlue];
        for (int i = 0; i < countBlue; ++i) {
            bluePalette[i] = (256 / (countBlue - 1)) * i;
        }
        bluePalette[countBlue - 1] = 256;

        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[0].length; j++) {
                ImageBmp.ImageRGB oldColor = new ImageBmp.ImageRGB(tempBitmap[i][j].red, tempBitmap[i][j].green, tempBitmap[i][j].blue);
                ImageBmp.ImageRGB newColor = findDef(oldColor.red, oldColor.green, oldColor.blue);
                if(newColor.red > 255) newColor.red = 255;
                if(newColor.red <= 0) newColor.red = 0;
                if(newColor.green > 255) newColor.green = 255;
                if(newColor.green <= 0) newColor.green = 0;
                if(newColor.blue > 255) newColor.blue = 255;
                if(newColor.blue <= 0) newColor.blue = 0;
                bitmapResult[i][j].red = newColor.red;
                bitmapResult[i][j].green = newColor.green;
                bitmapResult[i][j].blue = newColor.blue;

                int errRed = oldColor.red - newColor.red;
                int errGreen = oldColor.green - newColor.green;
                int errBlue = oldColor.blue - newColor.blue;
                ImageBmp.ImageRGB error = new ImageBmp.ImageRGB(errRed, errGreen, errBlue);
                if (j + 1 < bitmap[0].length)
                    calculateErr(tempBitmap[i][j + 1], 7./16, error);
                if (j - 1 >= 0 && i + 1 < bitmap.length)
                    calculateErr(tempBitmap[i + 1][j - 1], 3./16, error);
                if (i + 1 < bitmap.length)
                    calculateErr(tempBitmap[i + 1][j], 5./16, error);
                if (j + 1 < bitmap[0].length && i + 1 < bitmap.length)
                    calculateErr(tempBitmap[i + 1][j + 1], 1./16, error);
            }
        }

        return result;
    }

    private void calculateErr(ImageBmp.ImageRGB rgb, double difErr, ImageBmp.ImageRGB error){
        rgb.red += error.red * difErr;
        rgb.green += error.green * difErr;
        rgb.blue += error.blue * difErr;
    }

    private ImageBmp.ImageRGB findDef(int red, int green, int blue) {
        int maxDif = 0x00FFFFFF;
        int min = 0;
        for (int i = 0; i < redPalette.length; i++) {
            if (Math.abs(redPalette[i] - red) < maxDif) {
                maxDif = Math.abs(redPalette[i] - red);
                min = i;
            }
        }
        int newRed = redPalette[min];
        maxDif = 0x00FFFFFF;
        min = 0;
        for (int i = 0; i < greenPalette.length; i++) {
            if (Math.abs(greenPalette[i] - green) < maxDif) {
                maxDif = Math.abs(greenPalette[i] - green);
                min = i;
            }
        }
        int newGreen = greenPalette[min];
        maxDif = 0x00FFFFFF;
        min = 0;
        for (int i = 0; i < bluePalette.length; i++) {
            if (Math.abs(bluePalette[i] - blue) < maxDif) {
                maxDif = Math.abs(bluePalette[i] - blue);
                min = i;
            }
        }
        int newBlue = bluePalette[min];
        return new ImageBmp.ImageRGB(newRed, newGreen, newBlue);
    }
}
