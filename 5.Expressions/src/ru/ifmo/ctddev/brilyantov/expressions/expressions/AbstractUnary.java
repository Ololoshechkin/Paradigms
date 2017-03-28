package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public abstract class AbstractUnary implements SomeExpression {

    private SomeExpression operand;

    public AbstractUnary() {
        this(null);
    }

    public AbstractUnary(SomeExpression operand) {
        this.operand = operand;
    }

    public int evaluate(int x) {
        return operator(operand.evaluate(x));
    }

    public double evaluate(double x) {
        return operator(operand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return operator(operand.evaluate(x, y, z));
    }

    public abstract int operator(int value);

    public abstract double operator(double value);

    public abstract AbstractUnary getNewInstance(SomeExpression operand);

    public String toString() {
        if (operand == null) {
            return getOperatorName();
        }
        return getOperatorName() + "(" + operand.toString() + ")";
    }

    protected abstract String getOperatorName();

}
