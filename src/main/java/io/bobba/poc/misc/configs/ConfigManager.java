package io.bobba.poc.misc.configs;

public class ConfigManager extends Config {
    private ConfigFile configFile;

    @Override
    public void setPort(String port) {
        super.setPort(String.valueOf(checkInteger(port, 1232)));
        setFileFromConfig();
    }

    @Override
    public void setLogLevel(String logLevel) {
        super.setLogLevel(logLevel);
        setFileFromConfig();
    }

    @Override
    public void setSslEnabled(String sslEnabled) {
        super.setSslEnabled(sslEnabled);
        setFileFromConfig();
    }

    @Override
    public void setSslStoreType(String ssl) {
        super.setSslStoreType(ssl);
        setFileFromConfig();
    }

    @Override
    public void setSslStorePassword(String ssl) {
        super.setSslStorePassword(ssl);
        setFileFromConfig();
    }

    @Override
    public void setSslKeyPassword(String ssl) {
        super.setSslKeyPassword(ssl);
        setFileFromConfig();
    }
    @Override
    public void setSslStoreFile(String ssl) {
        super.setSslStoreFile(ssl);
        setFileFromConfig();
    }
    private static int checkInteger(String data, int failsafe) {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return failsafe;
        }
    }

    private void setConfigFromFile() {
        configFile.readFile();
        if (configFile.getProp("port") != null) {
            super.setPort(configFile.getProp("port"));
            super.setLogLevel(configFile.getProp("logLevel"));
            super.setSslEnabled(configFile.getProp("sslEnabled"));
            super.setSslStoreType(configFile.getProp("sslStoreType"));
            super.setSslStorePassword(configFile.getProp("sslStorePassword"));
            super.setSslKeyPassword(configFile.getProp("sslKeyPassword"));
            super.setSslStoreFile(configFile.getProp("sslStoreFile"));
        }
    }

    private void setFileFromConfig() {
        configFile.setProp("port", getPort());
        configFile.setProp("logLevel", getLogLevel());
        configFile.setProp("sslEnabled", getSslEnabled());
        configFile.setProp("sslStoreType", getSslStoreType());
        configFile.setProp("sslStorePassword", getSslStorePassword());
        configFile.setProp("sslKeyPassword", getSslKeyPassword());
        configFile.setProp("sslStoreFile", getSslStoreFile());
        configFile.writeFile();
    }

    public ConfigManager(String filename) {
        super();
        this.configFile = new ConfigFile(filename);
        if (configFile.fileExists()) {
            setConfigFromFile();
        } else {
            setFileFromConfig();
        }
    }

}
