package io.bobba.poc.core.rooms.users;

import java.awt.Point;
import java.util.List;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.core.catalogue.Catalogue;
import io.bobba.poc.core.catalogue.CatalogueItem;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.misc.TextHandling;
import io.bobba.poc.misc.logging.Logging;

public class SimpleChatCommandHandler {
    private SimpleChatCommandHandler() {

    }

    public static boolean parse(RoomUser currentUser, String input) {
        String[] args = input.split(" ");

        if (currentUser == null || currentUser.getUser() == null || currentUser.getUser().getClient() == null || currentUser.getRoom() == null)
            return false;
        RoomUser targetRoomUser = null;

        try {
            switch (args[0].toLowerCase()) {
                case "sit": {
                    if (!currentUser.hasStatus("sit")) {
                        if (currentUser.getRot() % 2 == 1) {
                            currentUser.setRot(currentUser.getRot() - 1);
                        }
                        currentUser.addStatus("sit", "0.55");
                        currentUser.setNeedsUpdate(true);
                    } else {
                        currentUser.getRoom().getRoomUserManager().updateUserStatus(currentUser);
                    }
                }
                return true;

                case "maps": {
                    currentUser.getRoom().getGameMap().generateMaps();
                    return true;
                }

                case "pickall": {
                    currentUser.getRoom().getRoomItemManager().handlePickAll(currentUser);
                    return true;
                }
                case "coords": {
                    currentUser.chat("My coords: " + currentUser.getX() + ", " + currentUser.getY() + ", " + TextHandling.getFloatString(currentUser.getZ()) + ", Rot: " + currentUser.getRot());
                    return true;
                }

                case "spawn": {
                    String itemName = args[1];
                    BaseItem item = BobbaEnvironment.getGame().getItemManager().findItem(itemName);
                    if (item != null) {
                        int rot = currentUser.getRot();
                        if (!item.getDirections().contains(rot))
                            rot = item.getDirections().get(0);
                        currentUser.getRoom().getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), currentUser.getX(), currentUser.getY(), currentUser.getZ(), rot, 0, item);
                    }
                    return true;
                }
                
                case "buy": {
                	 String itemName = args[1];
                     CatalogueItem item = BobbaEnvironment.getGame().getCatalogue().findItem(itemName);
                     if (item != null) {
                    	 BobbaEnvironment.getGame().getCatalogue().handlePurchase(currentUser.getUser(), item.getPageId(), item.getId());
                    	 return true;
                     }
                     return false;
                }

                case "pull": {
                    Point point = null;
                    if (currentUser.getRot() == 4) {
                        point = new Point(currentUser.getX(), currentUser.getY() + 2);
                    }
                    if (currentUser.getRot() == 0) {
                        point = new Point(currentUser.getX(), currentUser.getY() - 2);
                    }
                    if (currentUser.getRot() == 6) {
                        point = new Point(currentUser.getX() - 2, currentUser.getY());
                    }
                    if (currentUser.getRot() == 2) {
                        point = new Point(currentUser.getX() + 2, currentUser.getY());
                    }
                    if (point != null) {
                        List<RoomUser> userList = currentUser.getRoom().getGameMap().getRoomUsersForSquare(point);
                        if (userList.size() > 0) {
                            targetRoomUser = userList.get(0);
                        }
                    }
                    if (targetRoomUser != null) {
                        if (currentUser.getRot() == 0) {
                            targetRoomUser.moveTo(targetRoomUser.getX(), targetRoomUser.getY() + 1);
                        }
                        if (currentUser.getRot() == 4) {
                            targetRoomUser.moveTo(targetRoomUser.getX(), targetRoomUser.getY() - 1);
                        }
                        if (currentUser.getRot() == 2) {
                            targetRoomUser.moveTo(targetRoomUser.getX() - 1, targetRoomUser.getY());
                        }
                        if (currentUser.getRot() == 6) {
                            targetRoomUser.moveTo(targetRoomUser.getX() + 1, targetRoomUser.getY());
                        }
                        currentUser.chat("*pulls " + targetRoomUser.getUser().getUsername() + "*");
                    }
                    return true;
                }

                case "push": {
                    if (args.length > 1) {
                        targetRoomUser = currentUser.getRoom().getRoomUserManager().getUser(args[1]);
                    } else {

                        Point point = null;
                        if (currentUser.getRot() == 4) {
                            point = new Point(currentUser.getX(), currentUser.getY() + 1);
                        }
                        if (currentUser.getRot() == 0) {
                            point = new Point(currentUser.getX(), currentUser.getY() - 1);
                        }
                        if (currentUser.getRot() == 6) {
                            point = new Point(currentUser.getX() - 1, currentUser.getY());
                        }
                        if (currentUser.getRot() == 2) {
                            point = new Point(currentUser.getX() + 1, currentUser.getY());
                        }
                        if (point != null) {
                            List<RoomUser> userList = currentUser.getRoom().getGameMap().getRoomUsersForSquare(point);
                            if (userList.size() > 0) {
                                targetRoomUser = userList.get(0);
                            }
                        }
                    }
                    if (targetRoomUser != null) {
                        if ((targetRoomUser.getX() == currentUser.getX() - 1) || (targetRoomUser.getX() == currentUser.getX() + 1) || (targetRoomUser.getY() == currentUser.getY() - 1) || (targetRoomUser.getY() == currentUser.getY() + 1)) {
                            if (currentUser.getRot() == 4) {
                                targetRoomUser.moveTo(targetRoomUser.getX(), targetRoomUser.getY() + 1);
                            }

                            if (currentUser.getRot() == 0) {
                                targetRoomUser.moveTo(targetRoomUser.getX(), targetRoomUser.getY() - 1);
                            }

                            if (currentUser.getRot() == 6) {
                                targetRoomUser.moveTo(targetRoomUser.getX() - 1, targetRoomUser.getY());
                            }

                            if (currentUser.getRot() == 2) {
                                targetRoomUser.moveTo(targetRoomUser.getX() + 1, targetRoomUser.getY());
                            }

                            if (currentUser.getRot() == 3) {
                                targetRoomUser.moveTo(targetRoomUser.getX() + 1, targetRoomUser.getY() + 1);
                            }

                            if (currentUser.getRot() == 1) {
                                targetRoomUser.moveTo(targetRoomUser.getX() + 1, targetRoomUser.getY() - 1);
                            }

                            if (currentUser.getRot() == 7) {
                                targetRoomUser.moveTo(targetRoomUser.getX() - 1, targetRoomUser.getY() - 1);
                            }

                            if (currentUser.getRot() == 5) {
                                targetRoomUser.moveTo(targetRoomUser.getX() - 1, targetRoomUser.getY() + 1);
                            }

                            currentUser.chat("*pushes " + targetRoomUser.getUser().getUsername() + "*");
                        }
                    }
                    return true;
                }
            }

        } catch (Exception e) {
            Logging.getInstance().logError("Error handling command", e, SimpleChatCommandHandler.class);
        }

        return false;
    }
}
