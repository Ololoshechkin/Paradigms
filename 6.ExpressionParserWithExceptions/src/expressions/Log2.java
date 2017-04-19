package expressions;

/**
 * Created by Vadim on 28.03.17.
 */
public class Log2 extends AbstractUnary {

    @Override
    protected void check(int value) throws EvaluationException {
        if (value == 0) {
            throw new EvaluationException("log2(0)");
        }
        if (value < 0) {
            throw new EvaluationException("log2(-1)");
        }
    }

    public Log2() {
    }

    public Log2(TripleExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        int l = 0, r = 31, m;
        while (r != l + 1) {
            m = (l + r) / 2;
            if ((1 << m) <= value) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    @Override
    public Log2 getNewInstance(TripleExpression operand) {
        return new Log2(operand);
    }

    @Override
    protected String getOperatorName() {
        return "log2";
    }

}
