package expression;

public class Add extends AbstractBinaryOperation {
    public Add(ExpressionBase firstArg, ExpressionBase secondArg) {
        super(firstArg, secondArg);
    }

    public Add(TripleExpression te1, TripleExpression te2){
        super(te1, te2);
    }


    @Override
    public int compute(int first, int second) {
        return first + second;
    }

    @Override
    public String getOperator() {
        return " + ";
    }
}