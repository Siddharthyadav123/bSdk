package com.morfeus.android.mfsdk.messenger.message;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.morfeus.android.mfsdk.messenger.message.MessageStatus.READ;
import static com.morfeus.android.mfsdk.messenger.message.MessageStatus.SEND;
import static com.morfeus.android.mfsdk.messenger.message.MessageStatus.UNSEND;
import static com.morfeus.android.mfsdk.messenger.message.MessageStatus.FAILED;

/**
 * Represents the status of message
 */
@IntDef({UNSEND, SEND, READ, FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface MessageStatus {
    int UNSEND = 0;
    int SEND = 1;
    int READ = 2;
    int FAILED = 3;
}
