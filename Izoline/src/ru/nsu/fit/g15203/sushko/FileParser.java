package ru.nsu.fit.g15203.sushko;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public final class FileParser {
    private static final String PATH_SETTINGS = "FIT_15203_Sushko_Denis_izoline_Data/";
    private static final int A = 10;
    private static final int B = 0;
    private static final int C = 10;
    private static final int D = 0;
    private final JFrame field;
    private final JFileChooser fileChooser = new JFileChooser();
    private Color[] funcColors;
    private Color izolineColor;
    private ConfigField configField;
    private int countLevels;

    {
        fileChooser.setCurrentDirectory(new File(PATH_SETTINGS));
    }


    public FileParser(final JFrame field) {
        this.field = field;
    }

    public void loadState() {
        File file;
        configField = new ConfigField();
        file = showDialog();
        if (file == null){
            return;
        }

        try {
            readFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        configField.setA(A);
        configField.setB(B);
        configField.setC(C);
        configField.setD(D);
    }

    private void readFile(final File file) throws FileNotFoundException{
        final Scanner scanner = new Scanner(file);
        configField.setWidth(scanner.nextInt());
        configField.setHeight(scanner.nextInt());

        countLevels = scanner.nextInt() + 1;
        funcColors = new Color[countLevels];

        for (int i = 0; i < funcColors.length; i++) {
            funcColors[i] = new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }

        izolineColor = new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
    }

    private File showDialog(){
        int ret = fileChooser.showOpenDialog(field);

        if (ret == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }


    public Color[] getFuncColors() {
        return funcColors;
    }

    public Color getIzolineColor() {
        return izolineColor;
    }

    public ConfigField getConfigField() {
        return configField;
    }

    public int getCountLevels() {
        return countLevels;
    }

}
