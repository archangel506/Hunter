package ru.nsu.fit.g15203.sushko;

import javax.swing.*;
import java.awt.*;


public final class Main {
    private final static String resPath = "src/ru/nsu/fit/g15203/sushko/res/icons/";
    private final static String aboutDialog = "aboutDialog.jpg" +
            "";
    private final static String aboutIcon = "about.gif";
    private final static String gridIcon = "grid.png";
    private final static String interpolationIcon = "interpolation.png";
    private final static String izolineIcon = "izoline.png";
    private final static String mapIcon = "map.png";
    private final static String openIcon = "open.gif";
    private final static String settingsIcon = "settings.png";

    private final JFrame mainFrame = new JFrame("Izoline");
    private final JPanel actionPanel = new JPanel();

    {
        mainFrame.setLayout(new BorderLayout());
        initMenu();
        initToolbar();
        mainFrame.setSize(new Dimension(800, 600));
        mainFrame.add(actionPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();

        toolBar.add(createOpenForToolbar());
        toolBar.addSeparator();
        toolBar.add(createMapForToolbar());
        toolBar.add(createParametersForToolbar());
        toolBar.add(createInterpolationForToolbar());
        toolBar.add(createGridForToolbar());
        toolBar.add(createIzolineForToolbar());
        toolBar.addSeparator();
        toolBar.add(createAboutProgramForToolbar());
        toolBar.setFloatable(false);

        mainFrame.add(toolBar, BorderLayout.NORTH);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createMenuFile());
        menuBar.add(createSettings());
        menuBar.add(createAboutForMenu());
        mainFrame.setJMenuBar(menuBar);
    }

    private JButton createMapForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + mapIcon));
        about.addActionListener(actionEvent -> applyMap());
        about.setToolTipText("Map");
        return about;
    }

    private JButton createInterpolationForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + interpolationIcon));
        about.addActionListener(actionEvent -> applyInterpolation());
        about.setToolTipText("Interpolation");
        return about;
    }

    private JButton createParametersForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + settingsIcon));
        about.addActionListener(actionEvent -> showSetting());
        about.setToolTipText("Parameters");
        return about;
    }

    private JButton createGridForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + gridIcon));
        about.addActionListener(actionEvent -> applyGrid());
        about.setToolTipText("Grid");
        return about;
    }

    private JButton createIzolineForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + izolineIcon));
        about.addActionListener(actionEvent -> applyIzoline());
        about.setToolTipText("Izoline");
        return about;
    }


    private JButton createOpenForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + openIcon));
        about.addActionListener(actionEvent -> openFile());
        about.setToolTipText("Open");
        return about;
    }

    private JButton createAboutProgramForToolbar() {
        JButton about = new JButton(new ImageIcon(resPath + aboutIcon));
        about.addActionListener(actionEvent -> showAbout());
        about.setToolTipText("About");
        return about;
    }

    private JMenu createSettings() {
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem parameters = new JMenuItem("Parameters");
        parameters.addActionListener(actionEvent -> showSetting());
        settingsMenu.add(settingsMenu);

        JMenuItem grid = new JMenuItem("Grid");
        parameters.addActionListener(actionEvent -> applyGrid());
        settingsMenu.add(grid);

        JMenuItem interpolation = new JMenuItem("Interpolation");
        interpolation.addActionListener(actionEvent -> applyInterpolation());
        settingsMenu.add(interpolation);

        JMenuItem map = new JMenuItem("Map");
        map.addActionListener(actionEvent -> applyMap());
        settingsMenu.add(map);

        JMenuItem izoline = new JMenuItem("Izoline");
        izoline.addActionListener(actionEvent -> applyIzoline());
        settingsMenu.add(izoline);
        return settingsMenu;
    }

    private JMenu createMenuFile() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(actionEvent -> {
            openFile();
        });
        fileMenu.add(open);

        return fileMenu;
    }

    private JMenu createAboutForMenu() {
        JMenu jMenuAbout = new JMenu("About");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(actionEvent ->{
            showAbout();
        });
        jMenuAbout.add(itemAbout);
        return jMenuAbout;
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(mainFrame,
                "Version 0.3 alpha.\n" +
                        "FIT 15203 Sushko Denis\n" +
                        "Try Izoline",
                "About FIT_15203_Sushko_Denis",
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon( resPath + aboutDialog));

    }

    private void applyIzoline() {

    }

    private void applyGrid() {

    }

    private void applyMap() {

    }

    private void showSetting() {

    }


    private void openFile() {

    }

    private void applyInterpolation() {
    }


    public static void main(String[] args) {
        Main main = new Main();
    }
}
