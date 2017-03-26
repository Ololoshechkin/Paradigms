package ru.ifmo.ctddev.brilyantov.expressions.parser;

import ru.ifmo.ctddev.brilyantov.expressions.expressions.TripleExpression;

/**
 * Created by Vadim on 25.03.17.
 */

public interface Parser {

    TripleExpression parse(String expression);

}