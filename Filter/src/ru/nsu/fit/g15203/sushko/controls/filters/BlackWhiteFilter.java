package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class BlackWhiteFilter {

    public ImageBmp algroritm(ImageBmp imageBmp) {
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = result.getBitMap();

        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[0].length; j++) {

                int Y = (int) ((0.299) * bitmap[i][j].red +
                        (0.587) * bitmap[i][j].green +
                        (0.114) * bitmap[i][j].blue);

                bitmap[i][j].red = Y;
                bitmap[i][j].green = Y;
                bitmap[i][j].blue = Y;
            }
        }
        return result;
    }
}
