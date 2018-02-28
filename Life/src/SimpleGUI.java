import ru.nsu.fit.g15203.sushko.field.FigurePanel;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGUI extends JFrame {
    private static final String pathRes = "src/ru/nsu/fit/g15203/sushko/res/icons/";
    private static final String nextIcon = "next.gif";
    private static final String saveIcon = "save.gif";
    private static final String newIcon = "new.gif";
    private static final String openIcon = "open.gif";
    private static final String resetGameIcon = "resetGame.gif";
    private static final String aboutIcon = "about.gif";
    private static final String playIcon = "play.gif";
    private static final String parametersIcon = "parameters.gif";
    private static final String aboutDialogIcon = "aboutDialog.jpg";
    private static final String impactIcon = "impact.png";

    private static final int MINIMUM_WIDTH = 800;
    private static final int MINIMUM_HEIGHT = 600;

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
        FileDialog fileDialog = new FileDialog(this, "Save", FileDialog.SAVE);
        fileDialog.show();
        figurePanel.saveState(fileDialog.getDirectory() + fileDialog.getFile());
    }

    private void showDialogLoadFile() {
        FileDialog fileDialog = new FileDialog(this, "Load", FileDialog.LOAD);
        fileDialog.show();
        figurePanel.loadState(fileDialog.getDirectory() + fileDialog.getFile());
    }

    private void addMainInterface() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton newB = new JButton(new ImageIcon(pathRes + newIcon));
        newB.setToolTipText("New");
        newB.addActionListener(e -> newGame());
        toolBar.add(newB);

        JButton open = new JButton(new ImageIcon(pathRes + openIcon));
        open.addActionListener(e -> showDialogLoadFile());
        open.setToolTipText("Open");
        toolBar.add(open);

        JButton save = new JButton(new ImageIcon(pathRes + saveIcon));
        save.addActionListener(e -> showDialogSaveFile());
        save.setToolTipText("Save");
        toolBar.add(save);

        toolBar.addSeparator();

        JButton play = new JButton(new ImageIcon(pathRes + playIcon));
        play.setToolTipText("Play");
        play.addActionListener(e -> figurePanel.play());
        toolBar.add(play);

        JButton nextStep = new JButton(new ImageIcon(pathRes + nextIcon));
        nextStep.addActionListener(e -> figurePanel.nextStep());
        nextStep.setToolTipText("Next Step");
        toolBar.add(nextStep);

        JButton newGame = new JButton(new ImageIcon(pathRes + resetGameIcon));
        newGame.addActionListener((e -> figurePanel.resetField()));
        newGame.setToolTipText("Reset game");
        toolBar.add(newGame);

        JButton param = new JButton(new ImageIcon(pathRes + parametersIcon));
        param.setToolTipText("Parameters");
        param.addActionListener(e -> {
            figurePanel.stopGame();
            createSettingsDialog();
        });
        toolBar.add(param);

        JButton impact = new JButton(new ImageIcon(pathRes + impactIcon));
        param.setToolTipText("Show impact");
        impact.addActionListener(e -> figurePanel.showImpact());
        toolBar.add(impact);


        toolBar.addSeparator();

        JButton about = new JButton(new ImageIcon(pathRes + aboutIcon));
        AboutActionListener aboutActionListener = new AboutActionListener(this);
        about.addActionListener(aboutActionListener);
        about.setToolTipText("About");
        toolBar.add(about);

        add(toolBar, BorderLayout.NORTH);

        JMenu jMenuFile = new JMenu("File");
        JMenuItem itemNew = new JMenuItem("New");
        itemNew.addActionListener(e -> newGame());
        jMenuFile.add(itemNew);
        JMenuItem itemOpen = new JMenuItem("Open");
        itemOpen.addActionListener(e -> showDialogLoadFile());
        jMenuFile.add(itemOpen);
        JMenuItem itemSave = new JMenuItem("Save");
        itemSave.addActionListener(e -> showDialogSaveFile());
        jMenuFile.add(itemSave);
        menuBar.add(jMenuFile);

        JMenu jMenuGame = new JMenu("Game");
        JMenuItem itemPlay = new JMenuItem("Play");
        itemPlay.addActionListener(e -> figurePanel.play());
        jMenuGame.add(itemPlay);
        JMenuItem itemStep = new JMenuItem("Next step");
        itemStep.addActionListener(e -> figurePanel.nextStep());
        jMenuGame.add(itemStep);
        JMenuItem itemReset = new JMenuItem("Reset");
        itemReset.addActionListener((event) -> figurePanel.resetField());
        jMenuGame.add(itemReset);
        JMenuItem itemParameters = new JMenuItem("Parameters");
        itemParameters.addActionListener(e -> {
            figurePanel.stopGame();
            createSettingsDialog();
        });
        jMenuGame.add(itemParameters);
        JMenuItem itemImpact = new JMenuItem("Show impact");
        itemImpact.addActionListener(e -> figurePanel.showImpact());
        jMenuGame.add(itemImpact);
        menuBar.add(jMenuGame);

        JMenu jAbout = new JMenu("About");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(aboutActionListener);
        jAbout.add(itemAbout);
        menuBar.add(jAbout);

        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(MINIMUM_WIDTH, 800));
        JScrollPane pane = new JScrollPane(figurePanel);
        add(pane);

        setJMenuBar(menuBar);
    }

    private void newGame(){
        if(figurePanel.isStateChange()){
            int result = JOptionPane.showConfirmDialog(null,
                    "Save progress?",
                    "Save",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                showDialogSaveFile();
            }
        }

        figurePanel.newGame();
    }

    private void createSettingsDialog() {
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
        JSlider sliderWidth = new JSlider(JSlider.HORIZONTAL, 4, 50, 10);
        sliderWidth.setValue(figurePanel.getWidthCount());
        JSlider sliderHeight = new JSlider(JSlider.HORIZONTAL, 4, 50, 10);
        sliderHeight.setValue(figurePanel.getHeightCount());
        JSlider sliderLine = new JSlider(JSlider.HORIZONTAL, 1, 8, 1);
        sliderLine.setValue(figurePanel.getWidthLine());
        JSlider sliderRadius = new JSlider(JSlider.HORIZONTAL, 5, 50, 10);
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
        firstField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getFstImpact()));
        fieldParam.add(firstField);
        JTextField sndField = new JTextField();
        PlainDocument plainDocSND = (PlainDocument) sndField.getDocument();
        plainDocSND.setDocumentFilter(new DoubleFilter());
        sndField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getSndImpact()));
        fieldParam.add(sndField);
        JTextField liveBeginField = new JTextField();
        PlainDocument plainDocBeg = (PlainDocument) liveBeginField.getDocument();
        plainDocBeg.setDocumentFilter(new DoubleFilter());
        liveBeginField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getLiveBegin()));
        fieldParam.add(liveBeginField);
        JTextField liveEndField = new JTextField();
        PlainDocument plainDocEnd = (PlainDocument) liveEndField.getDocument();
        plainDocEnd.setDocumentFilter(new DoubleFilter());
        liveEndField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getLiveEnd()));
        fieldParam.add(liveEndField);
        JTextField birthBeginField = new JTextField();
        PlainDocument plainDocBirhBeg = (PlainDocument) birthBeginField.getDocument();
        plainDocBirhBeg.setDocumentFilter(new DoubleFilter());
        birthBeginField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getBirthBegin()));
        fieldParam.add(birthBeginField);
        JTextField birthEndField = new JTextField();
        PlainDocument plainDocBirhEnd = (PlainDocument) birthEndField.getDocument();
        plainDocBirhEnd.setDocumentFilter(new DoubleFilter());
        birthEndField.setText(String.format(Locale.ENGLISH, "%.1f", figurePanel.getBirthEnd()));
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
                return;
            }

            figurePanel.setField(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
            figurePanel.setWidthLine(Integer.parseInt(lineField.getText()));
            figurePanel.setRadius(Integer.parseInt(radiusField.getText()));
            figurePanel.setImpact(Double.valueOf(firstField.getText()),
                    Double.valueOf(sndField.getText()),
                    Double.valueOf(liveBeginField.getText()),
                    Double.valueOf(liveEndField.getText()),
                    Double.valueOf(birthBeginField.getText()),
                    Double.valueOf(birthEndField.getText()));
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


    private class AboutActionListener implements ActionListener {
        private JFrame frame;

        public AboutActionListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,
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
                int result = JOptionPane.showConfirmDialog(null,
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