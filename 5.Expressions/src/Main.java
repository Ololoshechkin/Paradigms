import ru.ifmo.ctddev.brilyantov.expressions.expressions.*;
import ru.ifmo.ctddev.brilyantov.expressions.parser.ExpressionParser;
import ru.ifmo.ctddev.brilyantov.expressions.parser.Parser;

import java.util.Scanner;

/**
 * Created by Vadim on 19.03.17.
 */
public class Main {
    //!!!
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String exprText = sc.nextLine();
        Parser parser = new ExpressionParser();
        TripleExpression expr = parser.parse(exprText);
        System.out.println("parsed expression : " + expr);
        int x = sc.nextInt(), y = sc.nextInt(), z = sc.nextInt();
        System.out.println(expr.evaluate(x, y, z));
        int x = 16;
    }

}
