package io.bobba.poc.core.rooms.gamemap.pathfinding.dream;

import io.bobba.poc.core.rooms.gamemap.SqState;

public class DreamPathfinder {

    public static SquarePoint getNextStep(int pUserX, int pUserY,
                                          int pUserTargetX, int pUserTargetY,
                                          SqState[][] pGameMap, double[][] pHeight,
                                          int MaxX, int MaxY,
                                          boolean pUserOverride, boolean pDiagonal) {
        ModelInfo mapInfo = new ModelInfo(MaxX, MaxY, pGameMap);
        SquarePoint targetPoint = new SquarePoint(pUserTargetX, pUserTargetY, pUserTargetX, pUserTargetY, mapInfo.getState(pUserTargetX, pUserTargetY), pUserOverride);

        if (pUserX == pUserTargetX && pUserY == pUserTargetY) //User is already standing on its target
            return targetPoint;

        SquareInformation squareOnUser = new SquareInformation(pUserX, pUserY, targetPoint, mapInfo, pUserOverride, pDiagonal);

        //if (!TargetPoint.CanWalk)
        //    return SquareOnUser.Point;

        return getClosestSquare(squareOnUser, new HeightInfo(MaxX, MaxY, pHeight));
    }

    private static SquarePoint getClosestSquare(SquareInformation pInfo, HeightInfo height) {
        double closest = pInfo.getPoint().getDistance(); //Initialized

        SquarePoint closestPoint = pInfo.getPoint();

        double infoOnSquare = height.getState(pInfo.getPoint().getX(), pInfo.getPoint().getY());

        for (int i = 0; i < 8; i++) {
            SquarePoint position = pInfo.pos(i);
            if (position == null || !position.isInUse())
                continue;

            if (position.canWalk()) {
                if (height.getState(position.getX(), position.getY()) - infoOnSquare < 3) {
                    double distance = position.getDistance();
                    if (closest > distance) {
                        closest = distance;
                        closestPoint = position;
                    }
                }
            }
        }
        return closestPoint;
    }

    public static double GetDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
