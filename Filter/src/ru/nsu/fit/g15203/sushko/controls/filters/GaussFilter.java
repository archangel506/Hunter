package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class GaussFilter {
    double[][] core = {{0.000789, 0.006581, 0.013347, 0.006581, 0.000789},
            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
            {0.013347, 0.111345, 0.225821, 0.111345, 0.013347},
            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789}};

    ImageBmp.ImageRGB[][] bitmap;
    ImageBmp.ImageRGB[][] bitmapResult;
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        bitmap = imageBmp.getBitMap();
        bitmapResult = result.getBitMap();

        for (int i = 3; i < bitmapResult.length - 3; i++) {
            for (int j = 3; j < bitmapResult[0].length - 3; j++) {
                calculateRed(i, j);
                calculateGreen(i, j);
                calculateBlue(i, j);
            }
        }
        return result;
    }

    private void calculateRed(int x, int y){
        double temp = 0;
        for(int i = 0; i < core.length; ++i){
            for(int j = 0; j < core[0].length; ++j){
                temp += core[i][j] * bitmap[x + (i - 3)][y + (j - 3)].red;
            }
        }
        bitmapResult[x][y].red = (int) temp;
    }

    private void calculateGreen(int x, int y){
        double temp = 0;
        for(int i = 0; i < core.length; ++i){
            for(int j = 0; j < core[0].length; ++j){
                temp += core[i][j] * bitmap[x + (i - 3)][y + (j - 3)].green;
            }
        }
        bitmapResult[x][y].green = (int) temp;
    }

    private void calculateBlue(int x, int y){
        double temp = 0;
        for(int i = 0; i < core.length; ++i){
            for(int j = 0; j < core[0].length; ++j){
                temp += core[i][j] * bitmap[x + (i - 3)][y + (j - 3)].blue;
            }
        }
        bitmapResult[x][y].blue = (int) temp;
    }
}
