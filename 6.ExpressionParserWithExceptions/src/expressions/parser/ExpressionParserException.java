package expressions.parser;

/**
 * Created by Vadim on 01.04.17.
 */
public class ExpressionParserException extends RuntimeException {

    public ExpressionParserException(String description, int position) {
        super(description + "   (at position : " + position + ")");
    }
}
