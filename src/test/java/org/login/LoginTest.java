package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.login.exceptions.InvalidUserInputException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    Login login = new Login();
    void setUp() {
        login.addUser("anna", "losen");
        login.addUser("berit", "123456");
        login.addUser("kalle", "password");
    }
    @Test
    void test_userName_paswword_success() {
        assertTrue(login.varifyUserAndPassword("anna", "losen"));
    }

    @Test
    void test_userName_paswword_Fail() {
        assertFalse(login.varifyUserAndPassword("Wrong", "passvard"));
    }

    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })
    void test_userName_password_list_success(String userName, String password) throws InvalidUserInputException {
        assertTrue(login.varifyUserAndPassword(userName, password));
    }

    @ParameterizedTest
    @CsvSource({
            "wrongUser, losen",
            "wrongBerit, 123456",
            "WrongKalle, password"
    })
    void test_username_password_list_with_wrong_UserName(String userName, String password) throws InvalidUserInputException {
        assertFalse(login.varifyUserAndPassword(userName, password));
    }

    @ParameterizedTest
    @CsvSource({
            "anna, wrongPassword",
            "berit, passwordWrong",
    })
    void test_username_password_list_with_wrong_password(String userName, String password) throws InvalidUserInputException {
        assertFalse(login.varifyUserAndPassword(userName, password));
    }

}
