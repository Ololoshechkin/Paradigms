import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Vadim on 05.03.17.
 */
public abstract class AbstractQueue implements Queue {

    protected int size = 0;

    protected abstract QueueIterator begin();

    public AbstractQueue() {
        deleteAll();
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object element() {
        assert size > 0 : "queue is empty";
        return front();
    }

    public Object dequeue() {
        assert !isEmpty() : "queue is empty!";
        Object answer = front();
        popFront();
        size--;
        return answer;
    }

    public void enqueue(Object e) {
        assert e != null : "element is null!";
        pushBack(e);
        size++;
    }

    public void clear() {
        deleteAll();
        size = 0;
    }

    //pre: Inv, newE != 0
    //post: {e0', .., en'} = {e0, ..., en-1, newE}
    protected abstract void pushBack(Object newE);

    //pre: Inv, size > 0
    //post: R = e0, nothing changes
    protected abstract Object front();

    //pre: Inv, size > 0
    //post: {e0', ..., en-1'} = {e0, ..., en-2}
    protected abstract void popFront();

    //pre: Inv
    //post: {e0', ..., en-1'} = {}
    protected abstract void deleteAll();

    protected abstract Queue emptyExemplar();

    public Queue filter(Predicate<Object> pred) {
        Queue answer = emptyExemplar();
        for (QueueIterator i = begin(); !i.isEnd(); i = i.next()) {
            if (pred.test(i.value())) {
                answer.enqueue(i.value());
            }
        }
        return answer;
    }

    public Queue map(Function<Object, Object> func) {
        Queue answer = emptyExemplar();
        for (QueueIterator i = begin(); !i.isEnd(); i = i.next()) {
            answer.enqueue(func.apply(i.value()));
        }
        return answer;
    }

}
