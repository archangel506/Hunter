package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import java.util.Arrays;

public class AquarelleFilter {
    private  ImageBmp result;
    private ImageBmp median;
    private ImageBmp.ImageRGB[][] bitmap;
    private ImageBmp.ImageRGB[][] medianBitmap;
    private ImageBmp.ImageRGB[][] resultBitmap;

    private double[][] core = {{-0.75, -0.75, -0.75},
            {-0.75, 7, -0.75},
            {-0.75, -0.75, -0.75}};

    public ImageBmp algroritm(ImageBmp imageBmp){
        result = new ImageBmp(imageBmp);
        median = new ImageBmp(imageBmp);

        bitmap = imageBmp.getBitMap();
        medianBitmap = median.getBitMap();
        resultBitmap = result.getBitMap();

        for (int i = 3; i < bitmap.length - 3; i++) {
            for (int j = 3; j < bitmap[0].length - 3; j++) {
                calculateRed(i,j);
                calculateGreen(i, j);
                calculateBlue(i, j);
            }
        }

        for (int i = 1; i < medianBitmap.length - 1; i++) {
            for (int j = 1; j < medianBitmap[0].length - 1; j++) {
                calculateConturRed(i,j);
                calculateConturGreen(i, j);
                calculateConturBlue(i, j);
            }
        }
        return result;
    }

    private void calculateRed(int x, int y){
        int[] mass = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mass[i * 3 + j] = bitmap[x + (i - 3)][y + (j - 3)].red;
            }
        }

        Arrays.sort(mass);
        medianBitmap[x][y].red = mass[13];
    }

    private void calculateBlue(int x, int y){
        int[] mass = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mass[i * 3 + j] = bitmap[x + (i - 3)][y + (j - 3)].blue;
            }
        }

        Arrays.sort(mass);
        medianBitmap[x][y].blue = mass[13];
    }

    private void calculateGreen(int x, int y){
        int[] mass = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mass[i * 3 + j] = bitmap[x + (i - 3)][y + (j - 3)].green;
            }
        }

        Arrays.sort(mass);
        medianBitmap[x][y].green = mass[13];
    }

    private void calculateConturRed(int i, int j){
        resultBitmap[i][j].red =
                (int)(core[0][0] * medianBitmap[i - 1][j - 1].red +
                        core[0][1] * medianBitmap[i - 1][j].red +
                        core[0][2] * medianBitmap[i - 1][j + 1].red +
                        core[1][0] * medianBitmap[i][j + 1].red +
                        core[1][1] * medianBitmap[i][j].red +
                        core[1][2] * medianBitmap[i][j + 1].red +
                        core[2][0] * medianBitmap[i + 1][j - 1].red +
                        core[2][1] * medianBitmap[i + 1][j].red +
                        core[2][2] * medianBitmap[i + 1][j + 1].red);
        if (resultBitmap[i][j].red < 0)
            resultBitmap[i][j].red = 0;
        if (resultBitmap[i][j].red > 255)
            resultBitmap[i][j].red = 255;
    }

    private void calculateConturGreen(int i, int j){
        resultBitmap[i][j].green =
                (int)(core[0][0] * medianBitmap[i - 1][j - 1].green +
                        core[0][1] * medianBitmap[i - 1][j].green +
                        core[0][2] * medianBitmap[i - 1][j + 1].green +
                        core[1][0] * medianBitmap[i][j + 1].green +
                        core[1][1] * medianBitmap[i][j].green +
                        core[1][2] * medianBitmap[i][j + 1].green +
                        core[2][0] * medianBitmap[i + 1][j - 1].green +
                        core[2][1] * medianBitmap[i + 1][j].green +
                        core[2][2] * medianBitmap[i + 1][j + 1].green);

        if (resultBitmap[i][j].green < 0)
            resultBitmap[i][j].green = 0;
        if (resultBitmap[i][j].green > 255)
            resultBitmap[i][j].green = 255;
    }

    private void calculateConturBlue(int i, int j){
        resultBitmap[i][j].blue =
                (int)(core[0][0] * medianBitmap[i - 1][j - 1].blue +
                        core[0][1] * medianBitmap[i - 1][j].blue +
                        core[0][2] * medianBitmap[i - 1][j + 1].blue +
                        core[1][0] * medianBitmap[i][j + 1].blue +
                        core[1][1] * medianBitmap[i][j].blue +
                        core[1][2] * medianBitmap[i][j + 1].blue +
                        core[2][0] * medianBitmap[i + 1][j - 1].blue +
                        core[2][1] * medianBitmap[i + 1][j].blue +
                        core[2][2] * medianBitmap[i + 1][j + 1].blue);

        if (resultBitmap[i][j].blue < 0)
            resultBitmap[i][j].blue = 0;
        if (resultBitmap[i][j].blue > 255)
            resultBitmap[i][j].blue = 255;
    }
}
