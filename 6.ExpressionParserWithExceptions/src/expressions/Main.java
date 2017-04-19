package expressions;

import expressions.parser.ExpressionParser;
import expressions.parser.Parser;

import java.util.Scanner;

/**
 * Created by Vadim on 19.04.17.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter expression : ");
        String exp = sc.nextLine();
        Parser p = new ExpressionParser();
        System.out.println("Enter three integers (x, y, z) : ");
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();
        System.out.println("answer : " + p.parse(exp).evaluate(x, y, z));
    }
}
