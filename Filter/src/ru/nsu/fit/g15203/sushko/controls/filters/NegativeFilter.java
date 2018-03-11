package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class NegativeFilter {
    public ImageBmp algroritm(ImageBmp imageBmp){
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = result.getBitMap();
        for (int i = 0; i < result.getHeight(); i++) {
            for (int j = 0; j < result.getWidth(); j++) {
                bitmap[i][j].red = 255 - bitmap[i][j].red;
                bitmap[i][j].green = 255 -bitmap[i][j].green;
                bitmap[i][j].blue = 255 - bitmap[i][j].blue;
            }
        }
        return result;
    }
}
