package expression.exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable e) {
        super(message, e);
    }
}