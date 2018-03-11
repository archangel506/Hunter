package ru.nsu.fit.g15203.sushko.controls.listeners;

import ru.nsu.fit.g15203.sushko.models.FileManager;
import ru.nsu.fit.g15203.sushko.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSaverAsListener implements ActionListener {
    protected FileManager fileManager;
    private JFileChooser jFileChooser = new JFileChooser();

    public FileSaverAsListener(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        choiseAndSave();
    }

    protected void choiseAndSave(){
        MainView mainView = fileManager.getMainView();
        if(mainView.getFinalImage() == null){
            JOptionPane.showMessageDialog(mainView,  "Zone C is empty!");
            return;
        }
        int ret = jFileChooser.showSaveDialog(mainView);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = getCorrectFile();
            if (file.exists()) file.delete();
            fileManager.save(file.getAbsolutePath());
        }
    }

    private File getCorrectFile() {
        File file = jFileChooser.getSelectedFile();
        String fileName = file.getAbsolutePath();
        Path path = Paths.get(fileName);

        if (!path.toString().endsWith(".bmp") || !path.toString().endsWith(".png")) {
            file = new File(fileName + ".bmp");
        }
        return file;
    }
}
