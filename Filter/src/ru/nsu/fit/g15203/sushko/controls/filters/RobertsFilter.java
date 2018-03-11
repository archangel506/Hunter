package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class RobertsFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResult = result.getBitMap();

        for (int i = 0; i < bitmapResult.length - 1; i++) {
            for (int j = 0; j < bitmapResult[0].length - 1; j++) {
                int Gx = bitmap[i + 1][j + 1].red - bitmap[i][j].red;
                int Gy = bitmap[i + 1][j].red - bitmap[i][j + 1].red;

                int r = (int) Math.sqrt (Gx * Gx + Gy * Gy);

                if (r < 0) r = 0;
                if (r > 255) r = 255;

                bitmapResult[i][j].red = r;
                bitmapResult[i][j].green = r;
                bitmapResult[i][j].blue = r;
            }
        }
        return result;
    }
}
