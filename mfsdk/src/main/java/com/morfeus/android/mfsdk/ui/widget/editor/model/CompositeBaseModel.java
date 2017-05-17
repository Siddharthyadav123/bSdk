package com.morfeus.android.mfsdk.ui.widget.editor.model;

import java.util.List;

public interface CompositeBaseModel extends BaseModel {

    List<BaseModel> getSubComponentModels();

}
