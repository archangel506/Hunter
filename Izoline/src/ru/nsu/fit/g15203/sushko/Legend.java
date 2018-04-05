package ru.nsu.fit.g15203.sushko;

public final class Legend extends Function{
    private ConfigField configField = new ConfigField();

    {
        configField.setA(0);
        configField.setB(10);
        configField.setC(0);
        configField.setD(0);
        configField.setMax(0);
        configField.setMin(-1 * configField.getB());
    }

    public double f(double x, double y){
        return -x;
    }

    @Override
    public ConfigField getConfigField() {
        return configField;
    }
}
