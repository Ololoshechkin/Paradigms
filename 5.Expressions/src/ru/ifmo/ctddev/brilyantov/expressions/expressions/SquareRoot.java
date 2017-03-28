package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 28.03.17.
 */
public class SquareRoot extends AbstractUnary {

    public SquareRoot() {
    }

    public SquareRoot(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return (int) Math.sqrt((double) value);
    }

    @Override
    public double operator(double value) {
        return Math.sqrt(value);
    }

    @Override
    public SquareRoot getNewInstance(SomeExpression operand) {
        return new SquareRoot(operand);
    }

    @Override
    protected String getOperatorName() {
        return "sqrt";
    }
}
