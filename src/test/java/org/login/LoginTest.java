package org.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.login.enums.Resource;
import org.login.enums.Rights;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    Login login = new Login();

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("anna", "losen", Resource.ACCOUNT, List.of(Rights.READ)),
                Arguments.of("berit", "123456", Resource.ACCOUNT, List.of(Rights.READ, Rights.WRITE)),
                Arguments.of("kalle", "password", Resource.PROVISION_CALC, List.of(Rights.EXECUTE))
        );
    }

    @Test
    void test_userName_paswword_success(){
        assertTrue(login.login("anna", "losen"));
    }

    @Test
    void test_userName_wrongPaswword_fail(){
        assertFalse(login.login("anna", "passvard"));
    }

    @Test
    void test_userName_wrongUser_fail(){
        assertFalse(login.login("wrong", "losen"));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void test_userName_password_list_success(String userName, String password){
        assertTrue(login.login(userName, password));
    }

    @ParameterizedTest
    @CsvSource({
            "wrong, losen",
            "berit, wrong",
            "Kalle, 123456"
    })
    void test_wrongUsername_wrongPassword_list_fail(String userName, String password){
        assertFalse(login.login(userName, password));
    }
}
