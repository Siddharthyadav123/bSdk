package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.HorizontalTemplateListModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfInfoCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HorizontalTemplateListView extends TemplateView {

    public HorizontalTemplateListView(Context context) {
        super(context);
    }

    public HorizontalTemplateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public HorizontalTemplateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        // LayoutInflater.from(context).inflate(R.layout.horizontal_template_list_item, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (HorizontalTemplateListView) LayoutInflater.from(context)
                .inflate(R.layout.horizontal_template_list_item, this, false);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view, TemplateFactory.getInstance());
    }


    @Override
    public void setBackground() {
        switch (type) {
            case INCOMING:

                break;
            case OUTGOING:

                break;
            default:
                throw new IllegalArgumentException("Error: Invalid template type!");
        }
    }

    @Override
    public BaseView create(Context context) {
        return new HorizontalTemplateListView(context);
    }

    public static class ViewHolder extends TemplateViewHolder {

        private RecyclerView recyclerView;

        private TemplateFactory templateFactory;

        public ViewHolder(View itemView, TemplateFactory templateFactory) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_template);
            this.templateFactory = templateFactory;
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof HorizontalTemplateListModel)) {
                throw new IllegalArgumentException(
                        "Error: Invalid template model!"
                );
            }

            if (((HorizontalTemplateListModel) model).getSubTemplateModelList() != null) {
                List<TemplateModel> subTemplateModelList = ((HorizontalTemplateListModel) model).getSubTemplateModelList();
                setMaxButtonsIfInfoCards(subTemplateModelList);
                sortCardsByGreaterTitleSize(subTemplateModelList);
                HorizontalTemplateListAdapter adapter = new HorizontalTemplateListAdapter(
                        subTemplateModelList, templateFactory
                );
                recyclerView.setAdapter(adapter);
                Integer templateId = templateFactory.getTemplateIntID(model.getTemplateID());
                recyclerView.getRecycledViewPool().setMaxRecycledViews(templateId, 0);
            }
        }

        private void sortCardsByGreaterTitleSize(List<TemplateModel> subTemplateModelList) {
            if (subTemplateModelList != null && subTemplateModelList.size() > 0) {
                TemplateModel templateModel = subTemplateModelList.get(0);
                if (templateModel instanceof MfButtonCardTemplateModel) {
                    List<MfButtonCardTemplateModel> templateModelList = (List<MfButtonCardTemplateModel>) (List<?>) subTemplateModelList;
                    Collections.sort(templateModelList);
                } else if (templateModel instanceof MfInfoCardTemplateModel) {
                    List<MfInfoCardTemplateModel> templateModelList = (List<MfInfoCardTemplateModel>) (List<?>) subTemplateModelList;
                    Collections.sort(templateModelList);
                }
            }
        }

        private void setMaxButtonsIfInfoCards(List<TemplateModel> subTemplateModelList) {
            int maxBtns = -1;
            for (int i = 0; i < subTemplateModelList.size(); i++) {
                TemplateModel templateModel = subTemplateModelList.get(i);
                if (templateModel instanceof MfButtonCardTemplateModel) {
                    return;
                }
                List<MfComponentData> mfComponentDatas = null;
                if (templateModel instanceof MfInfoCardTemplateModel) {
                    MfInfoCardTemplateModel infoCardWithButtonsTemplateModel = (MfInfoCardTemplateModel) templateModel;
                    mfComponentDatas = infoCardWithButtonsTemplateModel.getComponentDatas();
                }
                if (mfComponentDatas != null) {
                    for (MfComponentData mfComponentData : mfComponentDatas) {
                        List<MfElementData> buttonsList = mfComponentData.getElementDataList();
                        if (buttonsList != null) {
                            if (buttonsList.size() > maxBtns) {
                                maxBtns = buttonsList.size();
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < subTemplateModelList.size(); i++) {
                TemplateModel templateModel = subTemplateModelList.get(i);
                ((MfInfoCardTemplateModel) templateModel).setMaxButtonToShow(maxBtns);
            }
        }


        public RecyclerView getRecyclerView() {
            return this.recyclerView;
        }
    }
}
