package com.morfeus.android.mfsdk;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Template immutable pojo class to register new template.
 */
public final class Template {
    private final String templateID;
    private final TemplateView templateView;
    private final TemplateModel templateModel;

    public Template(@NonNull String templateID,
                    @NonNull TemplateView templateView,
                    @NonNull TemplateModel templateModel) {
        checkArgument(!TextUtils.isEmpty(templateID), "Error: Template Id can't be empty!");
        this.templateID = checkNotNull(templateID);
        this.templateView = checkNotNull(templateView);
        this.templateModel = checkNotNull(templateModel);
    }

    public String getTemplateID() {
        return templateID;
    }

    public TemplateView getTemplateView() {
        return templateView;
    }

    public TemplateModel getTemplateModel() {
        return templateModel;
    }

}
