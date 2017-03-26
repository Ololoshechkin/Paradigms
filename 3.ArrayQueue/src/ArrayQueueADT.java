/**
 * Created by Vadim on 28.02.17.
 */
public class ArrayQueueADT {
    //INV : ei != null for each i in [0..n-1]
    private Object[] array = new Object[10];
    private int left = 0, size = 0; // [l; r + [l > r] * array.length)

    //pre: size >= 0
    //post: R = size, nothing changes
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //pre: size >= 0
    //post: R = (left + size) % array.length or 0 if empty, nothing changes
    private static int right(ArrayQueueADT queue) {
        if (isEmpty(queue)) {
            return 0;
        }
        return (queue.left + queue.size) % queue.array.length;
    }

    //pre: size > 0
    //post: R = (left + size - 1) % array.length or 0 if empth, nothing changes
    private static int exactRight(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        return (queue.left + queue.size - 1) % queue.array.length;
    }

    //pre: size >= 0
    //post: array.length >= 2 * size, nothing else changes
    private static void provideWithNormalSize(ArrayQueueADT queue) {
        provideWithNormalSize(queue, queue.size);
    }

    //pre: size >= 0
    //post: array.length >= 2 * expSize, nothing else changes
    private static void provideWithNormalSize(ArrayQueueADT queue, int expectedSize) {
        if (expectedSize == 0) {
            clear(queue);
        }
        if (queue.array.length < 2 * expectedSize) {
            Object[] tmpArray = new Object[2 * queue.array.length + 2];
            if (queue.left < exactRight(queue)) {
                System.arraycopy(queue.array, queue.left, tmpArray, 0, queue.size);
            }
            int tempLen = queue.array.length - queue.left;
            System.arraycopy(queue.array, queue.left, tmpArray, 0, tempLen);
            System.arraycopy(queue.array, 0, tmpArray, tempLen, exactRight(queue) + 1);
            queue.array = tmpArray;
            queue.left = 0;
        }
    }

    //pre: size >= 0
    //post: R = [size == 0], nothing else changes
    public static boolean isEmpty(ArrayQueueADT queue) {
        return (size(queue) == 0);
    }

    //pre: size >= 0, element != null
    //post: e0', ..., en-1' = en' = e0,...,en-1, newE
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null : "element is null!";
        provideWithNormalSize(queue, queue.size + 1);
        queue.array[right(queue)] = element;
        queue.size++;
    }

    //pre: size > 0
    //post: R = e0, nothing changes
    public static Object element(ArrayQueueADT queue) {
        return queue.array[queue.left];
    }

    //pre: size > 0
    //post: e0', ..., en-1', en'= e0,...,en-1, R = e0
    public static Object dequeue(ArrayQueueADT queue) {
        Object answer = element(queue);
        queue.array[queue.left] = null;
        queue.left++;
        queue.left %= queue.array.length;
        queue.size--;
        provideWithNormalSize(queue);
        return answer;
    }

    //pre: size >= 0
    //post: size = 0
    public static void clear(ArrayQueueADT queue) {
        queue.array = new Object[10];
        queue.left = queue.size = 0;
    }

    //pre: size >= 0, element != null
    //post: size' = size + 1, {e0', e1',..., en+1'} = {newE, e0, e1, ..., en}
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null : "element is null";
        provideWithNormalSize(queue, queue.size + 1);
        queue.left = (queue.left - 1 + queue.array.length) % queue.array.length;
        queue.array[queue.left] = element;
        queue.size++;
    }

    //pre: size > 0
    //post: R = en-1, nothing changes
    public static Object peek(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        return queue.array[exactRight(queue)];
    }

    //pre: size > 0
    //post: {e0', ..., en-1'} = {e0,...,en-1} ; size' = size - 1, R = en-1
    public static Object remove(ArrayQueueADT queue) {
        assert !isEmpty(queue) : "dequeue is empty";
        Object answer = peek(queue);
        queue.array[exactRight(queue)] = null;
        queue.size--;
        provideWithNormalSize(queue);
        return answer;
    }
}
