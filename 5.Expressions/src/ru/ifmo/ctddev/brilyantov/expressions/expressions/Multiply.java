package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Multiply extends AbstractBinary {

    public Multiply() {
    }

    public Multiply(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return leftValue * rightValue;
    }

    @Override
    public Multiply getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new Multiply(leftOperand, rightOperand);
    }

    protected String getOperatorName() {
        return "*";
    }

}
