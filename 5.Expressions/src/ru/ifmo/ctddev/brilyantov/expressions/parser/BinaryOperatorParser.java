package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.AbstractBinary;
import ru.ifmo.ctddev.brilyantov.expressions.expressions.SomeExpression;

import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Vadim on 27.03.17.
 */
class BinaryOperatorParser implements OperatorParser {

    OperatorParser nextPriorityOperationParser;
    AbstractBinary[] expressionInstances;

    boolean isMentionedOperator(String s) {
        for (AbstractBinary operator : expressionInstances) {
            if (s.equals(operator.toString())) {
                return true;
            }
        }
        return false;
    }

    BinaryOperatorParser(AbstractBinary[] expressionInstances) {
        this(null, expressionInstances);
    }

    BinaryOperatorParser(OperatorParser nextPriorityOperationParser, AbstractBinary[] expressionInstances) {
        this.nextPriorityOperationParser = nextPriorityOperationParser;
        this.expressionInstances = expressionInstances;
    }

    @Override
    public SomeExpression parse(Deque<String> expressionUnits) {
        SomeExpression expr = nextPriorityOperationParser.parse(expressionUnits);
        while (!expressionUnits.isEmpty() && isMentionedOperator(expressionUnits.peekLast())) {
            String currentUnit = expressionUnits.peekLast();
            for (AbstractBinary binary : expressionInstances) {
                if (binary.toString().equals(currentUnit)) {
                    expressionUnits.pollLast();
                    expr = binary.getNewInstance(expr, nextPriorityOperationParser.parse(expressionUnits));
                }
            }
        }
        return expr;
    }

}
