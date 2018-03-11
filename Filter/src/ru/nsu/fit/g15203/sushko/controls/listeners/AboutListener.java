package ru.nsu.fit.g15203.sushko.controls.listeners;

import ru.nsu.fit.g15203.sushko.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutListener implements ActionListener {
    private static final String aboutDialogIcon = "aboutDialog.jpg";

    private JFrame jFrame;

    public AboutListener(JFrame jFrame){
        this.jFrame = jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(jFrame,
                "Version 0.2 alpha.\n" +
                        "FIT 15203 Sushko Denis\n" +
                        "Try Filter",
                "About FIT_15203_Sushko_Denis",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(MainView.pathRes + aboutDialogIcon));
    }
}
