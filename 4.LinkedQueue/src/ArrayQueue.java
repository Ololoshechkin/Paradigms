/**
 * Created by Vadim on 05.03.17.
 */
public class ArrayQueue extends AbstractQueue implements Queue {

    private Object[] array;
    private int left; // [l; r + [l > r] * array.length)

    //pre: Inv
    //post: R = isEmpty ? 0 : (left + size - 1) % |array|, nothing changes
    private int right() {
        if (isEmpty()) {
            return 0;
        }
        return (left + size) % array.length;
    }

    //pre: Inv, size > 0
    //post: R = (left + size - 1) % |array|, nothing changes
    private int exactRight() {
        assert !isEmpty() : "dequeue is empty";
        return (left + size - 1) % array.length;
    }

    //pre: Inv, expectedSize >= 0, expectedSize in [size - 1, size + 1]
    //post: array.length' in [expectedSize; 4 * expectedSize], nothing else changes
    private void provideWithNormalSize(int expectedSize) {
        if (array.length <= expectedSize || array.length > 4 * expectedSize) {
            Object[] tmpArray = new Object[2 * expectedSize + 1];
            if (left <= right()) {
                System.arraycopy(array, left, tmpArray, 0, size);
            } else {
                int tempLen = array.length - left;
                System.arraycopy(array, left, tmpArray, 0, tempLen);
                System.arraycopy(array, 0, tmpArray, tempLen, right());
            }
            array = tmpArray;
            left = 0;
        }
    }

    @Override
    protected ArrayQueueIterator begin() {
        return new ArrayQueueIterator(left);
    }

    //pre: Inv, newE != 0
    //post: {e0', .., en'} = {e0, ..., en-1, newE}
    @Override
    protected void pushBack(Object e) {
        provideWithNormalSize(size + 1);
        array[right()] = e;
    }

    //pre: Inv, size > 0
    //post: R = e0, nothing changes
    @Override
    protected Object front() {
        return array[left];
    }

    //pre: Inv, size > 0
    //post: {e0', ..., en-1'} = {e0, ..., en-2}
    @Override
    protected void popFront() {
        array[left] = null;
        left++;
        left %= array.length;
        provideWithNormalSize(size - 1);
    }


    //pre: Inv
    //post: {e0', ..., en-1'} = {}
    @Override
    protected void deleteAll() {
        array = new Object[2];
        left = 0;
    }

    @Override
    protected Queue emptyExemplar() {
        return new ArrayQueue();
    }

    public void printQueue() {
        int i = left;
        while (i != right()) {
            System.out.print(array[i] + " ");
            ++i;
            if (i == array.length) {
                i = 0;
            }
        }
        System.out.print('\n');
    }

    public class ArrayQueueIterator implements QueueIterator {

        private int currentIndex;
        private int prefixSize;

        public ArrayQueueIterator(int currentIndex) {
            this(currentIndex, 0);
        }

        public ArrayQueueIterator(int currentIndex, int prefixSize) {
            this.currentIndex = currentIndex;
            this.prefixSize = prefixSize;
        }

        public ArrayQueueIterator next() {
            return new ArrayQueueIterator((currentIndex + 1) % array.length, prefixSize + 1);
        }

        public boolean isEnd() {
            return (currentIndex == right() && prefixSize == size);
        }

        public Object value() {
            return array[currentIndex];
        }
    }
}
