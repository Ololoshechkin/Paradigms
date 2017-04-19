package expressions;

/**
 * Created by Vadim on 05.04.17.
 */
public class Max extends AbstractBinary {

    public Max() {
    }

    public Max(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {
    }

    @Override
    protected String getOperatorName() {
        return "max";
    }

    @Override
    protected int operator(int leftValue, int rightValue) throws EvaluationException {
        return (leftValue > rightValue ? leftValue : rightValue);
    }

    @Override
    public Max getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Max(leftOperand, rightOperand);
    }
}
