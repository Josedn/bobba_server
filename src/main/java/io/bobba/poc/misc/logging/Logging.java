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

    public boolean canLog(LogLevel logLevel) {
        return this.logLevel.ordinal() <= logLevel.ordinal();
    }

    public void writeLine(Object o, LogLevel logLevel, Class tag) {
        if (canLog(logLevel)) {
            System.out.println("[" + logLevel.toString().toUpperCase() + "][" + tag.getName() + "] - " + o);
        }
    }

    @Deprecated
    public void writeLine(Object o, LogLevel logLevel) {
        if (canLog(logLevel)) {
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
