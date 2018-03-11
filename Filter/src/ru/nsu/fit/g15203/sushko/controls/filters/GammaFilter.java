package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class GammaFilter {
    public ImageBmp algroritm(ImageBmp imageBmp, double gamma){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = result.getBitMap();

        gamma = 1.0 / gamma;

        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[0].length; j++) {
                bitmap[i][j].red = (int) (255 * (Math.pow((double) bitmap[i][j].red / (double) 255, gamma)));
                bitmap[i][j].green = (int) (255 * (Math.pow((double) bitmap[i][j].green / (double) 256, gamma)));
                bitmap[i][j].blue  = (int) (255 * (Math.pow((double) bitmap[i][j].blue / (double) 255, gamma)));

                if (bitmap[i][j].red > 255) bitmap[i][j].red = 255;
                if (bitmap[i][j].green > 255) bitmap[i][j].green = 255;
                if (bitmap[i][j].blue > 255) bitmap[i][j].blue = 255;

                if (bitmap[i][j].red < 0) bitmap[i][j].red = 0;
                if (bitmap[i][j].green < 0) bitmap[i][j].green = 0;
                if (bitmap[i][j].blue < 0) bitmap[i][j].blue = 0;
            }
        }

        return result;
    }
}
