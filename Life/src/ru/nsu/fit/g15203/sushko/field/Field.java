package ru.nsu.fit.g15203.sushko.field;

import java.awt.image.BufferedImage;

public interface Field {
    void drawField();
    void fillElement(int x, int y, int color);
    void resetField();
    void openField();
    void saveField();
    void setParameters();
    void elemChoise(int x, int y);
    void nextStep();
    void showImpact();
    BufferedImage getBufferedImage();
}
