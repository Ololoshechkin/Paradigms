package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class UnaryMinus extends AbstractUnary {

    public UnaryMinus() {
    }

    public UnaryMinus(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return -value;
    }

    @Override
    public double operator(double value) {
        return -value;
    }

    @Override
    public UnaryMinus getNewInstance(SomeExpression operand) {
        return new UnaryMinus(operand);
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
