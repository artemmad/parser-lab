package expression;

public class Multiply extends AbstractBinaryOperation {

    public Multiply(ExpressionBase firstOperand, ExpressionBase secondOperand) {
        super(firstOperand, secondOperand);
    }

    public Multiply(TripleExpression te1, TripleExpression te2) {
        super(te1, te2);
    }

    @Override
    protected int compute(int firstArg, int secondArg) {
        if (firstArg > 0 ? secondArg > Integer.MAX_VALUE / firstArg || secondArg < Integer.MIN_VALUE / firstArg
                : (firstArg < -1 ? secondArg > Integer.MIN_VALUE / firstArg || secondArg < Integer.MAX_VALUE / firstArg
                : firstArg == -1 && secondArg == Integer.MIN_VALUE)) {
            throw new ArithmeticException("Overflow in multiplication");
        }
        return firstArg * secondArg;
    }

    @Override
    protected String getOperator() {
        return " * ";
    }
}