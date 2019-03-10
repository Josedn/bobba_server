package io.bobba.poc.core.rooms.items.interactors;

import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.core.rooms.items.RoomItem;

public class InteractorGenericSwitch extends  RoomItemInteractor {

    public InteractorGenericSwitch(RoomItem item) {
        super(item);
    }

    @Override
    public void onTrigger(RoomUser roomUser, boolean userHasRights) {
        if (getItem().getState() + 1 < getItem().getBaseItem().getStates()) {
            getItem().setState(getItem().getState() + 1);
        } else {
            getItem().setState(0);
        }
        getItem().updateState();
    }
}
