package com.oriondev.moneywallet.ui.view.chart;

import java.util.ArrayList;
import java.util.List;


public class PieData {

    private final List<PieSlice> mPieSlices;

    public PieData() {
        mPieSlices = new ArrayList<>();
    }

    public PieData(List<PieSlice> sliceList) {
        mPieSlices = new ArrayList<>(sliceList);
    }

    public PieSlice get(int index) {
        return mPieSlices.get(index);
    }

    public boolean add(PieSlice slice) {
        return mPieSlices.add(slice);
    }

    public PieSlice remove(int index) {
        return mPieSlices.remove(index);
    }

    public List<PieSlice> getAllSlices() {
        return mPieSlices;
    }

    public int size() {
        return mPieSlices.size();
    }
}