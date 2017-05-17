package com.morfeus.android.mfsdk.ui.widget.dialog.login;

import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ImageModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.TextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import java.util.HashMap;

/**
 * Model represents the login screen
 */
public class LoginModel {

    private String id;

    private String token;

    private Header header;

    private Body body;

    private Footer footer;

    public LoginModel(String id, Header header, Body body, Footer footer) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public Footer getFooter() {
        return footer;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Body {
        private MfDialogStyle style;
        private ImageModel imageModel;
        private HashMap<String, EditTextModel> mapEditTextModel;

        public Body(MfDialogStyle style, ImageModel imageModel, HashMap<String, EditTextModel> mapEditTextModel) {
            this.style = style;
            this.imageModel = imageModel;
            this.mapEditTextModel = mapEditTextModel;
        }

        public MfDialogStyle getStyle() {
            return style;
        }

        public ImageModel getImageModel() {
            return imageModel;
        }

        public EditTextModel getEditTextModel(String editText) {
            return mapEditTextModel.get(editText);
        }
    }

    public static class Header {
        private MfDialogStyle style;
        private TextModel textModel;

        public Header(MfDialogStyle style, TextModel textModel) {
            this.style = style;
            this.textModel = textModel;
        }

        public MfDialogStyle getStyle() {
            return style;
        }

        public TextModel getTextModel() {
            return textModel;
        }
    }

    public static class Footer {
        private MfDialogStyle style;
        private HashMap<String, ButtonModel> mapButtonModel;

        public Footer(MfDialogStyle style, HashMap<String, ButtonModel> mapButtonModel) {
            this.style = style;
            this.mapButtonModel = mapButtonModel;
        }

        public MfDialogStyle getStyle() {
            return style;
        }

        public ButtonModel getButtonModel(String button) {
            return mapButtonModel.get(button);
        }
    }
}
