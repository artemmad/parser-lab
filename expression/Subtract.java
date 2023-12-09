package expression;

public class Subtract extends AbstractBinaryOperation {

    public Subtract(ExpressionBase firstOperand, ExpressionBase secondOperand) {
        super(firstOperand, secondOperand);
    }

    public Subtract(TripleExpression te1, TripleExpression te2){
        super(te1, te2);
    }
    @Override
    protected int compute(int firstOperand, int secondOperand) {
        return firstOperand - secondOperand;
    }

    @Override
    protected String getOperator() {
        return " - ";
    }
}