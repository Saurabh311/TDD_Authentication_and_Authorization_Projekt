package org.login.models;

import org.login.enums.Resource;
import org.login.enums.Rights;

import java.util.HashMap;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private String salt;
    private HashMap<Resource, List<Rights>> authorizations;

    public User(String userName, String password, String salt, HashMap<Resource, List<Rights>> authorizations) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.authorizations = authorizations;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public HashMap<Resource, List<Rights>> getAuthorizations() {
        return authorizations;
    }
}

