package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class EmbassingFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResult = result.getBitMap();

        for (int i = 1; i < bitmap.length - 1; i++) {
            for (int j = 1; j < bitmap[0].length - 1; j++) {
                int red;
                int green;
                int blue;
                red = bitmap[i - 1][j].red -
                        bitmap[i][j + 1].red +
                        bitmap[i][j + 1].red -
                        bitmap[i + 1][j].red;
                red += 128;

                green = bitmap[i - 1][j].green -
                        bitmap[i][j + 1].green +
                        bitmap[i][j + 1].green -
                        bitmap[i + 1][j].green;
                green += 128;

                blue = bitmap[i - 1][j].blue -
                        bitmap[i][j + 1].blue +
                        bitmap[i][j + 1].blue -
                        bitmap[i + 1][j].blue;
                blue += 128;

                if (red < 0) red = 0;
                if (green < 0) green = 0;
                if (blue < 0) blue = 0;

                if (red > 255) red = 255;
                if (green > 255) green = 255;
                if (blue > 255) blue = 255;

                bitmapResult[i][j].red = red;
                bitmapResult[i][j].green = green;
                bitmapResult[i][j].blue = blue;
            }
        }
        return result;
    }
}
