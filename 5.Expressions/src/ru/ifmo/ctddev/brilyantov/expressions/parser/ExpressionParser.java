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

    @Override
    public TripleExpression parse(String expression) {
        Deque<String> expressionUnits = new ArrayDeque<>();
        String operationsRegex = "\\*\\*|[\\+\\-\\*\\/]|<<|>>|[xyz]|abs|log|square|sqrt|mod|[0-9]+";
        Pattern operations = Pattern.compile(operationsRegex);
        Matcher operationsMatcher = operations.matcher(expression.replaceAll("\\p{javaWhitespace}", ""));

        String[] sArr = operations.split(expression.replaceAll("\\p{javaWhitespace}", ""));

        while (operationsMatcher.find()) {
            expressionUnits.push(operationsMatcher.group());
        }

        //StringTokenizer st = new StringTokenizer(expression.replaceAll("\\p{javaWhitespace}", ""), "+-*/()", true);
        /*
        while (st.hasMoreTokens()) {
            expressionUnits.push(st.nextToken());
        }
        // NOTEBENE : StringTokenizer takes 4 times less time (experimental fact)!!!
        */

        BinaryOperatorParser mulParser = new BinaryOperatorParser(
                new AbstractBinary[]{new Multiply(), new Divide(), new Mod()}
        );
        BinaryOperatorParser powParser = new BinaryOperatorParser(
                mulParser,
                new AbstractBinary[]{new Pow()}
        );
        BinaryOperatorParser sumParser = new BinaryOperatorParser(
                powParser,
                new AbstractBinary[]{new Add(), new Subtract()}
        );
        BinaryOperatorParser shiftParser = new BinaryOperatorParser(
                sumParser,
                new AbstractBinary[]{new LeftShift(), new RightShift()}
        );
        AnyUnaryParser anyUnaryParser = new AnyUnaryParser(
                sumParser,
                new AbstractUnary[]{new UnaryMinus(), new Log(), new Abs(), new Square(), new SquareRoot()}
        );
        mulParser.nextPriorityOperationParser = anyUnaryParser;
        TripleExpression answer = shiftParser.parse(expressionUnits);
        return answer;
    }

}
