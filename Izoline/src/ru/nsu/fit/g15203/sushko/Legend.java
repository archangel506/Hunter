package ru.nsu.fit.g15203.sushko;

public class Legend {
    private ConfigField configField = new ConfigField();

    {
        findMinAndMax();
    }

    private void findMinAndMax() {
        configField.setA(0);
        configField.setB(0);
        configField.setC(0);
        configField.setD(0);
    }

    public double getValue(double x, double y){
        return -y;
    }
}
