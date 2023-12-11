package expression;

public class Negate implements BasicExpressionInterface {
    private final TripleExpression expression;

    public Negate(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int result = expression.evaluate(x, y, z);
        if(result == Integer.MIN_VALUE){
            throw new RuntimeException("Cannot negate value" + result);
        }
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
        if (expression instanceof Const) {
            int value = ((Const) expression).getValue();
            if (value == Integer.MIN_VALUE) {
                // Специальный случай для Integer.MIN_VALUE
                return Integer.toString(value);
            } else if (value < 0) {
                // Для других отрицательных значений возвращаем значение в скобках
                return "-(" + Integer.toString(-value) + ")";
            }
        }
        // Для неотрицательных чисел и других типов выражений возвращаем выражение с унарным минусом и скобками
        return "-(" + expression.toMiniString() + ")";
    }

}