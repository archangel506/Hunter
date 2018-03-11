package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class FloydStanbergFilter {
    private ImageBmp.ImageRGB[][] bitmap;
    public ImageBmp algroritm(ImageBmp imageBmp, int countRed, int countGreen, int countBlue){
        ImageBmp result = new ImageBmp(imageBmp);
        bitmap = result.getBitMap();


        for (int i = 0; i < bitmap.length - 1; i++) {
            for (int j = 1; j < bitmap[0].length - 1; j++) {
                double error;

                int oldValue = bitmap[i][j].red;
                int newValue = bitmap[i][j].red / countRed * countRed;
                error = oldValue - newValue;

                calculateRedAndCheck(i, j + 1, (int) (7/16 * error));
                calculateRedAndCheck(i + 1, j + 1, (int) (1/16 * error));
                calculateRedAndCheck(i, j, (int) (5/16 * error));
                calculateRedAndCheck(i, j - 1, (int) (3/16 * error));

                oldValue = bitmap[i][j].green;
                newValue = bitmap[i][j].green / countGreen * countGreen;
                error = oldValue - newValue;

                calculateGreenAndCheck(i, j + 1, (int) (7/16 * error));
                calculateGreenAndCheck(i + 1, j + 1, (int) (1/16 * error));
                calculateGreenAndCheck(i, j, (int) (5/16 * error));
                calculateGreenAndCheck(i, j - 1, (int) (3/16 * error));

                oldValue = bitmap[i][j].blue;
                newValue = bitmap[i][j].blue / countBlue * countBlue;
                error = oldValue - newValue;

                calculateBlueAndCheck(i, j + 1, (int) (7/16 * error));
                calculateBlueAndCheck(i + 1, j + 1, (int) (1/16 * error));
                calculateBlueAndCheck(i, j, (int) (5/16 * error));
                calculateBlueAndCheck(i, j - 1, (int) (3/16 * error));


            }
        }
        return result;
    }

    private void calculateRedAndCheck(int i, int j, int value){
        bitmap[i][j].red += value;

        if(bitmap[i][j].red > 255){
            bitmap[i][j].red = 255;
            return;
        }

        if(bitmap[i][j].red < 0){
            bitmap[i][j].red = 0;
            return;
        }
    }

    private void calculateGreenAndCheck(int i, int j, int value){
        bitmap[i][j].green += value;

        if(bitmap[i][j].green > 255){
            bitmap[i][j].green = 255;
            return;
        }

        if(bitmap[i][j].green < 0){
            bitmap[i][j].green = 0;
            return;
        }
    }

    private void calculateBlueAndCheck(int i, int j, int value){
        bitmap[i][j].blue = +value;
        if(bitmap[i][j].blue > 255){
            bitmap[i][j].blue = 255;
            return;
        }

        if(bitmap[i][j].blue < 0){
            bitmap[i][j].blue = 0;
            return;
        }
    }
}
