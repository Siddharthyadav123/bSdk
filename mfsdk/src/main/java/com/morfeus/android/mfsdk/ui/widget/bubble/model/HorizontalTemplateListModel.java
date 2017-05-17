package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgItems;
import com.morfeus.android.mfsdk.ui.widget.bubble.HorizontalTemplateListView;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link HorizontalTemplateListModel} is <b>immutable</b> data model which represents the
 * horizontal template sub-list. This model is for displaying horizontal list of Templates.
 */
public final class HorizontalTemplateListModel extends TemplateModel {

    private static final String TAG = HorizontalTemplateListView.class.getSimpleName();

    private final List<TemplateModel> subTemplateModelList = new ArrayList<>();

    private MfMsgItems mMfMsgItems;

    private MFCardData mMfCardData;

    public HorizontalTemplateListModel(@NonNull List<TemplateModel> subTemplateModelList,
                                       @NonNull String templateID) {
        checkNotNull(templateID);
        checkNotNull(subTemplateModelList);

        this.templateID = templateID;
        this.subTemplateModelList.addAll(subTemplateModelList);
    }

    public List<TemplateModel> getSubTemplateModelList() {
        return subTemplateModelList;
    }

    public void setSubTemplateModelList(List<TemplateModel> templateModelList) {
        subTemplateModelList.addAll(templateModelList);
    }


    @Override
    public void deserialize(JsonArray cardsJson) {

        TemplateModelFactory modelFactory = TemplateModelFactory.getInstance();
        for (int i = 0; i < cardsJson.size(); i++) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(cardsJson.get(i));
            TemplateModel templateModel
                    = modelFactory.getTemplateModel(mMfCardData.getTemplateType());
            templateModel.deserialize(jsonArray);

            if (templateModel != null) {
                TemplateModel newTemplateModel = null;
                try {
                    newTemplateModel = (TemplateModel) templateModel.clone();
                    newTemplateModel.setCardData(mMfCardData);
                    newTemplateModel.setTemplateID(mMfCardData.getTemplateType());
                    newTemplateModel.setTo(mMfMsgItems.getTo());
                    newTemplateModel.setFrom(mMfMsgItems.getFrom());
                    newTemplateModel.setMsgId(mMfMsgItems.getMsgId());
                    newTemplateModel.setIncoming(mMfMsgItems.isInComing());
                    newTemplateModel.setShowBotIcon(false);
                    subTemplateModelList.add(newTemplateModel);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMfMsgItems(MfMsgItems mfMsgItems) {
        mMfMsgItems = mfMsgItems;
    }

    public void setMfCardData(MFCardData mfCardData) {
        mMfCardData = mfCardData;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
