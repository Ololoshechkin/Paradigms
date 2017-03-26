/**
 * Created by Vadim on 15.03.17.
 */
public interface QueueIterator {

    public QueueIterator next();

    public  boolean isEnd();

    public  Object value();

}
