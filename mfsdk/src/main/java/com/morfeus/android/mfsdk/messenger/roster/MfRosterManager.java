package com.morfeus.android.mfsdk.messenger.roster;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for
 */
public class MfRosterManager {
    private static MfRosterManager sInstance;
    private Map<String, MfRosterContact> mMapIdContacts;

    private MfRosterManager() {
        mMapIdContacts = new HashMap<>();
    }

    public static MfRosterManager getInstance() {
        if (sInstance == null)
            sInstance = new MfRosterManager();
        return sInstance;
    }


    public void addContact(@NonNull MfRosterContact contact) {
        mMapIdContacts.put(contact.getUserId(), contact);
    }

    public List<MfRosterContact> getAllContacts() {
        return new ArrayList<>(mMapIdContacts.values());
    }

    public void clear() {
        mMapIdContacts.clear();
    }

    public void removeContact(@NonNull String id) {
        mMapIdContacts.remove(id);
    }

    public void updateContact(@NonNull MfRosterContact contact) {
        mMapIdContacts.put(contact.getUserId(), contact);
    }

    public MfRosterContact getContact(@NonNull String id) {
        return mMapIdContacts.get(id);
    }

    public static class MfRosterContact {
        private final String userId;
        private String displayName;
        private String status;


        public MfRosterContact(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
