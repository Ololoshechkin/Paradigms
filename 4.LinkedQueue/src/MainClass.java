import java.util.Scanner;

/**
 * Created by Vadim on 05.03.17.
 */
public class MainClass {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedQueue lnkQueue = new LinkedQueue();
        ArrayQueue arrQueue = new ArrayQueue();
        while (true) {
            String cmd = sc.next();
            if (cmd.compareTo("filter") == 0) {
                Queue lq = lnkQueue.filter(n -> ((int)n % 2 == 0));
                Queue rq = arrQueue.filter(n -> ((int)n % 2 == 0));
                lq.printQueue();
                rq.printQueue();
            }
            if (cmd.compareTo("map") == 0) {
                Queue lq = lnkQueue.map(n -> (int)n*(int)n);
                Queue rq = arrQueue.map(n -> (int)n*(int)n);
                lq.printQueue();
                rq.printQueue();
            }
            if (cmd.compareTo("print") == 0) {
                lnkQueue.printQueue();
                arrQueue.printQueue();
            }
            /*if (cmd.compareTo("push") == 0) {
                int x = sc.nextInt();
                queue.push(x);
            }*/
            if (cmd.compareTo("enqueue") == 0) {
                int x = sc.nextInt();
                lnkQueue.enqueue(x);
                arrQueue.enqueue(x);
            }
            /*if (cmd.compareTo("remove") == 0) {
                System.out.println(queue.remove());
            }*/
            if (cmd.compareTo("dequeue") == 0) {
                System.out.println(lnkQueue.dequeue());
                System.out.println(arrQueue.dequeue());
            }
            /*if (cmd.compareTo("peek") == 0) {
                System.out.println(queue.peek());
            }*/
            if (cmd.compareTo("element") == 0) {
                System.out.println(lnkQueue.element());
                System.out.println(arrQueue.element());
            }
            if (cmd.compareTo("clear") == 0) {
                lnkQueue.clear();
                arrQueue.clear();
            }
            if (cmd.compareTo("size") == 0) {
                System.out.println(lnkQueue.size());
                System.out.println(arrQueue.size());
            }
            if (cmd.compareTo("isEmpty") == 0) {
                System.out.println(lnkQueue.isEmpty());
                System.out.println(arrQueue.isEmpty());
            }
        }
    }

}
