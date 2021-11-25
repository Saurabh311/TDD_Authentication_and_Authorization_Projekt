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
import static org.login.enums.Rights.READ;
import static org.login.enums.Rights.WRITE;

public class AuthorizationTest {
    Login login = new Login();

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("anna", "losen", Resource.ACCOUNT, List.of(Rights.READ)),
                Arguments.of("berit", "123456", Resource.ACCOUNT, List.of(Rights.READ, Rights.WRITE)),
                Arguments.of("kalle", "password", Resource.PROVISION_CALC, List.of(Rights.EXECUTE))
        );
    }

    @Test
    void test_rights_of_user_success() throws InvalidUserInputException {
        String token = login.generateUserToken("berit", "123456");
        List<Rights> result = login.getUserPermissions(token, Resource.ACCOUNT);
        assertEquals(List.of(READ,WRITE), result);
    }
    @ParameterizedTest
    @MethodSource("testData")
    void test_user_rights_success(String username, String password, Resource resource) throws InvalidUserInputException {
        assertDoesNotThrow(() -> login.varifyUser_generateToken(username, password));
        String token = login.generateUserToken(username, password);
        assertNotNull(login.getUserPermissions(token, resource));
        assertFalse(login.getUserPermissions(token, resource).isEmpty());
    }
}
