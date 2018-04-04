package ru.nsu.fit.g15203.sushko;

import lombok.Getter;

import java.util.Arrays;

public abstract class Function {
    public abstract double f(double x, double y);
    public abstract ConfigField getConfigField();
}