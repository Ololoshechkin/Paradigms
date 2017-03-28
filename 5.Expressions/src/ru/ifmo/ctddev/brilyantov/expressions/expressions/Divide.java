package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Divide extends AbstractBinary {

    public Divide() {
    }

    public Divide(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return leftValue / rightValue;
    }

    @Override
    public Divide getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new Divide(leftOperand, rightOperand);
    }


    protected String getOperatorName() {
        return "/";
    }

}
