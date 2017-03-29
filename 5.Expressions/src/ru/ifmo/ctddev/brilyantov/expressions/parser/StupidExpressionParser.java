package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.*;

import java.util.StringTokenizer;

/**
 * Created by Vadim on 28.03.17.
 */
public class StupidExpressionParser implements Parser {
    private int currentPtr;

    private String[] expressionUnits;

    public StupidExpressionParser() {
        currentPtr = 0;
    }

    private Const parseConst() {
        int value = Integer.parseUnsignedInt(expressionUnits[currentPtr]);
        currentPtr++;
        return new Const(value);
    }


    private Variable parseVar() {
        String varName = expressionUnits[currentPtr];
        currentPtr++;
        return new Variable(varName);
    }

    private boolean isVar(String s) {
        return s.equals("x") || s.equals("y") || s.equals("z");
    }

    private boolean isConst(String s) {
        return s.length() != 0 && Character.isDigit(s.charAt(0));
    }

    private SomeExpression parseUnary() {
        //unary = -mul or abs mul or square
        if (expressionUnits[currentPtr].equals("-")) {
            currentPtr++;
            return new UnaryMinus(parseMultipliement());
        }
        if (expressionUnits[currentPtr].equals("abs")) {
            currentPtr++;
            return new Abs(parseMultipliement());
        }
        if (expressionUnits[currentPtr].equals("square")) {
            currentPtr++;
            return new Square(parseMultipliement());
        }
        return new Const(0); // TODO : throw error
    }

    private SomeExpression parseMultipliement() {
        // multipliement = (expr) or const or var or unary ...
        if (expressionUnits[currentPtr].equals("(")) {
            currentPtr++;
            SomeExpression answer = parseExpr();
            currentPtr++;
            return answer;
        }
        if (isConst(expressionUnits[currentPtr])) {
            return parseConst();
        }
        if (isVar(expressionUnits[currentPtr])) {
            return parseVar();
        }
        return parseUnary();
    }

    private SomeExpression parseSummand() {
        // summand = multipliement1 */ multipliement2 */ ...
        SomeExpression expr = parseMultipliement();
        while (currentPtr < expressionUnits.length && (expressionUnits[currentPtr].equals("*") || expressionUnits[currentPtr].equals("/"))) {
            String currentOperation = expressionUnits[currentPtr];
            currentPtr++;
            if (currentOperation.equals("*")) {
                expr = new Multiply(expr, parseMultipliement());
            } else {
                expr = new Divide(expr, parseMultipliement());
            }
        }
        return expr;
    }

    private SomeExpression parseExpr() {
        // expr = summand1 +- summand2 +- ...
        SomeExpression expr = parseSummand();
        while (currentPtr < expressionUnits.length && (expressionUnits[currentPtr].equals("+") || expressionUnits[currentPtr].equals("-"))) {
            String currentOperation = expressionUnits[currentPtr];
            currentPtr++;
            if (currentOperation.equals("+")) {
                expr = new Add(expr, parseSummand());
            } else {
                expr = new Subtract(expr, parseSummand());
            }
        }
        return expr;
    }

    @Override
    public TripleExpression parse(String expression) {
        StringTokenizer exprTokens = new StringTokenizer(expression.replaceAll("\\p{javaWhitespace}", ""), "+-*/()", true);
        int tokensCnt = exprTokens.countTokens();
        expressionUnits = new String[tokensCnt];
        for (int i = 0; i < tokensCnt; i++) {
            expressionUnits[i] = exprTokens.nextToken();
        }
        TripleExpression answer = parseExpr();
        expressionUnits = null;
        return answer;
    }
}
