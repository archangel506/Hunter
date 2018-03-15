package ru.nsu.fit.g15203.sushko.view;

import ru.nsu.fit.g15203.sushko.controls.listeners.*;
import ru.nsu.fit.g15203.sushko.models.FileManager;
import ru.nsu.fit.g15203.sushko.models.ImageBmp;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainView extends JFrame{
    public static final String pathRes = "src/ru/nsu/fit/g15203/sushko/res/icons/";
    private static final String saveIcon = "save.gif";
    private static final String saveAsIcon = "saveAs.gif";
    private static final String newIcon = "new.gif";
    private static final String openIcon = "open.gif";
    private static final String aboutIcon = "about.gif";
    private static final String selectIcon = "select.png";
    private static final String negativeIcon = "negative.png";
    private static final String leftIcon = "left.png";
    private static final String rightIcon = "right.png";
    private static final String aquaIcon = "aqua.png";
    private static final String doubleZoomIcon = "doubleSize.png";
    private static final String embassingIcon = "embassing.png";
    private static final String blackWhiteIcon = "blackWhite.png";
    private static final String blurIcon = "blur.png";
    private static final String floydIcon = "floyd.png";
    private static final String orderIcon = "order.png";
    private static final String robertsIcon = "roberts.png";
    private static final String sobelIcon = "sobel.png";
    private static final String gammaIcon = "gamma.png";
    private static final String definitionIcon = "definition.png";
    private static final String rotationIcon = "rotation.png";

    private FileManager fileManager = new FileManager(this);
    private ParentZones parentZones = new ParentZones();

    private JScrollPane jScrollPane;
    private JToolBar jToolBar;
    private JMenuBar jMenuBar;
    private JCheckBoxMenuItem itemSelect;
    private Thread floydThread = null;

    private boolean stateChange = false;

    public MainView(String name) {
        super(name);
        initPanels();
        initMenu();
        initToolbar();
        initFrame();
    }

    public boolean isStateChange(){
        return stateChange;
    }

    public void clearFrame(){
        parentZones.clearZones();
    }

    public void setImage(ImageBmp image){
        parentZones.setImage(image);
    }

    public void setStateChange(boolean flag){
        stateChange = flag;
    }


    private void initFrame() {
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setJMenuBar(jMenuBar);

        add(jScrollPane, BorderLayout.CENTER);
        add(jToolBar, BorderLayout.NORTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new MyWindowsListener());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initPanels() {
        parentZones.setPreferredSize(new Dimension(1100, 370));

        jScrollPane = new JScrollPane(parentZones, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void initMenu() {
        jMenuBar = new JMenuBar();

        jMenuBar.add(createMenuFile());
        jMenuBar.add(createFilters());
        jMenuBar.add(createAboutForMenu());
    }

    private void initToolbar() {
        jToolBar = new JToolBar();

        jToolBar.add(createStandartStateForToolbar());
        jToolBar.add(createOpenerFileForToolbar());
        jToolBar.add(createSaverFileForToolbar());
        jToolBar.add(createSaverAsFileForToolbar());

        jToolBar.addSeparator();

        jToolBar.add(createSelectForToolbar());
        jToolBar.add(createRightForToolbar());
        jToolBar.add(createLeftForToolbar());
        jToolBar.add(createNegativeForToolbar());
        jToolBar.add(createAquarelleForToolbar());
        jToolBar.add(createDoubleZoomForToolbar());
        jToolBar.add(createBlackWhiteForToolbar());
        jToolBar.add(createEmbassingForToolbar());
        jToolBar.add(createGaussForToolbar());
        jToolBar.add(createFloydForToolbar());
        jToolBar.add(createOrderForToolbar());
        jToolBar.add(createSobelForToolbar());
        jToolBar.add(createRobertsForToolbar());
        jToolBar.add(createGammaForToolbar());
        jToolBar.add(createDefinitionForToolbar());
        jToolBar.add(createRotationForToolbar());

        jToolBar.addSeparator();

        jToolBar.add(createAboutProgramForToolbar());

        jToolBar.setFloatable(false);
    }

    private JMenu createMenuFile() {
        JMenu fileMenu = new JMenu("File");

        fileMenu.add(createStandartStateForMenu());
        fileMenu.add(createOpenerFileForMenu());
        fileMenu.add(createSaverFileForMenu());
        fileMenu.add(createSaverAsFileForMenu());
        return fileMenu;
    }

    private JMenu createFilters() {
        JMenu filtersMenu = new JMenu("Filters");

        filtersMenu.add(createSelectForMenu());

        filtersMenu.addSeparator();

        filtersMenu.add(createLeftForMenu());
        filtersMenu.add(createRightForMenu());

        filtersMenu.addSeparator();

        filtersMenu.add(createNegativeForMenu());
        filtersMenu.add(createAquarelleForMenu());
        filtersMenu.add(createDoubleZoomForMenu());
        filtersMenu.add(createBlackWhiteForMenu());
        filtersMenu.add(createEmbassingForMenu());
        filtersMenu.add(createGaussForMenu());
        filtersMenu.add(createFloydForMenu());
        filtersMenu.add(createOrderForMenu());
        filtersMenu.add(createSobelForMenu());
        filtersMenu.add(createRobertsForMenu());
        filtersMenu.add(createGammaForMenu());
        filtersMenu.add(createDefinitionForMenu());
        filtersMenu.add(createRotationForMenu());
        return filtersMenu;
    }



    private JMenuItem createStandartStateForMenu() {
        JMenuItem newM = new JMenuItem("New");
        newM.addActionListener(new NewStateListener(fileManager));
        return newM;
    }

    private JButton createStandartStateForToolbar() {
        JButton newB = new JButton(new ImageIcon(pathRes + newIcon));
        newB.addActionListener(new NewStateListener(fileManager));
        newB.setToolTipText("New");
        return newB;
    }

    private JMenuItem createOpenerFileForMenu() {
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new FileOpenListener(fileManager));
        return open;
    }

    private JButton createOpenerFileForToolbar() {
        JButton open = new JButton(new ImageIcon(pathRes + openIcon));
        open.addActionListener(new FileOpenListener(fileManager));
        open.setToolTipText("Open");
        return open;
    }

    private JMenuItem createSaverFileForMenu() {
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new FileSaverListener(fileManager));
        return save;
    }

    private JButton createSaverFileForToolbar() {
        JButton save = new JButton(new ImageIcon(pathRes + saveIcon));
        save.addActionListener(new FileSaverListener(fileManager));
        save.setToolTipText("Save");
        return save;
    }

    private JMenuItem createSaverAsFileForMenu() {
        JMenuItem saveAs = new JMenuItem("Save As...");
        saveAs.addActionListener(new FileSaverAsListener(fileManager));
        return saveAs;
    }

    private JButton createSaverAsFileForToolbar() {
        JButton saveAs = new JButton(new ImageIcon(pathRes + saveAsIcon));
        saveAs.addActionListener(new FileSaverAsListener(fileManager));
        saveAs.setToolTipText("Save As...");
        return saveAs;
    }

    private JMenu createAboutForMenu() {
        JMenu jMenuAbout = new JMenu("About");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(new AboutListener(this));
        jMenuAbout.add(itemAbout);
        return jMenuAbout;
    }

    private JButton createAboutProgramForToolbar() {
        JButton about = new JButton(new ImageIcon(pathRes + aboutIcon));
        about.addActionListener(new AboutListener(this));
        about.setToolTipText("About");
        return about;
    }

    private JCheckBoxMenuItem createSelectForMenu(){
        itemSelect = new JCheckBoxMenuItem("Select");
        itemSelect.setSelected(parentZones.getEnableSelect());
        itemSelect.addActionListener(e -> parentZones.setEnableSelect(!parentZones.getEnableSelect()));
        return itemSelect;
    }

    private JButton createSelectForToolbar(){
        JButton about = new JButton(new ImageIcon(pathRes + selectIcon));
        about.addActionListener(e -> {
            boolean flag = !parentZones.getEnableSelect();
            parentZones.setEnableSelect(flag);
            itemSelect.setState(flag);
        });
        about.setToolTipText("Select");
        return about;
    }

    private JMenuItem createLeftForMenu(){
        JMenuItem leftItem = new JMenuItem("Left");
        leftItem.addActionListener(e -> parentZones.copyLeft());
        return leftItem;
    }

    private JButton createLeftForToolbar(){
        JButton left = new JButton(new ImageIcon(pathRes + leftIcon));
        left.addActionListener(e -> parentZones.copyLeft());
        left.setToolTipText("Left");
        return left;
    }

    private JMenuItem createRightForMenu(){
        JMenuItem rightItem = new JMenuItem("Right");
        rightItem.addActionListener(e -> parentZones.copyRight());
        return rightItem;
    }

    private JButton createRightForToolbar(){
        JButton right = new JButton(new ImageIcon(pathRes + rightIcon));
        right.addActionListener(e -> parentZones.copyRight());
        right.setToolTipText("Right");
        return right;
    }

    private JMenuItem createNegativeForMenu(){
        JMenuItem negativeItem = new JMenuItem("Negative");
        negativeItem.addActionListener(e -> negativeFilter());
        return negativeItem;
    }

    private JButton createNegativeForToolbar(){
        JButton negative = new JButton(new ImageIcon(pathRes + negativeIcon));
        negative.addActionListener(e -> negativeFilter());
        negative.setToolTipText("Negative");
        return negative;
    }

    private JMenuItem createAquarelleForMenu(){
        JMenuItem aquarelleItem = new JMenuItem("Aquarelle");
        aquarelleItem.addActionListener(e -> aquarelleFilter());
        return  aquarelleItem;
    }

    private JButton createAquarelleForToolbar(){
        JButton aqua = new JButton(new ImageIcon(pathRes + aquaIcon));
        aqua.addActionListener(e -> aquarelleFilter());
        aqua.setToolTipText("Aquarelle");
        return aqua;
    }

    private JMenuItem createDoubleZoomForMenu(){
        JMenuItem doubleItem = new JMenuItem("Double Zoom");
        doubleItem.addActionListener(e -> doubleZoomFilter());
        return  doubleItem;
    }

    private JButton createDoubleZoomForToolbar(){
        JButton doubleZoom = new JButton(new ImageIcon(pathRes + doubleZoomIcon));
        doubleZoom.addActionListener(e -> doubleZoomFilter());
        doubleZoom.setToolTipText("Double Zoom");
        return doubleZoom;
    }

    private JMenuItem createBlackWhiteForMenu(){
        JMenuItem blackWhiteItem = new JMenuItem("Black-White");
        blackWhiteItem.addActionListener(e ->  blackWhiteFilter());
        return blackWhiteItem;
    }

    private JButton createBlackWhiteForToolbar(){
        JButton blacWhite = new JButton(new ImageIcon(pathRes + blackWhiteIcon));
        blacWhite.addActionListener(e -> blackWhiteFilter());
        blacWhite.setToolTipText("Black-White");
        return blacWhite;
    }

    private JMenuItem createEmbassingForMenu(){
        JMenuItem blackWhiteItem = new JMenuItem("Embassing");
        blackWhiteItem.addActionListener(e ->  embassingFilter());
        return blackWhiteItem;
    }

    private JButton createEmbassingForToolbar(){
        JButton blacWhite = new JButton(new ImageIcon(pathRes + embassingIcon));
        blacWhite.addActionListener(e -> embassingFilter());
        blacWhite.setToolTipText("Embassing");
        return blacWhite;
    }

    private JMenuItem createGaussForMenu(){
        JMenuItem blackWhiteItem = new JMenuItem("Gauss");
        blackWhiteItem.addActionListener(e -> gaussFilter());
        return blackWhiteItem;
    }

    private JButton createGaussForToolbar(){
        JButton blacWhite = new JButton(new ImageIcon(pathRes + blurIcon));
        blacWhite.addActionListener(e -> gaussFilter());
        blacWhite.setToolTipText("Gauss");
        return blacWhite;
    }

    private JMenuItem createFloydForMenu(){
        JMenuItem floydItem = new JMenuItem("Floyd");
        floydItem.addActionListener(e -> floydFilter());
        return floydItem;
    }

    private JButton createFloydForToolbar(){
        JButton floyd = new JButton(new ImageIcon(pathRes + floydIcon));
        floyd.addActionListener(e -> floydFilter());
        floyd.setToolTipText("Floyd");
        return floyd;
    }

    private JMenuItem createOrderForMenu(){
        JMenuItem orderItem = new JMenuItem("Order Dither");
        orderItem.addActionListener(e -> orderDither());
        return orderItem;
    }

    private JButton createOrderForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + orderIcon));
        order.addActionListener(e -> orderDither());
        order.setToolTipText("Order Dither");
        return order;
    }

    private JMenuItem createSobelForMenu(){
        JMenuItem orderItem = new JMenuItem("Sobel");
        orderItem.addActionListener(e -> sobel());
        return orderItem;
    }

    private JButton createSobelForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + sobelIcon));
        order.addActionListener(e -> sobel());
        order.setToolTipText("Sobel");
        return order;
    }

    private JMenuItem createRobertsForMenu(){
        JMenuItem orderItem = new JMenuItem("Roberts");
        orderItem.addActionListener(e -> roberts());
        return orderItem;
    }

    private JButton createRobertsForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + robertsIcon));
        order.addActionListener(e -> roberts());
        order.setToolTipText("Roberts");
        return order;
    }

    private JMenuItem createGammaForMenu(){
        JMenuItem orderItem = new JMenuItem("Gamma");
        orderItem.addActionListener(e -> gamma());
        return orderItem;
    }

    private JButton createGammaForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + gammaIcon));
        order.addActionListener(e -> gamma());
        order.setToolTipText("Gamma");
        return order;
    }

    private JMenuItem createDefinitionForMenu(){
        JMenuItem orderItem = new JMenuItem("Definition");
        orderItem.addActionListener(e -> definition());
        return orderItem;
    }

    private JButton createDefinitionForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + definitionIcon));
        order.addActionListener(e -> definition());
        order.setToolTipText("Definition");
        return order;
    }

    private JMenuItem createRotationForMenu(){
        JMenuItem orderItem = new JMenuItem("Rotation");
        orderItem.addActionListener(e -> rotation());
        return orderItem;
    }

    private JButton createRotationForToolbar(){
        JButton order = new JButton(new ImageIcon(pathRes + rotationIcon));
        order.addActionListener(e -> rotation());
        order.setToolTipText("Rotation");
        return order;
    }

    private void negativeFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.negative()).start();
        stateChange = true;
    }

    private void aquarelleFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.aquarelle()).start();
        stateChange = true;
    }

    private void doubleZoomFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.doubleZoom()).start();
        stateChange = true;
    }

    private void embassingFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.embassing()).start();
        stateChange = true;
    }

    private void blackWhiteFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.blackWhite()).start();
        stateChange = true;
    }

    private void gaussFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.gauss()).start();
        stateChange = true;
    }

    private void floydFilter(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        final JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel settingPanel = new JPanel(new GridLayout(1, 3));

        final JSlider rSlider = new JSlider(JSlider.VERTICAL, 2, 256, 2);
        rSlider.setMajorTickSpacing(100);
        rSlider.setPaintTicks(true);
        rSlider.setPaintLabels(true);

        JPanel redPanel = new JPanel();
        redPanel.setBorder(BorderFactory.createTitledBorder("red"));
        redPanel.add(rSlider);
        settingPanel.add(redPanel);

        final JSlider gSlider = new JSlider(JSlider.VERTICAL, 2, 256, 2);
        gSlider.setMajorTickSpacing(100);
        gSlider.setPaintTicks(true);
        gSlider.setPaintLabels(true);

        JPanel greenPanel = new JPanel();
        greenPanel.setBorder(BorderFactory.createTitledBorder("green"));
        greenPanel.add(gSlider);
        settingPanel.add(greenPanel);

        final JSlider bSlider = new JSlider(JSlider.VERTICAL, 2, 256, 2);
        bSlider.setMajorTickSpacing(100);
        bSlider.setPaintTicks(true);
        bSlider.setPaintLabels(true);

        JPanel bluePanel = new JPanel();
        bluePanel.setBorder(BorderFactory.createTitledBorder("blue"));
        bluePanel.add(bSlider);
        settingPanel.add(bluePanel);

        final JButton saveButton = new JButton("Accept");

        saveButton.addActionListener(s ->  dialog.dispose());

        ChangeListener changeListener = change -> {
            if(floydThread != null){
                floydThread.stop();
                floydThread = null;
            }
            int red = rSlider.getValue();

            int green = gSlider.getValue();

            int blue = bSlider.getValue();

            floydThread = new Thread(() -> {
                parentZones.floydStanberg(red, green, blue);
                floydThread = null;
            });
            floydThread.start();
        };

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new Thread(() -> parentZones.copyRight()).start();
            }
        });

        rSlider.addChangeListener(changeListener);
        gSlider.addChangeListener(changeListener);
        bSlider.addChangeListener(changeListener);

        mainPanel.add(settingPanel);
        mainPanel.add(saveButton);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        stateChange = true;
}

    private void orderDither(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }


        new Thread(() -> parentZones.orderDither()).start();
        stateChange = true;
    }

    private void sobel(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        parentZones.sobel(125);

        final JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JSlider rSlider = new JSlider(JSlider.VERTICAL, 1, 255, 125);
        rSlider.setMajorTickSpacing(50);
        rSlider.setPaintTicks(true);
        rSlider.setPaintLabels(true);

        final JButton acceptButton = new JButton("Accept");

        acceptButton.addActionListener(s ->  dialog.dispose());

        ChangeListener changeListener = change ->
            new Thread(() -> parentZones.sobel(rSlider.getValue())).start();

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new Thread(() -> parentZones.copyRight()).start();
            }
        });

        rSlider.addChangeListener(changeListener);

        mainPanel.add(rSlider);
        mainPanel.add(acceptButton);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        stateChange = true;
    }

    private void roberts(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        parentZones.roberts(25);

        final JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JSlider rSlider = new JSlider(JSlider.VERTICAL, 1, 255, 25);
        rSlider.setMajorTickSpacing(50);
        rSlider.setPaintTicks(true);
        rSlider.setPaintLabels(true);

        final JButton acceptButton = new JButton("Accept");

        acceptButton.addActionListener(s ->  dialog.dispose());

        ChangeListener changeListener = change ->
            new Thread(() -> parentZones.roberts(rSlider.getValue())).start();

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new Thread(() -> parentZones.copyRight()).start();
            }
        });

        rSlider.addChangeListener(changeListener);

        mainPanel.add(rSlider);
        mainPanel.add(acceptButton);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        stateChange = true;
    }

    private void gamma(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        parentZones.gamma(10);

        final JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JSlider rSlider = new JSlider(JSlider.VERTICAL, 1, 101, 10);
        rSlider.setMajorTickSpacing(10);
        rSlider.setPaintTicks(true);
        rSlider.setPaintLabels(true);

        final JButton acceptButton = new JButton("Accept");

        acceptButton.addActionListener(s ->  dialog.dispose());

        ChangeListener changeListener = change ->
                new Thread(() -> parentZones.gamma(rSlider.getValue())).start();


        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new Thread(() -> parentZones.copyRight()).start();
            }
        });

        rSlider.addChangeListener(changeListener);

        mainPanel.add(rSlider);
        mainPanel.add(acceptButton);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        stateChange = true;
    }

    private void definition(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        new Thread(() -> parentZones.definition()).start();
        stateChange = true;
    }

    private void rotation(){
        if(!parentZones.isChoiseFragment()){
            JOptionPane.showMessageDialog(MainView.this, "Zone B is empty!");
            return;
        }

        final JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        final JButton acceptButton = new JButton("Accept");
        JTextField rotationField = new JTextField();
        acceptButton.addActionListener(s -> {
            String angleStr = rotationField.getText();
            if(angleStr.isEmpty()){
                return;
            }
            int angle = Integer.parseInt(angleStr);
            if(angle < - 180 || angle > 180){
                return;
            }
            new Thread(() -> parentZones.rotation(angle)).start();
            dialog.dispose();
        });

        rotationField.setName("Angle");
        //PlainDocument plainDocW = (PlainDocument) rotationField.getDocument();
        //plainDocW.setDocumentFilter(new IntFilter());

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new Thread(() -> parentZones.copyRight()).start();
            }
        });


        mainPanel.add(rotationField);
        mainPanel.add(acceptButton);

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        stateChange = true;
    }



    private class MyWindowsListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            checkAndSave();
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

    private class IntFilter extends DocumentFilter {
        private static final String mask = "[+-]?[0-9]+";

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

    public boolean checkAndSave(){
        if(stateChange) {
            int result = JOptionPane.showConfirmDialog(MainView.this,
                    "Do you want to save filtered image?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                FileSaverListener fileSaverListener = new FileSaverListener(fileManager);
                fileSaverListener.saveState();
                return true;
            }
        }
        return false;
    }

    public ImageBmp getFinalImage() {
        return parentZones.getFinalImage();
    }

    public static void main(String[] args) {
        MainView mainView = new MainView("Filter");
    }
}
