package ru.nsu.fit.g15203.sushko;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Main {
    private final static String resPath = "src/ru/nsu/fit/g15203/sushko/res/icons/";
    private final static String aboutDialog = "aboutDialog.jpg";
    private final static String aboutIcon = "about.gif";
    private final static String gridIcon = "grid.png";
    private final static String interpolationIcon = "interpolation.png";
    private final static String izolineIcon = "izoline.png";
    private final static String mapIcon = "map.png";
    private final static String openIcon = "open.gif";
    private final static String settingsIcon = "settings.png";

    private final JFrame mainFrame = new JFrame("Izoline");
    private final JLabel statusBar = new JLabel("Status Bar");
    private final DecimalFormat decimalFormat = new DecimalFormat();

    private ControlsFunction controlsFunction;
    private ActionPanel actionPanel;


    {
        decimalFormat.setMaximumFractionDigits(3);
        mainFrame.setLayout(new BorderLayout());
        controlsFunction = new ControlsFunction(mainFrame, this);
        actionPanel = new ActionPanel(controlsFunction);
        initMenu();
        initToolbar();
        mainFrame.setSize(new Dimension(800, 600));
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.add(actionPanel, BorderLayout.CENTER);
        mainFrame.add(statusBar, BorderLayout.SOUTH);

        mainFrame.setVisible(true);

    }

    void setValueStatusBar(int x, int y, double value){
        if(y >= actionPanel.getHeight() * 8 / 10){
            statusBar.setText("Not value");
            return;
        }

        statusBar.setText
                (
                    "x: " + x + ", " +
                    "y: " + y + ", " +
                    "z: " + decimalFormat.format(value)
                );
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
        controlsFunction.changeStateIzoline();
        actionPanel.repaint();
    }

    private void applyGrid() {
        controlsFunction.setStateGrid(true);
        actionPanel.repaint();
    }

    private void applyMap() {
        controlsFunction.setStateGrid(false);
        actionPanel.repaint();
    }

    private void showSetting() {
        final JDialog dialog = new JDialog();
        IntFilter intFilter = new IntFilter();
        DoubleFilter doubleFilter = new DoubleFilter();

        ConfigField configField = controlsFunction.getConfigField();
        dialog.setLayout(new GridLayout(7, 2));

        final JTextField K = new JTextField(Integer.toString(configField.getWidth()));
        PlainDocument documentK = (PlainDocument) K.getDocument();
        documentK.setDocumentFilter(intFilter);

        final JTextField M = new JTextField(Integer.toString(configField.getHeight()));
        PlainDocument docM = (PlainDocument) M.getDocument();
        docM.setDocumentFilter(intFilter);

        final JTextField A = new JTextField(Double.toString(configField.getA()));
        PlainDocument docA = (PlainDocument) A.getDocument();
        docA.setDocumentFilter(doubleFilter);

        final JTextField B = new JTextField(Double.toString(configField.getB()));
        PlainDocument docB = (PlainDocument) B.getDocument();
        docB.setDocumentFilter(doubleFilter);

        final JTextField C = new JTextField(Double.toString(configField.getC()));
        PlainDocument docC = (PlainDocument) C.getDocument();
        docC.setDocumentFilter(doubleFilter);

        final JTextField D = new JTextField(Double.toString(configField.getD()));
        PlainDocument docD = (PlainDocument) D.getDocument();
        docD.setDocumentFilter(doubleFilter);

        JLabel labelK = new JLabel("K: ");
        JLabel labelM = new JLabel("M: ");
        JLabel labelA = new JLabel("A: ");
        JLabel labelB = new JLabel("B: ");
        JLabel labelC = new JLabel("C: ");
        JLabel labelD = new JLabel("D: ");

        JButton save = new JButton("Save");

        save.addActionListener( actionEvent ->  {
            configField.setWidth(Integer.parseInt(K.getText()));
            configField.setHeight(Integer.parseInt(M.getText()));
            configField.setA(Double.parseDouble(A.getText()));
            configField.setB(Double.parseDouble(B.getText()));
            configField.setC(Double.parseDouble(C.getText()));
            configField.setD(Double.parseDouble(D.getText()));
            dialog.setVisible(false);

            controlsFunction.flushMouseIzoline();
            actionPanel.repaint();
        });

        dialog.add(labelK);
        dialog.add(K);

        dialog.add(labelM);
        dialog.add(M);

        dialog.add(labelA);
        dialog.add(A);

        dialog.add(labelB);
        dialog.add(B);

        dialog.add(labelC);
        dialog.add(C);

        dialog.add(labelD);
        dialog.add(D);
        dialog.add(save);

        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setSize(200, 300);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);

        mainFrame.repaint();
    }


    private void openFile() {
        controlsFunction.loadConfig();
        actionPanel.repaint();
    }

    private void applyInterpolation() {
        controlsFunction.changeStateInterpolation();
        actionPanel.repaint();
    }

    private class IntFilter extends DocumentFilter {
        private static final String mask = "\\d+";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string.matches(mask)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches(mask)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    private class DoubleFilter extends DocumentFilter {
        private static final String maskInt = "\\d+";
        private final Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        private boolean wasDot = false;
        private int offsetDot = -1;

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (wasDot && offset == offsetDot) {
                wasDot = false;
                offsetDot = -1;
            }
            super.remove(fb, offset, length);
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            Matcher matcher = pattern.matcher(string);
            if (matcher.matches()) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.contains(".") && (offset != 0 || text.indexOf('.') != 0)) {
                if (wasDot) {
                    return;
                } else {
                    wasDot = true;
                    offsetDot = text.indexOf('.') + offset;
                }
            } else {
                if (!text.matches(maskInt)) {
                    return;
                }
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
    }
}
