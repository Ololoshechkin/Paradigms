package expressions;

/**
 * Created by Vadim on 19.03.17.
 */

public class Multiply extends AbstractBinary {

    private int sign(int x) {
        return (x >= 0 ? 1 : -1);
    }

    private int abs(int x) {
        return x < 0 ? -x : x;
    }

    @Override
    protected void check(int leftValue, int rightValue) throws OverflowException {
        if (leftValue == 0 || rightValue == 0) {
            return;
        }
        if (leftValue > 0) {
            if (rightValue > 0) {
                if (leftValue > Integer.MAX_VALUE / rightValue) {
                    throw new OverflowException();
                }
            } else {
                if (rightValue < Integer.MIN_VALUE / leftValue) {
                    throw new OverflowException();
                }
            }
        } else {
            if (rightValue > 0) {
                if (leftValue < Integer.MIN_VALUE / rightValue) {
                    throw new OverflowException();
                }
            } else {
                if (leftValue < Integer.MAX_VALUE / rightValue) {
                    throw new OverflowException();
                }
            }
        }
    }

    public Multiply() {
    }

    public Multiply(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }

    @Override
    public Multiply getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Multiply(leftOperand, rightOperand);
    }

    protected String getOperatorName() {
        return "*";
    }

}
