package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.login.enums.Resource;
import org.login.enums.Rights;
import org.login.exceptions.InvalidUserInputException;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TokenAuthenticationTest {
    Login login = new Login();

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("anna", "losen", Resource.ACCOUNT, List.of(Rights.READ)),
                Arguments.of("berit", "123456", Resource.ACCOUNT, List.of(Rights.READ, Rights.WRITE)),
                Arguments.of("kalle", "password", Resource.PROVISION_CALC, List.of(Rights.EXECUTE))
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void test_user_encrypted_password_generateToken(String user, String password) throws InvalidUserInputException {
        String token = login.varifyUserGenarteToken(user, password);
        assertNotNull(token);
        assertNotEquals("", token);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void test_username_token_success(String userName, String password) throws InvalidUserInputException {
        assertTrue(login.varifyUser_generateToken(userName, password));
    }

    @Test
    void test_failedLogin_wrongUser_exception_fail() {
        InvalidUserInputException exception = assertThrows(InvalidUserInputException.class,
                () -> login.varifyUser_generateToken("wrong", "losen"));
        assertEquals("Login Failed", exception.getMessage());
    }

    @Test
    void test_failedLogin_wrongPassword_exception_fail() {
        InvalidUserInputException exception = assertThrows(InvalidUserInputException.class,
                () -> login.varifyUser_generateToken("anna", "wrong"));
        assertEquals("Login Failed", exception.getMessage());
    }
}
