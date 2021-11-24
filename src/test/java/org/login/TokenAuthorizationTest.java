package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.login.enums.Resource;
import org.login.exceptions.InvalidUserInputException;

import static org.junit.jupiter.api.Assertions.*;

public class TokenAuthorizationTest {
    Login login = new Login();

    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })

    void test_user_encrypted_password_generateToken(String user, String password) throws InvalidUserInputException {
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
    void test_username_token_success(String userName, String password) throws InvalidUserInputException {
        assertTrue(login.varifyUserAndToken(userName, password));
    }

    @Test
    void test_failedLogin_exception() throws InvalidUserInputException {
        InvalidUserInputException exception = new InvalidUserInputException();
        assertEquals("Login failed", login.varifyUserAndToken("wrong", "gassword"));
    }

    @ParameterizedTest
    @CsvSource({
            "anna, losen, ACCOUNT" ,
            "berit, 123456, ACCOUNT",
            "kalle, password, PROVISION_CALC"
    })
    void test_user_rights_success(String username, String password, Resource resource) throws InvalidUserInputException {

        assertDoesNotThrow(() -> login.varifyUserAndToken(username, password));
        String token = login.varifyUserGenarteToken(username, password);
        assertNotNull(login.getUserPermissions(token, resource));
        assertFalse(login.getUserPermissions(token, resource).isEmpty());
    }
}
