package expression;

import expression.exceptions.OverflowException;

public class Subtract extends AbstractBinaryOperation {

    public Subtract(ExpressionBase firstOperand, ExpressionBase secondOperand) {
        super(firstOperand, secondOperand);
    }

    public Subtract(TripleExpression te1, TripleExpression te2){
        super(te1, te2);
    }

    @Override
    protected int compute(int firstOperand, int secondOperand) {
        if ((secondOperand < 0 && firstOperand > Integer.MAX_VALUE + secondOperand) ||
                (secondOperand > 0 && firstOperand < Integer.MIN_VALUE + secondOperand)) {
            throw new OverflowException("Overflow in subtraction");
        }
        return firstOperand - secondOperand;
    }

    @Override
    protected String getOperator() {
        return " - ";
    }
}