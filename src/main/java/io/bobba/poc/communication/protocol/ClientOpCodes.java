package io.bobba.poc.communication.protocol;

public class ClientOpCodes {
    public final static int LOGIN = 1;
    public final static int REQUEST_MAP = 2;
    public final static int REQUEST_MOVEMENT = 7;
    public final static int REQUEST_CHAT = 9;
    public final static int REQUEST_LOOK_AT = 12;
    public final static int REQUEST_WAVE = 13;
    public final static int REQUEST_ROOM_DATA = 15;
    public final static int REQUEST_ITEM_INTERACT = 18;
    public final static int REQUEST_ITEM_MOVE = 19;
    public final static int REQUEST_ITEM_PICK_UP = 20;
    public final static int REQUEST_CHANGE_LOOKS = 21;
    public final static int REQUEST_CHANGE_MOTTO = 22;
    public final static int REQUEST_INVENTORY_ITEMS = 23;
    public final static int REQUEST_ITEM_PLACE = 24;
    public final static int REQUEST_CATALOGUE_INDEX = 25;
    public final static int REQUEST_CATALOGUE_PAGE = 26;
    public final static int REQUEST_CATALOGUE_PURCHASE = 27;
    public final static int REQUEST_NAVIGATOR_POPULAR_ROOMS = 28;
    public final static int REQUEST_NAVIGATOR_OWN_ROOMS = 29;
    public final static int REQUEST_NAVIGATOR_SEARCH_ROOMS = 30;
    public final static int REQUEST_NAVIGATOR_MAKE_FAVOURITE = 31;
    public final static int REQUEST_NAVIGATOR_REMOVE_FAVOURITE = 32;
    public final static int REQUEST_NAVIGATOR_LEAVE_ROOM = 33;
    public final static int REQUEST_NAVIGATOR_GO_TO_ROOM = 34;
    public final static int REQUEST_NAVIGATOR_CREATE_ROOM = 35;
    public final static int REQUEST_MESSENGER_ACCEPT_FRIEND = 36;
    public final static int REQUEST_MESSENGER_DENY_FRIEND = 37;
    public final static int REQUEST_MESSENGER_FOLLOW_FRIEND = 38;
    public final static int REQUEST_MESSENGER_SEARCH_FRIEND = 39;
    public final static int REQUEST_MESSENGER_SEND_MESSAGE = 40;
    public final static int REQUEST_MESSENGER_REMOVE_FRIEND = 41;
    public final static int REQUEST_MESSENGER_ADD_FRIEND = 42;
    public final static int REQUEST_MESSENGER_LOAD_FRIENDS = 42;
    
    private ClientOpCodes() {

    }
}
