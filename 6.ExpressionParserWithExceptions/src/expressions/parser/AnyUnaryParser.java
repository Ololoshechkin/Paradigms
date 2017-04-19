package expressions.parser;

import expressions.*;
import expressions.AbstractUnary;
import expressions.TripleExpression;

import java.util.Arrays;

/**
 * Created by Vadim on 28.03.17.
 */

public class AnyUnaryParser implements OperatorParser {

    public BinaryOperatorParser baseOperationParser;
    public AbstractUnary[] expressionInstances;

    public AnyUnaryParser(BinaryOperatorParser baseOperationParser,
                   AbstractUnary... expressionInstances) {
        this.baseOperationParser = baseOperationParser;
        this.expressionInstances = expressionInstances;
        Arrays.sort(expressionInstances,
                (AbstractUnary op1, AbstractUnary op2) -> (op1.toString().length() - op2.toString().length()));
    }

    private boolean isVar(Character c) {
        return c == 'x' || c == 'y' || c == 'z';
    }

    private boolean isOpenBracket(Character c) {
        return c == '(';
    }

    @Override
    public TripleExpression parse(ParserState state) throws ExpressionParserException {
        Character c = state.skipWhitespacesAndGet();
        if (c == null) {
            throw new EndOfLineException("any unary operator, constant, variable or \"(\"", state.position);
        }
        if (state.startsLikeConst()) {
            int st = state.position;
            while (state.canMove(1) && Character.isDigit(state.getCharMoved(1))) {
                state.move(1);
            }
            state.move(1);
            try {
                return new Const(Integer.parseInt(state.revSufix(st)));
            } catch (Exception e) {
                state.move(st - state.position);
                throw new ExpressionParserException("bad integer number format : " + state.getIdentifier(true), st);
            }
        }
        if (isVar(c)) {
            String varName = state.prefix(1);
            state.move(1);
            return new Variable(varName);
        }
        if (isOpenBracket(c)) {
            state.move(1);
            TripleExpression answer = baseOperationParser.parse(state);
            c = state.skipWhitespacesAndGet();
            if (state.isLastState()) {
                throw new EndOfLineException("binary operator or \")\"", state.position);
            }
            if (c != ')') {
                throw new WrongOperationOrderException("binary operator or \")\"", state.getIdentifier(false), state.position);
            }
            state.move(1);
            return answer;
        }
        //int st = startIndex.intValue();
        for (AbstractUnary unary : expressionInstances) {
            int len = unary.toString().length();
            if (state.canMove(unary.toString())) {
                state.move(unary.toString());
                if (state.isLastState()) {
                    throw new EndOfLineException("operand of \"" + unary.toString() + "\"", state.position);
                }
                state.checkAfter(unary.toString(), false, "-");
                return unary.getNewInstance(parse(state));
            }
        }
        throw new WrongOperationOrderException(
                "unary operator, constant, variable or \"(\"",
                state.getIdentifier(false),
                state.position
        );
    }

}