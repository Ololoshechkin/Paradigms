package expressions.parser;

import expressions.TripleExpression;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vadim on 27.03.17.
 */

public interface OperatorParser {

    TripleExpression parse(ParserState state) throws ExpressionParserException;

}
