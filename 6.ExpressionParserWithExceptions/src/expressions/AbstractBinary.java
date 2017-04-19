package expressions;

/**
 * Created by Vadim on 19.03.17.
 */

public abstract class AbstractBinary implements TripleExpression {

    private TripleExpression leftOperand;
    private TripleExpression rightOperand;

    abstract protected void check(int leftValue, int rightValue) throws EvaluationException;

    public AbstractBinary() {
        this(null, null);
    }

    public AbstractBinary(TripleExpression leftOperand, TripleExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public int evaluate(int x, int y, int z) throws EvaluationException {
        int leftValue = leftOperand.evaluate(x, y, z);
        int rightValue = rightOperand.evaluate(x, y, z);
        check(leftValue, rightValue);
        return operator(leftValue, rightValue);
    }

    public String toString() {
        if (leftOperand == null && rightOperand == null) {
            return getOperatorName();
        }
        return "(" + leftOperand.toString() + ")" + getOperatorName() + "(" + rightOperand.toString() + ")";
    }

    protected abstract String getOperatorName();

    protected abstract int operator(int leftValue, int rightValue) throws EvaluationException;

    public abstract AbstractBinary getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand);

}
