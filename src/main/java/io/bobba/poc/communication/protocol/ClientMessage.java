package io.bobba.poc.communication.protocol;

import java.util.StringTokenizer;

public class ClientMessage {
    private final String SEPARATOR = "|";
    private String body;
    private StringTokenizer tokenizer;
    private int pointer;
    private int id;

    public ClientMessage(String message) throws NumberFormatException {
        this.pointer = 0;
        this.id = -1;
        this.body = message;
        tokenizer = new StringTokenizer(body, SEPARATOR);

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
        if (tokenizer.hasMoreTokens()) {
            return tokenizer.nextToken();
        }
        return null;
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
