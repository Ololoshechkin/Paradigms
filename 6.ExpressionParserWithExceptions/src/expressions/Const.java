package expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Const implements TripleExpression {

    private int value;

    public Const(int value) {
        this((double)value);
    }

    public Const(double value) {
        this.value = (int)value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}
