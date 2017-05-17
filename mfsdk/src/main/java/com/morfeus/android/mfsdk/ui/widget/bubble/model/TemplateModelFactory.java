package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory used for registering {@link TemplateModel} and providing
 * {@link TemplateModel} by Template ID.
 */
public final class TemplateModelFactory {
    private static TemplateModelFactory sInstance;

    private HashMap<String, TemplateModel> mMapIDTemplateModel = new HashMap<>();

    private TemplateModelFactory() {
        registerTemplateModel("InfoCardTemplate",
                new MfInfoCardTemplateModel(
                        "InfoCardTemplate",
                        new MFCardData()
                ));
        registerTemplateModel("HorizontalTemplates",
                new HorizontalTemplateListModel(
                        new ArrayList<TemplateModel>(),
                        "HorizontalTemplates"
                ));

        registerTemplateModel("ListCardTemplate",
                new MfListCardTemplateModel(
                        "ListCardTemplate",
                        new MFCardData()
                ));

        registerTemplateModel("text",
                new MfTextCardTemplateModel(
                        "text"
                ));

        registerTemplateModel("image",
                new MfImageCardTemplateModel(
                        "image"
                ));

        registerTemplateModel("SimleyCardTemplate",
                new StickerTemplateModel(
                        "SimleyCardTemplate"
                ));

        registerTemplateModel("ButtonCardTemplate",
                new MfButtonCardTemplateModel(
                        "ButtonCardTemplate",
                        new MFCardData()
                ));

        registerTemplateModel("RecieptCardTemplate",
                new MfRecieptCardTemplateModel(
                        "RecieptCardTemplate",
                        new MFCardData()
                ));

        registerTemplateModel("TouchIdCardTemplate",
                new MfTouchIdCardTemplateModel(
                        "TouchIdCardTemplate",
                        new MFCardData()
                ));
        registerTemplateModel("ButtonCardTemplateWithLimit",
                new MfButtonCardTemplateWithLimitModel(
                        "ButtonCardTemplateWithLimit",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithCardInfo",
                new MfButtonCardTemplateWithCardInfoModel(
                        "ButtonCardTemplateWithCardInfo",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithStatusInfo",
                new MfButtonCardTemplateWithStatusInfoModel(
                        "ButtonCardTemplateWithStatusInfo",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithReminderInfo",
                new MfButtonCardTemplateWithReminderInfoModel(
                        "ButtonCardTemplateWithReminderInfo",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithTransactionInfo",
                new MfButtonCardTemplateWithTransactionInfoModel(
                        "ButtonCardTemplateWithTransactionInfo",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithImageInfo",
                new MfButtonCardTemplateWithImageInfoModel(
                        "ButtonCardTemplateWithImageInfo",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithVideoTips",
                new MfButtonCardTemplateWithVideoTipsModel(
                        "ButtonCardTemplateWithVideoTips",
                        new MFCardData()
                ));

        registerTemplateModel("ButtonCardTemplateWithLoanInfo",
                new MfButtonCardTemplateWithLoanInfoModel(
                        "ButtonCardTemplateWithLoanInfo",
                        new MFCardData()
                ));

        registerTemplateModel("MapCardTemplateWithDetails",
                new MfMapCardTemplateWithDetailsModel(
                        "MapCardTemplateWithDetails",
                        new MFCardData()
                ));

        registerTemplateModel("LoadingCardTemplate",
                new MfLoadingTemplateModel("LoadingCardTemplate")
        );

        registerTemplateModel("webview",
                new MfWebViewCardTemplateModel(
                        "webview"
                ));
    }

    public static TemplateModelFactory getInstance() {
        if (sInstance == null)
            sInstance = new TemplateModelFactory();
        return sInstance;
    }

    public void registerTemplateModel(@NonNull String templateID,
                                      @NonNull TemplateModel templateModel) {
        checkNotNull(templateID);
        checkNotNull(templateModel);

        if (mMapIDTemplateModel.containsKey(templateID)) {
            throw new IllegalArgumentException("Error: TemplateModel is already registered with "
                    + templateID);
        }
        mMapIDTemplateModel.put(templateID, templateModel);
    }

    public TemplateModel getTemplateModel(@NonNull String templateID) {
        checkNotNull(templateID);
        return mMapIDTemplateModel.get(templateID);
    }

    public void unregisterTemplateModel(@NonNull String templateId) {
        checkNotNull(templateId);
        if (mMapIDTemplateModel.containsKey(templateId))
            mMapIDTemplateModel.remove(templateId);
    }
}
