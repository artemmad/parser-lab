package expression;

import expression.exceptions.OverflowException;

public class Add extends AbstractBinaryOperation {
    public Add(ExpressionBase firstArg, ExpressionBase secondArg) {
        super(firstArg, secondArg);
    }

    public Add(TripleExpression te1, TripleExpression te2){
        super(te1, te2);
    }


    @Override
    public int compute(int firstArg, int secondArg) {
        if (secondArg > 0 ? firstArg > Integer.MAX_VALUE - secondArg
                : firstArg < Integer.MIN_VALUE - secondArg) {
            throw new OverflowException("Overflow in addition");
        }
        return firstArg + secondArg;

    }

    @Override
    public String getOperator() {
        return " + ";
    }
}