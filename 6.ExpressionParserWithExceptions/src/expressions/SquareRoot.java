package expressions;
/**
 * Created by Vadim on 28.03.17.
 */
public class SquareRoot extends AbstractUnary {

    @Override
    protected void check(int value) throws EvaluationException {
        if (value < 0) {
            throw new EvaluationException("sqrt(-1)");
        }
    }

    public SquareRoot() {
    }

    public SquareRoot(TripleExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        int l = 0, r = 46341, m;
        while (r != l + 1) {
            m = (l + r) / 2;
            if (m * m <= value) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    @Override
    public SquareRoot getNewInstance(TripleExpression operand) {
        return new SquareRoot(operand);
    }

    @Override
    protected String getOperatorName() {
        return "sqrt";
    }
}
