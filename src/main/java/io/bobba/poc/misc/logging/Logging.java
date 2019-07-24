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

    public void writeLine(Object o, LogLevel logLevel, Class<?> tag) {
        if (canLog(logLevel)) {
            System.out.println("[" + logLevel.toString().toUpperCase() + "][" + tag.getName() + "] - " + o);
        }
    }

    public void logError(Object o, Exception e, Class<?> tag) {
        if (canLog(LogLevel.Warning)) {
            writeLine(o + ": " + e.toString(), LogLevel.Warning, tag);
        }
        if (canLog(LogLevel.Debug)) {
            e.printStackTrace();
        }
    }

    public static Logging getInstance() {
        if (instance == null) {
            instance = new Logging(LogLevel.Verbose);
        }
        return instance;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public static LogLevel valueOfLogLevel(String logLevel) {
        for (LogLevel level : LogLevel.values()) {
            if (level.toString().toLowerCase().equals(logLevel.toLowerCase()))
                return level;
        }
        return LogLevel.Verbose;
    }
}
