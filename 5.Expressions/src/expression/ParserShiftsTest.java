package expression;

import static expression.Util.*;

/**
 * @author Georgiy Korneev
 */
public class ParserShiftsTest extends ParserTest {
    protected ParserShiftsTest() {
        levels.add(0, list(
                op("<<", (a, b) -> (long) (a.intValue() << b.intValue())),
                op(">>", (a, b) -> (long) (a.intValue() >> b.intValue()))
        ));

        tests.addAll(list(
                op("1 << 5 + 3", (x, y, z) -> 256L),
                op("x + y << z", (x, y, z) -> x + y << z),
                op("x * y << z", (x, y, z) -> x * y << z),
                op("x << y << z", (x, y, z) -> x << y << z),
                op("1024 >> 5 + 3", (x, y, z) -> 4L),
                op("x + y >> z", (x, y, z) -> x + y >> z),
                op("x * y >> z", (x, y, z) -> x * y >> z),
                op("x >> y >> z", (x, y, z) -> x >> y >> z)
        ));
    }

    public static void main(final String[] args) {
        checkAssert(ParserShiftsTest.class);
        new ParserShiftsTest().test();
    }
}