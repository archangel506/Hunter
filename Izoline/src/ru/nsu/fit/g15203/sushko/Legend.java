package ru.nsu.fit.g15203.sushko;

import lombok.Getter;

public final class Legend extends Function{
    @Getter private ConfigField configField = new ConfigField();

    {
        configField.setA(0);
        configField.setB(0);
        configField.setC(0);
        configField.setD(10);
        configField.setMin(0);
        configField.setMax(-1 * configField.getD());
    }



    public double f(double x, double y){
        return -y;
    }
}
