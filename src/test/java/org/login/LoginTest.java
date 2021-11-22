package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })
    void test_username_password_list_success(String userName, String password){
        Login login = new Login();
        assertTrue(login.varifyUserAndPassword(userName, password));
    }

    @ParameterizedTest
    @CsvSource({
            "wrongUser, losen",
            "wrongBerit, 123456",
            "WrongKalle, password"
    })
    void test_username_password_list_with_wrong_UserName(String userName, String password){
        Login login = new Login();
        assertFalse(login.varifyUserAndPassword(userName, password));
    }

    @ParameterizedTest
    @CsvSource({
            "anna, wrongPassword",
            "berit, passwordWrong",
    })
    void test_username_password_list_with_wrong_password(String userName, String password){
        Login login = new Login();
        assertFalse(login.varifyUserAndPassword(userName, password));
    }

}
