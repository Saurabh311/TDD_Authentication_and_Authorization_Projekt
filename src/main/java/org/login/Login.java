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
    private final HashMap<String, User> userDB = new HashMap<>();

    public Login() {
        addUser("anna", "losen" );
        addUser("berit", "123456");
        addUser("kalle", "password");
        addAuthorizationsToUser("anna", Resource.ACCOUNT, List.of(Rights.READ));
        addAuthorizationsToUser("berit", Resource.ACCOUNT, List.of(Rights.READ,Rights.WRITE));
        addAuthorizationsToUser("kalle", Resource.PROVISION_CALC, List.of(Rights.EXECUTE));    }


    public boolean login(String userName, String password){
        return userDB.entrySet().stream().filter(user ->
                user.getKey().equals(userName)).map(user ->
                user.getValue()).anyMatch(userMatch ->
                Encryption.verifyPassword(password, userMatch.getPassword(), userMatch.getSalt()));
    }

    public boolean varifyUser_generateToken(String userName, String password)throws InvalidUserInputException {
        String token = varifyUserGenarteToken(userName,password);
        boolean isVerified = JWT.verifyUserToken(token, userName);
        if (isVerified){
            return isVerified;
        }else{
            throw new InvalidUserInputException();
        }
    }

    public String varifyUserGenarteToken (String userName, String password)throws InvalidUserInputException {
        User user = getUserDB().get(userName);
        if (login(userName,password)){
            return JWT.createJWT(user.getUserName());
        }
        throw new InvalidUserInputException();
    }

    public void addUser(String user, String password){
        String salt = Encryption.generateSalt(512).get();
        String passwordKey = Encryption.hashPassword(password, salt).get();
        userDB.put(user,new User(user, passwordKey, salt, new HashMap<>()));
    }

    public HashMap<String, User> getUserDB(){
        return userDB;
    }

    public void addAuthorizationsToUser(String userName, Resource resource, List<Rights> rights) {
        userDB.get(userName).getAuthorizations().put(resource, rights);
    }

    public List<Rights> getUserPermissions(String token, Resource resource) {
        return userDB.get(JWT.getUsernameFromToken(token)).getAuthorizations().get(resource);
    }
}
