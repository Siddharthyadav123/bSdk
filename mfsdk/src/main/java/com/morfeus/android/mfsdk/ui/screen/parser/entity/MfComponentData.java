package com.morfeus.android.mfsdk.ui.screen.parser.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MfComponentData {
    private MfElementData elementData;

    private List<MfElementData> elementDataList;

    private ArrayList<MfElementData[]> elementDataListOfArray;

    public MfComponentData(@NonNull MfElementData elementData) {
        this.elementData = checkNotNull(elementData);
    }

    public MfComponentData(@NonNull List<MfElementData> elementDataList) {
        this.elementDataList = checkNotNull(elementDataList);
    }

    public MfComponentData(@NonNull ArrayList<MfElementData[]> elementDataListOfArray) {
        this.elementDataListOfArray = checkNotNull(elementDataListOfArray);
    }


    /**
     * Can be null if {@link #getElementDataList()} is not null
     *
     * @return {@link MfElementData}
     */
    @Nullable
    public MfElementData getElementData() {
        return elementData;
    }

    /**
     * Can be null if {@link #getElementData()} is not null
     *
     * @return List of {@link MfElementData}
     */
    @Nullable
    public List<MfElementData> getElementDataList() {
        return elementDataList;
    }

    /**
     * Can be null if {@link #getElementDataListOfArray()} is not null
     *
     * @return List of {@link MfElementData[]}
     */
    @Nullable
    public ArrayList<MfElementData[]> getElementDataListOfArray() {
        return elementDataListOfArray;
    }

    @Override
    public String toString() {
        return "MfComponentData{" +
                "elementData=" + elementData +
                ", elementDataListOfArray=" + elementDataListOfArray +
                ", elementDataList=" + elementDataList +
                '}';
    }
}
