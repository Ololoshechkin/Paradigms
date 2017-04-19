package ru.ifmo.ctddev.brilyantov.expressions.expressions;

/**
 * Created by Vadim on 28.03.17.
 */
public class SquareRoot extends AbstractUnary {

    public SquareRoot() {
    }

    public SquareRoot(SomeExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        int l = 0, r = 46341, m;
        while (r != l + 1) {
            m = (l + r) / 2;
            if (m * m <= value) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    @Override
    public double operator(double value) {
        return Math.sqrt(value);
    }

    @Override
    public SquareRoot getNewInstance(SomeExpression operand) {
        return new SquareRoot(operand);
    }

    @Override
    protected String getOperatorName() {
        return "sqrt";
    }
}
