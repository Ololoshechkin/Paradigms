package expressions;
/**
 * Created by Vadim on 27.03.17.
 */
public class Square extends AbstractUnary {

    @Override
    protected void check(int value) throws OverflowException {
        if (value > Integer.MAX_VALUE / value) {
            throw new OverflowException();
        }
    }

    public Square() {
    }

    public Square(TripleExpression operand) {
        super(operand);
    }

    @Override
    public int operator(int value) {
        return value * value;
    }

    @Override
    public Square getNewInstance(TripleExpression operand) {
        return new Square(operand);
    }

    @Override
    protected String getOperatorName() {
        return "square";
    }

}
