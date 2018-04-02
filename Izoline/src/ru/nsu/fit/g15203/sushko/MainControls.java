package ru.nsu.fit.g15203.sushko;

import javax.swing.*;

public final class MainControls {
    private FileParser parser;
    private DrawerFunction drawerFunction;

    public MainControls(final JFrame frame){
       setPicture(frame);
    }

    public void setPicture(final JFrame frame){
        parser = new FileParser(frame);
        drawerFunction = new DrawerFunction(parser.getConfigField(), parser.getFuncColors(), parser.getIzolineColor());

    }
}
