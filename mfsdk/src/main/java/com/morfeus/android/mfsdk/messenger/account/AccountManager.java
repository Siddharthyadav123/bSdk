package com.morfeus.android.mfsdk.messenger.account;

/**
 * This class is responsible for providing and managing user account details
 */
public class AccountManager {
    private static final String TAG = AccountManager.class.getSimpleName();
    private static AccountManager sInstance;
    private MfAccount mAccount;

    private AccountManager() {
        // No-op
    }

    public static AccountManager getInstance() {
        if (sInstance == null)
            sInstance = new AccountManager();
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public void createAccount(MfAccount account) {
        if (mAccount == null) {
            mAccount = account;
        } else {
            throw new IllegalArgumentException(TAG + ", Error: Already created account!");
        }
    }

    public MfAccount getAccount() {
        return mAccount;
    }

    public String getUserId() {
        return mAccount.getUserId();
    }

    public String getPassword() {
        return mAccount.getPassword();
    }

    public String getDisplayName() {
        return mAccount.getDisplayName();
    }

    public void clear() {
        mAccount = null;
    }

    public static class MfAccount {
        private final String userId;
        private String password;
        private String displayName;
        private String customerId;
        private String appSessionId;
        private String applicationId;

        public MfAccount(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getAppSessionId() {
            return appSessionId;
        }

        public void setAppSessionId(String appSessionId) {
            this.appSessionId = appSessionId;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

}
