package expression.parser;

import expression.*;
import expression.exceptions.ParsingException;

public class ExpressionParser implements TripleParser {
    private String expression;
    private int index;

    private int parenthesesCount = 0; // отслеживание количества скобок

    public TripleExpression parse(String expression) {

        parenthesesCount = 0; // Перед каждым началом парса сбрасываем счетчик скобок в 0
        this.expression = expression;
        System.out.println("ex:" + expression);
        this.index = 0;
        TripleExpression ex = null;

//        if (this.expression.contains("(1\t+ 2147483647)")) {
//            System.out.println("HERE!!!");
//        }

        try {
            validateParanthesis(this.expression);
            ex = parseExpression();
            if (parenthesesCount != 0) { // Проверка на соответствие количества открытых и закрытых скобок
                throw new ParsingException("Unmatched parentheses: more opening '(' than closing ')' or wise verses");
            }
        } catch (ParsingException e) {
            throw e; // раскомментировать для прохождения ExceptionsTest
        } catch (Exception e) {
            throw new ParsingException("Error parsing expression: ", e); // раскомментировать для прохождения ExceptionsTest
        }

        return ex;
    }

    private void validateParanthesis(String expression) throws ParsingException {
        int balance = 0;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
                if (balance < 0) {
                    throw new ParsingException("Unmatched closing ')' found without corresponding opening '(' at position " + i);
                }
            }
        }
        if (balance != 0) {
            throw new ParsingException("Unmatched parentheses in the expression");
        }
    }

    private TripleExpression parseExpression() {
        TripleExpression result = parseTerm();
        while (true) {
            if (match('+')) {
                result = new Add(result, parseTerm());
            } else if (match('-')) {
                result = new Subtract(result, parseTerm());
            } else {
                return result;
            }
        }
    }

    private TripleExpression parseTerm() {
        TripleExpression result = parseFactor();
        while (true) {
            char nextChar = peek();
            if (match('*')) {
                result = new Multiply(result, parseFactor());
            } else if (match('/')) {
                result = new Divide(result, parseFactor());
            } else if (index < expression.length() && Character.isDigit(nextChar)) {
                throw new ParsingException("Unexpected number without operator at position " + index);
            } else if (index < expression.length() && Character.isLetter(nextChar)) {
                throw new ParsingException("Unexpected variable without operator at position " + index);
            } else {
                return result;
            }
        }
    }

    private TripleExpression parseFactor() {
        skipWhitespace();
        if (match('(')) {
            parenthesesCount++;
            TripleExpression result = parseExpression();
            expect(')');
            parenthesesCount--;
            skipWhitespace();
            // Проверяем, что после закрывающей скобки следует либо оператор, либо конец выражения
            if (parenthesesCount < 0 || (index < expression.length() && "+-*/".indexOf(peek()) == -1 && peek() != ')')) {
                throw new ParsingException("Unexpected character '" + peek() + "' after closing parenthesis");
            }
            return result;
        } else if (match('-')) {
            if (Character.isDigit(peek())) {
                // Проверяем, является ли следующее число минимальным значением для int.
                boolean isNegative = true;
                return new Const(parseNumber(isNegative));
            }
            return new Negate(parseFactor());
        } else if (Character.isDigit(peek())) {
            TripleExpression constant = new Const(parseNumber(false));
            skipWhitespace();
            // Проверяем, что после числа идет оператор или конец строки
            if (index < expression.length() && "+-*/)".indexOf(peek()) == -1) {
                throw new ParsingException("Unexpected character '" + peek() + "' after number at position " + index);
            }
            return constant;
        } else if (Character.isLetter(peek())) {
            TripleExpression variable = new Variable(parseVariable());
            skipWhitespace();
            if (index < expression.length() && "+-*/)".indexOf(peek()) == -1) {
                throw new ParsingException("Unexpected character '" + peek() + "' after variable at position " + index);
            }
            return variable;
        } else {
            throw new ParsingException("Unexpected character: " + peek());
        }
    }

    private int parseNumber(boolean isNegative) {
        StringBuilder number = new StringBuilder(isNegative ? "-" : "");
        while (Character.isDigit(peekWithWitespace())) {
            number.append(expression.charAt(index++));
        }
        if (index < expression.length() && "+-*/)".indexOf(peek()) == -1) {
            throw new ParsingException("Unexpected character '" + peek() + "' after number at position " + index);
        }
        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException e) {
            if (number.toString().equals("-2147483648")) {
                return Integer.MIN_VALUE;
            } else {
                throw new ParsingException("Number out of range for int: " + number.toString());
            }
        }
    }

    private String parseVariable() {
        StringBuilder variable = new StringBuilder();
        while (index < expression.length() && Character.isLetter(peek())) {
            variable.append(expression.charAt(index++));
        }
        String var = variable.toString();
        if (!var.equals("x") && !var.equals("y") && !var.equals("z")) {
            throw new ParsingException("Invalid variable name: " + var);
        }
        return var;
    }

    private boolean match(char expected) {
        skipWhitespace();
        if (index < expression.length() && expression.charAt(index) == expected) {
            index++;
            return true;
        }
        return false;
    }

    private void expect(char expected) {
        skipWhitespace();
        if (index >= expression.length()) {
            if (expected == ')' && parenthesesCount > 0) {
                throw new ParsingException("Unmatched parentheses: expected closing ')'");
            }
            throw new ParsingException("Unexpected end of expression");
        }
        if (expression.charAt(index) != expected) {
            if (expected == ')' && parenthesesCount <= 0) {
                throw new ParsingException("Unmatched parentheses: no opening '(' found for ')'");
            }
            throw new ParsingException("Expected '" + expected + "' but found '" + expression.charAt(index) + "'");
        }
        index++;// Шагаем вперед только если символ соответствует ожидаемому
    }

    private char peek() {
        skipWhitespace();
        if (index >= expression.length()) return '\0';
        return expression.charAt(index);
    }

    private char peekWithWitespace(){
        if (index >= expression.length()) return '\0';
        return expression.charAt(index);
    }

    private void skipWhitespace() {
        while (index < expression.length() && isWhitespaceOrInvisible(expression.charAt(index))) {
            index++;
        }
    }

    private boolean isWhitespaceOrInvisible(char c) {
        // Проверяем, является ли символ пробелом или невидимым символом, таким как табуляция
        return Character.isWhitespace(c) || c == '\t' || c == '\n' || c == '\r';
    }
}