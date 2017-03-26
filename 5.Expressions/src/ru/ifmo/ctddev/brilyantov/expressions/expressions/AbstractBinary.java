package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 19.03.17.
 */

public abstract class AbstractBinary implements SomeExpression {

    private SomeExpression leftOperand;
    private SomeExpression rightOperand;

    protected AbstractBinary(SomeExpression leftOperand, SomeExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public int evaluate(int x) {
        return operator(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    public double evaluate(double x) {
        return operator(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return operator(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
    }

    public String toString() {
        return "(" + leftOperand.toString() + getOperatorName() + rightOperand.toString() + ")";
    }

    protected abstract String getOperatorName();

    protected abstract int operator(int leftValue, int rightValue);

    protected abstract double operator(double leftValue, double rightValue);


}
