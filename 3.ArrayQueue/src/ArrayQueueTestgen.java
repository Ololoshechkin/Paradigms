import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Vadim on 01.03.17.
 */
public class ArrayQueueTestgen {

    private int testCnt;
    private Random rnd;
    private PrintWriter printWriter;

    ArrayQueueTestgen(int testCnt, String fileName) throws FileNotFoundException {
        this.testCnt = testCnt;
        printWriter = new PrintWriter(new File(fileName));
        rnd = new Random();
    }

    public void generate() {
        for (int i = 0; i < testCnt; i++) {
            int cmdCode = Math.abs(rnd.nextInt()) % 9;
            if (cmdCode == 0) {
                printWriter.println("push " + rnd.nextInt());
            }
            if (cmdCode == 1) {
                printWriter.println("enqueue " + rnd.nextInt());
            }
            if (cmdCode == 2) {
                printWriter.println("remove");
            }
            if (cmdCode == 3) {
                printWriter.println("dequeue");
            }
            if (cmdCode == 4) {
                printWriter.println("clear");
            }
            if (cmdCode == 5) {
                printWriter.println("isEmpty");
            }
            if (cmdCode == 6) {
                printWriter.println("peek");
            }
            if (cmdCode == 7) {
                printWriter.println("element");
            }
            if (cmdCode == 8) {
                printWriter.println("size");
            }
        }
        printWriter.close();
    }

}
