import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Vadim on 28.02.17.
 */
public class MainClass {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayQueue queue = new ArrayQueue();
        ArrayQueueTestgen testgen = new ArrayQueueTestgen(100, "input.txt");
        testgen.generate();
        ArrayQueueTest test = new ArrayQueueTest(queue, "input.txt");
        test.run();
    }

}
