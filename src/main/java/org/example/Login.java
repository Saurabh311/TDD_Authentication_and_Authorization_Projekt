package org.example;

import java.util.HashMap;

public class Login {
    private HashMap<String, String > userDB = new HashMap<>();

    public Login() {
        this.userDB.put("anna", "losen");
        this.userDB.put("berit", "123456");
        this.userDB.put("kalle", "password");
    }

    public boolean varifyUserAndPassword (User user) {
        Encryption encryption = new Encryption();
        String salt = encryption.generateSalt(512).get();

        String key = encryption.hashPassword(user.password, salt).get();
        System.out.println(key);

        if (userDB.containsKey(user.userName)) {
            String dbPassword = userDB.get(user.userName);
            String dbPasswordKey = encryption.hashPassword(dbPassword, salt).get();
            System.out.println(dbPasswordKey);
            if (dbPasswordKey.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
