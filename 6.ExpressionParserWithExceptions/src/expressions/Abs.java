package expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class Abs extends AbstractUnary {

    @Override
    protected void check(int value) throws EvaluationException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Abs() {
    }

    public Abs(TripleExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return value >= 0 ? value : -value;
    }

    @Override
    public Abs getNewInstance(TripleExpression operand) {
        return new Abs(operand);
    }

    @Override
    protected String getOperatorName() {
        return "abs";
    }

}
