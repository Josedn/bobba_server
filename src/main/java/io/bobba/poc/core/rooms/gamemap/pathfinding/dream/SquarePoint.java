package io.bobba.poc.core.rooms.gamemap.pathfinding.dream;

import io.bobba.poc.core.rooms.gamemap.SqState;

public class SquarePoint {
    private int x;
    private int y;
    private double distance;
    private SqState squareData;
    private boolean inUse;
    private boolean override;
    private boolean lastStep;

    public SquarePoint(int pX, int pY, int pTargetX, int pTargetY, SqState SquareData, boolean pOverride) {
        x = pX;
        y = pY;
        squareData = SquareData;
        inUse = true;
        override = pOverride;

        distance = 0.0;
        lastStep = (pX == pTargetX && pY == pTargetY);

        distance = DreamPathfinder.GetDistance(pX, pY, pTargetX, pTargetY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isInUse() {
        return inUse;
    }

    public boolean canWalk() {
        if (!lastStep) {
            if (!override) {
                return (squareData == SqState.Walkable);
            } else {
                return true;
            }
        } else {
            if (!override) {
                if (squareData == SqState.WalkableLast)
                    return true;
                if (squareData == SqState.Walkable)
                    return true;
            } else return true;
        }
        return false;
    }
}
