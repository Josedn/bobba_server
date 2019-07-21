package io.bobba.poc.communication.protocol;

public class ServerOpCodes {
    public final static int LOGIN_OK = 3;
    public final static int PLAYERS_DATA = 6;
    public final static int PLAYER_STATUS = 8;
    public final static int CHAT = 10;
    public final static int PLAYER_REMOVE = 11;
    public final static int PLAYER_WAVE = 14;
    public final static int ROOM_ITEM_DATA = 16;
    public final static int ITEM_REMOVE = 17;
    public final static int ITEM_STATE = 19;
    public final static int WALL_ITEM_DATA = 20;
    public final static int INVENTORY_ITEMS = 21;
    public final static int INVENTORY_ITEM_REMOVE = 22;
    public final static int CATALOGUE_INDEX = 23;
    public final static int CATALOGUE_PAGE = 24;
    public final static int CATALOGUE_PURCHASE_ERROR = 25;
    public final static int CATALOGUE_PURCHASE_INFO = 26;
    public final static int CREDITS_BALANCE = 27;
    public final static int ROOM_DATA_MODEL_INFO = 28;
    public final static int ROOM_DATA = 29;
    public final static int ROOM_DATA_HEIGHTMAP = 4;
    public final static int NAVIGATOR_ROOM_LIST = 30;
    public final static int NAVIGATOR_LEAVE_ROOM = 31;
    public final static int MESSENGER_FRIENDS = 32;
    public final static int MESSENGER_SEARCH_RESULT = 33;
    public final static int MESSENGER_MESSAGE = 34;
    public final static int MESSENGER_REQUESTS = 35;
    public final static int MESSENGER_UPDATE_FRIEND = 36;

    private ServerOpCodes() {

    }

}
