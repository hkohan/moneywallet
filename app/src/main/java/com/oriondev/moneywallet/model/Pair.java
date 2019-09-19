package com.oriondev.moneywallet.model;


public class Pair<L, R> {

    private final L mL;
    private final R mR;

    public Pair(L l, R r) {
        mL = l;
        mR = r;
    }

    public L getL() {
        return mL;
    }

    public R getR() {
        return mR;
    }
}