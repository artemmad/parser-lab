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
        return firstArg * secondArg;
    }

    @Override
    protected String getOperator() {
        return " * ";
    }
}