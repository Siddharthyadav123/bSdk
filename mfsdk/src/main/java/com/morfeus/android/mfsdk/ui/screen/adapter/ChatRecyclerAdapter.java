package com.morfeus.android.mfsdk.ui.screen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.widget.bubble.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfLoadingTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<TemplateViewHolder> {

    private static final String TAG = ChatRecyclerAdapter.class.getSimpleName();

    private List<TemplateModel> mTemplateModels;

    private TemplateFactory mTemplateFactory;

    private MfLoadingTemplateModel mLoadingModel;

    public ChatRecyclerAdapter(@NonNull List<TemplateModel> templateModels,
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
        // TODO create viewHolder for list items
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

    public void addMessage(@NonNull TemplateModel model) {
        checkNotNull(model);
        if (isContainsLoading()) {
            mTemplateModels.add(mTemplateModels.size() - 1, model);
            notifyItemInserted(mTemplateModels.size());
        } else {
            mTemplateModels.add(model);
            notifyItemInserted(mTemplateModels.size());
        }
    }

    private boolean isContainsLoading() {
        return mTemplateModels.contains(mLoadingModel);
    }

    public void addMessages(@NonNull List<TemplateModel> templateModels) {
        checkNotNull(templateModels);
        if (!templateModels.isEmpty()) {
            mTemplateModels.addAll(templateModels);
            notifyDataSetChanged();
        }
    }

    public void showTyping(boolean show) {
        if (mLoadingModel == null) {
            mLoadingModel = new MfLoadingTemplateModel("LoadingCardTemplate");
        }

        if (show && !isContainsLoading()) {
            LogManager.i(TAG, "TYPING INDICATOR: SHOWING");
            mTemplateModels.add(mLoadingModel);
            notifyItemInserted(mTemplateModels.size());
        } else if (!show) {
            LogManager.i(TAG, "TYPING INDICATOR: SHOWING");
            mTemplateModels.remove(mLoadingModel);
            notifyItemChanged(mTemplateModels.size());
        }
    }
}
