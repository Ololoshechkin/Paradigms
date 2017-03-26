/**
 * Created by Vadim on 28.02.17.
 */
public class ArrayQueue {
    //INV : ei != null for each i in [0..n-1]
    private Object[] array = new Object[10];
    private int left = 0, size = 0; // [l; r + [l > r] * array.length)

    //pre: size >= 0
    //post: R = size, nothing changes
    public int size() {
        return size;
    }

    //pre: size >= 0
    //post: R = (left + size) % array.length or 0 if empty, nothing changes
    private int right() {
        if (isEmpty()) {
            return 0;
        }
        return (left + size) % array.length;
    }

    //pre: size > 0
    //post: R = (left + size - 1) % array.length or 0 if empth, nothing changes
    private int exactRight() {
        assert !isEmpty() : "dequeue is empty";
        return (left + size - 1) % array.length;
    }

    //pre: size >= 0
    //post: array.length >= 2 * size, nothing else changes
    private void provideWithNormalSize() {
        provideWithNormalSize(size);
    }

    //pre: Inv, expectedSize >= 0, expectedSize in [size - 1, size + 1]
    //post: array.length' in [2 * expectedSize; 4 * expectedSize], nothing else changes
    private void provideWithNormalSize(int expectedSize) {
        if (expectedSize == 0) {
            clear();
        }
        if (array.length < 2 * expectedSize || array.length > 4 * expectedSize) {
            Object[] tmpArray = new Object[2 * array.length + 2];
            if (left < exactRight()) {
                System.arraycopy(array, left, tmpArray, 0, size);
            }
            int tempLen = array.length - left;
            System.arraycopy(array, left, tmpArray, 0, tempLen);
            System.arraycopy(array, 0, tmpArray, tempLen, exactRight() + 1);
            array = tmpArray;
            left = 0;
        }
    }

    //pre: size >= 0
    public boolean isEmpty() {
        return (size() == 0);
    }

    //pre: size >= 0, element != nul
    //post: e0', ..., en-1' = en' = e0,...,en-1, newE
    public void enqueue(Object element) {
        assert element != null : "element is null!";
        provideWithNormalSize(size + 1);
        array[right()] = element;
        size++;
    }

    //pre: size > 0
    public Object element() {
        return array[left];
    }

    //pre: size > 0
    //post: e0', ..., en-1', en'= e0,...,en-1, R = e0
    public Object dequeue() {
        Object answer = element();
        array[left] = null;
        left++;
        left %= array.length;
        size--;
        provideWithNormalSize();
        return answer;
    }

    //pre: size >= 0
    //post: size = 0
    public void clear() {
        array = new Object[10];
        left = size = 0;
    }

    //pre: size >= 0, element != null
    //post: size' = size + 1, {e0', e1',..., en+1'} = {newE, e0, e1, ..., en}
    public void push(Object element) {
        assert element != null : "element is null";
        provideWithNormalSize(size + 1);
        left = (left - 1 + array.length) % array.length;
        array[left] = element;
        size++;
    }

    //pre: size > 0
    //post: R = en-1, nothing changes
    public Object peek() {
        assert !isEmpty() : "dequeue is empty";
        return array[exactRight()];
    }

    //pre: size > 0
    //post: {e0', ..., en-1'} = {e0,...,en-1} ; size' = size - 1, R = en-1
    public Object remove() {
        assert !isEmpty() : "dequeue is empty";
        Object answer = peek();
        array[exactRight()] = null;
        size--;
        provideWithNormalSize();
        return answer;
    }
}
