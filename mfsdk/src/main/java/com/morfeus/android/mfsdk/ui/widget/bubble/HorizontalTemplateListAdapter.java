package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This adapter is responsible for setting horizontal template list into
 * the {@link com.morfeus.android.mfsdk.ui.screen.adapter.ChatRecyclerAdapter}.
 */
public class HorizontalTemplateListAdapter extends RecyclerView.Adapter<TemplateViewHolder> {

    private final TemplateFactory mTemplateFactory;
    private List<TemplateModel> mTemplateModels;

    public HorizontalTemplateListAdapter(@NonNull List<TemplateModel> templateModels,
                                         @NonNull TemplateFactory templateFactory) {
        checkNotNull(templateModels);
        checkNotNull(templateFactory);

        mTemplateModels = templateModels;
        mTemplateFactory = templateFactory;
    }

    @Override
    public int getItemViewType(int position) {
        String templateID = mTemplateModels.get(position).getTemplateID();
        return mTemplateFactory.getTemplateIntID(templateID);
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseView templateView = mTemplateFactory.createTemplate(viewType, parent.getContext());
        return templateView.createViewHolder(templateView);
    }

    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position) {
        TemplateModel model = mTemplateModels.get(position);
        holder.setData(model);
    }

    @Override
    public int getItemCount() {
        return mTemplateModels.size();
    }

    public void addMessages(@NonNull List<TemplateModel> templateModels) {
        checkNotNull(templateModels);
        mTemplateModels.clear();
        mTemplateModels.addAll(templateModels);
        notifyDataSetChanged();
    }

    public void addMessage(@NonNull TemplateModel model) {
        checkNotNull(model);
        mTemplateModels.add(model);
        notifyDataSetChanged();
    }
}
