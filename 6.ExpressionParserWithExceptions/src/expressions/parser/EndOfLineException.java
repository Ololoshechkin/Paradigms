package expressions.parser;

/**
 * Created by Vadim on 01.04.17.
 */
public class EndOfLineException extends WrongOperationOrderException {

    public EndOfLineException(String expected, int position) {
        super(expected, "End Of Line", position);
    }

}
