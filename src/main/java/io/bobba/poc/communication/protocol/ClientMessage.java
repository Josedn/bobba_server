package io.bobba.poc.communication.protocol;

public class ClientMessage {
    private final char SEPARATOR = '|';
    private String body;
    private String[] tokens;
    private int pointer;
    private int id;

    public ClientMessage(String message) throws NumberFormatException {
        this.pointer = 0;
        this.id = -1;
        this.body = message;
        tokens = body.split("\\" + SEPARATOR);

        try {
            this.id = Integer.parseInt(popToken());
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public int getId() {
        return this.id;
    }

    private String popToken() {
        if (tokens.length > pointer) {
            return tokens[pointer++];
        }
        return "";
    }

    public int popInt() {
        try {
            return Integer.parseInt(popToken());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String popString() {
        int tickets = popInt();
        String totalString = popToken();
        for (int i = 0; i < tickets; i++) {
            totalString += SEPARATOR + popToken();
        }
        return totalString;
    }
}
