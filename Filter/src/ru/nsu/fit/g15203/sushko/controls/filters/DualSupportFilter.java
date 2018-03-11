package ru.nsu.fit.g15203.sushko.controls.filters;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

public class DualSupportFilter {
    public ImageBmp algroritm(ImageBmp imageBmp, int limit) {
        ImageBmp result = new ImageBmp(imageBmp);
        ImageBmp.ImageRGB[][] bitmap = result.getBitMap();

        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[0].length; j++) {

                int Y = (int) ((0.299) * bitmap[i][j].red +
                        (0.587) * bitmap[i][j].green +
                        (0.114) * bitmap[i][j].blue);

                if(Y < limit){
                    bitmap[i][j].red = 0;
                    bitmap[i][j].green = 0;
                    bitmap[i][j].blue = 0;
                } else{
                    bitmap[i][j].red = 255;
                    bitmap[i][j].green = 255;
                    bitmap[i][j].blue = 255;
                }

            }
        }
        return result;
    }
}
