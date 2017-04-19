package expressions;
/**
 * Created by Vadim on 02.04.17.
 */
public class OverflowException extends EvaluationException {

    public OverflowException() {
        super("overflow");
    }

}
