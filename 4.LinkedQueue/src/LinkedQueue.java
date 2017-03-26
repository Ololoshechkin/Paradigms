import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Vadim on 05.03.17.
 */

public class LinkedQueue extends AbstractQueue implements Queue {

    private class LinkedQueueNode {

        public Object element;
        public LinkedQueueNode nextNode;

        public LinkedQueueNode() {
            this(null, null);
        }

        public LinkedQueueNode(Object element) {
            this(element, null);
        }

        public LinkedQueueNode(Object element, LinkedQueueNode nextNode) {
            this.element = element;
            this.nextNode = nextNode;
        }

        public boolean isLast() {
            return nextNode == null;
        }

    }

    private LinkedQueueNode rootNode; // (-1)th node
    private LinkedQueueNode lastNode; // (n-1)th node

    @Override
    protected LinkedQueueIterator begin() {
        return new LinkedQueueIterator(rootNode.nextNode);
    }

    //pre: Inv, newE != 0
    //post: {e0', .., en'} = {e0, ..., en-1, newE}
    @Override
    protected void pushBack(Object e) {
        lastNode.nextNode = new LinkedQueueNode(e);
        lastNode = lastNode.nextNode;
    }

    //pre: Inv, size > 0
    //post: R = e0, nothing changes
    @Override
    protected Object front() {
        return rootNode.nextNode.element;
    }

    //pre: Inv, size > 0
    //post: {e0', ..., en-1'} = {e0, ..., en-2}
    @Override
    protected void popFront() {
        rootNode.nextNode = rootNode.nextNode.nextNode;
        if (rootNode.nextNode == null) {
            lastNode = rootNode;
        }
    }

    //pre: Inv
    //post: {e0', ..., en-1'} = {}
    @Override
    protected void deleteAll() {
        rootNode = new LinkedQueueNode();
        lastNode = rootNode;
    }

    @Override
    protected Queue emptyExemplar() {
        return new LinkedQueue();
    }

    public void printQueue() {
        if (rootNode == null) {
            System.out.println("root is null");
            return;
        }
        LinkedQueueNode node = rootNode.nextNode;
        int cnt = 0;
        while (node != null) {
            if (node.element == null) {
                return;
            }
            System.out.print(node.element + " ");
            node = node.nextNode;
            cnt++;
            if (cnt >= 10) {
                break;
            }
        }
        System.out.print('\n');
    }

    protected class LinkedQueueIterator implements QueueIterator {

        private LinkedQueueNode currentNode;

        protected LinkedQueueIterator(LinkedQueueNode currentNode) {
            this.currentNode = currentNode;
        }

        public LinkedQueueIterator next() {
            return new LinkedQueueIterator(currentNode.nextNode);
        }

        public boolean isEnd() {
            return currentNode == null;
        }

        public Object value() {
            return  currentNode.element;
        }
    }

}
