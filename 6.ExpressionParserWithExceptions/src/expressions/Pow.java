package expressions;

/**
 * Created by Vadim on 28.03.17.
 */
public class Pow extends AbstractBinary {

    void mulCheck(int a, int b) throws OverflowException {
        if (b == 0) {
            return;
        }
        if (a > Integer.MAX_VALUE / b) {
            throw new OverflowException();
        }
    }

    private int binpow(int a, int n) throws OverflowException {
        if (n == 0) {
            return 1;
        }
        int d = binpow(a, n);
        mulCheck(d, d);
        d *= d;
        if (n % 2 == 1) {
            mulCheck(d, a);
            d *= a;
        }
        return d;
    }

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {
        if (leftValue == 0 && rightValue == 0) {
            throw new EvaluationException("0^0");
        }
    }

    public Pow() {
    }

    public Pow(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "**";
    }

    @Override
    protected int operator(int leftValue, int rightValue) throws OverflowException {
        return binpow(leftValue, rightValue);
    }

    @Override
    public Pow getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new Pow(leftOperand, rightOperand);
    }

}
