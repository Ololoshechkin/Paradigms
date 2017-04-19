package expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Divide extends AbstractBinary {

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {
        if (rightValue == 0) {
            throw new DBZException();
        }
        if (leftValue == Integer.MIN_VALUE && rightValue == -1) {
            throw new OverflowException();
        }
    }

    public Divide() {
    }

    public Divide(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }

    @Override
    public Divide getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Divide(leftOperand, rightOperand);
    }

    protected String getOperatorName() {
        return "/";
    }

}
