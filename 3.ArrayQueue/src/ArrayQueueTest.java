import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Vadim on 01.03.17.
 */
public class ArrayQueueTest {

    private String fileName;
    private ArrayQueue queue;
    private Scanner sc;

    public ArrayQueueTest(ArrayQueue queue, String fileName) throws FileNotFoundException {
        this.queue = queue;
        sc = (fileName.compareTo("stdin") == 0 ? new Scanner(System.in) : new Scanner(new File(fileName)));
    }

    public void run() {
        while (sc.hasNext()) {
            String cmd = sc.next();
            if (cmd.compareTo("push") == 0) {
                int x = sc.nextInt();
                queue.push(x);
            }
            if (cmd.compareTo("enqueue") == 0) {
                int x = sc.nextInt();
                queue.enqueue(x);
            }
            if (cmd.compareTo("remove") == 0) {
                System.out.println(queue.remove());
            }
            if (cmd.compareTo("dequeue") == 0) {
                System.out.println(queue.dequeue());
            }
            if (cmd.compareTo("peek") == 0) {
                System.out.println(queue.peek());
            }
            if (cmd.compareTo("element") == 0) {
                System.out.println(queue.element());
            }
            if (cmd.compareTo("clear") == 0) {
                queue.clear();
            }
            if (cmd.compareTo("size") == 0) {
                System.out.println(queue.size());
            }
            if (cmd.compareTo("isEmpty") == 0) {
                System.out.println(queue.isEmpty());
            }
        }
    }

}
