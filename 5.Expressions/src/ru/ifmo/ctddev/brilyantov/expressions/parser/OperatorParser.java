package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.SomeExpression;

import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Vadim on 27.03.17.
 */

public interface OperatorParser {

    SomeExpression parse(Deque<String> expressionUnits);

}
