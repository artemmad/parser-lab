package expression;

public class Multiply extends AbstractBinaryOperation {

    public Multiply(BasicExpressionInterface firstOperand, BasicExpressionInterface secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    protected int calculate(int firstOperand, int secondOperand) {
        long result = (long) firstOperand * (long) secondOperand;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow");
        }
        return (int) result;
    }

    @Override
    protected String getOperator() {
        return " * ";
    }
}