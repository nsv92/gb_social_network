package ru.gb.backend.exceptions;

public class TokenParserException extends RuntimeException {
    public TokenParserException(String message) {
        super(message);
    }

    public TokenParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
