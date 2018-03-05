import ru.nsu.fit.g15203.sushko.field.FigurePanel;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGUI extends JFrame {
    private static final String pathRes = "src/ru/nsu/fit/g15203/sushko/res/icons/";
    private static final String nextIcon = "next.gif";
    private static final String saveIcon = "save.gif";
    private static final String saveAsIcon = "saveAs.gif";
    private static final String newIcon = "new.gif";
    private static final String pauseIcon = "pause.png";
    private static final String openIcon = "open.gif";
    private static final String resetGameIcon = "resetGame.gif";
    private static final String aboutIcon = "about.gif";
    private static final String playIcon = "play.gif";
    private static final String parametersIcon = "parameters.gif";
    private static final String aboutDialogIcon = "aboutDialog.jpg";
    private static final String impactIcon = "impact.png";
    private static final String xorIcon = "xor.png";
    private static final String replaceIcon = "replace.png";

    private static final int MINIMUM_WIDTH = 800;
    private static final int MINIMUM_HEIGHT = 600;
    private static final int MINIMUM_COUNT = 4;
    private static final int MAXIMUM_COUNT = 50;
    private static final int MINIMUM_RADIUS = 5;
    private static final int MAXIMUM_RADIUS = 50;
    private static final int MINIMUM_LINE = 1;
    private static final int MAXIMUM_LINE = 10;


    private JMenuBar menuBar = new JMenuBar();
    private FigurePanel figurePanel;
    private JToolBar toolBar;

    public SimpleGUI(String name) {
        super(name);
        figurePanel = new FigurePanel();
        setLayout(new BorderLayout());
        add(figurePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMainInterface();
        addWindowListener(new MyWindowsListener());
        pack();
        setVisible(true);
    }

    private void showDialogSaveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showSaveDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION){
            figurePanel.saveState(fileChooser.getSelectedFile());
        }
    }

    private void askSaveAndLoadState(){
        if(figurePanel.isStateChange()){
            int result = JOptionPane.showConfirmDialog(this,
                    "Save progress?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                showDialogSaveFile();
            }
        }
        showDialogLoadFile();
    }

    private void showDialogLoadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showSaveDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION){
            figurePanel.loadState(fileChooser.getSelectedFile());
        }
    }

    private void addMainInterface() {
        AboutActionListener aboutActionListener = new AboutActionListener();

        JMenu jMenuFile = new JMenu("File");
        JMenuItem itemNew = new JMenuItem("New");
        itemNew.addActionListener(e -> newGame());
        jMenuFile.add(itemNew);
        JMenuItem itemOpen = new JMenuItem("Open");
        itemOpen.addActionListener(e -> askSaveAndLoadState());
        jMenuFile.add(itemOpen);
        JMenuItem itemSave = new JMenuItem("Save");
        itemSave.addActionListener(e -> savePress());
        jMenuFile.add(itemSave);
        JMenuItem itemSaveAs = new JMenuItem("Save as...");
        itemSaveAs.addActionListener(e -> showDialogSaveFile());
        jMenuFile.add(itemSaveAs);
        menuBar.add(jMenuFile);

        JMenu jMenuGame = new JMenu("Game");
        JMenuItem itemPlay = new JMenuItem("Play");
        itemPlay.addActionListener(e -> figurePanel.play());
        jMenuGame.add(itemPlay);
        JMenuItem itemStep = new JMenuItem("Next step");
        itemStep.addActionListener(e -> figurePanel.nextStep());
        jMenuGame.add(itemStep);
        JMenuItem itemPause = new JMenuItem("Pause");
        itemPause.addActionListener(e -> figurePanel.stopGame());
        jMenuGame.add(itemPause);
        JMenuItem itemReset = new JMenuItem("Reset");
        itemReset.addActionListener((event) -> figurePanel.resetField());
        jMenuGame.add(itemReset);
        JMenuItem itemParameters = new JMenuItem("Parameters");
        itemParameters.addActionListener(e -> {
            figurePanel.stopGame();
            createSettingsDialog(false);
        });
        jMenuGame.add(itemParameters);
        JMenuItem itemImpact = new JMenuItem("Show impact");
        itemImpact.addActionListener(e -> figurePanel.showImpact());
        jMenuGame.add(itemImpact);
        ButtonGroup group = new ButtonGroup();
        JCheckBoxMenuItem jCheckBoxMenuItemXor = new JCheckBoxMenuItem("Xor");
        jCheckBoxMenuItemXor.setSelected(figurePanel.isXorMode());
        group.add(jCheckBoxMenuItemXor);
        jCheckBoxMenuItemXor.addActionListener(e -> figurePanel.setXor(true));
        jMenuGame.add(jCheckBoxMenuItemXor);
        JCheckBoxMenuItem jCheckBoxMenuItemReplace = new JCheckBoxMenuItem("Replace");
        jCheckBoxMenuItemReplace.setSelected(!figurePanel.isXorMode());
        jCheckBoxMenuItemReplace.addActionListener(e -> figurePanel.setXor(false));
        group.add(jCheckBoxMenuItemReplace);
        jMenuGame.add(jCheckBoxMenuItemReplace);
        menuBar.add(jMenuGame);

        JMenu jAbout = new JMenu("About");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(aboutActionListener);
        jAbout.add(itemAbout);
        menuBar.add(jAbout);

        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton newB = new JButton(new ImageIcon(pathRes + newIcon));
        newB.setToolTipText("New");
        newB.addActionListener(e -> newGame());
        toolBar.add(newB);

        JButton open = new JButton(new ImageIcon(pathRes + openIcon));
        open.addActionListener(e -> askSaveAndLoadState());
        open.setToolTipText("Open");
        toolBar.add(open);

        JButton save = new JButton(new ImageIcon(pathRes + saveIcon));
        save.addActionListener(e -> savePress());
        save.setToolTipText("Save");
        toolBar.add(save);

        JButton saveAs = new JButton(new ImageIcon(pathRes + saveAsIcon));
        saveAs.addActionListener(e -> showDialogSaveFile());
        saveAs.setToolTipText("Save as...");
        toolBar.add(saveAs);

        toolBar.addSeparator();

        JButton play = new JButton(new ImageIcon(pathRes + playIcon));
        play.setToolTipText("Play");
        play.addActionListener(e -> figurePanel.play());
        toolBar.add(play);

        JButton nextStep = new JButton(new ImageIcon(pathRes + nextIcon));
        nextStep.addActionListener(e -> figurePanel.nextStep());
        nextStep.setToolTipText("Next Step");
        toolBar.add(nextStep);

        JButton pauseGameB = new JButton(new ImageIcon(pathRes + pauseIcon));
        pauseGameB.addActionListener(e -> figurePanel.stopGame());
        pauseGameB.setToolTipText("Pause");
        toolBar.add(pauseGameB);

        JButton newGame = new JButton(new ImageIcon(pathRes + resetGameIcon));
        newGame.addActionListener((e -> figurePanel.resetField()));
        newGame.setToolTipText("Reset game");
        toolBar.add(newGame);

        JButton param = new JButton(new ImageIcon(pathRes + parametersIcon));
        param.setToolTipText("Parameters");
        param.addActionListener(e -> {
            figurePanel.stopGame();
            createSettingsDialog(false);
        });
        toolBar.add(param);

        JButton xorButton = new JButton(new ImageIcon(pathRes + xorIcon));
        xorButton.setToolTipText("Xor");
        xorButton.addActionListener(e -> {
            figurePanel.setXor(true);
            jCheckBoxMenuItemXor.setState(true);
            jCheckBoxMenuItemReplace.setState(false);
        }) ;
        toolBar.add(xorButton);

        JButton replaceButton = new JButton(new ImageIcon(pathRes + replaceIcon));
        replaceButton.setToolTipText("Replace");
        replaceButton.addActionListener(e -> {
            figurePanel.setXor(false);
            jCheckBoxMenuItemXor.setState(false);
            jCheckBoxMenuItemReplace.setState(true);
        });
        toolBar.add(replaceButton);

        JButton impact = new JButton(new ImageIcon(pathRes + impactIcon));
        impact.setToolTipText("Show impact");
        impact.addActionListener(e -> figurePanel.showImpact());
        toolBar.add(impact);


        toolBar.addSeparator();

        JButton about = new JButton(new ImageIcon(pathRes + aboutIcon));
        about.addActionListener(aboutActionListener);
        about.setToolTipText("About");
        toolBar.add(about);

        add(toolBar, BorderLayout.NORTH);


        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(MINIMUM_WIDTH, 800));
        JScrollPane pane = new JScrollPane(figurePanel);
        add(pane);

        setJMenuBar(menuBar);
    }

    private void newGame(){
        if(figurePanel.isStateChange()){
            int result = JOptionPane.showConfirmDialog(this,
                    "Save progress?",
                    "Save",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                showDialogSaveFile();
            }
        }
        createSettingsDialog(true);

    }

    private void savePress(){
        String path = figurePanel.lastLoadFile();
        if(path == null) {
            showDialogSaveFile();
        } else{
            figurePanel.saveState(new File(path));
        }
    }

    private void createSettingsDialog(boolean needReset) {
        JDialog jDialog = new JDialog(this, "settings", true);
        JPanel settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        JPanel mainParameters = new JPanel(new GridLayout(1, 6));

        IntFilter intFilter = new IntFilter();

        JPanel nameSLiders = new JPanel(new GridLayout(6, 1));
        JLabel widthLabel = new JLabel("Width", SwingConstants.CENTER);
        JLabel heightLabel = new JLabel("Height", SwingConstants.CENTER);
        JLabel linelabel = new JLabel("Line width", SwingConstants.CENTER);
        JLabel radiusLabel = new JLabel("Radius", SwingConstants.CENTER);
        nameSLiders.add(widthLabel);
        nameSLiders.add(heightLabel);
        nameSLiders.add(linelabel);
        nameSLiders.add(radiusLabel);
        mainParameters.add(nameSLiders);

        JPanel sliders = new JPanel(new GridLayout(6, 1));
        JSlider sliderWidth = new JSlider(JSlider.HORIZONTAL, MINIMUM_COUNT, MAXIMUM_COUNT, 10);
        sliderWidth.setValue(figurePanel.getWidthCount());
        JSlider sliderHeight = new JSlider(JSlider.HORIZONTAL, MINIMUM_COUNT, MAXIMUM_COUNT, 10);
        sliderHeight.setValue(figurePanel.getHeightCount());
        JSlider sliderLine = new JSlider(JSlider.HORIZONTAL, MINIMUM_LINE, MAXIMUM_LINE, 1);
        sliderLine.setValue(figurePanel.getWidthLine());
        JSlider sliderRadius = new JSlider(JSlider.HORIZONTAL, MINIMUM_RADIUS, MAXIMUM_RADIUS, 10);
        sliderRadius.setValue(figurePanel.getRadiusFigure());
        sliders.add(sliderWidth);
        sliders.add(sliderHeight);
        sliders.add(sliderLine);
        sliders.add(sliderRadius);
        mainParameters.add(sliders);

        JPanel fieldTexts = new JPanel(new GridLayout(6, 1));
        JTextField widthField = new JTextField();
        PlainDocument plainDocW = (PlainDocument) widthField.getDocument();
        plainDocW.setDocumentFilter(intFilter);
        widthField.setText(String.valueOf(figurePanel.getWidthCount()));
        fieldTexts.add(widthField);
        JTextField heightField = new JTextField();
        PlainDocument plainDocH = (PlainDocument) heightField.getDocument();
        plainDocH.setDocumentFilter(intFilter);
        heightField.setText(String.valueOf(figurePanel.getHeightCount()));
        fieldTexts.add(heightField);
        JTextField lineField = new JTextField();
        PlainDocument plainDocL = (PlainDocument) lineField.getDocument();
        plainDocL.setDocumentFilter(intFilter);
        lineField.setText(String.valueOf(figurePanel.getWidthLine()));
        fieldTexts.add(lineField);
        JTextField radiusField = new JTextField();
        PlainDocument plainDocR = (PlainDocument) radiusField.getDocument();
        plainDocR.setDocumentFilter(intFilter);
        radiusField.setText(String.valueOf(figurePanel.getRadiusFigure()));
        fieldTexts.add(radiusField);
        mainParameters.add(fieldTexts);

        JPanel parameters = new JPanel(new GridLayout(6, 1));
        JLabel firstIm = new JLabel("FST_IMPACT", SwingConstants.CENTER);
        JLabel sndIm = new JLabel("SND_IMPACT", SwingConstants.CENTER);
        JLabel liveBegin = new JLabel("LIVE_BEGIN", SwingConstants.CENTER);
        JLabel liveEnd = new JLabel("LIVE_END", SwingConstants.CENTER);
        JLabel birthBegin = new JLabel("BIRTH_BEGIN", SwingConstants.CENTER);
        JLabel birthEnd = new JLabel("BIRTH_END", SwingConstants.CENTER);
        parameters.add(firstIm);
        parameters.add(sndIm);
        parameters.add(liveBegin);
        parameters.add(liveEnd);
        parameters.add(birthBegin);
        parameters.add(birthEnd);
        mainParameters.add(parameters);

        JPanel fieldParam = new JPanel(new GridLayout(6, 1));
        JTextField firstField = new JTextField();
        PlainDocument plainDocFRT = (PlainDocument) firstField.getDocument();
        plainDocFRT.setDocumentFilter(new DoubleFilter());
        double value = figurePanel.getFstImpact();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        firstField.setText(decimalFormat.format(value));
        fieldParam.add(firstField);
        JTextField sndField = new JTextField();
        PlainDocument plainDocSND = (PlainDocument) sndField.getDocument();
        plainDocSND.setDocumentFilter(new DoubleFilter());
        value = figurePanel.getSndImpact();

        decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        sndField.setText(decimalFormat.format(value));
        fieldParam.add(sndField);
        JTextField liveBeginField = new JTextField();
        PlainDocument plainDocBeg = (PlainDocument) liveBeginField.getDocument();
        plainDocBeg.setDocumentFilter(new DoubleFilter());
        value = figurePanel.getLiveBegin();
        decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        liveBeginField.setText(decimalFormat.format(value));
        fieldParam.add(liveBeginField);
        JTextField liveEndField = new JTextField();
        PlainDocument plainDocEnd = (PlainDocument) liveEndField.getDocument();
        plainDocEnd.setDocumentFilter(new DoubleFilter());
        value = figurePanel.getLiveEnd();
        decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        liveEndField.setText(decimalFormat.format(value));
        fieldParam.add(liveEndField);
        JTextField birthBeginField = new JTextField();
        PlainDocument plainDocBirhBeg = (PlainDocument) birthBeginField.getDocument();
        plainDocBirhBeg.setDocumentFilter(new DoubleFilter());
        value = figurePanel.getBirthBegin();
        decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        birthBeginField.setText(decimalFormat.format(value));
        fieldParam.add(birthBeginField);
        JTextField birthEndField = new JTextField();
        PlainDocument plainDocBirhEnd = (PlainDocument) birthEndField.getDocument();
        plainDocBirhEnd.setDocumentFilter(new DoubleFilter());
        value = figurePanel.getBirthEnd();
        decimalFormat = new DecimalFormat(getPattern(value), otherSymbols);
        birthEndField.setText(decimalFormat.format(value));
        fieldParam.add(birthEndField);
        mainParameters.add(fieldParam);

        JPanel xorPanel = new JPanel(new GridLayout(2, 1));
        ButtonGroup group = new ButtonGroup();
        JRadioButton replaceB = new JRadioButton("Replace", true);
        replaceB.setSelected(!figurePanel.isXorMode());
        group.add(replaceB);
        JRadioButton xorB = new JRadioButton("XOR", false);
        xorB.setSelected(figurePanel.isXorMode());
        group.add(xorB);
        xorPanel.add(replaceB);
        xorPanel.add(xorB);
        mainParameters.add(xorPanel);

        sliderWidth.addChangeListener(e ->
                widthField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        sliderHeight.addChangeListener(e ->
                heightField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        sliderLine.addChangeListener(e ->
                lineField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        sliderRadius.addChangeListener(e ->
                radiusField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        widthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!widthField.getText().isEmpty()) sliderWidth.setValue(Integer.parseInt(widthField.getText()));
            }
        });

        heightField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!heightField.getText().isEmpty())
                    sliderHeight.setValue(Integer.parseInt(heightField.getText()));
            }
        });

        lineField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!lineField.getText().isEmpty())
                    sliderLine.setValue(Integer.parseInt(lineField.getText()));
            }
        });

        radiusField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!radiusField.getText().isEmpty())
                    sliderRadius.setValue(Integer.parseInt(radiusField.getText()));
            }
        });

        settings.add(mainParameters);
        JButton jButton = new JButton("Accept");
        jButton.addActionListener(e -> {
            if(widthField.getText().isEmpty() || heightField.getText().isEmpty() || lineField.getText().isEmpty()
                    || radiusField.getText().isEmpty() || firstField.getText().isEmpty()  || sndField.getText().isEmpty()
                    || liveBeginField.getText().isEmpty()  || liveEndField.getText().isEmpty()  || birthBeginField.getText().isEmpty()
                    || birthEndField.getText().isEmpty() ){
                JOptionPane.showMessageDialog(this,
                        "Please, write value",
                        "Empty field",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int radius = Integer.parseInt(radiusField.getText());
            int widthLine = Integer.parseInt(lineField.getText());
            if(width < MINIMUM_COUNT){
                JOptionPane.showMessageDialog(this,
                        "Incorrent width. Width set minimum",
                        "Incorrent width",
                        JOptionPane.INFORMATION_MESSAGE);
                width = MINIMUM_COUNT;
            }
            if(width > MAXIMUM_COUNT){
                JOptionPane.showMessageDialog(this,
                        "Incorrent width. Width set maximum",
                        "Incorrent width",
                        JOptionPane.INFORMATION_MESSAGE);
                width = MAXIMUM_COUNT;
            }

            if(height < MINIMUM_COUNT){
                JOptionPane.showMessageDialog(this,
                        "Incorrent height. Height set minimum",
                        "Incorrent height",
                        JOptionPane.INFORMATION_MESSAGE);
                height = MINIMUM_COUNT;
            }
            if(height > MAXIMUM_COUNT ){
                JOptionPane.showMessageDialog(this,
                        "Incorrent height. Height set maximum",
                        "Incorrent height",
                        JOptionPane.INFORMATION_MESSAGE);
                height = MAXIMUM_COUNT;
            }

            if(widthLine < MINIMUM_LINE){
                JOptionPane.showMessageDialog(this,
                        "Incorrent width line. Width line set minimum",
                        "Incorrent width line",
                        JOptionPane.INFORMATION_MESSAGE);
                widthLine = MINIMUM_LINE;
            }

            if(widthLine > MAXIMUM_LINE){
                JOptionPane.showMessageDialog(this,
                        "Incorrent width line. Width line set maximum",
                        "Incorrent width line",
                        JOptionPane.INFORMATION_MESSAGE);
                widthLine = MAXIMUM_LINE;
            }
            if(radius < MINIMUM_RADIUS){
                JOptionPane.showMessageDialog(this,
                        "Incorrent radius. Radius set minimum",
                        "Incorrent radius",
                        JOptionPane.INFORMATION_MESSAGE);
                radius = MINIMUM_RADIUS;
            }

            if(radius > MAXIMUM_RADIUS ){
                JOptionPane.showMessageDialog(this,
                        "Incorrent radius. Radius set maximum",
                        "Incorrent radius",
                        JOptionPane.INFORMATION_MESSAGE);
                radius = MAXIMUM_RADIUS;
            }

            figurePanel.setField(width, height);
            figurePanel.setWidthLine(widthLine);
            figurePanel.setRadius(radius);

            double firstImpact = Double.valueOf(firstField.getText());
            double secondImpact = Double.valueOf(sndField.getText());
            double liveBeginDecimal = Double.valueOf(liveBeginField.getText());
            double liveEndDecimal = Double.valueOf(liveEndField.getText());
            if( liveBeginDecimal > liveEndDecimal){
                JOptionPane.showMessageDialog(this,
                        "Please, write correct live value",
                        "Empty field",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double birthBeginDecimal = Double.valueOf(birthBeginField.getText());
            double birthEndDecimal = Double.valueOf(birthEndField.getText());
            if( birthBeginDecimal > birthEndDecimal || birthBeginDecimal >= liveEndDecimal){
                JOptionPane.showMessageDialog(this,
                        "Please, write correct birth value",
                        "Empty field",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(needReset) {
                figurePanel.resetField();
            }
            figurePanel.setImpact(firstImpact, secondImpact,
                    liveBeginDecimal, liveEndDecimal,
                    birthBeginDecimal, birthEndDecimal);
            figurePanel.recalculate();
            figurePanel.setXor(xorB.isSelected());
            figurePanel.reloadImage();
            figurePanel.repaintField();
            jDialog.dispose();
        });
        settings.add(jButton);

        jDialog.add(settings);
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jDialog.setPreferredSize(new Dimension(600, 400));
        jDialog.setResizable(false);
        jDialog.pack();
        jDialog.setLocationRelativeTo(this);
        jDialog.setVisible(true);
    }

    private String getPattern(double value){
        if (value % 1 == 0) {
            return  "##0";
        } else {
            return "##0.0";
        }
    }


    private class AboutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(SimpleGUI.this,
                    "Version 0.1 alpha.\n" +
                            "FIT 15203 Sushko Denis\n" +
                            "Try Live",
                    "About FIT_15203_Sushko_Denis",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(pathRes + aboutDialogIcon));
        }
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

    private class MyWindowsListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            if(figurePanel.isStateChange()) {
                int result = JOptionPane.showConfirmDialog(SimpleGUI.this,
                        "Save progress?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    showDialogSaveFile();
                }
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


    public static void main(String[] args) {
        JFrame jFrame = new SimpleGUI("hex");
    }
}