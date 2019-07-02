package io.bobba.poc.communication.protocol;

public abstract class ServerMessage {
	private final char SEPARATOR = '|';
	private String body;

	public ServerMessage(int id) {
		this.body = String.valueOf(id);
	}

	private void appendToken(String token) {
		body += SEPARATOR + token;
	}

	public void appendInt(int i) {
		appendToken(String.valueOf(i));
	}
	
	public void appendBoolean(boolean b) {
		appendInt(b ? 1 : 0);
	}

	public void appendFloat(double d) {
		appendToken(String.valueOf(d).replace(',', '.'));
	}

	public void appendString(String str) {
		int tickets = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == SEPARATOR)
				tickets++;
		}
		appendInt(tickets);
		appendToken(str);
	}

	@Override
	public String toString() {
		return this.body;
	}
}