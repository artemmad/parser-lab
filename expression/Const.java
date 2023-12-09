package expression;

import java.util.Objects;

public class Const implements ExpressionBase {
    private final int value;
    public Const(int value) {
        this.value = value;
    }
    @Override
    public int evaluate(int value) {
        return this.value;
    }
    @Override
    public int evaluate(int value, int value2, int value3) {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value) * 100;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        } else {
            Const operator = (Const) object;
            return this.value == operator.value;
        }
    }
}