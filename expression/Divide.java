package expression;

public class Divide extends AbstractBinaryOperation {

    public Divide(BasicExpressionInterface firstOperand, BasicExpressionInterface secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    protected int calculate(int firstOperand, int secondOperand) {
        if (secondOperand == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new ArithmeticException("Overflow");
        }
        return firstOperand / secondOperand;
    }

    @Override
    protected String getOperator() {
        return " / ";
    }
}