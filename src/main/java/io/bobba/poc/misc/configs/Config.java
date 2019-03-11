package io.bobba.poc.misc.configs;

public class Config {
    private String port, logLevel, sslEnabled, sslStoreFile, sslStoreType, sslStorePassword, sslKeyPassword;

    public Config() {
        port = "1232";
        logLevel = "verbose";
        sslEnabled = "false";
        sslStoreFile = "";
        sslStoreType = "";
        sslStorePassword = "";
        sslKeyPassword = "";
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(String sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getSslStoreType() {
        return sslStoreType;
    }

    public void setSslStoreType(String sslStoreType) {
        this.sslStoreType = sslStoreType;
    }

    public String getSslStorePassword() {
        return sslStorePassword;
    }

    public void setSslStorePassword(String sslStorePassword) {
        this.sslStorePassword = sslStorePassword;
    }

    public String getSslKeyPassword() {
        return sslKeyPassword;
    }

    public void setSslKeyPassword(String sslKeyPassword) {
        this.sslKeyPassword = sslKeyPassword;
    }

    public String getSslStoreFile() {
        return sslStoreFile;
    }

    public void setSslStoreFile(String sslStoreFile) {
        this.sslStoreFile = sslStoreFile;
    }
}
