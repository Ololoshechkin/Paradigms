package expressions;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class DoubleExpressionTest extends ExpressionTest {
    public static void main(final String[] args) {
        new DoubleExpressionTest().run();
    }

    @Override
    protected void test() {
        super.test();

        testExpression("10", (DoubleExpression) new Const(10), x -> 10);
        testExpression("x", (DoubleExpression) new Variable("x"), x -> x);
        testExpression("x+2", (DoubleExpression) new Add(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("2-x", (DoubleExpression) new Subtract(new Const(2), new Variable("x")), x -> 2 - x);
        testExpression("3*x", (DoubleExpression) new Multiply(new Const(3), new Variable("x")), x -> 3*x);
        testExpression("x/-2", (DoubleExpression) new Divide(new Variable("x"), new Const(-2)), x -> -x / 2);
        testExpression(
                "x*x+(x-1)/10",
                (DoubleExpression) new Add(
                        new Multiply(new Variable("x"), new Variable("x")),
                        new Divide(new Subtract(new Variable("x"), new Const(1)), new Const(10))
                ),
                x -> x * x + (x - 1) / 10
        );
        testExpression("x*-1_000_000_000", (DoubleExpression) new Multiply(new Variable("x"), new Const(-1_000_000_000)), x -> x * -1_000_000_000.0);
        testExpression("10/x", (DoubleExpression) new Divide(new Const(10), new Variable("x")), x -> 10.0 / x);
        testExpression("x/x", (DoubleExpression) new Divide(new Variable("x"), new Variable("x")), x -> x / x);
    }

    private void testExpression(final String description, final DoubleExpression actual, final DoubleExpression expected) {
        System.out.println("Testing " + description);
        ops(description.length());
        for (int i = 0; i < 10; i++) {
            check(i, actual, expected);
            check(-i, actual, expected);
        }
    }

    private void check(final int x, final DoubleExpression actual, final DoubleExpression expected) {
        assertEquals(String.format("f(%d)", x), actual.evaluate(x), expected.evaluate(x));
    }
}