package expressions.parser;

import expressions.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vadim on 25.03.17.
 */

public class ExpressionParser implements Parser {

    private OperatorParser baseOperatorParser;

    public ExpressionParser() {
        BinaryOperatorParser mulParser = new BinaryOperatorParser(
                new Multiply(), new Divide());
        BinaryOperatorParser sumParser = new BinaryOperatorParser(
                mulParser,
                new Add(), new Subtract()
        );
        BinaryOperatorParser minParser = new BinaryOperatorParser(
                sumParser,
                new Min(), new Max()
        );
        AnyUnaryParser anyUnaryParser = new AnyUnaryParser(
                minParser,
                new Negate(), new Log2(), new Abs(), new Square(), new Pow2(), new SquareRoot()
        );
        mulParser.nextPriorityOperationParser = anyUnaryParser;
        baseOperatorParser = minParser;
    }

    @Override
    public TripleExpression parse(String expression) throws ExpressionParserException {
        ParserState state = new ParserState(expression, 0);
        TripleExpression expr = baseOperatorParser.parse(state);
        if (!state.isLastState()) {
            throw new WrongOperationOrderException("binary operator", state.getIdentifier(true), state.position);
        }
        return expr;
    }

}
