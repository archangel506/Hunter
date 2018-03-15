package ru.nsu.fit.g15203.sushko.view;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class MyXorBorder extends AbstractBorder {
    private ImageBmp imageBmp;
    private ImageBmp.ImageRGB[][] bitmap;
    private int shiftX;
    private int shiftY;
    public MyXorBorder(ImageBmp imageBmp, int shiftX, int shiftY){
        this.imageBmp = imageBmp;
        this.bitmap = imageBmp.getBitMap();
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }


    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D) g;
        int count = 5;
        for(int i = 0; i < height; ++i){
            g2d.setColor(new Color(255 - bitmap[x + i + shiftX][y + shiftY].red,
                    255 - bitmap[x + i + shiftX][y + shiftY].green,
                    255 - bitmap[x + i + shiftX][y + shiftY].blue));
            g2d.drawLine(x + i, y, x + i, y);
            g2d.setColor(new Color(255 - bitmap[x + i + shiftX][y + width - 1  + shiftY].red,
                    255 - bitmap[x + i + shiftX][y + width - 1 + shiftY].green,
                    255 - bitmap[x + i + shiftX][y + width - 1 + shiftY].blue));
            g2d.drawLine(x + i, y + width - 1, x + i, y + width - 1);
        }
        for(int i = 0; i < width; ++i){
            g2d.setColor(new Color(255 - bitmap[x + shiftX][y + i + shiftY].red,
                    255 - bitmap[x + shiftX][y + i + shiftY].green,
                    255 - bitmap[x + shiftX][y + i + shiftY].blue));
            g2d.drawLine(x, y + i, x, y + i);
            g2d.setColor(new Color(255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].red,
                    255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].green,
                    255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].blue));
            g2d.drawLine(x + height - 1, y + i, x + height - 1, y + i);
        }
    }


//    @Override
//    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        super.paintBorder(c, g, x, y, width, height);
//        Graphics2D g2d = (Graphics2D) g;
//        //System.out.println(x + " " + y);
//        int count = 5;
//        for(int i = 0; i < height; ++i){
//            ImageBmp.ImageRGB temp = bitmap[0][i];
//            g2d.setColor(new Color(255 - bitmap[0][i].red,
//                    255 - bitmap[0][i].green,
//                    255 - bitmap[0][i].blue));
//            g2d.drawLine(y, x + i, y, x + i);
//            g2d.drawLine(y, x + i, y, x + i);
//            g2d.drawLine(0, i, 0, i);
//            g2d.setColor(new Color(255 - bitmap[x + i][y + width - 1].red,
//                    255 - bitmap[x + i][y + width - 1].green,
//                    255 - bitmap[x + i][y + width - 1].blue));
//            g2d.setColor(Color.RED);
//            g2d.drawLine(x + i, y + width - 1, x + i, y + width - 1);
      //  }
//        for(int i = 0; i < width; ++i){
//            g2d.setColor(new Color(255 - bitmap[x][y + i].red,
//                    255 - bitmap[x][y + i].green,
//                    255 - bitmap[x][y + i].blue));
//            g2d.drawLine(x, y + i, x, y + i);
//            g2d.setColor(new Color(255 - bitmap[x + height - 1][y + i].red,
//                    255 - bitmap[x + height - 1 ][y + i].green,
//                    255 - bitmap[x + height - 1][y + i].blue));
//            g2d.drawLine(x + height - 1, y + i, x + height - 1, y + i);
//        }
    //}

//    @Override
//    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        super.paintBorder(c, g, x, y, width, height);
//        Graphics2D g2d = (Graphics2D) g;
//        int count = 5;
//        System.out.println(shiftX + " " + shiftY);
//        for(int i = 0; i < height; ++i){
//            g2d.setColor(new Color(255 - bitmap[x + i + shiftX][y + shiftY].red,
//                    255 - bitmap[x + i + shiftX][y + shiftY].green,
//                    255 - bitmap[x + i + shiftX][y + shiftY].blue));
//            g2d.drawLine(y, x + i, y, x + i);
//        }
//
//        for(int i = 0; i < height; ++i){
//            g2d.setColor(new Color(255 - bitmap[x + i + shiftX][y + width - 1  + shiftY].red,
//                    255 - bitmap[x + i + shiftX][y + width - 1 + shiftY].green,
//                    255 - bitmap[x + i + shiftX][y + width - 1 + shiftY].blue));
//            g2d.drawLine(y + width - 1, x + i, y + width - 1, x + i);
//        }
//        for(int i = 0; i < width; ++i){
//            g2d.setColor(new Color(255 - bitmap[x + shiftX][y + i + shiftY].red,
//                    255 - bitmap[x + shiftX][y + i + shiftY].green,
//                    255 - bitmap[x + shiftX][y + i + shiftY].blue));
//            g2d.drawLine(x, y + i, x, y + i);
//            g2d.setColor(new Color(255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].red,
//                    255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].green,
//                    255 - bitmap[x + height - 1 + shiftX][y + i + shiftY].blue));
//            g2d.drawLine(x + height - 1, y + i, x + height - 1, y + i);
//        }
//    }


}
