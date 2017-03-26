
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Vadim on 05.03.17.
 */
public interface Queue {
    //Inv : n >= 0, ei != null for each i in [0..n-1]
    //nothing changes <=> n' = n, {e0'...en-1'} = {e0...en-1}

    //pre: Inv
    //post: R = n, nothing changes
    public int size();

    //pre: Inv
    //post: R = n == 0 ? true : false, nothing changes
    public boolean isEmpty();

    //pre: Inv
    //post: R = e0, nothing changes
    public Object element();

    //pre: Inv, n > 0
    //post: R = e[n-1], n' = n - 1, {e0', ..., e'[n'-1]} = {e0, ..., e[n-2]}
    public Object dequeue();

    //pre: Inv, newE != null
    //post: {e0', ..., e'n} = {e0, ..., e[n-1], newE}, n' = n - 1
    public void enqueue(Object newE);

    //pre: Inv
    //post: n' = 0, {e0', ..., e[n-1]'} = {}
    public void clear();

    //pre: Inv
    //post: prints e0 e1 ... e[n-1], nothing changes
    public void printQueue();

    //pre: Inv
    //post: nothing changes, R = {elem[0], ..., elem[k-1]} : pred(elem[i]) == true for each i in {0...k-1} &&
    // && Exist {i[0]<...<i[k-1]} in {0,...,n-1} : elem[j] == e[i[j]] for each j in {0...k-1} && k -> max
    public Queue filter(Predicate<Object> pred);

    //pre: Inv, func(ei) != null for each i = 0..n-1
    //post: nothing changes, R = {func(e0),...,func(e[n-1])}
    public Queue map(Function<Object, Object> func);

}
