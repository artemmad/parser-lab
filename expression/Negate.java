package expression;

public class Negate implements BasicExpressionInterface {
    private final TripleExpression expression;

    public Negate(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Negate other = (Negate) obj;
        return expression.equals(other.expression);
    }

    @Override
    public int hashCode() {
        return 31 * expression.hashCode();
    }

    @Override
    public int evaluate(int x) {
        return  x*-1;
    }

    @Override
    public String toMiniString() {
        return BasicExpressionInterface.super.toMiniString();
    }
}