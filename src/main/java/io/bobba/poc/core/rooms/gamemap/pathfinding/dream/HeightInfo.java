package io.bobba.poc.core.rooms.gamemap.pathfinding.dream;

public class HeightInfo {
    private double[][] mMap;
    private int mMaxX;
    private int mMaxY;

    public HeightInfo(int mMaxX, int mMaxY, double[][] mMap) {
        this.mMap = mMap;
        this.mMaxX = mMaxX;
        this.mMaxY = mMaxY;
    }

    double getState(int x, int y) {
        if (x >= mMaxX || x < 0)
            return 0.0;
        if (y >= mMaxY || y < 0)
            return 0.0;
        return mMap[x][y];
    }
}
