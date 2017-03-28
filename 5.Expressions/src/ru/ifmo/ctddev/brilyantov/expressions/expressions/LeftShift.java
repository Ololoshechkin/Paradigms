package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class LeftShift extends AbstractBinary {

    public LeftShift() {
    }

    public LeftShift(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "<<";
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue << rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return (int) leftValue << (int) rightValue;
    }

    @Override
    public LeftShift getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new LeftShift(leftOperand, rightOperand);
    }

}
