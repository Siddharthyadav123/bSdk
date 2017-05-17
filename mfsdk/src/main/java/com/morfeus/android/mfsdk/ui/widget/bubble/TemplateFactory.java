package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.morfeus.android.mfsdk.log.LogManager;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link TemplateFactory} is singleton factory class to provides the {@link TemplateView}.
 */
public class TemplateFactory {

    private static final String TAG = TemplateFactory.class.getSimpleName();

    private static TemplateFactory INSTANCE;

    private AtomicInteger mTemplateIntID;

    private HashMap<String, Pair<Integer, BaseView>> multimap = new HashMap<>();

    private HashMap<Integer, String> mapIntStrTemplateID = new HashMap<>();

    private TemplateFactory() {
        mTemplateIntID = new AtomicInteger(0);
        // TODO register default templates
    }

    public static TemplateFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TemplateFactory();
        }
        return INSTANCE;
    }

    public void clearInstance() {
        INSTANCE = null;
    }

    public void registerTemplate(String templateID, BaseView templateView) {
        LogManager.i(TAG, "TEMPLATE REGISTERED: " + templateID);
        if (multimap.containsKey(templateID)) {
            throw new IllegalArgumentException("Error: Template is already registered with " + templateID);
        }
        Integer intTemplateID = mTemplateIntID.incrementAndGet();
        Pair<Integer, BaseView> pair = new Pair<>(intTemplateID, templateView);

        multimap.put(templateID, pair);
        mapIntStrTemplateID.put(intTemplateID, templateID);
    }

    public TemplateView createTemplate(String templateID, Context context) {
        LogManager.i(TAG, "TEMPLATE CREATED: KEY = " + templateID);
        return multimap.get(templateID).second.create(context).inflate(context);
    }

    public TemplateView createTemplate(Integer templateIntID, Context context) {
        LogManager.i(TAG, "TEMPLATE CREATED: KEY = " + templateIntID);
        String templateID = mapIntStrTemplateID.get(templateIntID);
        return multimap.get(templateID).second.create(context).inflate(context);
    }

    public Integer getTemplateIntID(@NonNull String templateID) {
        checkNotNull(templateID);
        return multimap.get(templateID).first;
    }

    public boolean isEmpty() {
        return mTemplateIntID.get() == 0 && mapIntStrTemplateID.isEmpty() && multimap.isEmpty();
    }

    public void clearTemplates() {
        mTemplateIntID = new AtomicInteger(0);
        mapIntStrTemplateID.clear();
        multimap.clear();
        LogManager.i(TAG, "TEMPLATE CLEARED");
    }
}
