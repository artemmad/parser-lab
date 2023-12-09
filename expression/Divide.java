package expression;

public class Divide extends AbstractBinaryOperation {

    public Divide(ExpressionBase firstArg, ExpressionBase secondArg) {
        super(firstArg, secondArg);
    }

    public Divide(TripleExpression te1, TripleExpression te2) {
        super(te1, te2);
    }

    @Override
    protected int compute(int firstArg, int secondArg) {
        // тут по хорошему стоит еще обрабатывать деление на 0
        return (firstArg / secondArg);
    }

    @Override
    protected String getOperator() {
        return " / ";
    }
}