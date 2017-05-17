package com.morfeus.android.mfsdk.ui.widget.dialog.otp;

import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.TextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import java.util.HashMap;

public class OTPModel {
    private String id;

    private Header header;

    private Body body;

    private Footer footer;

    public OTPModel(String id, Header header, Body body, Footer footer) {
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

    public static class Body {
        private MfDialogStyle style;
        private TextModel subheaderText;
        private PinpadModel pinpadModel;

        public Body(MfDialogStyle style, TextModel subheaderText, PinpadModel pinpadModel) {
            this.style = style;
            this.subheaderText = subheaderText;
            this.pinpadModel = pinpadModel;
        }

        public MfDialogStyle getStyle() {
            return style;
        }

        public TextModel getSubheaderText() {
            return subheaderText;
        }

        public PinpadModel getPinpadModel() {
            return pinpadModel;
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
