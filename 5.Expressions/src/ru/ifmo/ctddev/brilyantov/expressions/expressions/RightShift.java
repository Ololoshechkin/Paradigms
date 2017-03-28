package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class RightShift extends AbstractBinary {

    public RightShift() {
    }

    public RightShift(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">>";
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue >> rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return (int) leftValue >> (int) rightValue;
    }

    @Override
    public RightShift getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new RightShift(leftOperand, rightOperand);
    }

}
