package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class RobertsFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp temp = new BlackWhiteFilter().algroritm(imageBmp);
        ImageBmp result = new ImageBmp(temp);
        ImageBmp.ImageRGB[][] bitmap = temp.getBitMap();
        ImageBmp.ImageRGB[][] bitmapResult = result.getBitMap();

        for (int i = 0; i < bitmapResult.length - 1; i++) {
            for (int j = 0; j < bitmapResult[0].length - 1; j++) {
                int Gx = bitmap[i + 1][j + 1].red - bitmap[i][j].red;
                int Gy = bitmap[i + 1][j].red - bitmap[i][j + 1].red;

                int r = (int)Math.sqrt(Gx * Gx + Gy * Gy);

                if (r < 0) r = 0;
                if (r > 255) r = 255;

                bitmapResult[i][j].red = r;

                Gx = bitmap[i + 1][j + 1].green - bitmap[i][j].green;
                Gy = bitmap[i + 1][j].green - bitmap[i][j + 1].green;

                r = (int)Math.sqrt(Gx * Gx + Gy * Gy);

                if (r < 0) r = 0;
                if (r > 255) r = 255;

                bitmapResult[i][j].green = r;

                Gx = bitmap[i + 1][j + 1].blue - bitmap[i][j].blue;
                Gy = bitmap[i + 1][j].blue - bitmap[i][j + 1].blue;

                r = (int)Math.sqrt(Gx * Gx + Gy * Gy);

                if (r < 0) r = 0;
                if (r > 255) r = 255;

                bitmapResult[i][j].blue = r;
            }
        }
        return result;
    }
}
