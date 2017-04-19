package expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Subtract extends AbstractBinary {

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {
        if (rightValue == 0) {
            return;
        }
        if (rightValue < 0 && leftValue - rightValue <= leftValue) {
            throw new OverflowException();
        }
        if (rightValue > 0 && leftValue - rightValue >= leftValue) {
            throw new OverflowException();
        }
    }

    public Subtract() {
    }

    public Subtract(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }

    @Override
    public Subtract getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Subtract(leftOperand, rightOperand);
    }


    protected String getOperatorName() {
        return "-";
    }

}
