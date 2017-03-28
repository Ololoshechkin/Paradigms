package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 28.03.17.
 */
public class Log extends AbstractUnary {

    public Log() {
    }

    public Log(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return (int) Math.log((double) value);
    }

    @Override
    public double operator(double value) {
        return Math.log(value);
    }

    @Override
    public Log getNewInstance(SomeExpression operand) {
        return new Log(operand);
    }

    @Override
    protected String getOperatorName() {
        return "log";
    }

}
