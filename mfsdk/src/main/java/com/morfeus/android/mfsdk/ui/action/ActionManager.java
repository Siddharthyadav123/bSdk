package com.morfeus.android.mfsdk.ui.action;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.action.entity.Action;
import com.morfeus.android.mfsdk.ui.action.exception.UnsupportedActionException;

/**
 * This interface exposes the set of api
 * provided by action manager.
 */
//TODO command or executor
public interface ActionManager {

    /**
     * Get called on start of MfSdk
     */
    void onStart();

    /**
     * Get called on exit of MfSdk
     */
    void onStop();

    /**
     * Execute Action need to be executed
     *
     * @param action
     * @throws UnsupportedActionException
     */
    void execute(@NonNull Action action) throws UnsupportedActionException;
}
