package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Add extends AbstractBinary {

    public Add() {
    }

    public Add(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return leftValue + rightValue;
    }

    @Override
    public Add getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new Add(leftOperand, rightOperand);
    }

    protected String getOperatorName() {
        return "+";
    }

}
