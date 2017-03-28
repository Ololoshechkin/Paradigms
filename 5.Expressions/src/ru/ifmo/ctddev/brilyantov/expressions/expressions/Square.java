package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class Square extends AbstractUnary {

    public Square() {
    }

    public Square(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return value * value;
    }

    @Override
    public double operator(double value) {
        return value * value;
    }

    @Override
    public Square getNewInstance(SomeExpression operand) {
        return new Square(operand);
    }

    @Override
    protected String getOperatorName() {
        return "square";
    }

}
