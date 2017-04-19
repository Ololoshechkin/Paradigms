package expressions;

/**
 * Created by Vadim on 02.04.17.
 */
public class DBZException extends EvaluationException {

    public DBZException() {
        super("devision by zero");
    }
}
