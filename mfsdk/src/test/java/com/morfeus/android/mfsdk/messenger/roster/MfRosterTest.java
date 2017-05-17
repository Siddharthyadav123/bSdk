package com.morfeus.android.mfsdk.messenger.roster;

import com.morfeus.android.mfsdk.messenger.roster.MfRosterManager.MfRosterContact;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MfRosterTest {
    private MfRosterManager mRoster;

    @Before
    public void setUp() throws Exception {
        mRoster = MfRosterManager.getInstance();
    }

    @Test
    public void testAddContact() {
        clearRoster();
        MfRosterContact mockContact = mock(MfRosterContact.class);
        when(mockContact.getUserId()).thenReturn("1");
        MfRosterContact mockContact2 = mock(MfRosterContact.class);
        when(mockContact2.getUserId()).thenReturn("2");
        mRoster.addContact(mockContact);
        assertEquals(1, mRoster.getAllContacts().size());
        mRoster.addContact(mockContact2);
        assertEquals(2, mRoster.getAllContacts().size());
    }

    @Test
    public void testRemoveContact() {
        clearRoster();
        MfRosterContact mockContact = mock(MfRosterContact.class);
        when(mockContact.getUserId()).thenReturn("1");
        mRoster.addContact(mockContact);
        assertEquals(1, mRoster.getAllContacts().size());
        mRoster.removeContact("1");
        assertEquals(0, mRoster.getAllContacts().size());

    }

    @Test
    public void updateContact() {
        MfRosterContact mockContact = mock(MfRosterContact.class);
        when(mockContact.getUserId()).thenReturn("1");
        when(mockContact.getStatus()).thenReturn("online");
        mRoster.addContact(mockContact);
        MfRosterContact contact = mRoster.getContact("1");
        assertEquals(contact.getStatus(), "online");

        when(mockContact.getStatus()).thenReturn("offline");
        mRoster.updateContact(mockContact);
        contact = mRoster.getContact("1");
        assertEquals(contact.getStatus(), "offline");
    }

    private void clearRoster() {
        mRoster.clear();
    }
}