package ru.nsu.fit.g15203.sushko.view;

import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ChildZones extends JPanel {
    private ImageBmp image = null;
    private int sizeSelect;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(image == null) {
            return;
        }

        ImageBmp.ImageRGB[][] bitmap = image.getBitMap();
        int height = image.getHeight();
        int width = image.getWidth();

        sizeSelect = 350 * 350;
        sizeSelect = height > width ? sizeSelect / height : sizeSelect / width;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(bitmap[i][j].red, bitmap[i][j].green, bitmap[i][j].blue);
                bufferedImage.setRGB(j, i, color.getRGB());
            }
        }

        float resize = (float)(width) / height;

        if (resize >= 1)
            g.drawImage(bufferedImage, 0, 0, ParentZones.sizePanel, (int) (ParentZones.sizePanel / resize), null);

        else
            g.drawImage(bufferedImage, 0, 0, (int) (ParentZones.sizePanel * resize), ParentZones.sizePanel, null);
    }

    public ImageBmp getImage() {
        return image;
    }

    public void setImage(ImageBmp image) {
        this.image = image;
    }

    public int getSizeSelect() {
        return sizeSelect;
    }

    public boolean isEmpty(){
        return image == null;
    }
}
