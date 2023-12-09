package expression;

import java.util.Objects;

public class Variable implements ExpressionBase {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int value) {
        return value;
    }

    @Override
    public int evaluate(int value, int value2, int value3) {
        switch (variable) {
            case "x":
                return value;
            case "y":
                return value2;
            case "z":
                return value3;
        }
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Variable binaryOperator = (Variable) object;
        return Objects.equals(this.variable, binaryOperator.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable) * 100;
    }
}