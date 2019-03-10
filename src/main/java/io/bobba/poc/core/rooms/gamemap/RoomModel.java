package io.bobba.poc.core.rooms.gamemap;

public class RoomModel {
    public int maxX = 9;
    public int maxY = 13;

    public int doorX = 0;
    public int doorY = 4;

    public double doorZ = 0.0;
    public int doorRot = 2;

    public int[][] map;

    public RoomModel()
    {
        map = new int[maxX][maxY];
        for (int i = 1; i < maxX; i++)
        {
            for (int j = 0; j < maxY; j++)
            {
                map[i][j] = 1;
            }
        }
        map[doorX][doorY] = 1;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getDoorX() {
        return doorX;
    }

    public int getDoorY() {
        return doorY;
    }

    public double getDoorZ() {
        return doorZ;
    }

    public int getDoorRot() {
        return doorRot;
    }

    public int[][] getMap() {
        return map;
    }
}
