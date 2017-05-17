package com.morfeus.android.mfsdk.ui.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This interface represents core config provided by user.
 */
public abstract class ConfigManager {

    boolean isLoaded = false;
    Map<String, BaseModel> mapIDSubView = null;
    Map<String, Screen> mapIDScreen = null;

    public abstract void loadConfig(OnConfigLoad callback) throws ConfigLoadException;

    @Nullable
    public final BaseModel getSubView(@NonNull String ID) {
        if (!mapIDSubView.containsKey(ID))
            return null;
        return mapIDSubView.get(ID);
    }

    public final Screen getScreen(@NonNull String ID) throws ScreenNotFoundException {
        if (!mapIDScreen.containsKey(ID)) {
            throw new ScreenNotFoundException("Error: " + ID + " screen not found!");
        }
        return mapIDScreen.get(checkNotNull(ID));
    }

    public void registerScreen(@NonNull String id, @NonNull Screen screen) {
        checkNotNull(id);
        checkNotNull(screen);
        mapIDScreen.put(id, screen);
    }

    public void registerSubView(@NonNull String id, @NonNull BaseModel baseModel) {
        checkNotNull(id);
        checkNotNull(baseModel);
        mapIDSubView.put(id, baseModel);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public interface OnConfigLoad {
        void onSuccess(@NonNull Object configObject);

        void onFail(String errMsg);
    }

}
