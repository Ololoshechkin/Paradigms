/**
 * Created by Vadim on 28.02.17.
 */
public class ArrayQueueADT {
    //INV : ei != null for each i in [0..n-1]
    private Object[] array = new Object[10];
    private int left = 0, size = 0; // [l; r + [l > r] * array.length)

    //pre: size >= 0, queue != null
    //post: R = size, nothing changes, queue != null
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //pre: size >= 0, queue != null
    //post: R = (left + size) % array.length or 0 if empty, nothing changes, queue != null
    private static int right(ArrayQueueADT queue) {
        if (isEmpty(queue)) {
            return 0;
        }
        return (queue.left + queue.size) % queue.array.length;
    }

    //pre: size > 0, queue != null
    //post: R = (left + size - 1) % array.length or 0 if empth, nothing changes, queue != null
    private static int exactRight(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        return (queue.left + queue.size - 1) % queue.array.length;
    }

    //pre: Inv, queue != null, expectedSize >= 0
    //post: array.length' in [expectedSize; 4 * expectedSize], nothing else changes, queue != null
    private static void provideWithNormalSize(ArrayQueueADT queue, int expectedSize) {
        if (queue.array.length <= expectedSize ||queue.array.length > 4 * expectedSize) {
            Object[] tmpArray = new Object[2 * expectedSize + 1];
            if (queue.left <= ArrayQueueADT.right(queue)) {
                System.arraycopy(queue.array, queue.left, tmpArray, 0, queue.size);
            } else {
                int tempLen = queue.array.length - queue.left;
                System.arraycopy(queue.array, queue.left, tmpArray, 0, tempLen);
                System.arraycopy(queue.array, 0, tmpArray, tempLen, right(queue));
            }
            queue.array = tmpArray;
            queue.left = 0;
        }
    }

    //pre: size >= 0, queue != null
    //post: R = [size == 0], nothing else changes, queue != null
    public static boolean isEmpty(ArrayQueueADT queue) {
        return (size(queue) == 0);
    }

    //pre: size >= 0, element != null, queue != null
    //post: e0', ..., en-1' = en' = e0,...,en-1, newE, queue != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null : "element is null!";
        provideWithNormalSize(queue, queue.size + 1);
        queue.array[right(queue)] = element;
        queue.size++;
    }

    //pre: size > 0, queue != null
    //post: R = e0, nothing changes, queue != null
    public static Object element(ArrayQueueADT queue) {
        return queue.array[queue.left];
    }

    //pre: size > 0, queue != null
    //post: e0', ..., en-1', en'= e0,...,en-1, R = e0, queue != null
    public static Object dequeue(ArrayQueueADT queue) {
        Object answer = element(queue);
        queue.array[queue.left] = null;
        queue.left++;
        queue.left %= queue.array.length;
        queue.size--;
        provideWithNormalSize(queue, queue.size);
        return answer;
    }

    //pre: size >= 0, queue != null
    //post: size = 0, queue != null
    public static void clear(ArrayQueueADT queue) {
        queue.array = new Object[10];
        queue.left = queue.size = 0;
    }

    //pre: size >= 0, element != null, queue != null
    //post: size' = size + 1, {e0', e1',..., en+1'} = {newE, e0, e1, ..., en}, queue != null
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null : "element is null";
        provideWithNormalSize(queue, queue.size + 1);
        queue.left = (queue.left - 1 + queue.array.length) % queue.array.length;
        queue.array[queue.left] = element;
        queue.size++;
    }

    //pre: size > 0, queue != null
    //post: R = en-1, nothing changes, queue != null
    public static Object peek(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        return queue.array[exactRight(queue)];
    }

    //pre: size > 0, queue != null
    //post: {e0', ..., en-1'} = {e0,...,en-1} ; size' = size - 1, R = en-1, queue != null
    public static Object remove(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        Object answer = peek(queue);
        queue.array[exactRight(queue)] = null;
        queue.size--;
        provideWithNormalSize(queue, queue.size);
        return answer;
    }
}
