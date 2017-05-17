package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Interface represents the <b>immutable</b> data model for
 * {@link com.morfeus.android.mfsdk.ui.widget.bubble.TemplateView}
 */
public abstract class TemplateModel implements Cloneable {
    protected boolean isIncoming;

    protected String to;

    protected String from;

    protected String msgId;

    protected int status;

    protected String templateID;

    protected MFCardData cardData;

    protected boolean showBotIcon = true;

    protected boolean textToSpeech;

    protected String ttsText;

    public abstract void deserialize(JsonArray jsonArray);

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isShowBotIcon() {
        return showBotIcon;
    }

    public void setShowBotIcon(boolean showBotIcon) {
        this.showBotIcon = showBotIcon;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTemplateID() {
        return this.templateID;
    }

    public void setTemplateID(@NonNull String templateID) {
        checkNotNull(templateID);
        this.templateID = templateID;
    }

    public int getStatus() {
        return status;
    }

    public void setMsgStatus(@MessageStatus int status) {
        this.status = status;
    }

    public MFCardData getCardData() {
        return cardData;
    }

    public void setCardData(@NonNull MFCardData MFCardData) {
        checkNotNull(MFCardData);
        this.cardData = MFCardData;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public void setIncoming(boolean incoming) {
        isIncoming = incoming;
    }


    public boolean isTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(boolean textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public String getTtsText() {
        return ttsText;
    }

    public void setTtsText(String ttsText) {
        this.ttsText = ttsText;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemplateModel)) return false;

        TemplateModel that = (TemplateModel) o;

        if (isIncoming != that.isIncoming) return false;
        if (status != that.status) return false;
        if (showBotIcon != that.showBotIcon) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (msgId != null ? !msgId.equals(that.msgId) : that.msgId != null) return false;
        if (templateID != null ? !templateID.equals(that.templateID) : that.templateID != null)
            return false;
        if (templateID != null ? !templateID.equals(that.templateID) : that.templateID != null)
            return false;
        if (textToSpeech != that.textToSpeech) return false;
        if (ttsText != null ? !ttsText.equals(that.ttsText) : that.ttsText != null)
            return false;
        return cardData != null ? cardData.equals(that.cardData) : that.cardData == null;

    }

    @Override
    public int hashCode() {
        int result = (isIncoming ? 1 : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (msgId != null ? msgId.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (templateID != null ? templateID.hashCode() : 0);
        result = 31 * result + (cardData != null ? cardData.hashCode() : 0);
        result = 31 * result + (showBotIcon ? 1 : 0);
        result = 31 * result + (textToSpeech ? 1 : 0);
        result = 31 * result + (ttsText != null ? ttsText.hashCode() : 0);
        return result;
    }
}
