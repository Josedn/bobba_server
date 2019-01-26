package io.bobba.poc.misc.logging;

public class Logging {
    private static Logging instance;

    private LogLevel logLevel;

    private Logging(LogLevel logLevel) {
        this.setLogLevel(logLevel);
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void writeLine(Object o, LogLevel logLevel) {
        if (this.logLevel.ordinal() <= logLevel.ordinal()) {
            System.out.println("[" + logLevel.toString().toUpperCase() + "] - " + o);
        }
    }

    public static Logging getInstance() {
        if (instance == null) {
            instance = new Logging(LogLevel.Verbose);
        }
        return instance;
    }
}
