package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class Mod extends AbstractBinary {

    public Mod() {
    }

    public Mod(SomeExpression leftOperand, SomeExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "mod";
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue % rightValue;
    }

    @Override
    protected double operator(double leftValue, double rightValue) {
        return leftValue % rightValue;
    }

    @Override
    public Mod getNewInstance(SomeExpression leftOperand, SomeExpression rightOperand) {
        return new Mod(leftOperand, rightOperand);
    }

}
