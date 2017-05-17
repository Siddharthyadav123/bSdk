package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.SimpleTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MfAirlineBoardingPassTemplateView extends TemplateView {

    public static final String AIRLINE_BOARDING_PASS_TEMPLATE = "AirlineBoardingPassTemplate";

    public MfAirlineBoardingPassTemplateView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(AIRLINE_BOARDING_PASS_TEMPLATE, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (SimpleImageTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_airline_boarding_pass_template_layout, this);
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
        return null;
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private ImageView mIVHeaderPlaneImage;
        private ImageView mIVToFromFlightImage;

        private TextView mTVTerminalNumber;
        private TextView mTVGateNumber;
        private TextView mTVFlightNumber;
        private TextView mTVBoardingTime;
        private TextView mTVDepartsTime;
        private TextView mTVFlightFrom;
        private TextView mTVFlightTo;
        private TextView mTVBoardingPass;

        private TextView mTVFlightFromLabel;
        private TextView mTVFlightToLabel;

        private LinearLayout mLLPassangerList;

        ViewHolder(View itemView) {
            super(itemView);
            this.mIVHeaderPlaneImage = (ImageView) itemView.findViewById(R.id.ivHeaderPlaneImage);
            this.mIVToFromFlightImage = (ImageView) itemView.findViewById(R.id.ivToFromFlightImage);

            this.mTVTerminalNumber = (TextView) itemView.findViewById(R.id.tvTerminalNumber);
            this.mTVGateNumber = (TextView) itemView.findViewById(R.id.tvGateNumber);
            this.mTVFlightNumber = (TextView) itemView.findViewById(R.id.tvFlightNumber);
            this.mTVBoardingTime = (TextView) itemView.findViewById(R.id.tvBoardingTime);
            this.mTVDepartsTime = (TextView) itemView.findViewById(R.id.tvDepartsTime);

            this.mTVFlightFrom = (TextView) itemView.findViewById(R.id.tvFlightFrom);
            this.mTVFlightTo = (TextView) itemView.findViewById(R.id.tvFlightTo);
            this.mTVBoardingPass = (TextView) itemView.findViewById(R.id.tvBoardingPass);

            this.mTVFlightFromLabel = (TextView) itemView.findViewById(R.id.tvFlightFromLabel);
            this.mTVFlightToLabel = (TextView) itemView.findViewById(R.id.tvFlightToLabel);

            this.mLLPassangerList = (LinearLayout) itemView.findViewById(R.id.llPassangerList);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            //TODO need to check with respecitve template only
            if (!(model instanceof SimpleTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            //TODO need to set values from members
            this.mIVHeaderPlaneImage.setImageResource(R.drawable.airplane);
            this.mIVToFromFlightImage.setImageResource(R.drawable.airplane);

            this.mTVTerminalNumber.setText("");
            this.mTVGateNumber.setText("");
            this.mTVFlightNumber.setText("");
            this.mTVBoardingTime.setText("");
            this.mTVDepartsTime.setText("");
            this.mTVFlightFrom.setText("");
            this.mTVFlightTo.setText("");
            this.mTVBoardingPass.setText("");

            mTVBoardingPass.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO need to pass the payload/action
                    EventBus.getDefault().post(new PayloadEvent("", null, null));
                }
            });


            this.mTVFlightFromLabel.setText("");
            this.mTVFlightToLabel.setText("");

            //TODO need pass the items
            feedChildPassangerListItems(null);
        }

        private void feedChildPassangerListItems(@NonNull ArrayList<Object> someItemList) {
            checkNotNull(someItemList);

            if (someItemList != null) {

                for (int i = 0; i < someItemList.size(); i++) {
                    RelativeLayout listCardItem = (RelativeLayout)
                            LayoutInflater.from(
                                    this.itemView.getContext()).inflate(
                                    R.layout.airline_pessanger_list_item_layout,
                                    null
                            );

                    TextView ivPassangerName = (TextView) listCardItem.findViewById(R.id.ivPassangerName);
                    TextView ivSeatNo = (TextView) listCardItem.findViewById(R.id.ivSeatNo);

                    ivPassangerName.setText("");
                    ivSeatNo.setText("");

                    this.mLLPassangerList.addView(listCardItem);
                }
            }
        }

    }
}
