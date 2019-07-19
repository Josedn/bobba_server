package io.bobba.poc.core.rooms.gamemap;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.gamemap.pathfinding.astar.AStar;
import io.bobba.poc.core.rooms.gamemap.pathfinding.astar.ISearchGrid;
import io.bobba.poc.core.rooms.gamemap.pathfinding.astar.Node;
import io.bobba.poc.core.rooms.gamemap.pathfinding.dream.DreamPathfinder;
import io.bobba.poc.core.rooms.gamemap.pathfinding.dream.SquarePoint;
import io.bobba.poc.core.rooms.items.RoomItem;
import io.bobba.poc.core.rooms.users.RoomUser;

public class GameMap implements ISearchGrid {
    private Room room;
    private RoomModel roomModel;
    private boolean diagonalEnabled;
    private Map<Point, List<RoomItem>> coordinatedItems;
    private Map<Point, List<RoomUser>> coordinatedUsers;

    private SqState[][] map;
    private double[][] itemHeightMap;

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public GameMap(Room room, RoomModel roomModel) {
        this.room = room;
        this.roomModel = roomModel;
        this.diagonalEnabled = true;
        this.coordinatedItems = new HashMap<>();
        this.coordinatedUsers = new HashMap<>();
        this.map = new SqState[roomModel.getMapSizeX()][roomModel.getMapSizeY()];
        this.itemHeightMap = new double[roomModel.getMapSizeX()][roomModel.getMapSizeY()];
        generateMaps();
    }

    public void addUserToMap(RoomUser user, Point coord) {
        if (!coordinatedUsers.containsKey(coord)) {
            coordinatedUsers.put(coord, new ArrayList<>());
        }
        if (!coordinatedUsers.get(coord).contains(user)) {
            coordinatedUsers.get(coord).add(user);
        }
    }

    public void addItemToMap(RoomItem item) {
        //Check interaction type

        for (Point coord : item.getCoords()) {
            addCoordinatedItem(item, coord);
            constructMapForItem(item, coord);
        }
    }

    private void constructMapForItem(RoomItem item, Point coord) {
        if (itemHeightMap[coord.x][coord.y] <= item.getTotalHeight()) {  // If it is top of stack
            itemHeightMap[coord.x][coord.y] = item.getTotalHeight();
            if (item.getBaseItem().isWalkable()) {
                map[coord.x][coord.y] = SqState.Walkable;
            } else if (item.getBaseItem().isSeat()) {
                map[coord.x][coord.y] = SqState.WalkableLast;
            } else {
                map[coord.x][coord.y] = SqState.Closed;
            }
            List<RoomUser> usersOnSquare = getRoomUsersForSquare(coord);
            for (RoomUser user : usersOnSquare) {
                user.setCurrentSqState(map[coord.x][coord.y]);
                map[coord.x][coord.y] = SqState.Closed;
            }
        }
    }

    private void addCoordinatedItem(RoomItem item, Point coord) {
        if (!coordinatedItems.containsKey(coord)) {
            coordinatedItems.put(coord, new ArrayList<>());
        }
        if (!coordinatedItems.get(coord).contains(item)) {
            coordinatedItems.get(coord).add(item);
        }
    }

    public void removeItemFromMap(RoomItem item) {
        Map<Point, List<RoomItem>> otherItems = new HashMap<>();

        for (Point coord : item.getCoords()) {
            removeCoordinatedItem(item, coord);
            if (otherItems.containsKey(coord)) {
                otherItems.put(coord, coordinatedItems.get(coord));
            }
            setDefaultValue(coord.x, coord.y);

            List<RoomUser> usersOnSquare = getRoomUsersForSquare(coord);
            for (RoomUser user : usersOnSquare) {
                user.setCurrentSqState(map[coord.x][coord.y]);
                map[coord.x][coord.y] = SqState.Closed; //Maybe not always has to be Closed
            }
        }

        for (Point coord : otherItems.keySet()) {
            List<RoomItem> itemsToAdd = otherItems.get(coord);
            if (itemsToAdd != null) {
                for (RoomItem otherItem : itemsToAdd) {
                    constructMapForItem(otherItem, coord);
                }
            }
        }
    }

    private void setDefaultValue(int x, int y) {
        if (roomModel.getSqState()[x][y] == SqState.Walkable)
            map[x][y] = SqState.Walkable;
        else
            map[x][y] = SqState.Closed;
        if (x == roomModel.getDoorX() && y == roomModel.getDoorY()) {
            map[x][y] = SqState.WalkableLast;
        }
        itemHeightMap[x][y] = 0;
    }

    private void removeCoordinatedItem(RoomItem item, Point coord) {
        if (coordinatedItems.containsKey(coord)) {
            coordinatedItems.get(coord).remove(item);
        }
    }

    public void updateUserMovement(Point oldCoord, Point newCoord, RoomUser user) {
        removeUserFromMap(user, oldCoord);
        if (oldCoord.x != roomModel.getDoorX() || oldCoord.y != roomModel.getDoorY()) {
            map[oldCoord.x][oldCoord.y] = user.getCurrentSqState(); // EXPERIMENTAL
        }
        user.setCurrentSqState(map[newCoord.x][newCoord.y]); // EXPERIMENTAL
        if (newCoord.x != roomModel.getDoorX() || newCoord.y != roomModel.getDoorY()) {
            map[newCoord.x][newCoord.y] = SqState.Closed; // EXPERIMENTAL
        }

        addUserToMap(user, newCoord);
    }

    private void removeUserFromMap(RoomUser user, Point coord) {
        if (coordinatedUsers.containsKey(coord)) {
            coordinatedUsers.get(coord).remove(user);
        }
    }

    public void removeUserFromMap(RoomUser user) {
        map[user.getX()][user.getY()] = user.getCurrentSqState(); //May be wrong
        removeUserFromMap(user, new Point(user.getX(), user.getY()));
    }
    /*
    public boolean canWalk(int x, int y) {
        return !squareGotUsers(x, y);
    }*/

    public boolean squareGotUsers(int x, int y) {
        return getRoomUsersForSquare(new Point(x, y)).size() > 0;
    }

    public List<RoomUser> getRoomUsersForSquare(Point coord) {
        if (coordinatedUsers.containsKey(coord)) {
            return coordinatedUsers.get(coord);
        }
        return new ArrayList<>();
    }

    public Point getUserNextStep(int currentX, int currentY, int targetX, int targetY) {
        Node initialNode = new Node(currentX, currentY);
        Node finalNode = new Node(targetX, targetY);
        if (!initialNode.equals(finalNode)) {
            AStar aStar = new AStar(roomModel.getMapSizeX(), roomModel.getMapSizeY(), initialNode, finalNode, diagonalEnabled, this);
            List<Node> path = aStar.findPath();
            if (path.size() > 1) {
                return new Point(new Point(path.get(1).getRow(), path.get(1).getCol()));
            }
        }
        //Path is closed... then try heuristic walking
        SquarePoint point = DreamPathfinder.getNextStep(currentX, currentY, targetX, targetY, map, itemHeightMap, roomModel.getMapSizeX(), roomModel.getMapSizeY(), false, diagonalEnabled);
        return new Point(point.getX(), point.getY());
    }

    public List<RoomItem> getCoordinatedHeighestItems(Point coord) {
        if (!coordinatedItems.containsKey(coord)) {
            return new ArrayList<>();
        }
        List<RoomItem> items = coordinatedItems.get(coord);
        if (items.size() == 1) {
            return new ArrayList<>(items);
        }

        List<RoomItem> returnItems = new ArrayList<>();
        double heighest = -1; //TODO: Find a better way
        for (RoomItem item : items) {
            if (item.getTotalHeight() > heighest) {
                heighest = item.getTotalHeight(); //TODO: It may be getZ() or getTotalHeight()
                returnItems.clear();
                returnItems.add(item);
            } else if (item.getTotalHeight() == heighest) {
                returnItems.add(item);
            }
        }

        return returnItems;
    }

    public List<RoomItem> getRoomItemsForSquare(Point coord) {
        if (coordinatedItems.containsKey(coord)) {
            return coordinatedItems.get(coord);
        }
        return new ArrayList<>();
    }

    public void generateMaps() {
        map = new SqState[roomModel.getMapSizeX()][roomModel.getMapSizeY()];
        itemHeightMap = new double[roomModel.getMapSizeX()][roomModel.getMapSizeY()];

        for (int i = 0; i < roomModel.getMapSizeX(); i++) {
            for (int j = 0; j < roomModel.getMapSizeY(); j++) {
                setDefaultValue(i, j);
            }
        }

        List<RoomUser> users = room.getRoomUserManager().getUsers(); //Are there concurrency problems??1
        for (RoomUser user : users) {
            user.setCurrentSqState(map[user.getX()][user.getY()]);
            map[user.getX()][user.getY()] = SqState.Closed;
        }

        List<RoomItem> items = room.getRoomItemManager().getFloorItems();
        for (RoomItem item : items) {
            addItemToMap(item);
        }

        map[roomModel.getDoorX()][roomModel.getDoorY()] = SqState.WalkableLast;
    }

    public static int calculateRotation(int x1, int y1, int x2, int y2) {
        int rotation = 0;
        if (x1 > x2 && y1 > y2) {
            rotation = 7;
        } else if (x1 < x2 && y1 < y2) {
            rotation = 3;
        } else if (x1 > x2 && y1 < y2) {
            rotation = 5;
        } else if (x1 < x2 && y1 > y2) {
            rotation = 1;
        } else if (x1 > x2) {
            rotation = 6;
        } else if (x1 < x2) {
            rotation = 2;
        } else if (y1 < y2) {
            rotation = 4;
        } else if (y1 > y2) {
            rotation = 0;
        }
        return rotation;
    }

    public double sqAbsoluteHeight(Point coord) {
        if (coordinatedItems.containsKey(coord)) {
            List<RoomItem> items = coordinatedItems.get(coord);
            double highestStack = 0;

            boolean deduct = false;
            double deductable = 0.0;

            for (RoomItem item : items) {
                if (item.getTotalHeight() > highestStack) {
                    if (item.getBaseItem().isSeat()) {
                        deduct = true;
                        deductable = item.getBaseItem().getZ();
                    } else {
                        deduct = false;
                    }
                    highestStack = item.getTotalHeight();
                }
            }

            //double floorHeight = 0.0; // Always 0 cuz no model heightmap // Model.SqFloorHeight[X, Y];
            double floorHeight = getRoomModel().getSqFloorHeight()[coord.x][coord.y];
            double stackHeight = highestStack - floorHeight;

            if (deduct)
                stackHeight -= deductable;
            if (stackHeight < 0)
                stackHeight = 0;
            return floorHeight + stackHeight;
        }
        return getRoomModel().getSqFloorHeight()[coord.x][coord.y];
    }

    public static List<Point> getAffectedTiles(int x, int y, int posX, int posY, int rotation) {
        List<Point> pointList = new ArrayList<>();
        if (y > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < y; i++) {
                    pointList.add(new Point(posX, posY + i));

                    for (int j = 1; j < x; j++) {
                        pointList.add(new Point(posX + j, posY + i));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < y; i++) {
                    pointList.add(new Point(posX + i, posY));

                    for (int j = 1; j < x; j++) {
                        pointList.add(new Point(posX + i, posY + j));
                    }
                }
            }
        }

        if (x > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < x; i++) {
                    pointList.add(new Point(posX + i, posY));

                    for (int j = 1; j < y; j++) {
                        pointList.add(new Point(posX + i, posY + j));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < x; i++) {
                    pointList.add(new Point(posX, posY + i));

                    for (int j = 1; j < y; j++) {
                        pointList.add(new Point(posX + j, posY + i));
                    }
                }
            }
        }
        return pointList;
    }

    public boolean canWalkTo(int x, int y) {
        return x >= 0 && y >= 0 && x < roomModel.getMapSizeX() && y < roomModel.getMapSizeY() && map[x][y] != SqState.Closed;
    }

    public SqState[][] getMap() {
        return map;
    }

    @Override
    public boolean isBlocked(int x, int y) {
        return map[x][y] != SqState.Walkable;
    }
}
