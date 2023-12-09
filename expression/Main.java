package expression;

import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Subtract(new Const(20), new Const(3)).evaluate(5));

        System.out.println(new ExpressionParser().parse("-1").evaluate(0, 0,0));
    }
}