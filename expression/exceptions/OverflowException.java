package expression.exceptions;

public class OverflowException extends RuntimeException {
    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(String message, Throwable e) {
        super(message, e);
    }
}