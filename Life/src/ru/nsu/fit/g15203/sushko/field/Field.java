package ru.nsu.fit.g15203.sushko.field;

public interface Field {
    void drawField(int widthCount, int heightCount);
    void drawField();
    void fillElement(int x, int y, int color);
    void resetField();
    void openField();
    void saveField();
    void setParameters();
    void elemChoise(int x, int y);
    void nextStep();
}
