package expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public abstract class AbstractUnary implements TripleExpression {

    private TripleExpression operand;

    abstract protected void check(int value) throws EvaluationException;

    public AbstractUnary() {
        this(null);
    }

    public AbstractUnary(TripleExpression operand) {
        this.operand = operand;
    }

    public int evaluate(int x, int y, int z) throws EvaluationException {
        int value = operand.evaluate(x, y, z);
        check(value);
        return operator(value);
    }

    public abstract int operator(int value);

    public abstract AbstractUnary getNewInstance(TripleExpression operand);

    public String toString() {
        if (operand == null) {
            return getOperatorName();
        }
        return getOperatorName() + "(" + operand.toString() + ")";
    }

    protected abstract String getOperatorName();

}
