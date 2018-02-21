package ru.nsu.fit.g15203.sushko.hex;

import java.awt.image.BufferedImage;

public interface DrawerLine {
    void drawLine(int xstart, int ystart, int xend, int yend,
                                  BufferedImage bufferedImage);
}
