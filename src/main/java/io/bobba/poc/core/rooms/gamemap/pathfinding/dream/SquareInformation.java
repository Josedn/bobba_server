package io.bobba.poc.core.rooms.gamemap.pathfinding.dream;

public class SquareInformation {

    //private int mX;
    //private int mY;

    private SquarePoint[] mPos;

    //private SquarePoint mTarget;
    private SquarePoint mPoint;

    public SquareInformation(int pX, int pY, SquarePoint pTarget, ModelInfo pMap, boolean pUserOverride, boolean CalculateDiagonal) {
        //mX = pX;
        //mY = pY;
        //mTarget = pTarget;

        mPoint = new SquarePoint(pX, pY, pTarget.getX(), pTarget.getY(), pMap.getState(pX, pY), pUserOverride);
        //Analyze all the squares arround the user
        mPos = new SquarePoint[8];

        if (CalculateDiagonal) {
            mPos[1] = new SquarePoint(pX - 1, pY - 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX - 1, pY - 1), pUserOverride);
            mPos[3] = new SquarePoint(pX - 1, pY + 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX - 1, pY + 1), pUserOverride);
            mPos[5] = new SquarePoint(pX + 1, pY + 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX + 1, pY + 1), pUserOverride);
            mPos[7] = new SquarePoint(pX + 1, pY - 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX + 1, pY - 1), pUserOverride);
        }

        mPos[0] = new SquarePoint(pX, pY - 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX, pY - 1), pUserOverride);
        mPos[2] = new SquarePoint(pX - 1, pY, pTarget.getX(), pTarget.getY(), pMap.getState(pX - 1, pY), pUserOverride);
        mPos[4] = new SquarePoint(pX, pY + 1, pTarget.getX(), pTarget.getY(), pMap.getState(pX, pY + 1), pUserOverride);
        mPos[6] = new SquarePoint(pX + 1, pY, pTarget.getX(), pTarget.getY(), pMap.getState(pX + 1, pY), pUserOverride);
    }

    public SquarePoint getPoint() {
        return mPoint;
    }

    public SquarePoint pos(int val)
    {
        return mPos[val];
    }
}
