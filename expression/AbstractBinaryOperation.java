package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements ExpressionBase {
    protected final ExpressionBase firstPart;
    protected final ExpressionBase secondPart;

    protected AbstractBinaryOperation(ExpressionBase firstExpression, ExpressionBase secondExpression) {
        this.firstPart = firstExpression;
        this.secondPart = secondExpression;
    }

    public AbstractBinaryOperation(TripleExpression first, TripleExpression second) {
        this(new ExpressionBase() {
            @Override
            public int evaluate(int x) {
                return first.evaluate(x, 0, 0);
            }

            @Override
            public int evaluate(int x, int y, int z) {
                return first.evaluate(x, y, z);
            }

            @Override
            public String toString() {
                return first.toString();
            }
        }, new ExpressionBase() {
            @Override
            public int evaluate(int x) {
                // Аналогично первому адаптеру.
                return second.evaluate(x, 0, 0);
            }

            @Override
            public int evaluate(int x, int y, int z) {
                return second.evaluate(x, y, z);
            }

            @Override
            public String toString() {
                return second.toString();
            }
        });
    }

    protected abstract String getOperator();
    protected abstract int compute(int firstOperand, int secondOperand);

    @Override
    public int evaluate(int value) {
        return compute(firstPart.evaluate(value), secondPart.evaluate(value));
    }

    @Override
    public int evaluate(int value, int value2, int value3) {
        return compute(firstPart.evaluate(value, value2, value3), secondPart.evaluate(value, value2, value3));
    }

    @Override
    public String toString() {
        return "(" + firstPart.toString() + this.getOperator() + secondPart.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        AbstractBinaryOperation binaryOperator = (AbstractBinaryOperation) object;
        return this.firstPart.equals(binaryOperator.firstPart) &&
                secondPart.equals(binaryOperator.secondPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPart, secondPart, this.getOperator()) * 100;
    }
}