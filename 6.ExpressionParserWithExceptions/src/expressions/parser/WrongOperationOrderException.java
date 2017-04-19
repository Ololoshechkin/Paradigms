package expressions.parser;

/**
 * Created by Vadim on 01.04.17.
 */
public class WrongOperationOrderException extends ExpressionParserException {

    public WrongOperationOrderException(String expected, String found, int position) {
        super(found.equals("End Of Line")
                ? "Expected " + expected + " , BUT Found " + found
                : "Expected " + expected + " , BUT Found \"" + found + "\"",
                position
        );
    }

}
