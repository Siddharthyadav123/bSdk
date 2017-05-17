package com.morfeus.android.mfsdk.messenger.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

// TODO  change to Msg repo interface
public class MsgDataSource {

    private static final String TAG = MsgDataSource.class.getSimpleName();

    private static MsgDataSource INSTANCE;

    private MsgParser mMsgParser;

    private Context mContext;

    private MfMsgModel mMfMsgModel;

    private MsgDataSource(Context context, MsgParser msgParser) {
        mMsgParser = msgParser;
        mContext = context;
    }

    /**
     * Please provide application context while creating {@link MsgDataSource}
     */
    public static MsgDataSource createInstance(@NonNull Context context,
                                               @NonNull MsgParser msgParser) {
        if (INSTANCE == null) {
            checkNotNull(context);
            checkNotNull(msgParser);
            INSTANCE = new MsgDataSource(context, msgParser);
        }
        return INSTANCE;
    }

    public static MsgDataSource getInstance() {
        return INSTANCE;
    }

    void sendMsg() {
        // TODO impl later
    }

    public void loadDefaultMsg() {
        String jsonStr = loadJSONFromAsset(mContext, "MFMessage.json");
        if (TextUtils.isEmpty(jsonStr)) {
            throw new NullPointerException("Error: Message json string can't be null!");
        }

        mMfMsgModel = (MfMsgModel) mMsgParser.parse(true, jsonStr);
        LogManager.d(TAG, "MfMsgModel: " + mMfMsgModel.getMsgItems().size());
    }

    public MfMsgModel getDefaultMsg() {
        return mMfMsgModel;
    }

    // TODO temp method to test message flow. Remove after network implementation
    private String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
