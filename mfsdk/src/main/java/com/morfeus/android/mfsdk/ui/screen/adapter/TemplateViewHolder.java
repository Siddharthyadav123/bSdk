package com.morfeus.android.mfsdk.ui.screen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

public abstract class TemplateViewHolder extends RecyclerView.ViewHolder {

    public TemplateViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(@NonNull TemplateModel model);
}
