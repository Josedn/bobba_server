package io.bobba.poc.misc.configs;

public class ConfigManager extends Config {
	private ConfigFile configFile;
	
	@Override
	public void setMysqlDatabase(String mysqlDatabase) {
		super.setMysqlDatabase(mysqlDatabase);
		setFileFromConfig();
	}

	@Override
	public void setMysqlUser(String mysqlUser) {
		super.setMysqlUser(mysqlUser);
		setFileFromConfig();
	}

	@Override
	public void setMysqlPass(String mysqlPass) {
		super.setMysqlPass(mysqlPass);
		setFileFromConfig();
	}

	@Override
	public void setMysqlHost(String mysqlHost) {
		super.setMysqlHost(mysqlHost);
		setFileFromConfig();
	}

	@Override
	public void setMysqlPort(String mysqlPort) {
		super.setMysqlPort(String.valueOf(checkInteger(mysqlPort, 3306)));
		setFileFromConfig();
	}

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
			super.setMysqlHost(configFile.getProp("mysqlHost"));
			super.setMysqlPort(configFile.getProp("mysqlPort"));
			super.setMysqlUser(configFile.getProp("mysqlUser"));
			super.setMysqlPass(configFile.getProp("mysqlPass"));
			super.setMysqlDatabase(configFile.getProp("mysqlDatabase"));
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
		configFile.setProp("mysqlHost", getMysqlHost());
		configFile.setProp("mysqlPort", getMysqlPort());
		configFile.setProp("mysqlUser", getMysqlUser());
		configFile.setProp("mysqlPass", getMysqlPass());
		configFile.setProp("mysqlDatabase", getMysqlDatabase());
		configFile.writeFile();
	}

	public ConfigManager() {
		super();
	}
	
	public boolean initialize(String filename) {
		this.configFile = new ConfigFile(filename);
		if (configFile.fileExists()) {
			setConfigFromFile();
			return true;
		} else {
			setFileFromConfig();
			return false;
		}
	}

}
