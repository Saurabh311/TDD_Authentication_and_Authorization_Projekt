package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.login.exceptions.WrongUserInputException;

import static org.junit.jupiter.api.Assertions.*;

public class TokenAuthorizationTest {
    Login login = new Login();

    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })

    void test_user_encrypted_password_generateToken(String user, String password) throws WrongUserInputException{
        String token = login.varifyUserGenarteToken(user, password);
        assertNotNull(token);
        assertNotEquals("", token);
    }

    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })
    void test_username_token_success(String userName, String password) throws WrongUserInputException {
        assertTrue(login.varifyUserAndToken(userName, password));
    }

    @Test
    void test_failedLogin_exception() throws WrongUserInputException {
        WrongUserInputException exception = new WrongUserInputException("Login failed");
        assertEquals("Login failed", exception.getMessage());
    }
}
