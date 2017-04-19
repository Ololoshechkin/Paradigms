package expressions;

/**
 * Created by Vadim on 19.03.17.
 */
public class Variable implements TripleExpression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (name.equals("x") ? x
                : name.equals("y") ? y
                : name.equals("z") ? z
                : 0
        );
    }

    public String toString() {
        return name;
    }

}