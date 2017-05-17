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

public final class MfAirlineItineraryTemplateView extends TemplateView {

    public static final String AIRLINE_ITINERARY_TEMPLATE = "AirlineItineraryTemplate";

    public MfAirlineItineraryTemplateView(Context context) {
        super(context);
        initView(context, null);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(AIRLINE_ITINERARY_TEMPLATE, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (SimpleImageTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_itinerary_template_layout, this);
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

        private TextView mTVDelayed;
        private TextView mTVViewDetails;
        private TextView mTVTotal;

        private LinearLayout mLLPassengersSeatInfo;
        private LinearLayout mLLToFromFlightInfo;

        ViewHolder(View itemView) {
            super(itemView);
            this.mIVHeaderPlaneImage = (ImageView) itemView.findViewById(R.id.ivHeaderPlaneImage);

            this.mTVDelayed = (TextView) itemView.findViewById(R.id.tvDelayed);
            this.mTVViewDetails = (TextView) itemView.findViewById(R.id.tvViewDetails);
            this.mTVTotal = (TextView) itemView.findViewById(R.id.tvTotal);

            this.mLLPassengersSeatInfo = (LinearLayout) itemView.findViewById(R.id.llPassengersSeatInfo);
            this.mLLToFromFlightInfo = (LinearLayout) itemView.findViewById(R.id.llToFromFlightInfo);
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

            this.mTVDelayed.setText("");
            this.mTVViewDetails.setText("");
            this.mTVTotal.setText("");

            mTVViewDetails.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO need pass payload for view button
                    EventBus.getDefault().post(new PayloadEvent("", "", ""));
                }
            });


            //TODO need pass the items
            feedChildPassangerListItems(null);

            //TODO need pass the items
            feedFromFlitListListItems(null);
        }

        private void feedChildPassangerListItems(@NonNull ArrayList<Object> someItemList) {
            checkNotNull(someItemList);
            if (someItemList != null) {

                for (int i = 0; i < someItemList.size(); i++) {
                    TextView ivPassangerName = (TextView)
                            LayoutInflater.from(this.itemView.getContext()).inflate(R.layout.airline_pessenger_textview_layout, null);

                    ivPassangerName.setText("");

                    this.mLLPassengersSeatInfo.addView(ivPassangerName);
                }
            }
        }

        private void feedFromFlitListListItems(@NonNull ArrayList<Object> someItemList) {
            checkNotNull(someItemList);
            if (someItemList != null) {
                for (int i = 0; i < someItemList.size(); i++) {
                    RelativeLayout view = (RelativeLayout)
                            LayoutInflater.from(this.itemView.getContext()).inflate(R.layout.chat_item_single_itinaray_template_layout, null);

                    ImageView ivToFromFlightImage = (ImageView) view.findViewById(R.id.ivToFromFlightImage);

                    TextView tvFlightStopCount = (TextView) view.findViewById(R.id.tvFlightStopCount);
                    TextView tvFlightTime = (TextView) view.findViewById(R.id.tvFlightTime);
                    TextView tvFlightFromLabel = (TextView) view.findViewById(R.id.tvFlightFromLabel);
                    TextView tvFlightFrom = (TextView) view.findViewById(R.id.tvFlightFrom);
                    TextView tvFlightToLabel = (TextView) view.findViewById(R.id.tvFlightToLabel);
                    TextView tvFlightTo = (TextView) view.findViewById(R.id.tvFlightTo);

                    tvFlightStopCount.setText("");
                    tvFlightTime.setText("");
                    tvFlightFromLabel.setText("");
                    tvFlightFrom.setText("");
                    tvFlightToLabel.setText("");
                    tvFlightTo.setText("");

                    this.mLLToFromFlightInfo.addView(view);
                }
            }
        }

    }
}
