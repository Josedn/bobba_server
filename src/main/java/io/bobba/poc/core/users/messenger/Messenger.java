package io.bobba.poc.core.users.messenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.outgoing.messenger.MessengerFriendsComposer;
import io.bobba.poc.communication.outgoing.messenger.MessengerMessageComposer;
import io.bobba.poc.communication.outgoing.messenger.MessengerRequestsComposer;
import io.bobba.poc.communication.outgoing.messenger.MessengerSearchResultComposer;
import io.bobba.poc.communication.outgoing.messenger.MessengerUpdateFriendComposer;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.users.User;

public class Messenger {
	private List<MessengerRequest> friends;
	private List<MessengerRequest> requests;
	private User user;

	public Messenger(User user) {
		this.user = user;
		this.friends = new ArrayList<>();
		this.requests = new ArrayList<>();
	}
	
	public void addHardFriendship(User user) {
		user.getMessenger().addFriendship(this.user);
		addFriendship(user);
		if (user.isConnected()) {
			user.getMessenger().serializeFriends();
		}
	}
	
	public void notifyDisconnection() {
		for (MessengerRequest friend : new ArrayList<>(this.friends)) {
			User otherUser = friend.getTo();
			if (friend.getTo() == this.user) {
				otherUser = friend.getFrom();
			}
			
			if (otherUser.isConnected()) {
				otherUser.getClient().sendMessage(new MessengerUpdateFriendComposer(this.user));
			}
		}
	}

	public void handleAcceptFriend(int userId) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && !isFriendsWith(user) && hasRequestFrom(user)) {
			removeFriendRequest(user);
			user.getMessenger().addFriendship(this.user);
			addFriendship(user);
			serializeFriends();
			user.getMessenger().serializeFriends();
		}
	}

	public void handleAskForFriend(int userId) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && !isFriendsWith(user)) {
			if (hasRequestFrom(user)) {
				removeFriendRequest(user);
				user.getMessenger().removeFriendRequest(this.user);
				user.getMessenger().addFriendship(this.user);
				addFriendship(user);

				serializeFriends();
				user.getMessenger().serializeFriends();
			} else {
				user.getMessenger().addFriendRequest(this.user);
				user.getMessenger().serializeRequests();
			}
		}
	}

	private MessengerRequest getRequest(User otherUser) {
		for (MessengerRequest request : new ArrayList<>(requests)) {
			if (request.getFrom() == otherUser && request.getTo() == this.user) {
				return request;
			}
			if (request.getTo() == otherUser && request.getFrom() == this.user) {
				return request;
			}
		}
		return null;
	}

	private MessengerRequest getFriendship(User otherUser) {
		for (MessengerRequest request : new ArrayList<>(friends)) {
			if (request.getFrom() == otherUser && request.getTo() == this.user) {
				return request;
			}
			if (request.getTo() == otherUser && request.getFrom() == this.user) {
				return request;
			}
		}
		return null;
	}

	private boolean isFriendsWith(User otherUser) {
		return getFriendship(otherUser) != null;
	}

	private boolean hasRequestFrom(User otherUser) {
		return getRequest(otherUser) != null;
	}

	private void addFriendRequest(User otherUser) {
		if (!isFriendsWith(otherUser) && !hasRequestFrom(otherUser)) {
			this.requests.add(new MessengerRequest(otherUser, this.user));
		}
	}

	private void removeFriendRequest(User otherUser) {
		MessengerRequest request = getRequest(otherUser);
		if (request != null) {
			requests.remove(request);
		}
	}

	private void removeFriendship(User otherUser) {
		MessengerRequest request = getFriendship(otherUser);
		if (request != null) {
			friends.remove(request);
		}
	}

	private void addFriendship(User otherUser) {
		if (!isFriendsWith(otherUser)) {
			this.friends.add(new MessengerRequest(otherUser, this.user));
		}
	}

	public void handleDenyFriend(int userId) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && hasRequestFrom(user)) {
			removeFriendRequest(user);
		}
	}

	public void handleFollowFriend(int userId) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && isFriendsWith(user) && user.isConnected()) {
			Room currentRoom = user.getCurrentRoom();
			if (currentRoom != null) {
				BobbaEnvironment.getGame().getRoomManager().prepareRoomForUser(this.user,
						currentRoom.getRoomData().getId(), "");
			}
		}
	}

	public void serializeFriends() {
		List<User> users = new ArrayList<>();
		for (MessengerRequest friend : new ArrayList<>(this.friends)) {
			if (friend.getTo() == this.user) {
				users.add(friend.getFrom());
			} else {
				users.add(friend.getTo());
			}
		}
		this.user.getClient().sendMessage(new MessengerFriendsComposer(users));
	}

	private void serializeRequests() {
		List<User> users = new ArrayList<>();
		for (MessengerRequest friend : new ArrayList<>(this.requests)) {
			if (friend.getTo() == this.user) {
				users.add(friend.getFrom());
			} else {
				users.add(friend.getTo());
			}
		}
		this.user.getClient().sendMessage(new MessengerRequestsComposer(users));
	}

	public void handleLoadFriends() {
		serializeFriends();
		serializeRequests();
	}

	public void handleRemoveFriend(int userId) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && isFriendsWith(user)) {
			removeFriendship(user);
			user.getMessenger().removeFriendship(this.user);
			user.getMessenger().serializeFriends();
			serializeFriends();
		}
	}

	public void handleSearchFriends(String search) {
		this.user.getClient().sendMessage(new MessengerSearchResultComposer(Arrays.asList()));
	}

	public void handleMessengerMessage(int userId, String text) {
		User user = BobbaEnvironment.getGame().getUserManager().getUser(userId);
		if (user != null && isFriendsWith(user) && user.isConnected()) {
			user.getClient().sendMessage(new MessengerMessageComposer(this.user.getId(), text, false));
			this.user.getClient().sendMessage(new MessengerMessageComposer(user.getId(), text, true));
		}
	}
}
