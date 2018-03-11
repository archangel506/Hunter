package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class DoubleZoomFilter {
    private ImageBmp.ImageRGB[][] bitmapResult;

    public ImageBmp algroritm(ImageBmp imageBmp) {
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = imageBmp.getBitMap();
        bitmapResult = result.getBitMap();

        int width = bitmap[0].length;
        int height = bitmap.length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int iFinal;
                int jFinal;
                boolean heightEven = (i - 1) % 2 == 0;
                boolean widthEven = (j - 1) % 2 == 0;

                if (heightEven && widthEven) {
                    iFinal = width / 4 + (i - 1) / 2;
                    jFinal = height / 4 + (j - 1) / 2;

                    bitmapResult[i][j].red = bitmap[iFinal][jFinal].red;
                    bitmapResult[i][j].green = bitmap[iFinal][jFinal].green;
                    bitmapResult[i][j].blue = bitmap[iFinal][jFinal].blue;
                    continue;
                }

                if (heightEven) {
                    iFinal = width / 4 + (i - 1) / 2;
                    jFinal = height / 4 + (j - 2) / 2;

                    bitmapResult[i][j].red = (bitmap[iFinal][jFinal].red + bitmap[iFinal][jFinal + 1].red) / 2;
                    bitmapResult[i][j].green = (bitmap[iFinal][jFinal].green + bitmap[iFinal][jFinal + 1].green) / 2;
                    bitmapResult[i][j].blue = (bitmap[iFinal][jFinal].blue + bitmap[iFinal][jFinal + 1].blue) / 2;

                    continue;
                }
                if (widthEven) {
                    iFinal = width / 4 + (i - 2) / 2;
                    jFinal = height / 4 + (j - 1) / 2;

                    bitmapResult[i][j].red = (bitmap[iFinal][jFinal].red + bitmap[iFinal + 1][jFinal].red) / 2;
                    bitmapResult[i][j].green = (bitmap[iFinal][jFinal].green + bitmap[iFinal + 1][jFinal].green) / 2;
                    bitmapResult[i][j].blue = (bitmap[iFinal][jFinal].blue + bitmap[iFinal + 1][jFinal].blue) / 2;

                    continue;
                }
                iFinal = width / 4 + (i - 2) / 2;
                jFinal = height / 4 + (j - 2) / 2;

                bitmapResult[i][j].red = (bitmap[iFinal][jFinal].red + bitmap[iFinal][jFinal + 1].red +
                        bitmap[iFinal + 1][jFinal].red + bitmap[iFinal + 1][jFinal + 1].red) / 4;
                bitmapResult[i][j].green = (bitmap[iFinal][jFinal].green + bitmap[iFinal][jFinal + 1].green +
                        bitmap[iFinal][jFinal + 1].green + bitmap[iFinal + 1][jFinal + 1].green) / 4;
                bitmapResult[i][j].blue = (bitmap[iFinal][jFinal].blue + bitmap[iFinal][jFinal + 1].blue +
                        bitmap[iFinal][jFinal + 1].blue + bitmap[iFinal + 1][jFinal + 1].blue) / 4;

            }
        }
        return result;
    }
}
