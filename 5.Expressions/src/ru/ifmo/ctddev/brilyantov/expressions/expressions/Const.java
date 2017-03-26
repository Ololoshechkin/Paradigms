package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Const implements SomeExpression {

    private double value;

    public Const(int value) {
        this((double)value);
    }

    public Const(double value) {
        this.value = (int)value;
    }

    @Override
    public int evaluate(int x) {
        return (int)value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)value;
    }

    public String toString() {
        return (value == (double) (int) value ? String.valueOf((int) value) : String.valueOf(value));
    }

}
