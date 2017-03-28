package ru.ifmo.ctddev.brilyantov.expressions.expressions;

import java.awt.*;

/**
 * Created by Vadim on 28.03.17.
 */
public class Pow extends AbstractBinary {

    public Pow() {
    }

    public Pow(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "**";
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return (int) Math.pow(leftValue, rightValue);
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return Math.pow(leftValue, rightValue);
    }

    @Override
    public Pow getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new Pow(leftOperand, rightOperand);
    }

}
