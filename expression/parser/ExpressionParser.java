package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {
    private String expression;
    private int index;

    public TripleExpression parse(String expression) {
        this.expression = expression;
        System.out.println(expression);
        this.index = 0;
        TripleExpression ex = parseExpression();
        return ex;
    }

    private TripleExpression parseExpression() {
        TripleExpression result = parseTerm();
        while (true) {
            if (match('+')) {
                result = new Add((BasicExpressionInterface) result, (BasicExpressionInterface) parseTerm());
            } else if (match('-')) {
                result = new Subtract((BasicExpressionInterface) result, (BasicExpressionInterface) parseTerm());
            } else {
                return result;
            }
        }
    }

    private TripleExpression parseTerm() {
        TripleExpression result = parseFactor();
        while (true) {
            if (match('*')) {
                result = new Multiply((BasicExpressionInterface) result, (BasicExpressionInterface) parseFactor());
            } else if (match('/')) {
                result = new Divide((BasicExpressionInterface) result, (BasicExpressionInterface) parseFactor());
            } else {
                return result;
            }
        }
    }

    private TripleExpression parseFactor() {
        skipWhitespace();
        if (match('(')) {
            TripleExpression result = parseExpression();
            expect(')');
            return result;
        } else if (match('-')) {
            if (Character.isDigit(peek())) {
                // Проверяем, является ли следующее число минимальным значением для int.
                boolean isNegative = true;
                return new Const(parseNumber(isNegative));
            }
            return new Negate(parseFactor());
        } else if (Character.isDigit(peek())) {
            return new Const(parseNumber(false));
        } else if (Character.isLetter(peek())) {
            return new Variable(parseVariable());
        }
        throw new RuntimeException("Unexpected character: " + peek());
    }

    private int parseNumber(boolean isNegative) {
        StringBuilder number = new StringBuilder(isNegative ? "-" : "");
        while (Character.isDigit(peek())) {
            number.append(expression.charAt(index++));
        }
        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException e) {
            if (number.toString().equals("-2147483648")) {
                return Integer.MIN_VALUE;
            } else {
                throw new RuntimeException("Number out of range for int: " + number.toString());
            }
        }
    }

    private String parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (Character.isLetter(peek())) {
            variable.append(expression.charAt(index++));
        }
        return variable.toString();
    }

    private boolean match(char expected) {
        if (peek() == expected) {
            index++;
            return true;
        }
        return false;
    }

    private void expect(char expected) {
        if (!match(expected)) {
            throw new RuntimeException("Expected '" + expected + "' but found '" + peek() + "'");
        }
    }

    private char peek() {
        skipWhitespace();
        if (index >= expression.length()) return '\0';
        return expression.charAt(index);
    }

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }
}