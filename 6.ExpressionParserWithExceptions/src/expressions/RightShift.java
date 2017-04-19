package expressions;
/**
 * Created by Vadim on 27.03.17.
 */
public class RightShift extends AbstractBinary {

    @Override
    protected void check(int leftValue, int rightValue) throws EvaluationException {
    }

    public RightShift() {
    }

    public RightShift(TripleExpression leftOperand, TripleExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">>";
    }

    @Override
    protected int operator(int leftValue, int rightValue) {
        return leftValue >> rightValue;
    }

    @Override
    public RightShift getNewInstance(TripleExpression leftOperand, TripleExpression rightOperand) {
        return new RightShift(leftOperand, rightOperand);
    }

}
