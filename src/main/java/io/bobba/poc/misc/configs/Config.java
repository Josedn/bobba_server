package io.bobba.poc.misc.configs;

public class Config {
    private String port, logLevel, sslEnabled, sslStoreFile, sslStoreType, sslStorePassword, sslKeyPassword, mysqlUser, mysqlPass, mysqlHost, mysqlPort, mysqlDatabase;

    public Config() {
        port = "1232";
        logLevel = "verbose";
        sslEnabled = "false";
        sslStoreFile = "";
        sslStoreType = "";
        sslStorePassword = "";
        sslKeyPassword = "";
        mysqlUser = "root";
        mysqlPass = "";
        mysqlHost = "localhost"; 
        mysqlPort = "3306";
        mysqlDatabase = "bobba";
    }

    public String getMysqlUser() {
		return mysqlUser;
	}

	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}

	public String getMysqlPass() {
		return mysqlPass;
	}

	public void setMysqlPass(String mysqlPass) {
		this.mysqlPass = mysqlPass;
	}

	public String getMysqlHost() {
		return mysqlHost;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}

	public String getMysqlPort() {
		return mysqlPort;
	}

	public void setMysqlPort(String mysqlPort) {
		this.mysqlPort = mysqlPort;
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

	public String getMysqlDatabase() {
		return mysqlDatabase;
	}

	public void setMysqlDatabase(String mysqlDatabase) {
		this.mysqlDatabase = mysqlDatabase;
	}
}
