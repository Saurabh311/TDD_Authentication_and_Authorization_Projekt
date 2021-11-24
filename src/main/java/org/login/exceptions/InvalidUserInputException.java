package org.login.exceptions;

public class InvalidUserInputException extends Exception {
    public InvalidUserInputException() {
        super("Login Failed");
    }
}
