package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class DefinitionFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResult = result.getBitMap();
        double[][] core = {{0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}};


        for (int i = 1; i < bitmap.length - 1; i++) {
            for (int j = 1; j < bitmap[0].length - 1; j++) {
                bitmapResult[i][j].red =
                        (int) (core[0][0] * bitmap[i - 1][j - 1].red +
                        core[0][1] * bitmap[i - 1][j].red +
                        core[0][2] * bitmap[i - 1][j + 1].red +
                        core[1][0] * bitmap[i][j + 1].red +
                        core[1][1] * bitmap[i][j].red +
                        core[1][2] * bitmap[i][j + 1].red +
                        core[2][0] * bitmap[i + 1][j - 1].red +
                        core[2][1] * bitmap[i + 1][j].red +
                        core[2][2] * bitmap[i + 1][j + 1].red);

                bitmapResult[i][j].green =
                        (int) (core[0][0] * bitmap[i - 1][j - 1].green +
                        core[0][1] * bitmap[i - 1][j].green +
                        core[0][2] * bitmap[i - 1][j + 1].green +
                        core[1][0] * bitmap[i][j + 1].green +
                        core[1][1] * bitmap[i][j].green +
                        core[1][2] * bitmap[i][j + 1].green +
                        core[2][0] * bitmap[i + 1][j - 1].green +
                        core[2][1] * bitmap[i + 1][j].green +
                        core[2][2] * bitmap[i + 1][j + 1].green);

                bitmapResult[i][j].blue =
                        (int) (core[0][0] * bitmap[i - 1][j - 1].blue +
                        core[0][1] * bitmap[i - 1][j].blue +
                        core[0][2] * bitmap[i - 1][j + 1].blue +
                        core[1][0] * bitmap[i][j + 1].blue +
                        core[1][1] * bitmap[i][j].blue +
                        core[1][2] * bitmap[i][j + 1].blue +
                        core[2][0] * bitmap[i + 1][j - 1].blue +
                        core[2][1] * bitmap[i + 1][j].blue +
                        core[2][2] * bitmap[i + 1][j + 1].blue);

                if (bitmapResult[i][j].red > 255) bitmapResult[i][j].red = 255;
                if (bitmapResult[i][j].green > 255) bitmapResult[i][j].green = 255;
                if (bitmapResult[i][j].blue > 255) bitmapResult[i][j].blue = 255;

                if (bitmapResult[i][j].red < 0) bitmapResult[i][j].red = 0;
                if (bitmapResult[i][j].green < 0) bitmapResult[i][j].green = 0;
                if (bitmapResult[i][j].blue < 0) bitmapResult[i][j].blue = 0;
            }
        }
        return result;
    }
}
