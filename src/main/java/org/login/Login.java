package org.login;

import org.login.enums.Resource;
import org.login.enums.Rights;
import org.login.exceptions.InvalidUserInputException;
import org.login.models.User;
import org.login.utils.Encryption;
import org.login.utils.JWT;

import java.util.HashMap;
import java.util.List;

public class Login {
    private final HashMap<String, User> userDb = new HashMap<>();

    public Login() {
        addUser("anna", "losen");
        addUser("berit", "123456");
        addUser("kalle", "password");
        addAuthorizationsToUser("anna", Resource.ACCOUNT, List.of(Rights.READ));
        addAuthorizationsToUser("berit", Resource.ACCOUNT, List.of(Rights.READ, Rights.WRITE));
        addAuthorizationsToUser("kalle", Resource.PROVISION_CALC, List.of(Rights.EXECUTE));
    }


    public boolean login(String userName, String password) {
        return userDb.entrySet().stream().filter(user ->
                user.getKey().equals(userName)).map(user ->
                user.getValue()).anyMatch(userMatch ->
                Encryption.verifyPassword(password, userMatch.getPassword(), userMatch.getSalt()));
    }

    public boolean varifyUser_generateToken(String userName, String password) throws InvalidUserInputException {
        String token = generateUserToken(userName, password);
        boolean isVerified = JWT.verifyUserToken(token, userName);
        if (isVerified) {
            return isVerified;
        } else {
            throw new InvalidUserInputException();
        }
    }

    public String generateUserToken(String userName, String password) throws InvalidUserInputException {
        User user = getUserDb().get(userName);
        if (login(userName, password)) {
            return JWT.createJWT(user.getUserName());
        }
        throw new InvalidUserInputException();
    }

    public void addUser(String user, String password) {
        String salt = Encryption.generateSalt(512).get();
        String passwordKey = Encryption.hashPassword(password, salt).get();
        userDb.put(user, new User(user, passwordKey, salt, new HashMap<>()));
    }

    public HashMap<String, User> getUserDb() {
        return userDb;
    }

    public void addAuthorizationsToUser(String userName, Resource resource, List<Rights> rights) {
        userDb.get(userName).getAuthorizations().put(resource, rights);
    }

    public List<Rights> getUserPermissions(String token, Resource resource) {
        return userDb.get(JWT.getUsernameFromToken(token)).getAuthorizations().get(resource);
    }
}
