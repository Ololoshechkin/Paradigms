package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class Abs extends AbstractUnary {

    public Abs() {
    }

    public Abs(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return Math.abs(value);
    }

    @Override
    public double operator(double value) {
        return Math.abs(value);
    }

    @Override
    public Abs getNewInstance(SomeExpression operand) {
        return new Abs(operand);
    }

    @Override
    protected String getOperatorName() {
        return "abs";
    }

}
