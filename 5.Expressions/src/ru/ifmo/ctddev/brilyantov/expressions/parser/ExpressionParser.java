package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vadim on 25.03.17.
 */

public class ExpressionParser implements Parser {

    private OperatorParser baseOperatorParser;

    public ExpressionParser() {
        BinaryOperatorParser mulParser = new BinaryOperatorParser(
                new Multiply(), new Divide(), new Mod());
        BinaryOperatorParser sumParser = new BinaryOperatorParser(
                mulParser,
                new Add(), new Subtract()
        );
        BinaryOperatorParser shiftParser = new BinaryOperatorParser(
                sumParser,
                new LeftShift(), new RightShift()
        );
        AnyUnaryParser anyUnaryParser = new AnyUnaryParser(
                shiftParser,
                new UnaryMinus(), new Log(), new Abs(), new Square(), new SquareRoot()
        );
        mulParser.nextPriorityOperationParser = anyUnaryParser;
        baseOperatorParser = shiftParser;
    }

    @Override
    public TripleExpression parse(String expression) {
        Deque<String> expressionUnits = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            switch (c) {
                case '+':
                    expressionUnits.push("+");
                    break;
                case '-' :
                    expressionUnits.push("-");
                    break;
                case '*' :
                    expressionUnits.push("*");
                    break;
                case '/' :
                    expressionUnits.push("/");
                    break;
                case '(' :
                    expressionUnits.push("(");
                    break;
                case ')' :
                    expressionUnits.push(")");
                    break;
                case 'x' :
                    expressionUnits.push("x");
                    break;
                case 'y' :
                    expressionUnits.push("y");
                    break;
                case 'z' :
                    expressionUnits.push("z");
                    break;
                default:
                    if (Character.isDigit(c)) {
                        int numberStart = i;
                        int numberEnd = i;
                        while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                            numberEnd = ++i;
                        }
                        expressionUnits.push(expression.substring(numberStart, numberEnd + 1));
                        continue;
                    } else if (expression.substring(i, i + 2).equals("<<")) {
                        expressionUnits.push("<<");
                    } else if (expression.substring(i, i + 2).equals(">>")) {
                        expressionUnits.push(">>");
                    } else if (expression.substring(i, i + 3).equals("abs")) {
                        expressionUnits.push("abs");
                    } else if (expression.substring(i, i + 6).equals("square")) {
                        expressionUnits.push("square");
                    } else {
                        // TODO throw "no such identifer" exception
                    }
                    i += expressionUnits.peekFirst().length() - 1;
             }
        }

        return baseOperatorParser.parse(expressionUnits);
    }
}
