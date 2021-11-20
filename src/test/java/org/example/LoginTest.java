package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    @ParameterizedTest
    @CsvSource({
            "anna, losen",
            "berit, 123456",
            "kalle, password"
    })
    void test_username_password_list_success(String userName, String password){
        User user = new User(userName, password);
        Login login = new Login();
        assertEquals(true, login.varifyUserAndPassword(user));

    }
}
