package io.bobba.poc.core.rooms.gamemap.pathfinding.dream;

import io.bobba.poc.core.rooms.gamemap.SqState;

public class ModelInfo {
    private SqState[][] mMap;
    private int mMaxX;
    private int mMaxY;

    public ModelInfo(int mMaxX, int mMaxY, SqState[][] mMap) {
        this.mMap = mMap;
        this.mMaxX = mMaxX;
        this.mMaxY = mMaxY;
    }

    SqState getState(int x, int y) {
        if (x >= mMaxX || x < 0)
            return SqState.Closed;
        if (y >= mMaxY || y < 0)
            return SqState.Closed;
        return mMap[x][y];
    }
}