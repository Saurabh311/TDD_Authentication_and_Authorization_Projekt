package org.login;

import org.login.utils.Encryption;
import java.util.HashMap;

public class Login {
    private final HashMap<String, User > userDB = new HashMap<>();

    public Login() {
        addUser("anna", "losen");
        addUser("berit", "123456");
        addUser("kalle", "password");
    }

    public boolean varifyUserAndPassword (String userName, String password) {
        String salt = Encryption.generateSalt(512).get();
        String key = Encryption.hashPassword(password, salt).get();

        return userDB.entrySet().stream().filter(user -> user.getKey().equals(userName)).map(user -> (User) user.getValue()).anyMatch(userMatch -> Encryption.verifyPassword(password, userMatch.getPassword(), userMatch.getSalt()));
    }

    public void addUser(String user, String password){
        String salt = Encryption.generateSalt(512).get();
        String passwordKey = Encryption.hashPassword(password, salt).get();
        userDB.put(user,new User(user, passwordKey, salt));
    }


}
