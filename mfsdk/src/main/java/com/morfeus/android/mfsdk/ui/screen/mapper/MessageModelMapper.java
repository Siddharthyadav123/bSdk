package com.morfeus.android.mfsdk.ui.screen.mapper;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;

import static com.google.common.base.Preconditions.checkNotNull;

// TODO refactor
@Deprecated
public final class MessageModelMapper {

    public static final String MSG_TYPE_CUSTOM = "custom";
    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_IMAGE = "image";
    public static final String MSG_TYPE_WEBVIEW = "webview";

    private static TemplateModelFactory mModelFactory;

    private static MessageModelMapper INSTANCE;

    private MessageModelMapper(TemplateModelFactory modelFactory) {
        mModelFactory = modelFactory;
    }

    public static MessageModelMapper createInstance(@NonNull TemplateModelFactory modelFactory) {
        checkNotNull(modelFactory);
        if (INSTANCE == null)
            INSTANCE = new MessageModelMapper(modelFactory);
        return INSTANCE;
    }

//    public List<TemplateModel> mapMsgModel(List<MfMsgItems> msgItems) throws CloneNotSupportedException {
//        List<TemplateModel> templateModels = new ArrayList<>();
//        for (MfMsgItems mfMsgItems : msgItems) {
//            MfMsgData mfMsgData = mfMsgItems.getMsgData();
//            String msgType = mfMsgData.getMsgType();
//            switch (msgType) {
//                case MSG_TYPE_CUSTOM:
//                    //TODO add msg ID, to, from, set message type(INCOMING, OUTGOING, BOT)
////                    List<MFCardData> mfCardDatas = mfMsgData.getMfCards();
////                    // If contains more card then display as horizontal list of template model
////                    if (mfCardDatas.size() > 1) {
////                        List<TemplateModel> subTemplateModels
////                                = getTemplateModels(mfMsgItems.isInComing(), mfCardDatas);
////                        HorizontalTemplateListModel horizontalTemplates
////                                = new HorizontalTemplateListModel(
////                                subTemplateModels,
////                                "HorizontalTemplates"
////                        );
////                        templateModels.add(horizontalTemplates);
////                    } else {
////                        if (mfCardDatas.size() > 0) {
////                            MFCardData MFCardData = mfCardDatas.get(0);
////                            String templateType = MFCardData.getTemplateType();
////                            String to = checkNotNull(mfMsgItems.getTo());
////                            String from = checkNotNull(mfMsgItems.getFrom());
////                            String msgID = checkNotNull(mfMsgItems.getMsgId());
////                            boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
////
////                            TemplateModel templateModel = mModelFactory.getTemplateModel(templateType);
////                            if (templateModel != null) {
////                                TemplateModel newTemplateModel = (TemplateModel) templateModel.clone();
////                                newTemplateModel.setCardData(MFCardData);
////                                newTemplateModel.setTemplateID(MFCardData.getTemplateType());
////                                newTemplateModel.setTo(to);
////                                newTemplateModel.setFrom(from);
////                                newTemplateModel.setMsgId(msgID);
////                                newTemplateModel.setIncoming(isIncoming);
////                                templateModels.add(newTemplateModel);
////                            }
////                        }
////                    }
//                    break;
//                case MSG_TYPE_TEXT: {
//                    // MFCardData will be always null
//                    String to = checkNotNull(mfMsgItems.getTo());
//                    String from = checkNotNull(mfMsgItems.getFrom());
//                    String msgID = checkNotNull(mfMsgItems.getMsgId());
//                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
//                    MfMsgData msgData = mfMsgItems.getMsgData();
//                    String message = msgData.getText();
//
//                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_TEXT);
//
//                    if (templateModel != null) {
//                        TemplateModel textCardTemplateModel = (TemplateModel) templateModel.clone();
//                        textCardTemplateModel.setTo(to);
//                        textCardTemplateModel.setFrom(from);
//                        textCardTemplateModel.setMsgId(msgID);
//                        ((MfTextCardTemplateModel) textCardTemplateModel).setTextMessage(message);
//                        textCardTemplateModel.setIncoming(isIncoming);
//                        templateModels.add(textCardTemplateModel);
//                    }
//                    break;
//                }
//                case MSG_TYPE_IMAGE: {
//                    String to = checkNotNull(mfMsgItems.getTo());
//                    String from = checkNotNull(mfMsgItems.getFrom());
//                    String msgID = checkNotNull(mfMsgItems.getMsgId());
//                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
//                    MfMsgData msgData = mfMsgItems.getMsgData();
//                    String message = msgData.getText();
//                    String image = msgData.getThumb();
//
//                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_IMAGE);
//
//                    if (templateModel != null) {
//                        TemplateModel imageCardTemplateModel = (TemplateModel) templateModel.clone();
//                        imageCardTemplateModel.setTo(to);
//                        imageCardTemplateModel.setFrom(from);
//                        imageCardTemplateModel.setMsgId(msgID);
//                        ((MfImageCardTemplateModel) imageCardTemplateModel).setTextMessage(message);
//                        imageCardTemplateModel.setIncoming(isIncoming);
//                        ((MfImageCardTemplateModel) imageCardTemplateModel).setImage(image);
//
//                        templateModels.add(imageCardTemplateModel);
//                    }
//                    break;
//                }
//                case MSG_TYPE_WEBVIEW: {
//                    String to = checkNotNull(mfMsgItems.getTo());
//                    String from = checkNotNull(mfMsgItems.getFrom());
//                    String msgID = checkNotNull(mfMsgItems.getMsgId());
//                    boolean isIncoming = checkNotNull(mfMsgItems.isInComing());
//                    MfMsgData msgData = mfMsgItems.getMsgData();
//                    String url = msgData.getUrl();
//
//                    TemplateModel templateModel = mModelFactory.getTemplateModel(MSG_TYPE_WEBVIEW);
//
//                    if (templateModel != null) {
//                        TemplateModel webviewcard = (TemplateModel) templateModel.clone();
//                        webviewcard.setTo(to);
//                        webviewcard.setFrom(from);
//                        webviewcard.setMsgId(msgID);
//                        ((MfWebViewCardTemplateModel) webviewcard).setUrl(url);
//                        webviewcard.setIncoming(isIncoming);
//                        templateModels.add(webviewcard);
//                    }
//                    break;
//                }
//            }
//        }
//        return templateModels;
//    }
//
//    @NonNull
//    private List<TemplateModel> getTemplateModels(boolean isIncoming, List<MFCardData> mfCardDatas) throws CloneNotSupportedException {
//        List<TemplateModel> subTemplateModels = new ArrayList<>();
//        for (MFCardData MFCardData : mfCardDatas) {
//            String templateType = MFCardData.getTemplateType();
//            TemplateModel templateModel = mModelFactory.getTemplateModel(templateType);
//            if (templateModel != null) {
//                TemplateModel newTemplateModel = (TemplateModel) templateModel.clone();
//                newTemplateModel.setCardData(MFCardData);
//                newTemplateModel.setTemplateID(templateType);
//                newTemplateModel.setIncoming(isIncoming);
//                subTemplateModels.add(newTemplateModel);
//            }
//        }
//        return subTemplateModels;
//    }
}
