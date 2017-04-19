package expressions.parser;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vadim on 11.04.17.
 */
public class IdentifierFinder {

    private static boolean isIdentifierItem(Character c) {
        return Character.isDigit(c) || Character.isAlphabetic(c);
    }

    public static String getIdentifier(String expression, int startPos, boolean alwaysPickFirst) {
        int endPos = startPos + 1;
        if (!alwaysPickFirst && !isIdentifierItem(expression.charAt(startPos))) {
            return expression.substring(startPos, endPos);
        }
        while (endPos < expression.length() && isIdentifierItem(expression.charAt(endPos))) {
            endPos++;
        }
        return expression.substring(startPos, endPos);
    }

}
