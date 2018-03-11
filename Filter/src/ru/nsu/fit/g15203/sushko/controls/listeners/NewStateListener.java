package ru.nsu.fit.g15203.sushko.controls.listeners;

import ru.nsu.fit.g15203.sushko.models.FileManager;
import ru.nsu.fit.g15203.sushko.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewStateListener implements ActionListener {
    private FileManager fileManager;

    public NewStateListener(FileManager fileManager){
        this.fileManager = fileManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainView mainView = fileManager.getMainView();
        mainView.clearFrame();
        mainView.setStateChange(false);
        fileManager.setCurrentFilename(null);
    }
}
