package expressions;

/**
 * Created by Vadim on 27.03.17.
 */
public class Negate extends AbstractUnary {

    @Override
    protected void check(int value) throws OverflowException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Negate() {
    }

    public Negate(TripleExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return -value;
    }

    @Override
    public Negate getNewInstance(TripleExpression operand) {
        return new Negate(operand);
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
