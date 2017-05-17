package com.morfeus.android.mfsdk.ui.screen.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgItems;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.HorizontalTemplateListModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfImageCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfLocationCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfTextCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfWebViewCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Parse content JsonArray for TemplateModel
 */
public final class TemplateModelParser {

    public static final String MSG_TYPE_CUSTOM = "custom";
    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_IMAGE = "image";

    public static final String MSG_TYPE_LOCATION = "location";

    public static final String MSG_TYPE_WEBVIEW = "webview";

    private static final String TAG = TemplateModelParser.class.getSimpleName();
    private static TemplateModelParser sInstance;
    private static TemplateModelFactory mModelFactory;

    private TemplateModelParser(@NonNull TemplateModelFactory factory) {
        mModelFactory = checkNotNull(factory);
    }

    public static TemplateModelParser getInstance(TemplateModelFactory factory) {
        if (sInstance == null)
            sInstance = new TemplateModelParser(factory);
        return sInstance;
    }

    public List<TemplateModel> parse(@NonNull List<MfMsgItems> msgItems) throws CloneNotSupportedException {
        List<TemplateModel> templateModels = new ArrayList<>();
        for (MfMsgItems mfMsgItems : msgItems) {
            MfMsgData mfMsgData = mfMsgItems.getMsgData();
            String msgType = mfMsgData.getMsgType();
            switch (msgType) {
                case MSG_TYPE_CUSTOM:
                    //TODO add msg ID, to, from, set message type(INCOMING, OUTGOING, BOT)
                    MFCardData mfCardData = mfMsgData.getMfCards();
                    // If contains more card then display as horizontal list of template model
//                    if (mfCardData!=null) {
//                        List<TemplateModel> subTemplateModels
//                                = getTemplateModels(mfMsgItems.isInComing(), mfCardDatas);
//                        HorizontalTemplateListModel horizontalTemplates
//                                = new HorizontalTemplateListModel(
//                                subTemplateModels,
//                                "HorizontalTemplates"
//                        );
//                        templateModels.add(horizontalTemplates);
//                    } else {
                    if (mfCardData != null) {
                        String templateType = mfCardData.getTemplateType();
                        String to = checkNotNull(mfMsgItems.getTo());
                        String from = checkNotNull(mfMsgItems.getFrom());
                        String msgID = checkNotNull(mfMsgItems.getMsgId());
                        boolean isIncoming = checkNotNull(mfMsgItems.isInComing());

                        boolean textToSpeech = false;
                        String ttsText = null;
                        if (mfMsgItems != null && mfMsgItems.getMsgData() != null) {
                            textToSpeech = mfMsgItems.getMsgData().isTextToSpeech();
                            ttsText = mfMsgItems.getMsgData().getTtsText();
                        }


                        //for info and simple btn card considering each content as a single card
                        if (templateType.equalsIgnoreCase(DefaultCardDataType.INFO_CARD_TEMPLATE_VIEW)
                                || templateType.equalsIgnoreCase(DefaultCardDataType.BUTTON_CARD_TEMPLATE_VIEW)
                                || templateType.equalsIgnoreCase(DefaultCardDataType.BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW)) {

                            JsonArray cardDataJsonArray = mfCardData.getCardDataJsonArray();
                            LogManager.d(TAG, "parse: json size" + cardDataJsonArray.size());
                            if (cardDataJsonArray.size() > 1) {

                                HorizontalTemplateListModel templateModel = null;
//                                try {
//                                    templateModel = (HorizontalTemplateListModel) mModelFactory
//                                            .getTemplateModel("HorizontalTemplates")
//                                            .getClass()
//                                            .newInstance();
                                List<TemplateModel> templateModelList = new ArrayList<>();
                                templateModel = new HorizontalTemplateListModel(
                                        templateModelList,
                                        "HorizontalTemplates");

                                templateModel.setMfMsgItems(mfMsgItems);
                                templateModel.setMfCardData(mfCardData);
                                templateModel.setTextToSpeech(textToSpeech);
                                templateModel.setTtsText(ttsText);

                                templateModel.deserialize(cardDataJsonArray);
                                templateModels.add(templateModel);
//                                } catch (InstantiationException | IllegalAccessException e) {
//                                    e.printStackTrace();
//                                }

                            } else {
                                for (int i = 0; i < cardDataJsonArray.size(); i++) {
                                    JsonArray jsonArray = new JsonArray();
                                    jsonArray.add(cardDataJsonArray.get(i));

                                    TemplateModel templateModel = mModelFactory.getTemplateModel(templateType);
                                    templateModel.deserialize(jsonArray);

                                    if (templateModel != null) {
                                        TemplateModel newTemplateModel = (TemplateModel) templateModel.clone();
                                        newTemplateModel.setCardData(mfCardData);
                                        newTemplateModel.setTemplateID(mfCardData.getTemplateType());
                                        newTemplateModel.setTo(to);
                                        newTemplateModel.setFrom(from);
                                        newTemplateModel.setMsgId(msgID);
                                        newTemplateModel.setIncoming(isIncoming);
                                        newTemplateModel.setTextToSpeech(textToSpeech);
                                        newTemplateModel.setTtsText(ttsText);
                                        templateModels.add(newTemplateModel);
                                    }
                                }
                            }

                        } else {

                            TemplateModel templateModel = mModelFactory.getTemplateModel(templateType);
                            templateModel.deserialize(mfCardData.getCardDataJsonArray());

                            if (templateModel != null) {
                                TemplateModel newTemplateModel = (TemplateModel) templateModel.clone();
                                newTemplateModel.setCardData(mfCardData);
                                newTemplateModel.setTemplateID(mfCardData.getTemplateType());
                                newTemplateModel.setTo(to);
                                newTemplateModel.setFrom(from);
                                newTemplateModel.setMsgId(msgID);
                                newTemplateModel.setIncoming(isIncoming);
                                newTemplateModel.setTextToSpeech(textToSpeech);
                                newTemplateModel.setTtsText(ttsText);
                                templateModels.add(newTemplateModel);
                            }
                        }

                    }
//                    }
                    break;
                case MSG_TYPE_TEXT: {
                    // MFCardData will be always null
                    String to = checkNotNull(mfMsgItems.getTo());
                    String from = checkNotNull(mfMsgItems.getFrom());
                    String msgID = checkNotNull(mfMsgItems.getMsgId());
                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
                    MfMsgData msgData = mfMsgItems.getMsgData();
                    String message = msgData.getText();
                    boolean textToSpeech = msgData.isTextToSpeech();
                    String ttsText = msgData.getTtsText();

                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_TEXT);

                    if (templateModel != null) {
                        TemplateModel textCardTemplateModel = (TemplateModel) templateModel.clone();
                        textCardTemplateModel.setTo(to);
                        textCardTemplateModel.setFrom(from);
                        textCardTemplateModel.setMsgId(msgID);
                        textCardTemplateModel.setTextToSpeech(textToSpeech);
                        textCardTemplateModel.setTtsText(ttsText);
                        ((MfTextCardTemplateModel) textCardTemplateModel).setTextMessage(message);
                        textCardTemplateModel.setIncoming(isIncoming);
                        templateModels.add(textCardTemplateModel);
                    }
                    break;
                }
                case MSG_TYPE_IMAGE: {
                    String to = checkNotNull(mfMsgItems.getTo());
                    String from = checkNotNull(mfMsgItems.getFrom());
                    String msgID = checkNotNull(mfMsgItems.getMsgId());
                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
                    MfMsgData msgData = mfMsgItems.getMsgData();
                    String message = msgData.getText();
                    String image = msgData.getThumb();
                    boolean textToSpeech = msgData.isTextToSpeech();
                    String ttsText = msgData.getTtsText();

                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_IMAGE);

                    if (templateModel != null) {
                        TemplateModel imageCardTemplateModel = (TemplateModel) templateModel.clone();
                        imageCardTemplateModel.setTo(to);
                        imageCardTemplateModel.setFrom(from);
                        imageCardTemplateModel.setMsgId(msgID);
                        ((MfImageCardTemplateModel) imageCardTemplateModel).setTextMessage(message);
                        imageCardTemplateModel.setIncoming(isIncoming);
                        imageCardTemplateModel.setTextToSpeech(textToSpeech);
                        imageCardTemplateModel.setTtsText(ttsText);
                        ((MfImageCardTemplateModel) imageCardTemplateModel).setImage(image);

                        templateModels.add(imageCardTemplateModel);
                    }
                    break;
                }
                case MSG_TYPE_WEBVIEW: {
                    String to = checkNotNull(mfMsgItems.getTo());
                    String from = checkNotNull(mfMsgItems.getFrom());
                    String msgID = checkNotNull(mfMsgItems.getMsgId());
                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
                    MfMsgData msgData = mfMsgItems.getMsgData();
                    String url = msgData.getUrl();

                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_WEBVIEW);

                    if (templateModel != null) {
                        TemplateModel webviewcard = (TemplateModel) templateModel.clone();
                        webviewcard.setTo(to);
                        webviewcard.setFrom(from);
                        webviewcard.setMsgId(msgID);
                        ((MfWebViewCardTemplateModel) webviewcard).setUrl(url);
                        webviewcard.setIncoming(isIncoming);
                        templateModels.add(webviewcard);
                    }
                    break;
                }
            }
        }
        return templateModels;
    }

    @NonNull
    private List<TemplateModel> getTemplateModels(boolean isIncoming, List<MFCardData> mfCardDatas) throws CloneNotSupportedException {
        List<TemplateModel> subTemplateModels = new ArrayList<>();
        for (MFCardData MFCardData : mfCardDatas) {
            String templateType = MFCardData.getTemplateType();
            TemplateModel templateModel = mModelFactory.getTemplateModel(templateType);
            if (templateModel != null) {
                TemplateModel newTemplateModel = (TemplateModel) templateModel.clone();
                newTemplateModel.setCardData(MFCardData);
                newTemplateModel.setTemplateID(templateType);
                newTemplateModel.setIncoming(isIncoming);
                subTemplateModels.add(newTemplateModel);
            }
        }
        return subTemplateModels;
    }

    public interface DefaultCardDataType {
        String INFO_CARD_TEMPLATE_VIEW = "InfoCardTemplate";
        String BUTTON_CARD_TEMPLATE_VIEW = "ButtonCardTemplate";
        String BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW = "ButtonCardTemplateWithLoanInfo";

    }
}