package expressions;
import java.beans.Expression;

/**
 * Created by Vadim on 02.04.17.
 */
public class EvaluationException extends RuntimeException {

    public EvaluationException(String message) {
        super("evaluation exception : " + message);
    }
}
