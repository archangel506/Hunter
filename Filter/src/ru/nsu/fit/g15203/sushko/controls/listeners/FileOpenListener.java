package ru.nsu.fit.g15203.sushko.controls.listeners;

import ru.nsu.fit.g15203.sushko.models.FileManager;
import ru.nsu.fit.g15203.sushko.models.ImageBmp;
import ru.nsu.fit.g15203.sushko.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileOpenListener implements ActionListener {
    private FileManager fileManager;

    public FileOpenListener(FileManager fileManager){
        this.fileManager = fileManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainView mainView = fileManager.getMainView();
        JFileChooser jFileChooser = fileManager.getjFileChooser();
        int ret = jFileChooser.showOpenDialog(mainView);

        if (ret == JFileChooser.APPROVE_OPTION) {
            mainView.clearFrame();
            File file = jFileChooser.getSelectedFile();
            try {
                File temp;
                boolean isBmp = file.toString().endsWith(".bmp");
                if (!isBmp) {
                    temp = convertToBmp(file);
                }
                else {
                    temp = file;
                }
                fileManager.load(temp.getAbsolutePath());
                if(!isBmp) temp.delete();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainView,"Unsupported file");
            }
        }
    }

    private File convertToBmp(File file) throws IOException{
        BufferedImage bufferedImage = ImageIO.read(file);
        File temp = new File("temp.png");
        ImageIO.write(bufferedImage, "BMP", temp);
        return temp;
    }
}
