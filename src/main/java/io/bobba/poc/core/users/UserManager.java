package io.bobba.poc.core.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.bobba.poc.communication.outgoing.users.LoginOkComposer;
import io.bobba.poc.communication.outgoing.users.UpdateCreditsBalanceComposer;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class UserManager {
	private Map<Integer, User> users;
	private int nextId;
	
	public UserManager() {
		this.users = new HashMap<>();
		this.nextId = 1;
	}
	
	public User getUser(int id) {
		return users.getOrDefault(id, null);
	}
	
	private User addUser(String username, String look, GameClient client) {
		User user = new User(nextId++, username, "I \uD83D\uDC96 bobba", look, client);
		this.users.put(user.getId(), user);
		return user;
	}
	
	private void addDummyFriends(User user) {
		for (User otherUser: new ArrayList<>(users.values())) {
			if (user != otherUser) {
				user.getMessenger().addHardFriendship(otherUser);	
			}
		}
		user.getMessenger().serializeFriends();
	}
	
	public void tryLogin(GameClient client, String username, String look) {
        if (client.getUser() == null) {
        	User user = addUser(username, look, client);
            client.setUser(user);            
            Logging.getInstance().writeLine(client.getUser().getUsername() + " (" + client.getUser().getId() + ") has logged in!", LogLevel.Verbose, this.getClass());           

            client.sendMessage(new LoginOkComposer(user.getId(), user.getUsername(), user.getLook(), user.getMotto()));
            client.sendMessage(new UpdateCreditsBalanceComposer(user.getCredits()));
            
            addDummyFriends(user);
        } else {
            Logging.getInstance().writeLine("Client already logged!", LogLevel.Warning, this.getClass());
            client.stop();
        }
    }
}
