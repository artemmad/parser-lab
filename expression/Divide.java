package expression;

public class Divide extends AbstractBinaryOperation {

    public Divide(ExpressionBase firstArg, ExpressionBase secondArg) {
        super(firstArg, secondArg);
    }

    public Divide(TripleExpression te1, TripleExpression te2) {
        super(te1, te2);
    }

    @Override
    protected int compute(int firstOperand, int secondOperand) {
        if (secondOperand == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new ArithmeticException("Overflow in division");
        }
        return (firstOperand / secondOperand);
    }

    @Override
    protected String getOperator() {
        return " / ";
    }
}