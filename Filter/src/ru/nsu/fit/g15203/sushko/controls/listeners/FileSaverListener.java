package ru.nsu.fit.g15203.sushko.controls.listeners;

import ru.nsu.fit.g15203.sushko.models.FileManager;

import java.awt.event.ActionEvent;
import java.io.File;

public class FileSaverListener extends FileSaverAsListener {

    public FileSaverListener(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveState();
    }

    public void saveState(){
        if(fileManager.getMainView().isStateChange()){
            String filename = fileManager.getCurrentFilename();
            if(filename == null){
                choiseAndSave();
            } else{
                fileManager.save(filename);
            }

        }
    }
}
