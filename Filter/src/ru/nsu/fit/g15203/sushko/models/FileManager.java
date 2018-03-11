package ru.nsu.fit.g15203.sushko.models;

import ru.nsu.fit.g15203.sushko.view.MainView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class FileManager {
    public static final String DEFAULT_SAVE = "FIT_15203_Sushko_Denis_Filter_Data/";

    private String currentFilename = null;

    private JFileChooser jFileChooser = new JFileChooser();

    private MainView mainView;

    public FileManager(MainView mainView) {
        this.mainView = mainView;

       chooserDefault();
    }

    public void save(String filename) {
        try {
            ImageBmp imageBmp = mainView.getFinalImage();
            if(imageBmp == null){
                JOptionPane.showMessageDialog(mainView, "Zone C is empty!");
                return;
            }

            imageBmp.write(new FileOutputStream(filename));
        }
        catch (IOException e1) {
            JOptionPane.showMessageDialog(mainView, "IOex save");
        }
        currentFilename = filename;

        mainView.setStateChange(false);
    }

    public void load(String filename) {
        File file = new File(filename);
        ImageBmp image = new ImageBmp();
        try {
            image.read(new FileInputStream(file));
            mainView.setImage(image);
        } catch (IOException e){
            JOptionPane.showMessageDialog(mainView, "IOex load");
        }

        currentFilename = filename;
        mainView.setStateChange(false);
    }

    public String getCurrentFilename() {
        return currentFilename;
    }

    public MainView getMainView() {
        return mainView;
    }

    public JFileChooser getjFileChooser() {
        return jFileChooser;
    }

    public void setCurrentFilename(String currentFilename) {
        this.currentFilename = currentFilename;
    }

    private void chooserDefault(){
        jFileChooser.setCurrentDirectory(new File(DEFAULT_SAVE));
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setFileFilter(new FileNameExtensionFilter("BMP/PNG Images", "bmp", "png"));
    }
}
