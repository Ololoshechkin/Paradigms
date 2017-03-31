package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.AbstractUnary;
import ru.ifmo.ctddev.brilyantov.expressions.expressions.Const;
import ru.ifmo.ctddev.brilyantov.expressions.expressions.SomeExpression;
import ru.ifmo.ctddev.brilyantov.expressions.expressions.Variable;

import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Vadim on 28.03.17.
 */

public class AnyUnaryParser implements OperatorParser {

    public static final Const ZERO = new Const(0);
    public BinaryOperatorParser baseOperationParser;
    public AbstractUnary[] expressionInstances;

    public AnyUnaryParser(BinaryOperatorParser baseOperationParser,
                   AbstractUnary... expressionInstances) {
        this.baseOperationParser = baseOperationParser;
        this.expressionInstances = expressionInstances;
    }

    private boolean isConst(String s) {
        return s.matches("\\d+");
    }

    private boolean isVar(String s) {
        return s.matches("[xyz]");
    }

    private boolean isOpenBracket(String s) {
        return s.equals("(");
    }

    @Override
    public SomeExpression parse(Deque<String> expressionUnits) {
        String currentUnit = expressionUnits.pollLast();
        if (isConst(currentUnit)) {
            return new Const(Integer.parseUnsignedInt(currentUnit));
        }
        if (isVar(currentUnit)) {
            return new Variable(currentUnit);
        }
        if (isOpenBracket(currentUnit)) {
            SomeExpression answer = baseOperationParser.parse(expressionUnits);
            expressionUnits.pollLast();
            return answer;
        }
        for (AbstractUnary unary : expressionInstances) {
            if (unary.toString().equals(currentUnit)) {
                return unary.getNewInstance(parse(expressionUnits));
            }
        }
        return ZERO; // TODO throw exception
    }

}
