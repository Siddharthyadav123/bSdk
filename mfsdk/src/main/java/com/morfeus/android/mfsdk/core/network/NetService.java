package com.morfeus.android.mfsdk.core.network;

/**
 * Created by User on 9/11/2016.
 */

public interface NetService {

    interface Auth {
        void login(String username, String password);
    }
}
