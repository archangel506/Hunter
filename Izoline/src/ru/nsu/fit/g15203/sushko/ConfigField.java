package ru.nsu.fit.g15203.sushko;

import lombok.Getter;
import lombok.Setter;

public final class ConfigField {
    @Getter @Setter private double max;
    @Getter @Setter private double min;

    @Getter @Setter private double a;
    @Getter @Setter private double b;
    @Getter @Setter private double c;
    @Getter @Setter private double d;

    @Getter @Setter private int width;
    @Getter @Setter private int height;

    public ConfigField(int width, int height, double a, double b, double c, double d){
        this.width = width;
        this.height = height;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public ConfigField(){}

}
