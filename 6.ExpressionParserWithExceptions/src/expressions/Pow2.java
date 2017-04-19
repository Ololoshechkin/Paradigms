package expressions;

/**
 * Created by Vadim on 05.04.17.
 */
public class Pow2 extends AbstractUnary {

    public Pow2() {
    }

    public Pow2(TripleExpression expression) {
        super(expression);
    }

    @Override
    protected void check(int n) throws EvaluationException {
        if (n >= 31) {
            throw new OverflowException();
        }
        if (n < 0) {
            throw new EvaluationException("pow2^-1");
        }
    }

    @Override
    public int operator(int n) {
        return (1 << n);
    }

    @Override
    public Pow2 getNewInstance(TripleExpression operand) {
        return new Pow2(operand);
    }

    @Override
    protected String getOperatorName() {
        return "pow2";
    }
}
