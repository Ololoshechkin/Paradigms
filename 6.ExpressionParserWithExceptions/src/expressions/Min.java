package expressions;

/**
 * Created by Vadim on 05.04.17.
 */
public class Min extends AbstractBinary {

    public Min() {
    }

    public Min(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {

    }

    @Override
    protected String getOperatorName() {
        return "min";
    }

    @Override
    protected int operator(int leftValue, int rightValue) throws EvaluationException {
        return (leftValue < rightValue ? leftValue : rightValue);
    }

    @Override
    public Min getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Min(leftOperand, rightOperand);
    }
}
