package com.morfeus.android.mfsdk.ui.lang;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.morfeus.android.mfsdk.log.LogManager;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides the Text resource for the template.
 */
public class LanguageRepository {
    private static final String TAG = LanguageRepository.class.getSimpleName();
    private static LanguageRepository sINSTANCE;
    private HashMap<String, TextResource> mMapLanguageTextResource;

    private boolean mMultiLanguageSupportEnabled;
    private TextResource mSelectedTextResource;

    private LanguageRepository() {
        mMapLanguageTextResource = new HashMap<>();
    }

    public static LanguageRepository getInstance() {
        if (sINSTANCE == null)
            sINSTANCE = new LanguageRepository();
        return sINSTANCE;
    }

    /**
     * Method to add a particular language resource
     *
     * @param textResource
     */
    public void add(@NonNull TextResource textResource) {
        checkNotNull(textResource);
        String lang = textResource.getLang();
        if (!TextUtils.isEmpty(lang)) {
            if (!mMapLanguageTextResource.containsKey(lang)) {
                LogManager.i(TAG, "LANGUAGE REPOSITORY: ADDED = " + lang);
                mMapLanguageTextResource.put(lang, textResource);
            }
        }
    }

    /**
     * Method to remove a particular language resource
     *
     * @param textResource
     */
    public void remove(@NonNull TextResource textResource) {
        checkNotNull(textResource);
        String lang = textResource.getLang();
        if (!TextUtils.isEmpty(lang)) {
            if (mMapLanguageTextResource.containsKey(lang)) {
                LogManager.i(TAG, "LANGUAGE REPOSITORY: REMOVED = " + lang);
                mMapLanguageTextResource.remove(lang);
            }
        }
    }


    public void setmMultiLanguageSupportEnabled(boolean mMultiLanguageSupportEnabled) {
        this.mMultiLanguageSupportEnabled = mMultiLanguageSupportEnabled;
    }


    public void setSelectedLanguage(String selectedLanguage) {
        try {
            LogManager.i(TAG, "LANGUAGE REPOSITORY: SELECTED LANGUAGE = " + selectedLanguage);
            mSelectedTextResource = getLanguageTextResource(selectedLanguage);
        } catch (LanguageNotFoundException e) {
            LogManager.d(TAG, "setSelectedLanguage: " + e.getMessage());
        }
    }

    @Nullable
    public TextResource getLanguageTextResource(@NonNull String language) throws LanguageNotFoundException {
        checkNotNull(language);
        if (mMapLanguageTextResource.containsKey(language)) {
            return mMapLanguageTextResource.get(language);
        } else {
            throw new LanguageNotFoundException("Error: " + language + " not found!");
        }
    }

    public String getText(String key) {
        if (mMultiLanguageSupportEnabled
                && mSelectedTextResource != null) {
            String value = mSelectedTextResource.getResource().get(key);
            if (value != null && value.length() > 0) {
                return value;
            }
        }
        return key;
    }

    public String getLanguageCode() {
        if (mSelectedTextResource != null) {
            return mSelectedTextResource.getLang();
        }
        //sending default language as us english if no language found
        return "en-US";
    }
}
