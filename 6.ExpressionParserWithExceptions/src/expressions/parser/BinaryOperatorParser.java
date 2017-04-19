package expressions.parser;

import expressions.AbstractBinary;
import expressions.TripleExpression;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vadim on 27.03.17.
 */

public class BinaryOperatorParser implements OperatorParser {

    public OperatorParser nextPriorityOperationParser;
    public AbstractBinary[] expressionInstances;

    public BinaryOperatorParser(AbstractBinary... expressionInstances) {
        this(null, expressionInstances);
    }

    public BinaryOperatorParser(OperatorParser nextPriorityOperationParser, AbstractBinary... expressionInstances) {
        this.nextPriorityOperationParser = nextPriorityOperationParser;
        this.expressionInstances = expressionInstances;
        Arrays.sort(expressionInstances, Comparator.comparingInt(op -> op.toString().length()).reversed());
    }

    @Override
    public TripleExpression parse(ParserState state) throws ExpressionParserException {
        TripleExpression expr = nextPriorityOperationParser.parse(state);
        while (!state.isLastState()) {
            state.skipWhitespaces();
            boolean didFindOperator = false;
            for (AbstractBinary binary : expressionInstances) {
                if (state.canMove(binary.toString())) {
                    state.move(binary.toString());
                    state.checkAfter(binary.toString(), true, "min", "max");
                    didFindOperator = true;
                    expr = binary.getNewInstance(expr, nextPriorityOperationParser.parse(state));
                    break;
                }
            }
            if (!didFindOperator) {
                break;
            }
        }
        return expr;
    }
}
