package com.morfeus.android.mfsdk.messenger.source.entity;

import java.util.List;

public final class MfMsgModel {
    private List<MfMsgItems> mfMsgItemsList = null;

    public MfMsgModel(List<MfMsgItems> mfMsgItemsList) {
        this.mfMsgItemsList = mfMsgItemsList;
    }

    public List<MfMsgItems> getMsgItems() {
        return mfMsgItemsList;
    }

    @Override
    public String toString() {
        return "MfMsgModel{" +
                "mfMsgItemsList=" + mfMsgItemsList +
                '}';
    }
}
