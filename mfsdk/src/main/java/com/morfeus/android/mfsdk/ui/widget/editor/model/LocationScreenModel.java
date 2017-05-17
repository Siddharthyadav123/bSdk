package com.morfeus.android.mfsdk.ui.widget.editor.model;

import com.morfeus.android.mfsdk.ui.widget.editor.style.LocationScreenStyle;

import java.util.HashMap;

public class LocationScreenModel {
    private String id;
    private Header header;
    private Body body;
    private Footer footer;

    public LocationScreenModel(String id, Header header, Body body, Footer footer) {
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


    public static class Body {
        private LocationScreenStyle style;
        private Map map;

        public Body(LocationScreenStyle style, Map map) {
            this.style = style;
            this.map = map;
        }

        public LocationScreenStyle getStyle() {
            return style;
        }

        public Map getMap() {
            return map;
        }
    }

    public static class Map {
        private LocationScreenStyle style;
        private String type;

        public Map(LocationScreenStyle style, String type) {
            this.style = style;
            this.type = type;
        }

        public LocationScreenStyle getStyle() {
            return style;
        }

        public String getType() {
            return type;
        }
    }

    public static class Header {
        private LocationScreenStyle style;
        private ImageModel imageModel;
        private TextModel textModel;

        public Header(LocationScreenStyle style, ImageModel imageModel, TextModel textModel) {
            this.style = style;
            this.imageModel = imageModel;
            this.textModel = textModel;
        }

        public LocationScreenStyle getStyle() {
            return style;
        }

        public ImageModel getImageModel() {
            return imageModel;
        }

        public TextModel getTextModel() {
            return textModel;
        }
    }

    public static class Footer {
        private LocationScreenStyle style;
        private HashMap<String, FooterStrip> fotterStripMap;

        public Footer(LocationScreenStyle style, HashMap<String, FooterStrip> fotterStripMap) {
            this.style = style;
            this.fotterStripMap = fotterStripMap;
        }

        public LocationScreenStyle getStyle() {
            return style;
        }

        public HashMap<String, FooterStrip> getFotterStripMap() {
            return fotterStripMap;
        }
    }

    public static class FooterStrip {
        private ImageModel leftImageModel;
        private ImageModel rightImageModel;
        private TextModel textModel;

        public FooterStrip(ImageModel leftImageModel,
                           ImageModel rightImageModel,
                           TextModel textModel) {
            this.leftImageModel = leftImageModel;
            this.rightImageModel = rightImageModel;
            this.textModel = textModel;
        }

        public ImageModel getLeftImageModel() {
            return leftImageModel;
        }

        public ImageModel getRightImageModel() {
            return rightImageModel;
        }

        public TextModel getTextModel() {
            return textModel;
        }
    }

    public static class TextModel {
        private LocationScreenStyle style;
        private String text;

        public TextModel(String text, LocationScreenStyle style) {
            this.text = text;
            this.style = style;
        }

        public LocationScreenStyle getStyle() {
            return style;
        }

        public String getText() {
            return text;
        }
    }

    public static class ImageModel {
        private LocationScreenStyle style;
        private String label;
        private String image;
        private String action;


        public LocationScreenStyle getStyle() {
            return style;
        }

        public void setStyle(LocationScreenStyle style) {
            this.style = style;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
