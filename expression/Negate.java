package expression;

public class Negate implements BasicExpressionInterface {
    private final TripleExpression expression;

    public Negate(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int result = expression.evaluate(x, y, z);
        return -result;
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
        return  -x;
    }

    @Override
    public String toMiniString() {
        if (expression instanceof Negate) {
            // Возвращаем строковое представление внутреннего выражения без дополнительного отрицания
            return expression.toMiniString();
        } else if (expression instanceof Const) {
            int value = ((Const) expression).getValue();
            if (value < 0) {
                // Если выражение является отрицательной константой, просто возвращаем это значение
                return Integer.toString(value);
            }
        }
        // В остальных случаях возвращаем выражение с унарным минусом
        return "-(" + expression.toMiniString() + ")";
    }

}