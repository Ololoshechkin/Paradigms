package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.*;

import java.util.function.Predicate;

/**
 * Created by Vadim on 25.03.17.
 */
public class ExpressionParser implements Parser {

    private int currentPtr;

    private String expression;

    public ExpressionParser() {
        currentPtr = 0;
    }

    private Const parseConst() {
        int value = 0;
        while (currentPtr < expression.length() && Character.isDigit(expression.charAt(currentPtr))) {
            value *= 10;
            value += Character.getNumericValue(expression.charAt(currentPtr));
            currentPtr++;
        }
        return new Const(value);
    }

    private Variable parseVar() {
        String varName = ((Character) expression.charAt(currentPtr)).toString();
        currentPtr++;
        return new Variable(varName);
    }

    private TripleExpression parseMultipliement() {
        // multipliement = (expr) or const or {x, y, z} or -expr ...
        if (expression.charAt(currentPtr) == '(') {
            currentPtr++;
            TripleExpression answer = parseExpr();
            currentPtr++;
            return answer;
        }
        if (expression.charAt(currentPtr) == '-') {
            currentPtr++;
            return new Subtract(
                    new Const(0),
                    (SomeExpression) parseMultipliement()
            );
        }
        return (Character.isDigit(expression.charAt(currentPtr)) ? parseConst() : parseVar());
    }

    private TripleExpression parseSummand() {
        // summand = multipliement1 */ multipliement2 */ ...
        TripleExpression expr = parseMultipliement();
        while (currentPtr < expression.length() && (expression.charAt(currentPtr) == '*' || expression.charAt(currentPtr) == '/')) {
            char currentOperation = expression.charAt(currentPtr);
            currentPtr++;
            if (currentOperation == '*') {
                expr = new Multiply((SomeExpression) expr, (SomeExpression) parseMultipliement());
            } else {
                expr = new Divide((SomeExpression) expr, (SomeExpression) parseMultipliement());
            }
        }
        return expr;
    }

    private TripleExpression parseExpr() {
        // expr = summand1 +- summand2 +- ...
        TripleExpression expr = parseSummand();
        while (currentPtr < expression.length() && (expression.charAt(currentPtr) == '+' || expression.charAt(currentPtr) == '-')) {
            char currentOperation = expression.charAt(currentPtr);
            currentPtr++;
            if (currentOperation == '+') {
                expr = new Add((SomeExpression) expr, (SomeExpression) parseSummand());
            } else {
                expr = new Subtract((SomeExpression) expr, (SomeExpression) parseSummand());
            }
        }
        return expr;
    }

    @Override
    public TripleExpression parse(String expression) {
        this.expression = expression.replaceAll("\\p{javaWhitespace}", "");
        return parseExpr();
    }

}
