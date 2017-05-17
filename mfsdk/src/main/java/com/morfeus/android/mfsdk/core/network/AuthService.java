package com.morfeus.android.mfsdk.core.network;

/**
 * This class is responsible for triggering authentication service
 */
public class AuthService implements NetService.Auth {

    private static AuthService INSTANCE;


    private AuthService() {

    }

    public static AuthService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthService();
        }
        return INSTANCE;
    }


    @Override
    public void login(String username, String password) {

    }
}
