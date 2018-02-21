import ru.nsu.fit.g15203.sushko.field.FigurePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI extends JFrame{
    private static final String pathRes = "FIT_15203_Sushko_Denis_Life_Data/icons/";
    private static final String nextIcon = "next.gif";
    private static final String saveIcon = "save.gif";
    private static final String newIcon = "new.gif";
    private static final String openIcon = "open.gif";
    private static final String resetGameIcon = "resetGame.gif";
    private static final String aboutIcon = "about.gif";
    private static final String playIcon = "play.gif";
    private static final String parametersIcon = "parameters.gif";
    private static final String aboutDialogIcon = "aboutDialog.jpg";

    private static final int MINIMUM_WIDTH = 800;
    private static final int MINIMUM_HEIGHT = 600;

    private JMenuBar menuBar = new JMenuBar();
    private FigurePanel figurePanel;
    private JToolBar toolBar;

    public SimpleGUI(String name){
        super(name);
        figurePanel = new FigurePanel();
        setLayout(new BorderLayout());
        add(figurePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolBar = new JToolBar();
//        MatteBorder matteBorder = new MatteBorder(1, 0, 0, 0, Color.BLACK);
//        toolBar.setBorder(matteBorder);
//        menuBar.setBorder(matteBorder);
        toolBar.setFloatable(false);

        JButton newB = new JButton(new ImageIcon(pathRes + newIcon));
        newB.setToolTipText("New");
        toolBar.add(newB);

        JButton open = new JButton(new ImageIcon(pathRes + openIcon));
        open.setToolTipText("Open");
        toolBar.add(open);

        JButton save = new JButton(new ImageIcon(pathRes + saveIcon));
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
        toolBar.add(param);

        toolBar.addSeparator();

        JButton about = new JButton(new ImageIcon(pathRes + aboutIcon));
        //about.setPressedIcon();
        AboutActionListener aboutActionListener = new AboutActionListener(this);
        about.addActionListener(aboutActionListener);
        about.setToolTipText("About");
        toolBar.add(about);

        add(toolBar, BorderLayout.NORTH);

        JMenu jMenuFile = new JMenu("File");
        JMenuItem itemNew = new JMenuItem("New");
        jMenuFile.add(itemNew);
        JMenuItem itemOpen = new JMenuItem("Open");
        jMenuFile.add(itemOpen);
        JMenuItem itemSave = new JMenuItem("Save");
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
        jMenuGame.add(itemParameters);
        menuBar.add(jMenuGame);

        JMenu jAbout = new JMenu("About");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(aboutActionListener);
        jAbout.add(itemAbout);
        menuBar.add(jAbout);

        //setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(MINIMUM_WIDTH, 800));
        JScrollPane pane = new JScrollPane(figurePanel);
        add(pane);

        setJMenuBar(menuBar);
        pack();
        setVisible(true);
    }

    private class AboutActionListener implements ActionListener{
        private JFrame frame;

        public AboutActionListener(JFrame frame){
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



    public static void main(String[] args) {
        JFrame jFrame = new SimpleGUI("ru/nsu/fit/g15203/sushko/hex");
    }
}