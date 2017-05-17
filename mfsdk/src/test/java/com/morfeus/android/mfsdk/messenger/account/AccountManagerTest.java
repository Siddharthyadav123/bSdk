package com.morfeus.android.mfsdk.messenger.account;

import com.morfeus.android.mfsdk.messenger.account.AccountManager.MfAccount;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagerTest {

    private AccountManager mAcountManager;
    private MfAccount mMockAccount;

    @Before
    public void setUp() throws Exception {
        mMockAccount = mock(MfAccount.class);
    }

    @Test
    public void testCreateAccount() {
        AccountManager.destroyInstance();
        mAcountManager = AccountManager.getInstance();

        mAcountManager.createAccount(mMockAccount);
        assertNotNull(mAcountManager.getAccount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccount_throwException() {
        AccountManager.destroyInstance();
        mAcountManager = AccountManager.getInstance();

        mAcountManager.createAccount(mMockAccount);
        mAcountManager.createAccount(mMockAccount);
    }

    @Test
    public void testGetJId() {
        AccountManager.destroyInstance();
        mAcountManager = AccountManager.getInstance();

        when(mMockAccount.getUserId()).thenReturn("jID");
        mAcountManager.createAccount(mMockAccount);
        String jId = mAcountManager.getUserId();
        assertNotNull(jId);
        assertEquals("jID", jId);
    }

    @Test
    public void testGetPassword() {
        AccountManager.destroyInstance();
        mAcountManager = AccountManager.getInstance();

        when(mMockAccount.getPassword()).thenReturn("password");
        mAcountManager.createAccount(mMockAccount);
        String password = mAcountManager.getPassword();
        assertNotNull(password);
        assertEquals("password", password);
    }

    @Test
    public void testGetName() {
        AccountManager.destroyInstance();
        mAcountManager = AccountManager.getInstance();

        when(mMockAccount.getDisplayName()).thenReturn("name");
        mAcountManager.createAccount(mMockAccount);
        String name = mAcountManager.getDisplayName();
        assertNotNull(name);
        assertEquals("name", name);
    }
}