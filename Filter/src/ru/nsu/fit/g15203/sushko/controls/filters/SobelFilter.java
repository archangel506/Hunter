package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class SobelFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResoult = imageBmp.getBitMap();


        for (int i = 1; i < bitmap.length - 1; i++) {
            for (int j = 1; j < bitmap[0].length - 1; j++) {
                int Gx = (bitmap[i + 1][j - 1].red + 2*bitmap[i + 1][j].red + bitmap[i + 1][j + 1].red)
                        - (bitmap[i - 1][j - 1].red + 2*bitmap[i - 1][j].red + bitmap[i - 1][j + 1].red);
                int Gy = (bitmap[i - 1][j + 1].red + 2*bitmap[i][j + 1].red + bitmap[i + 1][j + 1].red)
                        - (bitmap[i - 1][j - 1].red + 2*bitmap[i][j - 1].red + bitmap[i + 1][j - 1].red);
                int r = (int) Math.sqrt (Gx * Gx + Gy * Gy);
                if (r > 255) r = 255;

                bitmapResoult[i][j].red = r;

                Gx = (bitmap[i + 1][j - 1].green + 2*bitmap[i + 1][j].green + bitmap[i + 1][j + 1].green)
                        - (bitmap[i - 1][j - 1].green + 2*bitmap[i - 1][j].green + bitmap[i - 1][j + 1].green);
                Gy = (bitmap[i - 1][j + 1].green + 2*bitmap[i][j + 1].green + bitmap[i + 1][j + 1].green)
                        - (bitmap[i - 1][j - 1].green + 2*bitmap[i][j - 1].green + bitmap[i + 1][j - 1].green);
                r = (int) Math.sqrt (Gx * Gx + Gy * Gy);
                if (r > 255) r = 255;
                bitmapResoult[i][j].green = r;

                Gx = (bitmap[i + 1][j - 1].blue + 2*bitmap[i + 1][j].blue + bitmap[i + 1][j + 1].blue)
                        - (bitmap[i - 1][j - 1].blue + 2*bitmap[i - 1][j].blue + bitmap[i - 1][j + 1].blue);
                Gy = (bitmap[i - 1][j + 1].blue + 2*bitmap[i][j + 1].blue + bitmap[i + 1][j + 1].blue)
                        - (bitmap[i - 1][j - 1].blue + 2*bitmap[i][j - 1].blue + bitmap[i + 1][j - 1].blue);
                r = (int) Math.sqrt (Gx * Gx + Gy * Gy);
                if (r > 255) r = 255;

                bitmapResoult[i][j].blue = r;
            }
        }

        return result;
    }
}
