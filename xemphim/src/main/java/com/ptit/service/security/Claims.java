package com.ptit.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ptit.service.model.entity.User;

public class Claims {
    private String accountID;
    private String userName;

    public Claims(String accountID,String userName) {
        this.accountID = accountID;
        this.userName = userName;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static Claims from(DecodedJWT decodedJWT) {
        return new Claims(
                decodedJWT.getClaim("id").asString(),
                decodedJWT.getClaim("username").asString()
        );
    }

    public static Claims from(User user) {
        return new Claims(
                user.getId(),
                user.getUsername()
        );
    }
}
